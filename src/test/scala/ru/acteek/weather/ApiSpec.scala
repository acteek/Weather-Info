package ru.acteek.weather

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.MissingQueryParamRejection
import akka.http.scaladsl.testkit.ScalatestRouteTest

import scala.io.Source.fromFile

class ApiSpec extends BaseSpec with ScalatestRouteTest {

  import Application.route

  val testRoute = route(storageMock)
  val requestMetrics =
    s"/metrics?city=${data.city}&date-from=${data.dateFrom}&date-to=${data.dateTo}"

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
        Get(requestMetrics) ~> testRoute ~> check {
          status shouldBe StatusCodes.OK
        }
      }
      "return valid response" in {
        Get(requestMetrics) ~> testRoute ~> check {
          responseAs[String] shouldBe apiValidJson
        }
      }
    }
    "get metrics without param city" should {
      "rejection missing param city" in {
        Get(s"/metrics") ~> testRoute ~> check {
          rejection shouldBe MissingQueryParamRejection("city")
        }
      }
    }
    "get metrics without param date-from" should {
      "rejection missing param date-from" in {
        Get(s"/metrics?city=${data.city}") ~> testRoute ~> check {
          rejection shouldBe MissingQueryParamRejection("date-from")
        }
      }
    }
    "get metrics without param date-to" should {
      "rejection missing param date-to" in {
        Get(s"/metrics?city=${data.city}&date-from=${data.dateFrom}") ~> testRoute ~> check {
          rejection shouldBe MissingQueryParamRejection("date-to")
        }
      }
    }
  }
}
