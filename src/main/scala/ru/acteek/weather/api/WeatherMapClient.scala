package ru.acteek.weather.api

import akka.actor.ActorSystem
import ru.acteek.weather.conf.ApiConfig
import ru.acteek.weather.conf.ApplicationConfig._
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.Uri.Query
import akka.http.scaladsl.model.{HttpRequest, Uri}
import org.json4s._

import scala.concurrent.Future
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import com.typesafe.scalalogging.StrictLogging
import org.json4s.jackson.JsonMethods._
import ru.acteek.weather.storage.{ApiResponse, CityMetrics, Metrics, Weather}

import scala.concurrent.ExecutionContext.Implicits.global


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
                       materializer: ActorMaterializer) extends StrictLogging {
  //  http://api.openweathermap.org/data/2.5/forecast?q=Москва&APPID=b88d219056735117545d234d3cbc0714&units=metric&lang=ru&cnt=1

  import apiConfig._

  private val http = Http()

  private def request(city: String): HttpRequest = HttpRequest(
    uri = Uri(s"$url/$version/$method")
      .withQuery(
        Query(Map("q" -> city, "APPID" -> token, "units" -> "metric", "lang" -> "ru", "cnt" -> "1"))
      )
  )

  private def parseJson(jsonString: String): CityMetrics = {
    implicit val formats = DefaultFormats
    val json = parse(jsonString)
    val cityId = (json \ "city" \ "id").values.toString
    val metrics = (json \ "list").extract[List[Metrics]].map { m =>
      Map(m.dt_txt -> (m.main, m.wind))
    }
    logger.debug("For CityID {} Metrics => {}",cityId, metrics)
    CityMetrics(cityId, metrics)
  }

  def getDataByCityName(cityName: String): Future[CityMetrics] = {
    http
      .singleRequest(request(cityName))
      .flatMap { response =>
        if (response.status.isSuccess) {
          Unmarshal(response.entity).to[String].map { jsonString =>
            logger.debug("Response From Api => {}", jsonString)
            parseJson(jsonString)
          }
        } else throw new RuntimeException(s"Response has code ${response.status.value}")
      }
  }
}
