package ru.acteek.weather.storage

import akka.actor.ActorSystem
import ru.acteek.weather.api.ApiClient
import com.github.blemale.scaffeine.{Cache, Scaffeine}
import ru.acteek.weather.storage.data.CityMetrics
import org.json4s.{DefaultFormats, NoTypeHints}
import org.json4s.jackson.Serialization
import org.json4s.jackson.Serialization.{read, write}
import org.json4s.jackson.JsonMethods._
import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration._

class StorageImpl(val apiClient: ApiClient)(implicit system: ActorSystem) extends Storage {

  implicit val executionContext: ExecutionContext = system.dispatcher

  val cache: Cache[String, CityMetrics] =
    Scaffeine()
      .recordStats()
      .expireAfterWrite(20.minutes)
      .maximumSize(1000)
      .build[String, CityMetrics]()


  def renderJson(metrics: CityMetrics): ResponseJson = {
    implicit val formats = Serialization.formats(NoTypeHints)
    val ser = write(metrics)
    val outJson = compact(render(parse(ser).snakizeKeys))
    logger.debug("Out Json => {}", outJson)
    outJson
  }

  def getMetrics(cityName: String, dateFrom: String, dateTo: String): Future[ResponseJson] = {
    cache.getIfPresent(cityName) match {
      case Some(metrics) =>
        //TODO  тут нужно фильтровать метрики пo времени
        Future.successful(renderJson(metrics))
      case None =>
        apiClient.getMetricByCityName(cityName).map { response =>
          cache.put(cityName, response)
          renderJson(response)
        }
    }
  }
}
