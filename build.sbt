name := "vpn-server"

organization := "im.xun"

version := "1.0"

scalaVersion := "2.11.8"

fork := true
//javaOptions += "-Djna.dump_memory=true"
//scalacOptions += "-Ylog-classpath"

//libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % "test"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.3"
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.4.7"
libraryDependencies += "com.typesafe.akka" %% "akka-slf4j" % "2.4.7"
//libraryDependencies += "com.lihaoyi" %% "sourcecode" % "0.1.1"

