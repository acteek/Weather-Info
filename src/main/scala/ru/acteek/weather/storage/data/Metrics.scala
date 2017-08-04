package ru.acteek.weather.storage.data

case class Metrics(
                    temp: Double,
                    tempMin: Double,
                    tempMax: Double,
                    humidity: Double,
                    windSpeed: Double,
                    windDeg: Int
                  )
