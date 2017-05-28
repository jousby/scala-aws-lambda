package demo


import org.scalatest.FlatSpec
import org.scalatest.Matchers._


class ImageResponseSpec extends FlatSpec {

  "A image" should " be encoded" in {
    val encodedImage = ImageResponse.encodeImage("scala-logo.png")

    encodedImage should not be empty
  }

}
