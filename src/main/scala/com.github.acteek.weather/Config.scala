package com.github.acteek.weather

import cats.effect.IO
import pureconfig._
import pureconfig.generic.auto._

import scala.concurrent.duration.FiniteDuration
import scala.util.Try

object Config {

  case class Cache(expire: FiniteDuration, size: Int)
  case class Api(url: String, token: String)

  case class ServiceConf(port: Int, api: Api, cache: Cache)

  def load: IO[ServiceConf] =
    IO.fromTry {
      Try(ConfigSource.default.loadOrThrow[ServiceConf])
    }

}
