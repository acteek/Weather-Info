package ru.acteek.weather

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import com.typesafe.scalalogging.StrictLogging
import ru.acteek.weather.api.weathermap.WeatherMapClient
import ru.acteek.weather.conf.ApplicationConfig._
import ru.acteek.weather.storage.{Storage, StorageImpl}


object Application extends App with StrictLogging {

  implicit val system = ActorSystem("weather-info")
  implicit val executionContext = system.dispatcher

  val api = WeatherMapClient.fromConfig()
  val storage = new StorageImpl(api)

  def route(storage: Storage) =
    get {
      path("") {
        getFromResource("ui/index.html")
      } ~
        pathPrefix("css") {
          encodeResponse {
            getFromResourceDirectory("ui/css")
          }
        } ~
        pathPrefix("js") {
          encodeResponse {
            getFromResourceDirectory("ui/dist")
          }
        } ~
        path("metrics") {
          parameters("city".as[String], "date-from".as[String], "date-to".as[String]) { (city, dateFrom, dateTo) =>
            logger.info("Received  request with city=>[{}] dateFrom=>[{}] dateTo=>[{}]", city, dateFrom, dateTo)
            onSuccess(storage.getMetrics(city, dateFrom, dateTo)) { resp =>
              complete(resp)
            }
          }
        }
    }


  Http().bindAndHandle(route(storage), "0.0.0.0", port)
  logger.info(s"Server start at http://0.0.0.0:$port/")

}
