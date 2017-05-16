package io.github.jousby.lambda.http

/**
  * Model the required output for api gateway driven lambda functions.
  *
  * Output format specification is documented at:
  * https://docs.aws.amazon.com/apigateway/latest/developerguide/api-gateway-set-up-simple-proxy.html#api-gateway-simple-proxy-for-lambda-output-format
  *
  * {
  *    "isBase64Encoded": true|false,
  *    "statusCode": httpStatusCode,
  *    "headers": { "headerName": "headerValue", ... },
  *    "body": "..."
  * }
  *
  * @param statusCode
  * @param body
  * @param headers
  * @param isBase64Encoded
  */
case class HttpResponse(statusCode: HttpStatusCode = HttpStatusCode.CODE_200_OK,
                        body: Option[String] = None,
                        headers: Seq[HttpHeader] = Seq.empty,
                        isBase64Encoded: Boolean = false) {

  def toJsonString: String = {
    val b = new StringBuilder

    b.append("{")

    // output status code
    b.append(s""""statusCode":${statusCode}""")

    // output body if defined
    body.foreach(s => b.append(s""","body":"$s""""))

    // output headers if defined
    if (!headers.isEmpty) {
      b.append(s""","headers":""")
      b.append(headers.map(_.toJsonString).mkString("{", ",", "}"))
    }

    // output base64 encoding flag if turned on
    if (isBase64Encoded) b.append(s""""sBase64Encoded":$isBase64Encoded""")

    b.append("}")

    b.toString()
  }
}

object HttpResponse {
  def apply(bodyValue: String): HttpResponse = HttpResponse(body = Some(bodyValue))
}



