package com.github.acteek.weather

import cats.effect._
import org.http4s.client.blaze._
import fs2.Stream
import scala.concurrent.ExecutionContext.global

object Application extends IOApp {

  def run(args: List[String]): IO[ExitCode] =
    (for {
      config     <- Stream.eval(Config.load)
      blocker    <- Stream.resource(Blocker[IO])
      http       <- Stream.resource(BlazeClientBuilder[IO](global).resource)
      api        <- Stream.eval(IO(ApiClient.apply[IO](http, config.api)))
      repository <- Stream.eval(IO(Repository[IO](config.cache, api)))
      exitCode   <- Server[IO](repository, config, blocker, global).run
    } yield exitCode).compile.lastOrError

}
