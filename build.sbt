import sbt._
import Keys._
import com.typesafe.sbt.SbtScalariform
import de.heikoseeberger.sbtheader.license.Apache2_0
import scalariform.formatter.preferences._
import com.typesafe.sbt.SbtScalariform

scalaVersion := "2.11.8"
organization := "com.github.notvitor"
licenses     += ("Apache-2.0", url("http://opensource.org/licenses/apache2.0.php"))
headers      := Map(
  "scala" -> Apache2_0("2016", "Vitor Vieira"),
  "conf"  -> Apache2_0("2016", "Vitor Vieira", "#")
)

lazy val buildSettings = Seq(
  version       := "0.0.1",
  scalaVersion  := "2.11.8",
  scalacOptions ++= Seq("-unchecked", "-deprecation", "-encoding", "utf8", "-feature", "-language:higherKinds", "-language:implicitConversions", "-Ybackend:GenBCode", "-Ydelambdafy:method", "-target:jvm-1.8"),
  resolvers     ++= Seq(
    "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/",
    Resolver.sonatypeRepo("snapshots"),
    Resolver.sonatypeRepo("releases"),
    Resolver.bintrayRepo("hseeberger", "maven")
  )
)

val akkaV  = "2.4.3"
val circeV = "0.3.0"
val scalaTestV  = "2.2.6"
val akkaCirceV  = "1.5.3"


lazy val `template` = project
  .in(file("."))
  .settings(buildSettings: _*)
  .settings(mainClass in assembly := Some("com.github.notvitor.http.server.ServerTemplate"))
  .settings(
    name := "akka-http-circe-json-template",
    libraryDependencies ++= Seq(
      "io.circe"          %% "circe-core"             % circeV,
      "io.circe"          %% "circe-generic"          % circeV,
      "io.circe"          %% "circe-jawn"             % circeV,
      "de.heikoseeberger" %% "akka-http-circe"        % akkaCirceV,
      "com.typesafe.akka" %% "akka-http-experimental" % akkaV,
      "org.scalatest"     %% "scalatest"              % scalaTestV % Test
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