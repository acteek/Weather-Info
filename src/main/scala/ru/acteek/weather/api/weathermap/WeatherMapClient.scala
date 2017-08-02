package ru.acteek.weather.api.weathermap

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.Uri.Query
import akka.http.scaladsl.model.{HttpRequest, Uri}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import org.json4s._
import org.json4s.jackson.JsonMethods._
import ru.acteek.weather.api.weathermap.data.{City, RowMetrics}
import ru.acteek.weather.api.ApiClient
import ru.acteek.weather.conf.ApiConfig
import ru.acteek.weather.conf.ApplicationConfig._
import ru.acteek.weather.storage.data.{CityMetrics, Metrics}
import scala.concurrent.{ExecutionContext, Future}


object WeatherMapClient {
  def fromConfig()(implicit system: ActorSystem, materializer: ActorMaterializer): WeatherMapClient = {
    val url = apiConfig.getString("url")
    val version = apiConfig.getString("version")
    val method = apiConfig.getString("method")
    val token = apiConfig.getString("token")
    new WeatherMapClient(
      ApiConfig(url, version, method, token)
    )
  }
}

class WeatherMapClient(apiConfig: ApiConfig)
                      (implicit system: ActorSystem,
                       materializer: ActorMaterializer) extends  ApiClient {


  import apiConfig._
  implicit val executionContext: ExecutionContext = system.dispatcher

  private val http = Http()
  private val baseUri: Uri = Uri(s"$url/$version/$method")

  private def makeRequest(name: Option[String], id: Option[Int]): HttpRequest = HttpRequest(
    uri = (name, id) match {
      case (Some(n), None) =>
        baseUri.withQuery(
          Query(Map("q" -> n, "APPID" -> token, "units" -> "metric", "lang" -> "ru"))
        )
      case (None, Some(i)) =>
        baseUri.withQuery(
          Query(Map("id" -> i.toString, "APPID" -> token, "units" -> "metric", "lang" -> "ru"))
        )
      case _ => throw new NoSuchElementException(s"No city name or id for  get metrics!")
    }
  )

  private def normalized(jsonString: String): CityMetrics = {
    implicit val formats = DefaultFormats
    val json = parse(jsonString)
    val city = (json \ "city").extract[City]
    val metrics = (json \ "list").extract[List[RowMetrics]].map { m =>
      Map(m.dt_txt -> Metrics(
        m.main.temp, m.main.temp_min, m.main.temp_max, m.main.pressure, m.wind.speed, m.wind.deg)
      )
    }
    logger.debug("For City => {} Metrics => {}", city, metrics)
    CityMetrics(city.id, city.name, metrics)
  }

  def getMetricByCityId(id: Int): Future[CityMetrics] =
    sendRequest(cityId = Some(id), cityName = None)

  def getMetricByCityName(cityName: String): Future[CityMetrics] =
    sendRequest(cityName = Some(cityName), cityId = None)

  private def sendRequest(cityName: Option[String], cityId: Option[Int]): Future[CityMetrics] = {
    http
      .singleRequest(makeRequest(cityName, cityId))
      .flatMap { response =>
        if (response.status.isSuccess) {
          Unmarshal(response.entity).to[String].map { jsonString =>
            logger.debug("Response From Api => {}", jsonString)
            normalized(jsonString)
          }
        } else throw new RuntimeException(s"Response has code ${response.status.value}")
      }
  }
}
