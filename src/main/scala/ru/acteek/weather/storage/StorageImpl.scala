package ru.acteek.weather.storage

import akka.actor.ActorSystem
import ru.acteek.weather.api.ApiClient
import com.github.blemale.scaffeine.{Cache, Scaffeine}
import ru.acteek.weather.storage.data.{CityMetrics, Metrics}
import org.json4s.{DefaultFormats, NoTypeHints}
import org.json4s.jackson.Serialization
import org.json4s.jackson.Serialization.{read, write}
import org.json4s.jackson.JsonMethods._

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration._
import ru.acteek.weather.storage.Utils._


class StorageImpl(val apiClient: ApiClient)(implicit system: ActorSystem) extends Storage {

  implicit val executionContext: ExecutionContext = system.dispatcher

  val cache: Cache[String, CityMetrics] =
    Scaffeine()
      .recordStats()
      .expireAfterWrite(20.minutes)
      .maximumSize(1000)
      .build[String, CityMetrics]()

  def renderJson(metrics: List[(String, Metrics)]): ResponseJson = {
    implicit val formats = Serialization.formats(NoTypeHints)
    val ser = write(metrics)
    val outJson = compact(render(parse(ser).snakizeKeys))
    logger.debug("Out Json => {}", outJson)
    outJson
  }

  private def filterMetric(city: CityMetrics, dateFromString: String, dateToString: String) = {
    val dateFrom = frontTimeFormat.parseLocalDateTime(dateFromString)
    val dateTo = frontTimeFormat.parseLocalDateTime(dateToString)

    city.metrics.filter { case (date, _) =>
      val dateTarget = backTimeFormat.parseLocalDateTime(date)
      (dateTarget isAfter dateFrom) && (dateTarget isBefore dateTo)
    }
  }

  def getMetrics(cityName: String, dateFromString: String, dateToString: String): Future[ResponseJson] = {

    cache.getIfPresent(cityName) match {
      case Some(city) =>
        val metrics = filterMetric(city, dateFromString, dateToString)
        Future.successful(renderJson(metrics))
      case None =>
        apiClient.getMetricByCityName(cityName).map { city =>
          cache.put(cityName, city)
          val metrics = filterMetric(city, dateFromString, dateToString)
          renderJson(metrics)
        }
    }
  }
}
