/*
 * Copyright 2016 Vitor Vieira
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.notvitor.http.server

import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.{ IncomingConnection, ServerBinding }
import akka.http.scaladsl.server.Route._
import akka.stream.scaladsl.{ Sink, Source }
import com.github.notvitor.http.config.ServerSettingsTemplate
import com.github.notvitor.http.routes.AkkaHttpRoutesTemplate

import scala.concurrent.Future
import scala.io.StdIn

object AkkaHttpServerTemplate extends App {

  import ServerSettingsTemplate._

  val server: Source[IncomingConnection, Future[ServerBinding]] =
    Http(actorSystem).bind(httpInterface, httpPort)

  log.info(s"\nAkka HTTP Server - Version ${actorSystem.settings.ConfigVersion} - running at http://$httpInterface:$httpPort/")

  val handler: Future[ServerBinding] =
    server
      .to(
        Sink.foreach {
          connection ⇒
            connection.handleWithAsyncHandler(asyncHandler(AkkaHttpRoutesTemplate.availableRoutes))
        }
      )
      .run()

  handler onFailure { case ex: Exception ⇒ log.error(ex, "Failed to bind to {}:{}", httpInterface, httpPort) }

  StdIn.readLine(s"\nPress RETURN to stop...")

  handler
    .flatMap(binding ⇒ binding.unbind())
    .onComplete(_ ⇒ actorSystem.terminate())
}
