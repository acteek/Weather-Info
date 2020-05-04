package com.github.acteek.weather

import cats.effect.Sync
import cats.implicits._
import com.github.acteek.weather.Types._
import com.github.blemale.scaffeine.{Cache, Scaffeine}

trait Repository[F[_]] {

  def getMetricsByCity(name: CityName, from: InstantSec, to: InstantSec): F[List[Metrics]]

}
object Repository {
  def apply[F[_]: Sync](conf: Config.Cache, api: ApiClient[F]): Repository[F] = new Repository[F] {

    private val cache: Cache[CityName, City] =
      Scaffeine()
        .expireAfterWrite(conf.expire)
        .maximumSize(conf.size)
        .build[CityName, City]()

    def getMetricsByCity(name: CityName, from: InstantSec, to: InstantSec): F[List[Metrics]] =
      for {
        cityOpt <- Sync[F].delay(cache.getIfPresent(name))
        city <- cityOpt.fold {
                 api
                   .getCityByName(name)
                   .flatTap(city => Sync[F].delay(cache.put(name, city)))
               }(Sync[F].pure(_))
      } yield city.metrics
        .rangeFrom(from)
        .rangeTo(to)
        .values
        .toList

  }
}
