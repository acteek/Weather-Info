package com.github.acteek.weather

import cats.effect._
import com.github.acteek.weather.Config.ServiceConf
import org.http4s.{HttpRoutes, StaticFile}
import org.http4s.dsl.Http4sDsl
import io.circe.syntax._
import cats.implicits._
import org.http4s.implicits._
import org.http4s.server.blaze._
import fs2.Stream

import scala.concurrent.ExecutionContext

trait Server[F[_]] {
  def run: Stream[F, ExitCode]
}

object Server {

  def apply[F[_]: ConcurrentEffect: Timer: ContextShift](
        repository: Repository[F]
      , conf: ServiceConf
      , blocker: Blocker
      , ex: ExecutionContext
  ): Server[F] = new Server[F] with Http4sDsl[F] {

    private val service =
      HttpRoutes
        .of[F] {
          case req @ GET -> Root / "metrics" =>
            val city = req.params("city")
            val from = req.params("from").toLong
            val to   = req.params("to").toLong

            repository
              .getMetricsByCity(city, from, to)
              .flatMap(m => Ok(m.asJson.spaces2))

          case GET -> Root =>
            StaticFile
              .fromResource("ui/index.html", blocker)
              .getOrElseF(NotFound())

          case GET -> path =>
            StaticFile
              .fromResource(s"ui/$path", blocker)
              .getOrElseF(NotFound())

        }
        .orNotFound

    def run: Stream[F, ExitCode] =
      BlazeServerBuilder[F](ex)
        .bindHttp(conf.port, "0.0.0.0")
        .withHttpApp(service)
        .withoutBanner
        .serve

  }

}
