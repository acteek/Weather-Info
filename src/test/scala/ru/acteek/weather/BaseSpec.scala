package ru.acteek.weather

import org.scalatest._
import ru.acteek.weather.storage.Storage
import org.scalamock.scalatest.MockFactory

import scala.concurrent.Future

abstract class BaseSpec extends WordSpec
  with Matchers
  with MockFactory
  with OneInstancePerTest {

  case class Data(city: String, dateFrom: String, dateTo: String)
  val  data = Data("Moscow", "2017-09-05T19:00", "2017-09-05T20:00")

  val apiValidJson = "{\"06_00:00\":" +
    "{\"temp\":10.55,\"temp_min\":10.13," +
    "\"temp_max\":10.55,\"humidity\":98.0," +
    "\"wind_speed\":5.22,\"wind_deg\":\"ENE\"}}"

  val pathStatic = "src/main/resources/ui"
  val storageMock = mock[Storage]

  (storageMock.getMetrics _)
    .expects(data.city, data.dateFrom, data.dateTo)
    .returning(Future.successful {apiValidJson}) anyNumberOfTimes()
}
