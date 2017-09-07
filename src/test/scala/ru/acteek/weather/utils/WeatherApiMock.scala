package ru.acteek.weather.utils

import akka.http.scaladsl.model.HttpEntity
import akka.http.scaladsl.server.HttpApp
import akka.http.scaladsl.server.Route
import ru.acteek.weather.conf.ApplicationConfig.apiConfig

object WeatherApiMock extends HttpApp with App { self =>

  val version = apiConfig.getString("version")
  val method = apiConfig.getString("method")
  val tokenApi = apiConfig.getString("token")

  override def routes: Route =
    get {
      path("data" / version / method) {
        parameters("q", "APPID", "units", "lang") { (city, token, units, lang) =>
          assert(city.nonEmpty)
          assert(token == tokenApi)
          assert(units == "metric")
          assert(lang == "ru")

          complete(HttpEntity("[{}]"))
        }
      }
    }
  self.startServer("0.0.0.0", 9091)
}