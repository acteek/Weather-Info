//package ru.acteek.weather
//
//import org.scalacheck.Gen
//import org.scalatest.prop.PropertyChecks
//
//
//class UtilsSpec extends BaseSpec with PropertyChecks {
//
//  import ru.acteek.weather.utils.Utils._
//  case class Wind(deg: Gen[Double], direction: String)
//
//  val winds = List[Wind](
//    Wind(Gen.choose[Double](11.25, 33.75), "NNE"),
//    Wind(Gen.choose[Double](33.75, 56.25), "NE"),
//    Wind(Gen.choose[Double](56.25, 78.75), "ENE"),
//    Wind(Gen.choose[Double](78.75, 101.25), "E"),
//    Wind(Gen.choose[Double](101.25, 123.75), "ESE"),
//    Wind(Gen.choose[Double](123.75, 146.25), "SE"),
//    Wind(Gen.choose[Double](146.25, 168.75), "SSE"),
//    Wind(Gen.choose[Double](168.75, 191.25), "S"),
//    Wind(Gen.choose[Double](191.25, 213.75), "SSW"),
//    Wind(Gen.choose[Double](213.75, 236.25), "SW"),
//    Wind(Gen.choose[Double](236.25, 258.75), "WSW"),
//    Wind(Gen.choose[Double](258.75, 281.25), "W"),
//    Wind(Gen.choose[Double](281.25, 303.75), "WNW"),
//    Wind(Gen.choose[Double](303.75, 326.25), "NW"),
//    Wind(Gen.choose[Double](326.25, 348.75), "NNW")
//  )
//
//  "Method windDirectionByDegrees" when {
//    winds.foreach { w =>
//      s"wind degrees in ${w.direction}" should {
//        s"return valid direction ${w.direction}" in {
//          forAll(w.deg) { deg: Double =>
//            windDirectionByDegrees(deg) shouldBe w.direction
//          }
//        }
//      }
//    }
//  }
//
//}
//
