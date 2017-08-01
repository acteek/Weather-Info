package ru.acteek.weather.storage

case class Metrics (
                   dt: Long,
                   main : MetricMain,
                   weather: List[Weather],
                   clouds: Clouds,
                   wind: Wind,
                   rain: Option[Rain] ,
                   sys: Option[Sys],
                   dt_txt: String
                   )
