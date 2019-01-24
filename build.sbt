organization in ThisBuild := "com.sonartrading"
version in ThisBuild := "1.0-SNAPSHOT"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.12.4"

lazy val `crypto` = (project in file("."))
  .aggregate(`crypto-api`, `crypto-impl`, `crypto-stream-api`, `crypto-stream-impl`)

lazy val `crypto-api` = (project in file("crypto-api"))
  .settings(common: _*)
  .settings(
    libraryDependencies ++= Seq(
      lagomJavadslApi,
      lombok
    )
  )

lazy val `crypto-impl` = (project in file("crypto-impl"))
  .enablePlugins(LagomJava)
  .settings(common: _*)
  .settings(
    libraryDependencies ++= Seq(
      lagomJavadslPersistenceCassandra,
      lagomJavadslKafkaBroker,
      lagomLogback,
      lagomJavadslTestKit,
      lombok
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`crypto-api`)

lazy val `crypto-stream-api` = (project in file("crypto-stream-api"))
  .settings(common: _*)
  .settings(
    libraryDependencies ++= Seq(
      lagomJavadslApi
    )
  )

lazy val `crypto-stream-impl` = (project in file("crypto-stream-impl"))
  .enablePlugins(LagomJava)
  .settings(common: _*)
  .settings(
    libraryDependencies ++= Seq(
      lagomJavadslPersistenceCassandra,
      lagomJavadslKafkaClient,
      lagomLogback,
      lagomJavadslTestKit
    )
  )
  .dependsOn(`crypto-stream-api`, `crypto-api`)

val lombok = "org.projectlombok" % "lombok" % "1.16.18"

// https://mvnrepository.com/artifact/org.quickfixj/quickfixj-core
libraryDependencies += "org.quickfixj" % "quickfixj-core" % "2.1.1"


def common = Seq(
  javacOptions in compile += "-parameters"
)
