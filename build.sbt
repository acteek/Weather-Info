
lazy val projectName = "Weather-Info"
lazy val projectVersion = "0.0.4"
lazy val dhNamespace = "acteek"

scalaVersion := "2.11.7"

npmWorkingDir := "src/main/js"
npmCompileCommands := "run build"
npmTestCommands := "run test"
npmCleanCommands := "run clean"

lazy val main = (project in file("."))
  .settings(
    scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature"),
    name := projectName,
    version := projectVersion,
      libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.0.0",
      "com.typesafe.akka" %% "akka-http" % "10.0.6",
      "com.typesafe" % "config" % "1.3.1",
      "ch.qos.logback" % "logback-classic" % "1.1.7",
      "com.typesafe.scala-logging" %% "scala-logging" % "3.4.0",
      "joda-time" % "joda-time" % "2.9.9",
      "org.joda" % "joda-convert" % "1.8.2",
      "com.github.blemale" % "scaffeine_2.11" % "2.2.0",
      "org.json4s" % "json4s-jackson_2.11" % "3.3.0",
      "com.typesafe.akka" %% "akka-slf4j" % "2.4.19"
    )
  )
  .enablePlugins(DockerPlugin, Npm)
  .settings(docker := docker.dependsOn(Keys.`package`.in(Compile, packageBin)).value)
  .settings(
    imageNames in docker := Seq(
      ImageName(s"$dhNamespace/${name.value.toLowerCase}:latest"),
      ImageName(
        repository = name.value.toLowerCase,
        namespace = Some(dhNamespace),
        tag = Some(version.value)
      )
    )).settings(
  dockerfile in docker := {
    val jarFile = artifactPath.in(Compile, packageBin).value
    val classpath = managedClasspath.in(Compile).value
    val depClasspath = dependencyClasspath.in(Runtime).value
    val mainclass = mainClass.in(Compile, packageBin).value.get
    val resoursespath = sourceDirectory.in(Compile, packageBin).value
    val app = "/app"
    val etc = s"$app/etc"
    val data = s"$app/data"
    val log = s"$app/log"
    val libs = s"$app/libs"
    val jarTarget = s"$app/${name.value.toLowerCase}.jar"
    val classpathString = s"$libs/*:$jarTarget"
    new Dockerfile {
      from("anapsix/alpine-java")
      run("mkdir", app, etc, data, log)
      workDir(app)
      classpath.files.foreach { depFile =>
        val target = file(libs) / depFile.name
        stageFile(depFile, target)
      }
      depClasspath.files.foreach { depFile =>
        val target = file(libs) / depFile.name
        stageFile(depFile, target)
      }
      addRaw(libs, libs)
      add(jarFile, jarTarget)
      add(resoursespath, etc)
      cmd("java",
        "-Xms256m",
        "-Xmx256m",
        "-Dlogger.file=/app/etc/resources/logback.xml",
        "-Dconfig.file=/app/etc/resources/application.conf",
        "-cp", classpathString, mainclass)
    }
  }
)
