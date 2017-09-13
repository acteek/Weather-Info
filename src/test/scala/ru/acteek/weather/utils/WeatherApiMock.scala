package ru.acteek.weather.utils

import java.util.concurrent.Executors

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpEntity
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import ru.acteek.weather.conf.ApplicationConfig.apiConfig
import ru.acteek.weather.utils.TestData._
import org.scalatest._


object WeatherApiMock {

  private val executor = Executors.newSingleThreadExecutor()

  def start(port: Int = 9091): Unit = executor.submit(new WeatherApiMock(port))
  Thread.sleep(300)

  def stop(): Unit = executor.shutdown()
}

class WeatherApiMock(port: Int) extends Matchers with Runnable {

  private implicit val system = ActorSystem("weather-api-mock")
  private implicit val materializer = ActorMaterializer()

  private val version = apiConfig.getString("version")
  private val method = apiConfig.getString("method")
  private val tokenApi = apiConfig.getString("token")

  import akka.http.scaladsl.server.Directives._

  private val route: Route =
    get {
      path("data" / version / method) {
        parameters('q.?, 'id.? ,"APPID", "units", "lang") { (city, id, token, units, lang) =>
          (city.isDefined || id.isDefined) shouldBe true
          token shouldBe tokenApi
          units shouldBe "metric"
          lang shouldBe "ru"

          complete(HttpEntity(weatherApiResponse))
        }
      }
    }

  override def run(): Unit = Http().bindAndHandle(Route.handlerFlow(route), "0.0.0.0", port)

  def stop(): Unit = system.terminate()
}