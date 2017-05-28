package io.github.jousby.lambda.http.model

import java.io.InputStream

import com.amazonaws.services.lambda.runtime.LambdaLogger
import io.github.jousby.lambda.http.json.SimpleJsonParser

import scala.io.Source
import scala.util.Try

/**
  * Model the expected json input for api gateway driven lambda functions.
  *
  * Input format specification is documented at:
  * https://docs.aws.amazon.com/apigateway/latest/developerguide/api-gateway-set-up-simple-proxy.html#api-gateway-simple-proxy-for-lambda-input-format
  *
  * {
  *    "resource": "Resource path",
  *    "path": "Path parameter",
  *    "httpMethod": "Incoming request's method name"
  *    "headers": {Incoming request headers}
  *    "queryStringParameters": {query string parameters } (Optional)
  *    "pathParameters":  {path parameters}
  *    "stageVariables": {Applicable stage variables}
  *    "requestContext": {Request context, including authorizer-returned key-value pairs}
  *    "body": "A JSON string of the request payload."
  *    "isBase64Encoded": "A boolean flag to indicate if the applicable request payload is Base64-encode"
  * }
  *
  */
case class HttpRequest(resource: String,
                       path: String,
                       httpMethod: String,
                       headers: Map[HttpHeaderKey, HttpHeaderValue],
                       requestContext: Map[String, String],
                       isBase64Encoded: Boolean,
                       queryStringParameters: Option[Map[String, String]] = None,
                       pathParameters: Option[Map[String, String]] = None,
                       stageVariables: Option[Map[String, String]] = None,
                       body: Option[String] = None)


object HttpRequest {

  def fromInputStream(is: InputStream, logger: LambdaLogger): Try[HttpRequest] = {
    // read json string from stream
    val json = Source.fromInputStream(is).mkString
    logger.log(s"Request json: $json")

    val request = parseRequestJson(json)
    logger.log(s"Request object: $request")

    request
  }

  import SimpleJsonParser._

  def parseRequestJson(json: String): Try[HttpRequest] = {
    val parser = SimpleJsonParser()
    val parseResult: Try[Map[String, Any]] = parser.parse(json)

    val request: Try[HttpRequest] = parseResult.flatMap(result => {
      val resource: Try[String] = getRequiredAttribute(result, "resource")
      val path: Try[String] = getRequiredAttribute(result, "path")
      val httpMethod: Try[String] = getRequiredAttribute(result, "httpMethod")
      val headers: Try[Map[HttpHeaderKey, HttpHeaderValue]] = getRequiredAttribute(result, "headers")
      val requestContext: Try[Map[String, String]] = getRequiredAttribute(result, "requestContext")
      val isBase64Encoded: Try[Boolean] = getRequiredAttribute(result, "isBase64Encoded")
      val queryStringParameters: Option[Map[String, String]] = getOptionalAttribute(result, "queryStringParameters")
      val pathParameters: Option[Map[String, String]] = getOptionalAttribute(result, "pathParameters")
      val stageVariables: Option[Map[String, String]] = getOptionalAttribute(result, "stageVariables")
      val body: Option[String] = getOptionalAttribute(result, "body")

      for {
        r <- resource
        p <- path
        hm <- httpMethod
        h <- headers
        rc <- requestContext
        i <- isBase64Encoded
      } yield {
        HttpRequest(r, p, hm, h, rc, i, queryStringParameters, pathParameters, stageVariables, body)
      }
    })

    request
  }
}

