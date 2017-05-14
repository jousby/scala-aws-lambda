package io.github.jousby.lambda.http

import java.io.{InputStream, OutputStream}

import com.amazonaws.services.lambda.runtime.{Context, RequestStreamHandler}

import scala.util.{Failure, Success, Try}

abstract class HttpRequestStreamHandler extends RequestStreamHandler {

  override def handleRequest(in: InputStream, out: OutputStream, context: Context): Unit = {
    val httpRequest = HttpRequest()

    val jsonHttpResponse = handleHttpRequest(httpRequest, context) match {
      case Success(httpResponse) => {
        httpResponse.toJsonString
      }
      case Failure(ex) => {
        // log error to cloudwatch
        val logger = context.getLogger
        logger.log(ex.getStackTrace.toString)

        // return a 500 internal server error
        HttpResponse(statusCode = HttpStatusCode.CODE_500_INTERNAL_SERVER_ERROR).toJsonString
      }
    }

    out.write(jsonHttpResponse.getBytes)
    out.close()
  }

  def handleHttpRequest(request: HttpRequest, context: Context): Try[HttpResponse]
}
