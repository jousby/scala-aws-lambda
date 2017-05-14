package hello

import com.amazonaws.services.lambda.runtime.Context
import io.github.jousby.lambda.http.{HttpRequest, HttpRequestStreamHandler, HttpResponse}

import scala.util.Try

class Handler extends HttpRequestStreamHandler {
  def handleHttpRequest(request: HttpRequest, context: Context): Try[HttpResponse] = Try {
    HttpResponse(body = Some("hello"))
  }
}
