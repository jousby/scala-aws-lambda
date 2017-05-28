package io.github.jousby.lambda.http.json

import scala.util.{Failure, Success, Try}
import scala.util.parsing.combinator.JavaTokenParsers


case class JsonParseException(private val message: String = "",
                              private val cause: Throwable = None.orNull)
  extends Exception(message, cause)

// Simple json parser from "Programming in Scala" 2nd Edition
class SimpleJsonParser extends JavaTokenParsers {
  import SimpleJsonParser._

  def obj: Parser[Map[String, Any]] =
    "{"~> repsep(member, ",") <~"}" ^^ (Map() ++ _)

  def arr: Parser[List[Any]] =
    "["~> repsep(value, ",") <~"]"

  def member: Parser[(String, Any)] =
    stringLiteral~":"~value ^^
      { case name~":"~value => (stripQuotes(name), value) }

  def value: Parser[Any] = (
    obj
      | arr
      | stringLiteral ^^ (stripQuotes(_))
      | floatingPointNumber ^^ (_.toDouble)
      | "null" ^^ (x => null)
      | "true" ^^ (x => true)
      | "false" ^^ (x => false)
    )

  def parse(input: String): Try[Map[String, Any]] = parseAll(obj, input) match {
    case Success(result, _) => scala.util.Success(result)
    case failure : NoSuccess => scala.util.Failure(new JsonParseException(failure.msg))
  }

}

object SimpleJsonParser {
  def apply(): SimpleJsonParser = new SimpleJsonParser

  def stripQuotes(s: String): String =
    if (s.head == '\"' && s.last == '\"') s.drop(1).dropRight(1) else s

  def getRequiredAttribute[T](parseResult: Map[String, Any], attributeName: String): Try[T] = {
    parseResult.get(attributeName) match {
      case Some(a) => Try {
        if (a == null)
          throw new JsonParseException(s"Required attribute ($attributeName) was null.")
        else
          a.asInstanceOf[T]
      }
      case None => Failure(new JsonParseException(s"Required attribute ($attributeName) was not found."))
    }
  }

  def getOptionalAttribute[T](parseResult: Map[String, Any], attributeName: String): Option[T] = {
    parseResult.get(attributeName).flatMap(x => if (x == null) None else Some(x.asInstanceOf[T]))
  }
}
