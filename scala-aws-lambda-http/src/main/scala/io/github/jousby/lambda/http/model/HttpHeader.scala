package io.github.jousby.lambda.http.model


case class HttpHeader(key: HttpHeaderKey, value: HttpHeaderValue) {
  def toJsonString: String =  s""""$key":"$value""""
}

/**
  * Constants for [[https://en.wikipedia.org/wiki/List_of_HTTP_header_fields standard headers]]
  */
object HttpHeaderKeys {

  // Response headers
  val AccessControlAllowOrigin: HttpHeaderKey = "Access-Control-Allow-Origin"
  val AcceptPatch: HttpHeaderKey = "Accept-Patch"
  val Age: HttpHeaderKey = "Age"
  val ContentType: HttpHeaderKey = "Content-Type"


//  Allow	Valid methods for a specified resource. To be used for a 405 Method not allowed	Allow: GET, HEAD	Permanent
//    Alt-Svc[31]	A server uses "Alt-Svc" header (meaning Alternative Services) to indicate that its resources can also be accessed at a different network location (host or port) or using a different protocol	Alt-Svc: h2="http2.example.com:443"; ma=7200	Permanent
//    Cache-Control	Tells all caching mechanisms from server to client whether they may cache this object. It is measured in seconds	Cache-Control: max-age=3600	Permanent
//    Connection	Control options for the current connection and list of hop-by-hop response fields[7]	Connection: close	Permanent
//    Content-Disposition[32]	An opportunity to raise a "File Download" dialogue box for a known MIME type with binary format or suggest a filename for dynamic content. Quotes are necessary with special characters.	Content-Disposition: attachment; filename="fname.ext"	Permanent
//    Content-Encoding	The type of encoding used on the data. See HTTP compression.	Content-Encoding: gzip	Permanent
//    Content-Language	The natural language or languages of the intended audience for the enclosed content[33]	Content-Language: da	Permanent
//    Content-Length	The length of the response body in octets (8-bit bytes)	Content-Length: 348	Permanent
//  Content-Location	An alternate location for the returned data	Content-Location: /index.htm	Permanent
//    Content-MD5	A Base64-encoded binary MD5 sum of the content of the response	Content-MD5: Q2hlY2sgSW50ZWdyaXR5IQ==	Obsolete[8]
//  Content-Range	Where in a full body message this partial message belongs	Content-Range: bytes 21010-47021/47022	Permanent
//    Date	The date and time that the message was sent (in "HTTP-date" format as defined by RFC 7231) [34]	Date: Tue, 15 Nov 1994 08:12:31 GMT	Permanent
//  ETag	An identifier for a specific version of a resource, often a message digest	ETag: "737060cd8c284d8af7ad3082f209582d"	Permanent
//  Expires	Gives the date/time after which the response is considered stale (in "HTTP-date" format as defined by RFC 7231)	Expires: Thu, 01 Dec 1994 16:00:00 GMT	Permanent: standard
//  Last-Modified	The last modified date for the requested object (in "HTTP-date" format as defined by RFC 7231)	Last-Modified: Tue, 15 Nov 1994 12:45:26 GMT	Permanent
//    Link	Used to express a typed relationship with another resource, where the relation type is defined by RFC 5988	Link: </feed>; rel="alternate"[35]	Permanent
//  Location	Used in redirection, or when a new resource has been created.	Location: http://www.w3.org/pub/WWW/People.html	Permanent
//    P3P	This field is supposed to set P3P policy, in the form of P3P:CP="your_compact_policy". However, P3P did not take off,[36] most browsers have never fully implemented it, a lot of websites set this field with fake policy text, that was enough to fool browsers the existence of P3P policy and grant permissions for third party cookies.	P3P: CP="This is not a P3P policy! See http://www.google.com/support/accounts/bin/answer.py?hl=en&answer=151657 for more info."	Permanent
//    Pragma	Implementation-specific fields that may have various effects anywhere along the request-response chain.	Pragma: no-cache	Permanent
//    Proxy-Authenticate	Request authentication to access the proxy.	Proxy-Authenticate: Basic	Permanent
//    Public-Key-Pins[37]	HTTP Public Key Pinning, announces hash of website's authentic TLS certificate	Public-Key-Pins: max-age=2592000; pin-sha256="E9CZ9INDbd+2eRQozYqqbQ2yXLVKB9+xcprMF+44U1g=";	Permanent
//  Refresh	Used in redirection, or when a new resource has been created. This refresh redirects after 5 seconds.	Refresh: 5; url=http://www.w3.org/pub/WWW/People.html	Proprietary and non-standard: a header extension introduced by Netscape and supported by most web browsers.
//  Retry-After	If an entity is temporarily unavailable, this instructs the client to try again later. Value could be a specified period of time (in seconds) or a HTTP-date.[38]
//  Example 1: Retry-After: 120
//  Example 2: Retry-After: Fri, 07 Nov 2014 23:59:59 GMT
//  Permanent
//
//  Server	A name for the server	Server: Apache/2.4.1 (Unix)	Permanent
//    Set-Cookie	An HTTP cookie	Set-Cookie: UserID=JohnDoe; Max-Age=3600; Version=1	Permanent: standard
//  Strict-Transport-Security	A HSTS Policy informing the HTTP client how long to cache the HTTPS only policy and whether this applies to subdomains.	Strict-Transport-Security: max-age=16070400; includeSubDomains	Permanent: standard
//  Trailer	The Trailer general field value indicates that the given set of header fields is present in the trailer of a message encoded with chunked transfer coding.	Trailer: Max-Forwards	Permanent
//    Transfer-Encoding	The form of encoding used to safely transfer the entity to the user. Currently defined methods are: chunked, compress, deflate, gzip, identity.	Transfer-Encoding: chunked	Permanent
//    TSV	Tracking Status Value, value suggested to be sent in response to a DNT(do-not-track), possible values:
//  "!" — under construction
//    "?" — dynamic
//  "G" — gateway to multiple parties
//    "N" — not tracking
//    "T" — tracking
//  "C" — tracking with consent
//  "P" — tracking only if consented
//  "D" — disregarding DNT
//    "U" — updated
//  TSV: ?	Permanent
//    Upgrade	Ask the client to upgrade to another protocol.	Upgrade: HTTP/2.0, HTTPS/1.3, IRC/6.9, RTA/x11, websocket	Permanent
//    Vary	Tells downstream proxies how to match future request headers to decide whether the cached response can be used rather than requesting a fresh one from the origin server.
//    Example 1: Vary: *
//  Example 2: Vary: Accept-Language
//  Permanent
//  Via	Informs the client of proxies through which the response was sent.	Via: 1.0 fred, 1.1 example.com (Apache/1.1)	Permanent
//    Warning	A general warning about possible problems with the entity body.	Warning: 199 Miscellaneous warning	Permanent
//  WWW-Authenticate	Indicates the authentication scheme that should be used to access the requested entity.	WWW-Authenticate: Basic	Permanent

}
