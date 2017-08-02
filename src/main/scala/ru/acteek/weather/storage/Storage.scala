package ru.acteek.weather.storage

import com.typesafe.scalalogging.StrictLogging
import ru.acteek.weather.api.ApiClient

import scala.concurrent.Future

trait Storage extends StrictLogging {

  type ResponseJson = String

  val apiClient: ApiClient // Клиент для получения метрик

  /**
    * Интерфейс для получения метрик из хранилища
    * @param cityName название города
    * @param dateFrom дата периода
    * @param dateTo окончание периода
    * @return сформированный json CityMetrics
    */

  def getMetrics(cityName: String, dateFrom: String, dateTo: String ): Future[ResponseJson]

}
