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

package com.vitorsvieira.http.routes

import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import com.vitorsvieira.http.config.ServerSettingsTemplate
import com.vitorsvieira.http.model.{ ApiMessage, ApiStatusMessages }
import de.heikoseeberger.akkahttpcirce.CirceSupport._

import scala.concurrent.Future
import scala.util.{ Failure, Success }

trait ResponseFactory {

  import ServerSettingsTemplate._
  import io.circe.generic.auto._

  def sendResponse[T](eventualResult: Future[T])(implicit marshaller: T ⇒ ToResponseMarshallable): Route = {
    onComplete(eventualResult) {
      case Success(result) ⇒
        complete(result)
      case Failure(e) ⇒
        log.error(s"Error: ${e.toString}")
        complete(ToResponseMarshallable(InternalServerError → ApiMessage(ApiStatusMessages.unknownException)))
    }
  }
}
