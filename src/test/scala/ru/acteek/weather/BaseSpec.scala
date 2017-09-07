package ru.acteek.weather

import org.scalatest._
import ru.acteek.weather.storage.Storage
import org.scalamock.scalatest.MockFactory
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Span}
import ru.acteek.weather.api.ApiClient
import ru.acteek.weather.storage.data.{CityMetrics, Metrics}
import ru.acteek.weather.Utils._

import scala.concurrent.Future

abstract class BaseSpec extends WordSpec
  with Matchers
  with MockFactory
  with OneInstancePerTest
  with ScalaFutures {

  case class ReqData(city: String, dateFrom: String, dateTo: String)

  case class RespData(cityId: Int, cityName: String, label: String, metrics: Metrics)

  implicit val config =
    PatienceConfig(timeout = Span(500, Millis), interval = Span(10, Millis))

  val reqData = ReqData("Moscow", "2017-09-05T00:00", "2017-09-05T00:05")
  val metrics = Metrics(10.55, 10.13, 10.55, 98.0, 5.22, "ENE")
  val respData = RespData(21421, reqData.city, "2017-09-05 00:03:45", metrics)

  val apiJson = {
    import metrics._
    val snip = normalizeLabel(respData.label).replace(" ","_")
    s"""[{"$snip":{"temp":$temp,"temp_min":$tempMin,"temp_max":$tempMax,"humidity":$humidity,"wind_speed":$windSpeed,"wind_deg":"$windDeg"}}]"""
  }

  val apiClientResp = CityMetrics(respData.cityId, respData.cityName, List((respData.label, metrics)))

  val pathStatic = "src/main/resources/ui"
  val storageMock = mock[Storage]
  val apiClientMock = mock[ApiClient]

  (storageMock.getMetrics _)
    .expects(reqData.city, reqData.dateFrom, reqData.dateTo)
    .returning(Future.successful {apiJson}) anyNumberOfTimes()

  (apiClientMock.getMetricByCityName _)
    .expects(reqData.city)
    .returning(Future.successful(apiClientResp)) anyNumberOfTimes()
}

