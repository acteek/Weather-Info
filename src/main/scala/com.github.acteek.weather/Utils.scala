package com.github.acteek.weather

import org.joda.time.format._

object Utils {

  private val frontPattern = "YYYY-MM-DD'T'HH:mm"
  private val backPattern = "YYYY-MM-DD' 'HH:mm:ss"

  val frontTimeFormat: DateTimeFormatter = DateTimeFormat.forPattern(frontPattern)
  val backTimeFormat: DateTimeFormatter = DateTimeFormat.forPattern(backPattern)

  def normalizeLabel(label: String): String = label.drop(8).dropRight(3)

  def windDirectionByDegrees(degrees: Double): String = degrees match {
    case deg if (11.25 <= deg) && (deg < 33.75) => "NNE"
    case deg if (33.75 <= deg) && (deg < 56.25) => "NE"
    case deg if (56.25 <= deg) && (deg < 78.75) => "ENE"
    case deg if (78.75 <= deg) && (deg < 101.25) => "E"
    case deg if (101.25 <= deg) && (deg < 123.75) => "ESE"
    case deg if (123.75 <= deg) && (deg < 146.25) => "SE"
    case deg if (146.25 <= deg) && (deg < 168.75) => "SSE"
    case deg if (168.75 <= deg) && (deg < 191.25) => "S"
    case deg if (191.25 <= deg) && (deg < 213.75) => "SSW"
    case deg if (213.75 <= deg) && (deg < 236.25) => "SW"
    case deg if (236.25 <= deg) && (deg < 258.75) => "WSW"
    case deg if (258.75 <= deg) && (deg < 281.25) => "W"
    case deg if (281.25 <= deg) && (deg < 303.75) => "WNW"
    case deg if (303.75 <= deg) && (deg < 326.25) => "NW"
    case deg if (326.25 <= deg) && (deg < 348.75) => "NNW"
    case _ => "N"
  }
}
