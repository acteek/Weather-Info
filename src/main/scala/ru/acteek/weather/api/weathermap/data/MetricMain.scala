package ru.acteek.weather.api.weathermap.data

case class MetricMain (
                      temp: Float,
                      temp_min: Float,
                      temp_max: Float,
                      pressure: Float,
                      sea_level: Float,
                      grnd_level: Float,
                      humidity: Int,
                      temp_kf: Float
                      )