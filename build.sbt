val scala3Version = "3.3.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "binaryen4s",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies += "net.java.dev.jna" % "jna" % "5.14.0",

    libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test
  )
