ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "scala-cats-effect"
  )

lazy val catsCoreVersion = "2.1.1"
lazy val catsEffectVersion = "3.3.14"

addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1")

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % catsCoreVersion,
  "org.typelevel" %% "cats-effect" % catsEffectVersion,
  "org.typelevel" %% "munit-cats-effect-3" % "1.0.6" % Test
)

scalacOptions ++= Seq(
  "-language:higherKinds"
)
