lazy val projectName    = "weather-info"
lazy val projectVersion = "0.0.7"
lazy val dhNamespace    = "acteek"

scalaVersion := "2.13.2"

lazy val http4sVersion = "0.21.4"
lazy val circeVersion  = "0.13.0"

//npmWorkingDir := "src/main/js"
//npmCompileCommands := "run build"
//npmTestCommands := "run test"
//npmCleanCommands := "run clean"

lazy val main = (project in file("."))
  .settings(
      scalacOptions ++= Seq(
        "-encoding"
      , "UTF-8"
      , "-deprecation"
      , "-explaintypes"
      , "-unchecked"
      , "-feature"
      , "-language:higherKinds"
      , "-language:existentials"
      , "-language:implicitConversions"
      , "-Xlint:stars-align"
      , "-Xsource:2.13"
      , "-Ywarn-dead-code"
      , "-Ywarn-unused:imports"
      , "-Ypatmat-exhaust-depth"
      , "40"
      , "-opt:l:method"
      , "-Ymacro-annotations"
    )
    , name := projectName
    , version := projectVersion
    , libraryDependencies ++= Seq(
        "com.github.blemale"         %% "scaffeine"           % "3.1.0"
      , "org.scalatest"              %% "scalatest"           % "3.1.0" % Test
      , "org.scalacheck"             %% "scalacheck"          % "1.14.3" % Test

      , "com.typesafe.scala-logging" %% "scala-logging"       % "3.9.2"
      , "joda-time"                  % "joda-time"            % "2.10.5"
      , "org.joda"                   % "joda-convert"         % "2.2.1"
      , "com.typesafe"               % "config"               % "1.4.0"
      , "ch.qos.logback"             % "logback-classic"      % "1.2.3"

      , "io.circe"                   %% "circe-core"          % circeVersion
      , "io.circe"                   %% "circe-generic"       % circeVersion
      , "io.circe"                   %% "circe-parser"        % circeVersion

      , "org.http4s"                 %% "http4s-core"         % http4sVersion
      , "org.http4s"                 %% "http4s-server"       % http4sVersion
      , "org.http4s"                 %% "http4s-client"       % http4sVersion
      , "org.http4s"                 %% "http4s-circe"        % http4sVersion
      , "org.http4s"                 %% "http4s-dsl"          % http4sVersion
      , "org.http4s"                 %% "http4s-blaze-server" % http4sVersion
      , "org.http4s"                 %% "http4s-blaze-client" % http4sVersion

      , "com.github.pureconfig"      %% "pureconfig"          % "0.12.3"
      , "org.typelevel"              %% "cats-core"           % "2.1.1"
      , "org.typelevel"              %% "cats-effect"         % "2.1.3"
      , "co.fs2"                     %% "fs2-core"            % "2.3.0"
      , "co.fs2"                     %% "fs2-io"              % "2.3.0"

      //Don't support for 2.13
      //      "org.scalamock"              %% "scalamock-scalatest-support" % "3.6.0"   % Test,
    )
  )
  .enablePlugins()
//  .settings(docker := docker.dependsOn(Keys.`package`.in(Compile, packageBin)).value)
//  .settings(
//    imageNames in docker := Seq(
//      ImageName(s"$dhNamespace/${name.value.toLowerCase}:latest"),
//      ImageName(
//        repository = name.value.toLowerCase,
//        namespace = Some(dhNamespace),
//        tag = Some(version.value)
//      )
//    )).settings(
//  dockerfile in docker := {
//    val jarFile = artifactPath.in(Compile, packageBin).value
//    val classpath = managedClasspath.in(Compile).value
//    val depClasspath = dependencyClasspath.in(Runtime).value
//    val mainclass = mainClass.in(Compile, packageBin).value.get
//    val resoursespath = sourceDirectory.in(Compile, packageBin).value
//    val app = "/app"
//    val etc = s"$app/etc"
//    val log = s"$app/log"
//    val libs = s"$app/libs"
//    val jarTarget = s"$app/${name.value.toLowerCase}.jar"
//    val classpathString = s"$libs/*:$jarTarget"
//    new Dockerfile {
//      from("anapsix/alpine-java")
//      run("mkdir", app, etc, log)
//      workDir(app)
//      classpath.files.foreach { depFile =>
//        val target = file(libs) / depFile.name
//        stageFile(depFile, target)
//      }
//      depClasspath.files.foreach { depFile =>
//        val target = file(libs) / depFile.name
//        stageFile(depFile, target)
//      }
//      addRaw(libs, libs)
//      add(jarFile, jarTarget)
//      add(resoursespath, etc)
//      cmd("java",
//        "-Xms256m",
//        "-Xmx256m",
//        "-Dlogger.file=/app/etc/resources/prod_conf/logback.xml",
//        "-Dconfig.file=/app/etc/resources/prod_conf/application.conf",
//        "-cp", classpathString, mainclass)
//    }
//  }
//)
