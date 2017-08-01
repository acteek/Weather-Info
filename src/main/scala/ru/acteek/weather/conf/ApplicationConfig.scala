package ru.acteek.weather.conf

import com.typesafe.config.{Config, ConfigFactory}


object ApplicationConfig {

  val conf: Config = ConfigFactory.load()

  val port: Int = conf.getInt("http.port")
  val apiConfig: Config = conf.getConfig("api.openWeatherMap")
}
