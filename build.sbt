import sbt.Keys.libraryDependencies

import scala.Seq
import scala.util.Try

name := "kafka"

version := "0.1"

scalaVersion := "2.11.12"

javaHome := Option {new File("/Library/Java/JavaVirtualMachines/jdk1.8.0_271.jdk/Contents/Home/bin")}

scalacOptions += "-target:jvm-1.8"

val sparkVersion = "2.4.0"

val liftVersion = "3.3.0"

val log4jVersion = "2.13.3"

val circeVersion = "0.11.2"

val kafkaVersion = "2.5.0"

lazy val rootSettings = Seq(
  publishArtifact := false,
  publishArtifact in Test := false
)

lazy val commonSettings = Seq()

lazy val rootProject = (project in file("."))
  .settings(rootSettings: _*)
  .aggregate(producer, consumer, util)

lazy val util = (project in file("util"))
  .settings(commonSettings: _*)
  .settings(
    name := "util",
    libraryDependencies ++= Seq(
      "org.apache.logging.log4j" % "log4j-api" % log4jVersion,
      "org.apache.logging.log4j" % "log4j-core" % log4jVersion,
      "org.apache.logging.log4j" % "log4j-web" % log4jVersion,
      "org.apache.logging.log4j" % "log4j-slf4j-impl" % log4jVersion,
    )
  )

lazy val producer = (project in file("producer"))
  .settings(commonSettings: _*)
  .dependsOn(util)
  .settings(
    name := "producer",
    mainClass in Compile := Some("com.target.producer.ProducerApp"),
    libraryDependencies ++= Seq(
      "org.apache.logging.log4j" % "log4j-api" % log4jVersion,
      "org.apache.logging.log4j" % "log4j-core" % log4jVersion,
      "org.apache.logging.log4j" % "log4j-web" % log4jVersion,
      "org.apache.kafka" % "kafka-clients" % kafkaVersion,
      "org.apache.kafka" % "kafka-streams" % kafkaVersion
    )
  )

lazy val consumer = (project in file("consumer"))
  .settings(commonSettings: _*)
  .dependsOn(util)
  .settings(
    name := "consumer",
    mainClass in Compile := Some("com.target.consumer.ConsumerApp"),
    libraryDependencies ++= Seq(
      "org.apache.logging.log4j" % "log4j-api" % log4jVersion,
      "org.apache.logging.log4j" % "log4j-core" % log4jVersion,
      "org.apache.logging.log4j" % "log4j-web" % log4jVersion,
      "org.apache.kafka" % "kafka-clients" % kafkaVersion,
      "org.apache.kafka" % "kafka-streams" % kafkaVersion
    )
  )
