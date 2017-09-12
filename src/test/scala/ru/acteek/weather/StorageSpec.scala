package ru.acteek.weather

import akka.actor.ActorSystem
import ru.acteek.weather.storage.StorageImpl
import ru.acteek.weather.utils.TestData._



class StorageSpec extends BaseSpec {

  implicit val system: ActorSystem = ActorSystem("testing")
  val storage = new StorageImpl(apiClientMock)

  "Storage" when {
    "called getMetrics" should {
      "return valid json response" in {
        val response = storage.getMetrics(reqData.cityName, reqData.dateFrom, reqData.dateTo)
        whenReady(response) { json =>
          json shouldBe apiJson
        }
      }
      "apply correct filter" in {
        val response = storage.getMetrics(reqData.cityName, reqData.dateTo, reqData.dateTo)
        whenReady(response) { json =>
          json shouldBe "[]"
        }
      }
    }
    "called getMetrics no valid date" should {
      "return exception" in {
        val response = storage.getMetrics(reqData.cityName, "2017-09-000:00", "")
        whenReady(response.failed) { ex =>
          ex shouldBe an[IllegalArgumentException]
          ex.getMessage shouldBe """Invalid format: "2017-09-000:00" is malformed at ":00""""
        }
      }
    }
    "called getMetrics empty CityName" should {
      "throw exception" in {
        intercept[IllegalArgumentException] {
          storage.getMetrics(" ", reqData.dateFrom, reqData.dateTo)
        }.getMessage shouldBe "requirement failed: CityName should no be empty"
      }
    }
  }
}
