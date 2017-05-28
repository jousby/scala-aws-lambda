package demo

import com.amazonaws.services.lambda.runtime.Context
import io.github.jousby.lambda.http._
import io.github.jousby.lambda.http.model._

import scala.util.Try


class SimpleHelloResponseWithHeaders extends HttpRequestStreamHandler {
  def handleHttpRequest(request: HttpRequest, context: Context): Try[HttpResponse] = Try {
    val customHeaders = Seq(
      HttpHeader(HttpHeaderKeys.AccessControlAllowOrigin, "*"),
      HttpHeader(HttpHeaderKeys.Age, "12"))
    HttpResponse(body = Some("hello"), headers = customHeaders)
  }
}
