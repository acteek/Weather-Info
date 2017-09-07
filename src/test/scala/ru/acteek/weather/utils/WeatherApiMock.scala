package ru.acteek.weather.utils

import akka.http.scaladsl.model.HttpEntity
import akka.http.scaladsl.server.HttpApp
import akka.http.scaladsl.server.Route
import ru.acteek.weather.conf.ApplicationConfig.apiConfig

object WeatherApiMock extends HttpApp {

  val version = apiConfig.getString("version")
  val method = apiConfig.getString("method")
  val tokenApi = apiConfig.getString("token")

  override def routes: Route =
    get {
      path("data" / version / method) {
        parameters("APPID", "units", "lang") { (token, units, lang) =>
          assert(token == tokenApi)
          assert(units == "metric")
          assert(lang == "ru")

          complete(HttpEntity("[{}]"))
        }
      }
    }
}