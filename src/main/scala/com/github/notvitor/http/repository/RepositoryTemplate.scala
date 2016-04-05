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

package com.github.notvitor.http.repository

import com.github.notvitor.http.model.ModelTemplate
import scala.collection.immutable.IndexedSeq
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object RepositoryTemplate {

  def getModels(amount: Int): Future[IndexedSeq[ModelTemplate]] = Future {
    require(amount > 0, "The amount must be greater than zero.")
    (1 to amount).map { i ⇒
      ModelTemplate(s"Model$i", i, i.toLong, i.toFloat, i.toDouble, Seq(1, 2, 3), List(1, 2, 3))
    }
  }

  def getModelsByName(name: String): Future[ModelTemplate] = Future {
    require(name.nonEmpty, "The name must be present.")
    ModelTemplate(s"Model-$name", 1, 1.toLong, 1.toFloat, 1.toDouble, Seq(1, 2, 3), List(1, 2, 3))
  }

}
