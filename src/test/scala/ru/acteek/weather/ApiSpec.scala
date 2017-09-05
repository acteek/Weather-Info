package ru.acteek.weather

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalamock.scalatest.MockFactory
import scala.io.Source.fromFile

class ApiSpec extends BaseSpec with ScalatestRouteTest {

  import Application.route
  val testRoute = route(storageMock)

  "Http Api" when {
    "get root request " should {
      "return status 200" in {
        Get("/") ~> testRoute ~> check {
          status shouldBe StatusCodes.OK
        }
      }
      "return valid content" in {
        Get("/") ~> testRoute ~> check {
          responseAs[String] shouldBe
            fromFile(s"$pathStatic/index.html").mkString
        }
      }
    }
    "get js request" should {
      "return status 200" in {
        Get("/js/build.js") ~> testRoute ~> check {
          status shouldBe StatusCodes.OK
        }
      }
      "return valid js content" in {
        Get("/js/build.js") ~> testRoute ~> check {
          responseAs[String] shouldBe
            fromFile(s"$pathStatic/dist/build.js").mkString
        }
      }
    }
    "get css request" should {
      "return status 200" in {
        Get("/css/bootstrap.min.css") ~> testRoute ~> check {
          status shouldBe StatusCodes.OK
        }
      }
      "return valid css content" in {
        Get("/css/bootstrap.min.css") ~> testRoute ~> check {
          responseAs[String] shouldBe
            fromFile(s"$pathStatic/css/bootstrap.min.css").mkString
        }
      }
    }
    "get metrics request" should {
      "return status 200" in {
        val request = "/metrics?city=Moscow&date-from=2017-09-05T19:00&date-to=2017-09-05T20:00"
        Get(request) ~> testRoute ~> check {
          status shouldBe StatusCodes.OK
        }
      }

    }

  }
}
