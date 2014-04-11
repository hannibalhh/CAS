name := "cas-web"

version := "1.0-SNAPSHOT"

scalaVersion := "2.10.3"

resolvers += "twitter4j.org Repository" at "http://twitter4j.org/maven2"

libraryDependencies += "com.google.protobuf" % "protobuf-java" % "2.4.1"

libraryDependencies += "com.rabbitmq" % "amqp-client" % "3.1.4"

libraryDependencies += "com.googlecode.protobuf-java-format" % "protobuf-java-format" % "1.2"

libraryDependencies += "com.typesafe.akka" % "akka-remote_2.10" % "2.2.0"

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.0.7"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache
)     

play.Project.playScalaSettings
