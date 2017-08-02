package ru.acteek.weather.api.weathermap.data

case class City(
                 id: Int,
                 name: String,
                 coord: Coord,
                 country: String
               )
