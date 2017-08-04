package ru.acteek.weather.storage

import org.joda.time._
import org.joda.time.format._

object Utils {

  private val frontPattern = "YYYY-MM-DD'T'HH:mm"
  private val backPattern = "YYYY-MM-DD' 'HH:mm:ss"

  val frontTimeFormat: DateTimeFormatter = DateTimeFormat.forPattern(frontPattern)
  val backTimeFormat: DateTimeFormatter = DateTimeFormat.forPattern(backPattern)


}
