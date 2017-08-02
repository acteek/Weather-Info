import org.json4s.{DefaultFormats, JArray}
import org.json4s.jackson.JsonMethods.parse
import ru.acteek.weather.api.weathermap.data.RowMetrics

val jsonString = """{"cod":"200","message":0.0028,"cnt":1,"list":[{"dt":1501632000,"main":{"temp":16.88,"temp_min":15.15,"temp_max":16.88,"pressure":1012.94,"sea_level":1033.3,"grnd_level":1012.94,"humidity":94,"temp_kf":1.74},"weather":[{"id":800,"main":"Clear","description":"ясно","icon":"01n"}],"clouds":{"all":0},"wind":{"speed":1.76,"deg":332.004},"sys":{"pod":"n"},"dt_txt":"2017-08-02 00:00:00"}],"city":{"id":732277,"name":"Obshtina Devnya","coord":{"lat":43.25,"lon":27.6},"country":"BG"}}"""


implicit val formats = DefaultFormats
val json = (parse(jsonString) \ "list").asInstanceOf[JArray].f
json.extract[List[RowMetrics]]