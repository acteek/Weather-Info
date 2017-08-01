
//lazy val dhRegistry = "hub.docker.com"
lazy val dhNamespace = "acteek"
//lazy val nexusUrl = "http://sonatype-nexus.livetex.ru"
lazy val projectVersion = "0.0.2"
//lazy val scalaUtilsVersion = "0.1.25-SNAPSHOT"

scalaVersion := "2.11.7"

lazy val main = (project in file("."))
  .settings(
    scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature"),
    name := "Weather-Info",
    version := projectVersion,
      libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.0.0",
      "com.typesafe.akka" %% "akka-http" % "10.0.6",
      "com.typesafe" % "config" % "1.3.1",
      "ch.qos.logback" % "logback-classic" % "1.1.7",
      "com.typesafe.scala-logging" %% "scala-logging" % "3.4.0"
    )
  )
  .enablePlugins(DockerPlugin).
  settings(docker <<= docker.dependsOn(Keys.`package`.in(Compile, packageBin))).
  settings(
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
      from("cogniteev/oracle-java:java8")
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
