package ru.acteek.weather.storage

case class Metrics (
                   dt: Long,
                   main : MetricMain,
                   weather: List[Weather],
                   clouds: Tuple1[Int],
                   wind: Wind,
                   rain: Nothing,
                   sys: Tuple1[String],
                   dt_txt: String
                   )
