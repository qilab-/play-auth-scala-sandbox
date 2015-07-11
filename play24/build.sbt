name := """play24-auth-scala-sandbox"""

version := "0.1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "com.github.nscala-time" %% "nscala-time" % "2.0.0",
  "org.skinny-framework" %% "skinny-orm" % "1.3.18",
  "jp.t2v" %% "play2-auth" % "0.14.0",
  "com.h2database" % "h2" % "1.4.187",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "org.scalikejdbc" %% "scalikejdbc-test" % "2.2.1" % "test",
  "jp.t2v" %% "play2-auth-test" % "0.14.0" % "test",
  play.sbt.Play.autoImport.cache
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
