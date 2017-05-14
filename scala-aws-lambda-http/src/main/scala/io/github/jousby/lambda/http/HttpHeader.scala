package io.github.jousby.lambda.http


case class HttpHeader(key: HttpHeaderKey, value: HttpHeaderValue) {
  def toJsonString: String =  s""""$key":"$value"""
}
