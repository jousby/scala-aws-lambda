package io.github.jousby.lambda.http

import org.scalatest.FlatSpec
import org.scalatest.Matchers._
import io.circe._
import io.circe.parser._
import io.github.jousby.lambda.http.model.{HttpHeader, HttpHeaderKeys, HttpResponse, HttpStatus}

class HttpResponseSpec extends FlatSpec {

  def compareJson(response: HttpResponse, expected: String) = {
    val parseAttempt: Either[ParsingFailure, Json] = parse(response.toJsonString)

    val prettyJson: String = parseAttempt match {
      case Left(failure) => {
        println(response.toJsonString)
        throw failure
      }
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
    val response = HttpResponse(HttpStatus.CODE_500_INTERNAL_SERVER_ERROR)
    val expected =
      """{
        |  "statusCode" : 500
        |}""".stripMargin

    compareJson(response, expected)
  }

  it should "handle custom headers" in {
    val customHeaders = Seq(
      HttpHeader(HttpHeaderKeys.AccessControlAllowOrigin, "*"),
      HttpHeader(HttpHeaderKeys.Age, "12"))
    val response = HttpResponse(headers = customHeaders)
    val expected =
      """{
        |  "statusCode" : 200,
        |  "headers" : {
        |    "Access-Control-Allow-Origin" : "*",
        |    "Age" : "12"
        |  }
        |}""".stripMargin

    compareJson(response, expected)
  }

  it should "handle a custom body and custom headers" in {
    val customHeaders = Seq(
      HttpHeader(HttpHeaderKeys.AccessControlAllowOrigin, "*"),
      HttpHeader(HttpHeaderKeys.Age, "12"))
    val response = HttpResponse(body = Some("hello"), headers = customHeaders)
    val expected =
      """{
        |  "statusCode" : 200,
        |  "body" : "hello",
        |  "headers" : {
        |    "Access-Control-Allow-Origin" : "*",
        |    "Age" : "12"
        |  }
        |}""".stripMargin

    compareJson(response, expected)
  }

  it should "handle a response specifying all attributes" in {
    val customHeaders = Seq(
      HttpHeader(HttpHeaderKeys.ContentType, "image/png"),
      HttpHeader(HttpHeaderKeys.Age, "12"))
    val response = HttpResponse(
      isBase64Encoded = true,
      statusCode = 200,
      body = Some("ASPDUNASDOUNA"),
      headers = customHeaders)

    val expected =
      """{
        |  "isBase64Encoded" : true,
        |  "statusCode" : 200,
        |  "body" : "ASPDUNASDOUNA",
        |  "headers" : {
        |    "Content-Type" : "image/png",
        |    "Age" : "12"
        |  }
        |}""".stripMargin

    compareJson(response, expected)
  }
}
