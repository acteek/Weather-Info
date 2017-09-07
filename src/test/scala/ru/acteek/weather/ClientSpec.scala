package ru.acteek.weather

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import ru.acteek.weather.api.weathermap.WeatherMapClient
import ru.acteek.weather.conf.ApiConfig
import ru.acteek.weather.utils.WeatherApiMock
import java.util.concurrent.Executors


class ClientSpec extends BaseSpec {

  implicit val system = ActorSystem("testing")
  implicit val materializer = ActorMaterializer()

//  private val executor = Executors.newSingleThreadExecutor()
//  executor.submit(WeatherApiMock)
//  Thread.sleep(2000)

  val conf = ApiConfig("http://0.0.0.0:9091/data", "2.5", "forecast", "b88d219056735117545d234d3cbc0714")
  val client = new WeatherMapClient(conf)


  "WeatherMapClient" when {
    "called  " should {
      "return valid response" in {
       val response = client.getMetricByCityName(reqData.city)
        whenReady(response) { r =>
//          r.metrics should not be empty
        r
        }
      }
    }
  }

}
