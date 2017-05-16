package io.github.jousby.lambda.http


/**
  * Not an exhaustive list of predefined codes but my view on the ones you are most likely to need in a lambda function.
  *
  * See [[https://en.wikipedia.org/wiki/List_of_HTTP_status_codes status codes]] for a more comprehensive list.
  */
object HttpStatusCode {
  val CODE_200_OK = 200

  val CODE_301_MOVED_PERMANENTLY = 301
  val CODE_302_FOUND = 302
  val CODE_303_SEE_OTHER = 303

  val CODE_400_BAD_REQUEST = 400
  val CODE_401_UNAUTHORIZED = 401
  val CODE_403_FORBIDDEN = 403
  val CODE_404_NOT_FOUND = 404

  val CODE_500_INTERNAL_SERVER_ERROR = 500
  val CODE_501_NOT_IMPLEMENTED = 501
  val CODE_502_BAD_GATEWAY = 502
  val CODE_503_SERVICE_UNAVAILABLE = 503
  val CODE_504_GATEWAY_TIMEOUT = 504
}


