package io.github.jousby.lambda.http

import org.scalatest.FlatSpec

class HttpResponseSpec extends FlatSpec {

  "A HttpResponse" should "convert to json" in {
    val httpResponse = HttpResponse(body = Some("hello"))
    val json = httpResponse.toJsonString
    println(json)
  }

}
