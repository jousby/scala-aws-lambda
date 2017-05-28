package demo

import com.amazonaws.services.lambda.runtime.Context
import io.github.jousby.lambda.http.HttpRequestStreamHandler
import io.github.jousby.lambda.http.model.{HttpRequest, HttpResponse}

import scala.util.Try


class SimpleOkResponse extends HttpRequestStreamHandler {
  def handleHttpRequest(request: HttpRequest, context: Context): Try[HttpResponse] = Try {
    HttpResponse()
  }
}
