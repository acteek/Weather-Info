package ru.acteek.weather.storage.data


case class CityMetrics(
                      cityId: Int,
                      cityName: String,
                      metrics: List[Map[String, Metrics]]
                    )
