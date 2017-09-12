package ru.acteek.weather.utils

import java.util.concurrent.Executors

import akka.actor.ActorSystem
import akka.http.scaladsl.model.HttpEntity
import akka.http.scaladsl.server.HttpApp
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.settings.ServerSettings
import com.typesafe.config.ConfigFactory
import ru.acteek.weather.conf.ApplicationConfig.apiConfig
import ru.acteek.weather.utils.TestData._
import org.scalatest._


object WeatherApiMock {

  private val executor = Executors.newSingleThreadExecutor()

  def start(port: Int = 9091): Unit = executor.submit(new WeatherApiMock(port))

  Thread.sleep(100)

  def stop(): Unit = executor.shutdown()
}

class WeatherApiMock(port: Int) extends HttpApp with Matchers with Runnable {
  self =>

  private val system = ActorSystem("weather-api-mock")
  private val version = apiConfig.getString("version")
  private val method = apiConfig.getString("method")
  private val tokenApi = apiConfig.getString("token")

  val settings = ServerSettings(ConfigFactory.load)

  override def route(): Route =
    get {
      path("data" / version / method) {
        parameters("q", "APPID", "units", "lang") { (city, token, units, lang) =>
          city should not be empty
          token shouldBe tokenApi
          units shouldBe "metric"
          lang shouldBe "ru"

          complete(HttpEntity(weatherApiResponse))
        }
      }
    }

  override def run(): Unit = self.startServer("0.0.0.0", port, settings, system)

  def stop(): Unit = system.terminate()
}