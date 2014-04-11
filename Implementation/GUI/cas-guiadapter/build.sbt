name := "cas-guiadapter"

version := "0.1-SNAPSHOT"

libraryDependencies += "com.rabbitmq" % "amqp-client" % "3.1.4"

libraryDependencies += "com.google.guava" % "guava" % "15.0"

libraryDependencies += "org.apache.camel" % "camel-amqp" % "2.12.1"

libraryDependencies += "org.apache.camel" % "camel-rabbitmq" % "2.12.1"

libraryDependencies += "com.google.protobuf" % "protobuf-java" % "2.5.0"

EclipseKeys.projectFlavor := EclipseProjectFlavor.Java

autoScalaLibrary := false

crossPaths := false

exportJars := true