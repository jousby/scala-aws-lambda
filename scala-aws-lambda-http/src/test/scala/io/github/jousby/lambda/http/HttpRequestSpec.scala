package io.github.jousby.lambda.http

import io.circe._
import io.circe.parser._
import io.github.jousby.lambda.http.model.{HttpRequest, HttpResponse}
import org.scalatest.FlatSpec
import org.scalatest.Matchers._

class HttpRequestSpec extends FlatSpec {

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

  import HttpRequest._

  "A HttpRequest" should "parse from the example json" in {
//    val expected = HttpRequest("", "", "", Map.empty, Map.empty, false)
    val json =
      """{
        |  "resource": "/{proxy+}",
        |  "path": "/hello/world",
        |  "httpMethod": "POST",
        |  "headers": {
        |    "Accept": "*/*",
        |    "Accept-Encoding": "gzip, deflate",
        |    "cache-control": "no-cache",
        |    "CloudFront-Forwarded-Proto": "https",
        |    "CloudFront-Is-Desktop-Viewer": "true",
        |    "CloudFront-Is-Mobile-Viewer": "false",
        |    "CloudFront-Is-SmartTV-Viewer": "false",
        |    "CloudFront-Is-Tablet-Viewer": "false",
        |    "CloudFront-Viewer-Country": "US",
        |    "Content-Type": "application/json",
        |    "headerName": "headerValue",
        |    "Host": "gy415nuibc.execute-api.us-east-1.amazonaws.com",
        |    "Postman-Token": "9f583ef0-ed83-4a38-aef3-eb9ce3f7a57f",
        |    "User-Agent": "PostmanRuntime/2.4.5",
        |    "Via": "1.1 d98420743a69852491bbdea73f7680bd.cloudfront.net (CloudFront)",
        |    "X-Amz-Cf-Id": "pn-PWIJc6thYnZm5P0NMgOUglL1DYtl0gdeJky8tqsg8iS_sgsKD1A==",
        |    "X-Forwarded-For": "54.240.196.186, 54.182.214.83",
        |    "X-Forwarded-Port": "443",
        |    "X-Forwarded-Proto": "https"
        |  },
        |  "queryStringParameters": {
        |    "name": "me"
        |  },
        |  "pathParameters": {
        |    "proxy": "hello/world"
        |  },
        |  "stageVariables": {
        |    "stageVariableName": "stageVariableValue"
        |  },
        |  "requestContext": {
        |    "accountId": "12345678912",
        |    "resourceId": "roq9wj",
        |    "stage": "testStage",
        |    "requestId": "deef4878-7910-11e6-8f14-25afc3e9ae33",
        |    "identity": {
        |      "cognitoIdentityPoolId": null,
        |      "accountId": null,
        |      "cognitoIdentityId": null,
        |      "caller": null,
        |      "apiKey": null,
        |      "sourceIp": "192.168.196.186",
        |      "cognitoAuthenticationType": null,
        |      "cognitoAuthenticationProvider": null,
        |      "userArn": null,
        |      "userAgent": "PostmanRuntime/2.4.5",
        |      "user": null
        |    },
        |    "resourcePath": "/{proxy+}",
        |    "httpMethod": "POST",
        |    "apiId": "gy415nuibc"
        |  },
        |  "body": "{\r\n\t\"a\": 1\r\n}",
        |  "isBase64Encoded": false
        |}
      """.stripMargin

    println(parseRequestJson(json))
  }

  it should "handle a real path only example" in {
    val json =
      """{
        |    "resource": "/echo",
        |    "path": "/echo",
        |    "httpMethod": "GET",
        |    "headers": {
        |        "Accept": "*/*",
        |        "CloudFront-Forwarded-Proto": "https",
        |        "CloudFront-Is-Desktop-Viewer": "true",
        |        "CloudFront-Is-Mobile-Viewer": "false",
        |        "CloudFront-Is-SmartTV-Viewer": "false",
        |        "CloudFront-Is-Tablet-Viewer": "false",
        |        "CloudFront-Viewer-Country": "AU",
        |        "Host": "ie4e348qt6.execute-api.us-east-1.amazonaws.com",
        |        "User-Agent": "curl/7.47.0",
        |        "Via": "1.1 d4dbc6987ddd22a023698236d3f09b02.cloudfront.net (CloudFront)",
        |        "X-Amz-Cf-Id": "QulVPjmWSVSm8eHiY7XtG-YzfjYCfo5y22W7UllDwaeN96Fc7SJMbA==",
        |        "X-Amzn-Trace-Id": "Root=1-594632e8-7d14cf714a172e0f392a0ac6",
        |        "X-Forwarded-For": "115.64.13.44, 54.240.152.126",
        |        "X-Forwarded-Port": "443",
        |        "X-Forwarded-Proto": "https"
        |    },
        |    "queryStringParameters": null,
        |    "pathParameters": null,
        |    "stageVariables": null,
        |    "requestContext": {
        |        "path": "/dev/echo",
        |        "accountId": "689889572183",
        |        "resourceId": "v54m0g",
        |        "stage": "dev",
        |        "requestId": "1294925f-53fc-11e7-abd6-41d27f9d7fb1",
        |        "identity": {
        |            "cognitoIdentityPoolId": null,
        |            "accountId": null,
        |            "cognitoIdentityId": null,
        |            "caller": null,
        |            "apiKey": "",
        |            "sourceIp": "115.64.13.44",
        |            "accessKey": null,
        |            "cognitoAuthenticationType": null,
        |            "cognitoAuthenticationProvider": null,
        |            "userArn": null,
        |            "userAgent": "curl/7.47.0",
        |            "user": null
        |        },
        |        "resourcePath": "/echo",
        |        "httpMethod": "GET",
        |        "apiId": "ie4e348qt6"
        |    },
        |    "body": null,
        |    "isBase64Encoded": false
        |}
      """.stripMargin

    println(parseRequestJson(json))
  }
}
