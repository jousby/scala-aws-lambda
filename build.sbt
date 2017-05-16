// common settings
organization in ThisBuild := "io.github.jousby"
version in ThisBuild := "0.0.1"
scalaVersion in ThisBuild := "2.12.1"
scalacOptions in ThisBuild := Seq("-unchecked", "-feature", "-deprecation", "-encoding", "utf8")

// dependencies
val awsLambdaJavaCore = "com.amazonaws" % "aws-lambda-java-core" % "1.1.0"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.1" % Test
val circeParser = "io.circe" %% "circe-parser" % "0.8.0" % Test

// http library
lazy val `scala-aws-lambda-http` = (project in file("scala-aws-lambda-http"))
  .settings(
    libraryDependencies ++= Seq(
      awsLambdaJavaCore,
      scalaTest,
      circeParser
    )
  )

// demo project that showcases the http library
lazy val `demo` = (project in file("demo"))
  .settings(
    assemblyJarName in assembly := "demo.jar"
  )
  .dependsOn(`scala-aws-lambda-http`)
