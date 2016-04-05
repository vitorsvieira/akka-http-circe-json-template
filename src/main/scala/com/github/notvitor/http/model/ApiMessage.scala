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

package com.github.notvitor.http.model

import akka.actor.ActorSystem

case class ApiMessage(message: String)

object ApiStatusMessages {

  def currentStatus()(implicit actorSystem: ActorSystem) = {
    s"service: ${actorSystem.name} | uptime: ${(actorSystem.uptime.toFloat / 3600).formatted("%10.2f")} hours"
  }

  val unknownException = "An error occurred during the request."
}
