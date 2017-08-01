package ru.acteek.weather

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.typesafe.scalalogging.StrictLogging
import org.json4s.DefaultFormats
import org.json4s.jackson.JsonMethods.parse
import ru.acteek.weather.api.WeatherMapClient
import ru.acteek.weather.conf.ApplicationConfig._
import ru.acteek.weather.storage.ApiResponse

object Application extends App with StrictLogging {

  implicit val system = ActorSystem("weather-info")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val route =
    get {
      path("") {
        getFromResource("static/index.html")
      } ~
        pathPrefix("css") {
          encodeResponse {
            getFromResourceDirectory("static/css")
          }
        } ~
        pathPrefix("js") {
          encodeResponse {
            getFromResourceDirectory("static/js")
          }
        } ~
        path("conversion") {
          parameters("accounts", "date-from", "date-to", "batch".as[Int], "interval".as[Int]) { (accounts, dateFrom, dateTo, batch, interval) =>
            val accountsList = accounts.split(",").toList
            logger.info(s"Acc => ${accountsList.mkString(",")}  DateFrom => $dateFrom  DateTo => $dateTo Batch => $batch Interval => $interval")
            complete("OK")
          }
        }
    }

  Http()
    .bindAndHandle(route, "0.0.0.0", port)
     logger.info(s"Server start at http://0.0.0.0:$port/")

  val api = WeatherMapClient.fromConfig()
val re =   api.getDataByCityName("Кипр").map {
    r =>
//      implicit val formats = DefaultFormats
//      val a = parse(r).extract[ApiResponse]
      system.log.info("RESPONSE => {}", r)
  }
}
