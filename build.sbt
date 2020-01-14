
lazy val projectName = "Weather-Info"
lazy val projectVersion = "0.0.7"
lazy val dhNamespace = "acteek"

scalaVersion := "2.13.1"

//npmWorkingDir := "src/main/js"
//npmCompileCommands := "run build"
//npmTestCommands := "run test"
//npmCleanCommands := "run clean"

lazy val main = (project in file("."))
  .settings(
    scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature"),
    name := projectName,
    version := projectVersion,
      libraryDependencies ++= Seq(

       "com.github.blemale"          %% "scaffeine"                   % "3.1.0",
       "org.scalatest"               %% "scalatest"                   % "3.1.0"   % Test,
       "org.scalacheck"              %% "scalacheck"                  % "1.14.3"  % Test,
       "com.typesafe.akka"           %% "akka-http-testkit"           % "10.1.11" % Test,
       "org.json4s"                  %% "json4s-jackson"              % "3.6.7",
       "com.typesafe.scala-logging"  %% "scala-logging"               % "3.9.2",
       "com.typesafe.akka"           %% "akka-slf4j"                  % "2.6.1",
       "com.typesafe.akka"           %% "akka-stream"                 % "2.6.1",
       "com.typesafe.akka"           %% "akka-http"                   % "10.1.8",
       "joda-time"                    % "joda-time"                   % "2.10.5",
       "org.joda"                     % "joda-convert"                % "2.2.1",
       "com.typesafe"                 % "config"                      % "1.4.0",
       "ch.qos.logback"               % "logback-classic"             % "1.2.3"

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
