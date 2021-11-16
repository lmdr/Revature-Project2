name := "project2"

version := "0.1"

scalaVersion := "2.12.15"

libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.26"
libraryDependencies += "org.apache.spark" %% "spark-core" % "3.2.0"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "3.2.0"
libraryDependencies += "org.apache.spark" %% "spark-hive" % "3.2.0"