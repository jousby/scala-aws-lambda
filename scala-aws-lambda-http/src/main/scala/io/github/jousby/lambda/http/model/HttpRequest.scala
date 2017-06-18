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
  *    "pathParameters":  {path parameters} (Optional)
  *    "stageVariables": {Applicable stage variables} (Optional)
  *    "requestContext": {Request context, including authorizer-returned key-value pairs}
  *    "body": "A JSON string of the request payload." (Optional)
  *    "isBase64Encoded": "A boolean flag to indicate if the applicable request payload is Base64-encode"
  * }
  *
  */
case class HttpRequest(resource: String,
                       path: String,
                       httpMethod: String,
                       headers: Map[HttpHeaderKey, HttpHeaderValue],
                       requestContext: RequestContext,
                       isBase64Encoded: Boolean,
                       queryStringParameters: Option[Map[String, String]] = None,
                       pathParameters: Option[Map[String, String]] = None,
                       stageVariables: Option[Map[String, String]] = None,
                       body: Option[String] = None)

case class RequestContext(accountId: String,
                          resourceId: String,
                          stage: String,
                          requestId: String,
                          identity: Identity,
                          resourcePath: String,
                          httpMethod: String,
                          apiId: String,
                          path: Option[String] = None)

case class Identity(sourceIp: String,
                    userAgent: String,
                    apiKey: Option[String] = None,
                    cognitoIdentityPoolId: Option[String] = None,
                    accountId: Option[String] = None,
                    cognitoIdentityId: Option[String] = None,
                    caller: Option[String] = None,
                    accessKey: Option[String] = None,
                    cognitoAuthenticationType: Option[String] = None,
                    cognitoAuthenticationProvider: Option[String] = None,
                    userArn: Option[String] = None,
                    user: Option[String] = None)


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
      val requestContext: Try[RequestContext] =
        getRequiredAttribute[Map[String, Any]](result, "requestContext").flatMap(buildRequestContext(_))
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

  def buildRequestContext(result: Map[String, Any]): Try[RequestContext] = {
    val accountId: Try[String] = getRequiredAttribute(result, "accountId")
    val resourceId: Try[String] = getRequiredAttribute(result, "resourceId")
    val stage: Try[String] = getRequiredAttribute(result, "stage")
    val requestId: Try[String] = getRequiredAttribute(result, "requestId")
    val identity: Try[Identity] = getRequiredAttribute[Map[String, Any]](result, "identity").flatMap(buildIdentity(_))
    val resourcePath: Try[String] = getRequiredAttribute(result, "resourcePath")
    val httpMethod: Try[String] = getRequiredAttribute(result, "httpMethod")
    val apiId: Try[String] = getRequiredAttribute(result, "apiId")
    val path: Option[String] = getOptionalAttribute(result, "path")

    for {
      a <- accountId
      resId <- resourceId
      s <- stage
      reqId <- requestId
      i <- identity
      rp <- resourcePath
      hm <- httpMethod
      aId <- apiId
    } yield {
      RequestContext(a, resId, s, reqId, i, rp, hm, aId, path)
    }
  }

  def buildIdentity(result: Map[String, Any]): Try[Identity] = {
    val sourceIp: Try[String] = getRequiredAttribute(result, "sourceIp")
    val userAgent: Try[String] = getRequiredAttribute(result, "userAgent")
    val apiKey: Option[String] = getOptionalAttribute(result, "apiKey")
    val cognitoIdentityPoolId: Option[String] = getOptionalAttribute(result, "cognitoIdentityPoolId")
    val accountId: Option[String] = getOptionalAttribute(result, "accountId")
    val cognitoIdentityId: Option[String] = getOptionalAttribute(result, "cognitoIdentityId")
    val caller: Option[String] = getOptionalAttribute(result, "caller")
    val accessKey: Option[String] = getOptionalAttribute(result, "accessKey")
    val cognitoAuthenticationType: Option[String] = getOptionalAttribute(result, "cognitoAuthenticationType")
    val cognitoAuthenticationProvider: Option[String] = getOptionalAttribute(result, "cognitoAuthenticationProvider")
    val userArn: Option[String] = getOptionalAttribute(result, "userArn")
    val user: Option[String] = getOptionalAttribute(result, "user")

    for {
      s <- sourceIp
      u <- userAgent
    } yield {
      Identity(s, u, apiKey, cognitoIdentityPoolId, accountId, cognitoIdentityId, caller, accessKey,
        cognitoAuthenticationType, cognitoAuthenticationProvider, userArn, user)
    }
  }
}

