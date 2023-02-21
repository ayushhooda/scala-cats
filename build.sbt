ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "scala-cats-effect"
  )

lazy val catsCoreVersion = "2.1.1"
lazy val catsEffectVersion = "3.3.14"
lazy val http4sVersion = "0.23.18"
lazy val doobieVersion = "1.0.0-RC2"
lazy val circeVersion = "0.14.3"
lazy val fs2Cassandra = "0.2.1"

addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1")

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % catsCoreVersion,
  "org.typelevel" %% "cats-effect" % catsEffectVersion,
  "org.http4s" %% "http4s-ember-client" % http4sVersion,
  "org.http4s" %% "http4s-ember-server" % http4sVersion,
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-circe" % http4sVersion,
  "org.http4s" %% "http4s-core" % http4sVersion,
  "org.http4s" %% "http4s-client" % http4sVersion,
  "org.http4s" %% "http4s-server" % http4sVersion,
  "org.tpolecat" %% "doobie-core" % doobieVersion,
  // Optional for auto-derivation of JSON codecs
  "io.circe" %% "circe-generic" % circeVersion,
  // Optional for string interpolation to JSON model
  "io.circe" %% "circe-literal" % circeVersion,
  "com.ringcentral" %% "cassandra4io" % "0.1.14",
//  "com.spinoco" %% "fs2-cassandra" % fs2Cassandra,
  "com.datastax.cassandra" % "cassandra-driver-core" % "3.6.0",
  "org.typelevel" %% "munit-cats-effect-3" % "1.0.6" % Test
)

scalacOptions ++= Seq(
  "-language:higherKinds"
)
