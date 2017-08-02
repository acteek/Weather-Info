package ru.acteek.weather.api.weathermap.data

case class ApiResponse (
                         cod: String,
                         message: Float,
                         cnt: Int,
                         list: List[RowMetrics],
                         city: City
                       )
