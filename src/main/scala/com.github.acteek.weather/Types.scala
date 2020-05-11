package com.github.acteek.weather

import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}
import scala.collection.SortedMap

object Types {
  type CityName      = String
  type MetricsByTime = SortedMap[Long, Metrics]
  type InstantSec    = Long

  case class Metrics(
        time: InstantSec
      , temp: Double
      , humidity: Double
      , windSpeed: Double
      , windDirection: String
  )
  object Metrics {
    implicit val decoder: Decoder[Metrics] = Decoder.instance { c =>
      val main = c.downField("main")
      val wind = c.downField("wind")
      for {
        time       <- c.get[InstantSec]("dt")
        temp       <- main.get[Double]("temp")
        humidity   <- main.get[Double]("humidity")
        windSpeed  <- wind.get[Double]("speed")
        windDegNum <- wind.get[Double]("deg")
      } yield Metrics(
          time = time
        , temp = temp
        , humidity = humidity
        , windSpeed = windSpeed
        , windDirection = Utils.windDirectionByDegrees(windDegNum)
      )

    }

    implicit val encoder: Encoder[Metrics] = deriveEncoder[Metrics]
  }

  case class City(
        name: String
      , metrics: MetricsByTime
  )
  object City {
    implicit val decoder: Decoder[City] = Decoder.instance { c =>
      val city = c.downField("city")
      for {
        name    <- city.get[String]("name")
        metrics <- c.get[List[Metrics]]("list")
        metricsByTime = SortedMap.from(metrics.map(m => m.time -> m))
      } yield City(name, metricsByTime)

    }
  }

}
