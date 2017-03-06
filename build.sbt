import sbt._
import Keys._
import com.typesafe.sbt.SbtScalariform
import de.heikoseeberger.sbtheader.license.Apache2_0
import scalariform.formatter.preferences._
import com.typesafe.sbt.SbtScalariform

licenses     += ("Apache-2.0", url("http://opensource.org/licenses/apache2.0.php"))
headers      := Map(
  "scala" -> Apache2_0("2016", "Vitor Vieira"),
  "conf"  -> Apache2_0("2016", "Vitor Vieira", "#")
)

lazy val buildSettings = Seq(
  version       := "0.0.1",
  scalaVersion  := "2.12.1",
  organization := "com.vitorsvieira",
  scalacOptions ++= Seq("-unchecked", "-deprecation", "-encoding", "utf8", "-feature", "-language:higherKinds", "-language:implicitConversions", "-Ydelambdafy:method", "-target:jvm-1.8"),
  resolvers     ++= Seq(
    "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/",
    Resolver.sonatypeRepo("snapshots"),
    Resolver.sonatypeRepo("releases"),
    Resolver.bintrayRepo("hseeberger", "maven")
  )
)

val akkaV      = "2.4.17"
val akkaHttpV  = "10.0.4"
val akkaCors   = "0.1.11"
val circeV     = "0.7.0"
val akkaCirceV = "1.12.0"
val scalaTestV = "3.0.1"
val logbackV   = "1.2.1"


lazy val `template` = project
  .in(file("."))
  .settings(buildSettings: _*)
  .settings(mainClass in assembly := Some("com.vitorsvieira.http.server.ServerTemplate"))
  .settings(
    name := "akka-http-circe-json-template",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http"         % akkaHttpV,
      "com.typesafe.akka" %% "akka-slf4j"        % akkaV,
      "de.heikoseeberger" %% "akka-http-circe"   % akkaCirceV,
      "ch.megard"         %% "akka-http-cors"    % akkaCors,
      "ch.qos.logback"    % "logback-classic"    % logbackV,
      "io.circe"          %% "circe-core"        % circeV,
      "io.circe"          %% "circe-generic"     % circeV,
      "io.circe"          %% "circe-jawn"        % circeV,
      "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpV  % "test",
      "org.scalatest"     %% "scalatest"         % scalaTestV % "test"
    )
  )
  .enablePlugins(AutomateHeaderPlugin)

SbtScalariform.autoImport.scalariformPreferences := SbtScalariform.autoImport.scalariformPreferences.value
  .setPreference(AlignSingleLineCaseStatements, true)
  .setPreference(AlignSingleLineCaseStatements.MaxArrowIndent, 100)
  .setPreference(DoubleIndentClassDeclaration, true)
  .setPreference(RewriteArrowSymbols, true)

wartremoverWarnings ++= Warts.unsafe

//Test specific configuration
test in assembly := {}
parallelExecution in Test := false
fork in Test := true
