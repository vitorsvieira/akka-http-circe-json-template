## akka-http-circe-json-template

[![Build Status](https://travis-ci.org/vitorsvieira/akka-http-circe-json-template.svg?branch=master)](https://travis-ci.org/vitorsvieira/akka-http-circe-json-template)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

REST API Project Template using Akka HTTP 10.0.4 with Circe 0.7.0 targeting Scala 2.12.x.

This project template proudly uses:
 
 * [circe](https://github.com/circe/circe) version 0.7.0, is a JSON library for Scala [circe.github.io](https://circe.github.io/circe/)
 * [akka-http-json](https://github.com/hseeberger/akka-http-json) version 1.12.0, is a supplementary JSON support for Akka HTTP from [@hseeberger](https://github.com/hseeberger)
 * [akka-http-cors](https://github.com/lomigmegard/akka-http-cors) version 0.1.11, adds a mechanism to enable cross origin requests for Akka HTTP from [@lomigmegard](https://github.com/lomigmegard)


## Usage

Start services with sbt:

```
$ sbt
> run
```

You can send requests to the following paths:

```
$ curl http://localhost:8000/api/v1/service1/status
$ curl http://localhost:8000/api/v1/service1/models/<int>
$ curl http://localhost:8000/api/v1/service1/modelsByName/<string>
$ curl -X POST -H 'Content-Type: application/json' http://localhost:8000/api/v2/service1/model -d 
    '{
        "vString": "akka-http-circe",
        "vInt":42, 
        "vLong":100000000,
        "vFloat":2.4,
        "vDouble":4.2,
        "vSeqInt":[1,2,3],
        "vListInt":[4,5,6]
     }'
```

The routes with logging enabled are under the `/v2/` path as below:

```
$ curl http://localhost:8000/api/v2/service1/status
$ curl http://localhost:8000/api/v2/service1/models/<int>
$ curl http://localhost:8000/api/v2/service1/modelsByName/<string>
$ curl -X POST -H 'Content-Type: application/json' http://localhost:8000/api/v2/service1/model -d 
    '{
        "vString": "akka-http-circe",
        "vInt":42, 
        "vLong":100000000,
        "vFloat":2.4,
        "vDouble":4.2,
        "vSeqInt":[1,2,3],
        "vListInt":[4,5,6]
     }'
```

You will be able to see in the console the requests reaching the `/v2/` as below:

```
Thread:[akka-http-circe-json-akka.actor.default-dispatcher-16] DEBUG akka.actor.ActorSystemImpl - log-service: Response for
Request : HttpRequest(HttpMethod(POST),http://localhost:8000/api/v2/service1/model,List(Host: localhost:8000, User-Agent: curl/7.51.0, Accept: */*, Timeout-Access: <function1>),
HttpEntity.Strict(application/json,{"vString": "akka-http-circe", "vInt":42, "vLong":100000000, "vFloat":2.4,"vDouble":4.2,"vSeqInt":[1,2,3],"vListInt":[4,5,6]}),HttpProtocol(HTTP/1.1))
Response: Complete(HttpResponse(200 OK,List(),HttpEntity.Strict(application/json,{"message":"model.vString: akka-http-circe - model.vListInt: List(4, 5, 6)"}),HttpProtocol(HTTP/1.1)))
```

## License ##

This code is open source software licensed under the [Apache 2.0 License](http://www.apache.org/licenses/LICENSE-2.0.html).
