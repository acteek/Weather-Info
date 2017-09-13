package ru.acteek.weather

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import ru.acteek.weather.api.weathermap.WeatherMapClient
import ru.acteek.weather.conf.ApiConfig
import org.scalatest.BeforeAndAfterAll
import ru.acteek.weather.utils.TestData._
import ru.acteek.weather.utils.WeatherApiMock

class ClientSpec extends BaseSpec with BeforeAndAfterAll {

  override def beforeAll(): Unit = WeatherApiMock.start()

  override def afterAll(): Unit = WeatherApiMock.stop()

  implicit val system = ActorSystem("testing")
  implicit val materializer = ActorMaterializer()
  val conf = ApiConfig("http://0.0.0.0:9091/data", "2.5", "forecast", "b88d219056735117545d234d3cbc0714")
  val client = new WeatherMapClient(conf)


  "WeatherMapClient" when {
    "called getMetricByCityName" should {
      "return valid response" in {
        val response = client.getMetricByCityName(reqData.cityName)
        whenReady(response) { data =>
          data shouldBe apiClientResp
        }
      }
    }
    "called getMetricByCityId" should {
      "return valid response" in {
        val response = client.getMetricByCityId(respData.cityId)
        whenReady(response) { data =>
          data shouldBe apiClientResp
        }
      }
    }
  }

}
