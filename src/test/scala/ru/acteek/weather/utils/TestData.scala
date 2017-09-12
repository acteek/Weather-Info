package ru.acteek.weather.utils

import Utils.normalizeLabel
import ru.acteek.weather.storage.data.{CityMetrics, Metrics}

object TestData {

  case class ReqData(cityName: String, dateFrom: String, dateTo: String)

  case class RespData(cityId: Int, cityName: String, label: String, metrics: Metrics)

  val reqData = ReqData("Moscow", "2017-09-05T00:00", "2017-09-05T00:05")
  val metrics = Metrics(10.55, 10.13, 10.55, 98.0, 5.22, "SSE")
  val respData = RespData(524901, reqData.cityName, "2017-09-05 00:03:45", metrics)

  val apiJson = {
    import metrics._
    val snip = normalizeLabel(respData.label).replace(" ","_")
    s"""[{"$snip":{"temp":$temp,"temp_min":$tempMin,"temp_max":$tempMax,"humidity":$humidity,"wind_speed":$windSpeed,"wind_deg":"$windDeg"}}]"""
  }

  val apiClientResp = CityMetrics(respData.cityId, respData.cityName, List((respData.label, metrics)))

  val weatherApiResponse =
    s"""{"cod":"200",
      |"message":0.0029,"cnt":1,
      |"list":[{"dt":1505239200,"main":
      |{"temp":${metrics.temp},"temp_min":${metrics.tempMin},
      |"temp_max":${metrics.tempMax},"pressure":1005.62,
      |"sea_level":1025.03,
      |"grnd_level":1005.62,
      |"humidity":${metrics.humidity},"temp_kf":0.02},
      |"weather":[{"id":800,"main":"Clear","description":"ясно","icon":"01n"}],
      |"clouds":{"all":0},"wind":{"speed":${metrics.windSpeed},"deg":162.502},"sys":{"pod":"n"},
      |"dt_txt":"${respData.label}"}],"city":{"id":${respData.cityId},"name":"${reqData.cityName}",
      |"coord":{"lat":55.7522,"lon":37.6156},"country":"RU"}}""".stripMargin.trim

}
