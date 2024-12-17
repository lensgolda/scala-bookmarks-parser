ThisBuild / organization := "com.github.lensgolda"
ThisBuild / scalaVersion := "2.13.15"

// // Versions
// lazy val circeVersion = "0.14.1"
// lazy val catsVersion = "3.3.12"

// // Deps
// val circeDeps = Seq(
//   "io.circe" %% "circe-core" % circeVersion,
//   "io.circe" %% "circe-generic" % circeVersion,
//   "io.circe" %% "circe-parser" % circeVersion
// )

// val catsDeps = Seq(
//   // "core" module - IO, IOApp, schedulers
//   // This pulls in the kernel and std modules automatically.
//   "org.typelevel" %% "cats-effect" % catsVersion,
//   // concurrency abstractions and primitives (Concurrent, Sync, Async etc.)
//   "org.typelevel" %% "cats-effect-kernel" % catsVersion,
//   // standard "effect" library (Queues, Console, Random etc.)
//   "org.typelevel" %% "cats-effect-std" % catsVersion,
//   // better monadic for compiler plugin as suggested by documentation
//   compilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1")
// )

// // Projects
// val bookmarkParserDeps = catsDeps ++ circeDeps

lazy val root = (project in file(".")).settings(
  name := "bookmarks-parser",
  libraryDependencies ++= Dependencies.bookmarkParserDeps
    
)
