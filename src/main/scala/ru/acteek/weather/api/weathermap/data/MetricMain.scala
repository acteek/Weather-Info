package ru.acteek.weather.api.weathermap.data

case class MetricMain (
                      temp: Double,
                      temp_min: Double,
                      temp_max: Double,
                      pressure: Double,
                      sea_level: Double,
                      grnd_level: Double,
                      humidity: Int,
                      temp_kf: Double
                      )
