package io.github.jousby.lambda.http


case class HttpStatusCode(code: Int) {
  def toJsonString: String = code.toString
}

/**
  * Not an exhaustive list of predefined codes but my view on the ones you are most likely to need in a lambda function.
  *
  * See https://en.wikipedia.org/wiki/List_of_HTTP_status_codes for a more comprehensive list.
  *
  * You can create a code manually using the case class constructor for the ones i have missed.
  */
object HttpStatusCode {
  val CODE_200_OK = HttpStatusCode(200)

  val CODE_301_MOVED_PERMANENTLY = HttpStatusCode(301)
  val CODE_302_FOUND = HttpStatusCode(302)
  val CODE_303_SEE_OTHER = HttpStatusCode(303)

  val CODE_400_BAD_REQUEST = HttpStatusCode(400)
  val CODE_401_UNAUTHORIZED = HttpStatusCode(401)
  val CODE_403_FORBIDDEN = HttpStatusCode(403)
  val CODE_404_NOT_FOUND = HttpStatusCode(404)

  val CODE_500_INTERNAL_SERVER_ERROR = HttpStatusCode(500)
  val CODE_501_NOT_IMPLEMENTED = HttpStatusCode(501)
  val CODE_502_BAD_GATEWAY = HttpStatusCode(502)
  val CODE_503_SERVICE_UNAVAILABLE = HttpStatusCode(503)
  val CODE_504_GATEWAY_TIMEOUT = HttpStatusCode(504)
}


