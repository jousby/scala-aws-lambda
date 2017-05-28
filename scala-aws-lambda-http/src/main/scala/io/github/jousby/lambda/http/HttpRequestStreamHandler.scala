package io.github.jousby.lambda.http

import java.io.{InputStream, OutputStream}

import com.amazonaws.services.lambda.runtime.{Context, RequestStreamHandler}
import io.github.jousby.lambda.http.model.{HttpRequest, HttpResponse, HttpStatus}

import scala.util.{Failure, Success, Try}

abstract class HttpRequestStreamHandler extends RequestStreamHandler {

  override def handleRequest(in: InputStream, out: OutputStream, context: Context): Unit = {
    val logger = context.getLogger

    val httpRequest: Try[HttpRequest] = HttpRequest.fromInputStream(in, logger)
    val httpResponse: Try[HttpResponse] = httpRequest.flatMap(handleHttpRequest(_, context))

    val jsonHttpResponse = httpResponse match {
      case Success(httpResponse) => httpResponse.toJsonString
      case Failure(ex) => {
        // log error to cloudwatch
        logger.log(ex.getStackTrace.toString)

        // return a 500 internal server error
        HttpResponse(statusCode = HttpStatus.CODE_500_INTERNAL_SERVER_ERROR).toJsonString
      }
    }

    out.write(jsonHttpResponse.getBytes)
    out.close()
  }

  def handleHttpRequest(request: HttpRequest, context: Context): Try[HttpResponse]
}
