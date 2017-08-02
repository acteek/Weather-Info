package ru.acteek.weather.api.weathermap.data

case class RowMetrics(
                   dt: Long,
                   main : MetricMain,
                   weather: List[Weather],
                   clouds: Clouds,
                   wind: Wind,
                   rain: Option[Rain] ,
                   sys: Option[Sys],
                   dt_txt: String
                   )
