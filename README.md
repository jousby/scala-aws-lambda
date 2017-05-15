# Scala AWS Lambda Libraries

[![Build Status](https://travis-ci.org/jousby/scala-aws-lambda.svg?branch=master)](https://travis-ci.org/jousby/scala-aws-lambda)

Lightweight set of libraries that puts a more user friendly functional api on top of the default aws java interfaces.

### Design Goals:

* Minimal set of dependencies (just the aws provided ```aws-lambda-java-core```) to keep lamdba function artifacts small.
* Don't force the use of a particular json serialisation library. 
* Provide some basic plumbing / types that transform the fairly raw aws java intefaces into something that more 
naturally models the particular lambda use case at hand. 

