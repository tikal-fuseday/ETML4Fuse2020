name := """ETML"""
organization := "com.tikal"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala) //enable plugin

scalaVersion := "2.12.3"
lazy val mongoVersion = "3.1.1"
lazy val json4sVersion = "3.5.2"


libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.publish.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.publish.binders._"
libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
    guice,
  specs2 % Test,
  "org.specs2" %% "specs2-matcher-extra" % "3.9.5" % Test,
  "com.github.tototoshi" %% "play-json4s-native" % "0.8.0",
  "com.facebook.presto" % "presto-jdbc" % "0.231.1",
  "org.mongodb" %% "casbah-commons" % mongoVersion,
  "org.mongodb" %% "casbah-core" % mongoVersion,
  "org.mongodb" %% "casbah-query" % mongoVersion,
  "org.json4s" %% "json4s-native" % json4sVersion,
  "org.json4s" %% "json4s-jackson" % json4sVersion,
  "org.json4s" %% "json4s-ext" % json4sVersion,
  "org.json4s" %% "json4s-mongo" % json4sVersion ,
  "com.typesafe.scala-logging" % "scala-logging_2.12" % "3.7.2",
  "org.scalacheck" %% "scalacheck" % "1.13.4" % "test",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "org.scalaj" % "scalaj-http_2.12" % "2.3.0",
  "com.stripe" % "stripe-java" % "5.22.1",
  "com.google.api-client" % "google-api-client" % "1.20.0",
//  "org.webjars" % "swagger-ui" % "2.2.0",  //play-swagger ui integration
  "org.postgresql" % "postgresql" % "9.4.1212",
  "com.typesafe.akka" %% "akka-slf4j" % "2.5.8",
  "com.typesafe.akka" %% "akka-stream" % "2.5.8",
  "com.github.firebase4s" %% "firebase4s" % "0.0.4",
  "org.scalacheck" %% "scalacheck" % "1.13.4" % "test",
  "org.specs2" %% "specs2-matcher-extra" % "3.6" % Test,
  "org.specs2" %% "specs2-core" % "3.6" % "test",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "com.google.cloud" % "google-cloud-bigquery" % "1.64.0",
  "com.github.seratch" %% "bigquery4s" % "0.8"



)
// swaggerDomainNameSpaces := Seq("persistence","controllers")
