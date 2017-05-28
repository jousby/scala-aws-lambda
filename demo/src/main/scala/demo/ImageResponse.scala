package demo

import java.io.InputStream
import java.nio.charset.StandardCharsets
import java.util.Base64

import com.amazonaws.services.lambda.runtime.Context
import io.github.jousby.lambda.http._
import io.github.jousby.lambda.http.model.{HttpRequest, HttpResponse}
import org.apache.commons.io.IOUtils

import scala.util.Try


class ImageResponse extends HttpRequestStreamHandler {
  import ImageResponse._

  def handleHttpRequest(request: HttpRequest, context: Context): Try[HttpResponse] = Try {
    val encodedImage: String = encodeImage("add-128.png")

    val response = HttpResponse(
      body = Some(encodedImage),
      isBase64Encoded = true)

    context.getLogger.log(s"$response")

    response
  }
}

object ImageResponse {
  def encodeImage(filename: String): String = {
    // load image
    val imageStream: InputStream = this.getClass.getClassLoader.getResourceAsStream(filename)

    // to base64 string
    val bytes = IOUtils.toByteArray(imageStream)
    new String(Base64.getEncoder().encode(bytes), StandardCharsets.UTF_8)
  }
}
