package ru.acteek.weather

import org.scalatest._
import ru.acteek.weather.storage.Storage
import ru.acteek.weather.storage.Storage
import org.scalamock.scalatest.MockFactory

import scala.concurrent.Future

abstract class BaseSpec extends WordSpec with Matchers with MockFactory {

  val pathStatic = "src/main/resources/ui"
  val storageMock = mock[Storage]

  (storageMock.getMetrics _)
    .expects("Moscow","2017-09-05T19:00","2017-09-05T20:00")
    .returning(Future.successful{"ok"})
}
