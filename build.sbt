name := "myspark"

scalaVersion := "2.11.7"

version := "1.0"

libraryDependencies += "org.apache.spark" %% "spark-core" % "1.4.1"

javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-Xlint")

initialize := {
  val _ = initialize.value
  if (sys.props("java.specification.version") != "1.8")
    sys.error("Java 8 is required for this project.")
}
