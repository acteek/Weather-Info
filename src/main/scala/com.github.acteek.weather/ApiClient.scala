package com.github.acteek.weather

import cats.effect.Sync
import io.circe.Decoder
import org.http4s.Status.Successful
import org.http4s.Uri
import org.http4s.client.Client
import org.http4s.circe._
import cats.implicits._
import com.github.acteek.weather.Types._
import com.github.acteek.weather.Types.City

trait ApiClient[F[_]] {
  def getCityByName(name: CityName): F[City]
}

object ApiClient {
  def apply[F[_]: Sync](http: Client[F], conf: Config.Api)(implicit d: Decoder[City]): ApiClient[F] = new ApiClient[F] {

    def getCityByName(name: CityName): F[City] =
      for {
        uri <- Sync[F].fromEither(Uri.fromString(conf.url))
        params = Map("q" -> name, "APPID" -> conf.token, "units" -> "metric", "lang" -> "ru")
        req    = uri.setQueryParams(params.view.mapValues(List.apply(_)).toMap)
        city <- http.get(req) {
                 case Successful(r) => r.decodeJson[City]
                 case r =>
                   val error = new RuntimeException(s"Response has code: ${r.status.reason}")
                   Sync[F].raiseError[City](error)
               }

      } yield city

  }

//    private def makeRequest(name: Option[String], id: Option[Int]): HttpRequest = HttpRequest(
//        uri = (name, id) match {
//        case (Some(n), None) =>
//          baseUri.withQuery(
//              Query(Map("q" -> n.trim, "APPID" -> token, "units" -> "metric", "lang" -> "ru"))
//          )
//        case (None, Some(i)) =>
//          baseUri.withQuery(
//              Query(Map("id" -> i.toString, "APPID" -> token, "units" -> "metric", "lang" -> "ru"))
//          )
//        case _ => throw new NoSuchElementException(s"No city name or id for  get metrics!")
//      }
//    )

}
