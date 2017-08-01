package ru.acteek.weather.storage

case class ApiResponse (
                       cod: String,
                       message: Float,
                       cnt: Int,
                       list: List[Metrics],
                       city: City
                       )
