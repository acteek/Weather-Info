package com.github.acteek.weather

import cats.effect.{ConcurrentEffect, ExitCode,  Timer}
import com.github.acteek.weather.Config.ServiceConf
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import org.http4s.implicits._
import org.http4s.server.blaze._
import fs2.Stream

import scala.concurrent.ExecutionContext

trait Server[F[_]] {
  def run: Stream[F, ExitCode]
}

object Server {
//  def route(storage: Storage) =
//    get {
//      path("") {
//        getFromResource("ui/index.html")
//      } ~
//        pathPrefix("css") {
//          encodeResponse {
//            getFromResourceDirectory("ui/css")
//          }
//        } ~
//        pathPrefix("js") {
//          encodeResponse {
//            getFromResourceDirectory("ui/dist")
//          }
//        } ~
//        path("metrics") {
//          parameters("city".as[String], "date-from".as[String], "date-to".as[String]) { (city, dateFrom, dateTo) =>
//            logger.info("Received  request with city=>[{}] dateFrom=>[{}] dateTo=>[{}]", city, dateFrom, dateTo)
//            onSuccess(storage.getMetrics(city, dateFrom, dateTo)) { resp =>
//              complete(resp)
//            }
//          }
//        }
//    }
//
//
//  Http().bindAndHandle(route(storage), "0.0.0.0", port)
//  logger.info(s"Server start at http://0.0.0.0:$port/")

  def apply[F[_]: ConcurrentEffect: Timer](
        repository: Repository[F]
      , conf: ServiceConf
      , ex: ExecutionContext
  ): Server[F] = new Server[F] with Http4sDsl[F] {

    private val service =
      HttpRoutes
        .of[F] {
          case GET -> Root / "hello" / name =>
            Ok(s"Hello, $name.")
        }
        .orNotFound

    def run: Stream[F, ExitCode] =
      BlazeServerBuilder[F](ex)
        .bindHttp(conf.port, "0.0.0.0")
        .withHttpApp(service)
        .serve

  }

}
