package ru.acteek.weather.api

import com.typesafe.scalalogging.StrictLogging
import ru.acteek.weather.storage.data.CityMetrics

import scala.concurrent.Future

trait ApiClient extends StrictLogging {

  /** Получить метрики по названию города
    *
    * @param cityName Название города
    * @return Имя и id города, набор метрик разбитых по часам
    */

  def getMetricByCityName(cityName: String): Future[CityMetrics]

  /**  Получить метрики по Id города
    *
    * @param id ID города в метео Api
    * @return Имя и id города, набор метрик разбитых по часам
    */

  def getMetricByCityId(id: Int): Future[CityMetrics]

}
