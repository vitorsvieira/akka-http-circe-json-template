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

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.vitorsvieira.http.config.ServerSettingsTemplate._
import com.vitorsvieira.http.model.{ ApiMessage, ApiStatusMessages, ModelTemplate }
import com.vitorsvieira.http.repository.RepositoryTemplate
import de.heikoseeberger.akkahttpcirce.CirceSupport._
import scala.concurrent.Future

object AkkaHttpRoutesTemplate extends BaseRoute with ResponseFactory {

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

  protected lazy val apiV1: Route = api(this.templateDirectives, prefix = true, "v1")

  protected lazy val apiV2: Route =
    api(dsl = logRequestResult("log-service") {
      this.templateDirectives
    }, prefix = true, version = "v2")

  def availableRoutes: Route = apiV1 ~ apiV2

}
