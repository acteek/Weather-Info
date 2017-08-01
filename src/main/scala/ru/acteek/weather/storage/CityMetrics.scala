package ru.acteek.weather.storage

case class CityMetrics(
                      cityId: String,
                      metrics: List[Map[String,Tuple2[MetricMain,Wind]]]

                    )
