package ru.acteek.weather

import org.scalatest._
import ru.acteek.weather.storage.Storage
import org.scalamock.scalatest.MockFactory
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Span}
import ru.acteek.weather.api.ApiClient
import ru.acteek.weather.storage.data.{CityMetrics, Metrics}
import ru.acteek.weather.utils.Utils._
import ru.acteek.weather.utils.TestData._


import scala.concurrent.Future

abstract class BaseSpec extends WordSpec
  with Matchers
  with MockFactory
  with OneInstancePerTest
  with ScalaFutures {

  implicit val config =
    PatienceConfig(timeout = Span(500, Millis), interval = Span(10, Millis))

  val pathStatic = "src/main/resources/ui"
  val storageMock = mock[Storage]
  val apiClientMock = mock[ApiClient]

  (storageMock.getMetrics _)
    .expects(reqData.cityName, reqData.dateFrom, reqData.dateTo)
    .returning(Future.successful {apiJson}) anyNumberOfTimes()

  (apiClientMock.getMetricByCityName _)
    .expects(reqData.cityName)
    .returning(Future.successful(apiClientResp)) noMoreThanOnce()
}

