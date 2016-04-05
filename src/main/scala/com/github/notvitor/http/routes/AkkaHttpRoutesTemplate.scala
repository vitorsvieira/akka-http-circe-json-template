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

package com.github.notvitor.http.routes

import akka.http.scaladsl.coding.Gzip
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.github.notvitor.http.config.ServerSettingsTemplate
import com.github.notvitor.http.model.{ ApiMessage, ApiStatusMessages, ModelTemplate }
import com.github.notvitor.http.repository.RepositoryTemplate
import de.heikoseeberger.akkahttpcirce.CirceSupport._
import scala.concurrent.Future

object AkkaHttpRoutesTemplate extends ResponseFactory {

  import ServerSettingsTemplate._
  import io.circe.generic.auto._

  protected def templateDirectives: Route =
    pathPrefix("service1") {
      get {
        path("status") {
          extractRequest { req ⇒
            sendResponse(Future(ApiMessage(ApiStatusMessages.currentStatus())))
          }
        } ~
          path("models" / IntNumber) { (amount) ⇒
            extractRequest { req ⇒
              sendResponse(RepositoryTemplate.getModels(amount))
            }
          } ~
          path("modelsByName" / Segment) { (name) ⇒
            extractRequest { req ⇒
              sendResponse(RepositoryTemplate.getModelsByName(name))
            }
          }
      } ~
        post {
          path("model") {
            decodeRequest {
              entity(as[ModelTemplate]) { model ⇒
                sendResponse(Future(ApiMessage(s"model.vString: ${model.vString} - model.vListInt: ${model.vListInt}")))
              }
            }
          }
        }
    }

  protected lazy val apiV1: Route =
    respondWithHeaders(
      RawHeader("Access-Control-Allow-Origin", "*"),
      RawHeader("Access-Control-Allow-Methods", "POST, GET, PUT, PATCH, DELETE")
    ) {
        pathPrefix("api" / "v1") {
          encodeResponseWith(Gzip) {
            templateDirectives
          }
        }
      }

  protected lazy val apiV2: Route =
    respondWithHeaders(
      RawHeader("Access-Control-Allow-Origin", "*"),
      RawHeader("Access-Control-Allow-Methods", "POST, GET")
    ) {
        pathPrefix("api" / "v2") {
          encodeResponseWith(Gzip) {
            logRequestResult("log-service1") {
              templateDirectives
            }
          }
        }
      }

  def availableRoutes: Route = apiV1 ~ apiV2

}