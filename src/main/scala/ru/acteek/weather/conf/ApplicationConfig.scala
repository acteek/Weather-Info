package ru.acteek.weather.conf

import com.typesafe.config.ConfigFactory


object ApplicationConfig {

  val conf = ConfigFactory.load()

  val port: Int = conf.getInt("http.port")

}
