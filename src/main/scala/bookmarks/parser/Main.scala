package bookmarks.parser

import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

import cats.effect._
import cats.effect.syntax._
import cats.syntax.all._

import scala.io.Source
import scala.io.BufferedSource
import cats.data.EitherT
import java.io.File
import java.net.URI
import cats.syntax.traverse

object Main extends IOApp {

  val source = IO.blocking(Source.fromFile(new File("/Users/lens/Downloads/bookmarks-2024-12-16.json")))
  val resourceReader = Resource.make(source)(src => IO.blocking(src.close()))

  sealed trait Bookmark
  case class Node(
    guid: String,
    id: Long,
    index: Long,
    title: String,
    uri: Option[String],
    tags: Option[String],
    dateAdded: Long,
    lastModified: Long,
    children: Option[List[Node]]
  ) extends Bookmark

  def parseBookmarks(bs: BufferedSource): IO[Either[Error, Node]] = IO {
    decode[Node](bs.mkString)
  }

  def walkNodesTree(n: Node, acc: List[Node]): List[Node] = {
    n.children match {
      case None => acc
      case Some(cl) => cl.flatMap(n => {
        walkNodesTree(n, n :: acc).toSet
      })
    }
  }

  def iterateBookmarks(eitherBookmarks: Either[Error, Node]): IO[List[Node]] = IO {
    eitherBookmarks match {
        case Left(err) => List.empty[Node]
        case Right(bookmarks) => walkNodesTree(bookmarks, List.empty[Node])
    }
  }

  def run(args: List[String]): IO[ExitCode] = for {
    eitherBookmarks <- resourceReader.use(parseBookmarks)
    bookmarksNodes <- iterateBookmarks(eitherBookmarks)
    _ <- bookmarksNodes.traverse(node => IO.println(s"title: ${node.title}"))
    _ <- IO.println(s">>> Total: ${bookmarksNodes.length}")
  } yield ExitCode.Success
}
