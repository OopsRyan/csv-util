name := "csv-util"

version := "0.1"

scalaVersion := "2.12.7"

mainClass in assembly := Some("qirun.utils.Main")

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.5"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"

libraryDependencies += "com.typesafe" % "config" % "1.3.3"

libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.24"

libraryDependencies ++= Seq(
  "org.apache.logging.log4j" %% "log4j-api-scala" % "11.0",
  "org.apache.logging.log4j" % "log4j-api" % "2.11.0",
  "org.apache.logging.log4j" % "log4j-core" % "2.11.0" % Runtime
)
