package ru.acteek.weather.storage.data

case class Metrics(
                    temp: Float,
                    tempMin: Float,
                    tempMax: Float,
                    pressure: Float,
                    windSpeed: Float,
                    windDeg: Int
                  )
