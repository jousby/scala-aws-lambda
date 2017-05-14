package io.github.jousby.lambda.http

import org.scalatest.FlatSpec
import org.scalatest.Matchers._

import io.circe._
import io.circe.parser._

class HttpResponseSpec extends FlatSpec {

  def compareJson(response: HttpResponse, expected: String) = {
    val parseAttempt: Either[ParsingFailure, Json] = parse(response.toJsonString)

    val prettyJson: String = parseAttempt match {
      case Left(failure) => throw failure
      case Right(json) => json.spaces2
    }

    prettyJson shouldBe expected
  }


  "A HttpResponse" should "convert to json" in {
    val response = HttpResponse()
    val expected =
      """{
        |  "statusCode" : 200
        |}""".stripMargin

    compareJson(response, expected)
  }

  it should "handle passing in a custom body" in {
    val response = HttpResponse(body = Some("hello"))
    val expected =
      """{
        |  "statusCode" : 200,
        |  "body" : "hello"
        |}""".stripMargin

    compareJson(response, expected)
  }

  it should "handle the alternate body only constructor" in {
    val response = HttpResponse("hello")
    val expected =
      """{
        |  "statusCode" : 200,
        |  "body" : "hello"
        |}""".stripMargin

    compareJson(response, expected)
  }

  it should "handle a status code other than the default" in {
    val response = HttpResponse(HttpStatusCode.CODE_500_INTERNAL_SERVER_ERROR)
    val expected =
      """{
        |  "statusCode" : 500
        |}""".stripMargin

    compareJson(response, expected)
  }
}
