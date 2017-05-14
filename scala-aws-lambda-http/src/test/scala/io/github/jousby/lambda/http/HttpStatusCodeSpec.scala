package io.github.jousby.lambda.http

import org.scalatest.FlatSpec
import org.scalatest.Matchers._

class HttpStatusCodeSpec extends FlatSpec {

  "A HttpStatusCode" should "convert to json" in {
    val code = HttpStatusCode.CODE_200_OK
    val json = code.toJsonString

    json.toString shouldBe "200"
  }
}
