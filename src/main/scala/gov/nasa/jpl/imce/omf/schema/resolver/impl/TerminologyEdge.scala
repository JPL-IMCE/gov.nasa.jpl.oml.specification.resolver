/*
 * Copyright 2016 California Institute of Technology ("Caltech").
 * U.S. Government sponsorship acknowledged.
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
 * License Terms
 */

package gov.nasa.jpl.imce.omf.schema.resolver.impl

import scalax.collection.GraphEdge.{DiEdge, EdgeCopy, ExtendedKey, NodeProduct}
import scalax.collection.GraphPredef.OuterEdge
import scala.collection.immutable.Seq
import scala.{Product,StringContext}

case class TerminologyEdge[N]
(override val nodes: Product, tAxiom: TerminologyAxiom)
  extends DiEdge[N](nodes)
    with ExtendedKey[N]
    with EdgeCopy[TerminologyEdge]
    with OuterEdge[N, TerminologyEdge]
{
  def keyAttributes = Seq(tAxiom)
  override def copy[NN](newNodes: Product) = new TerminologyEdge[NN](newNodes, tAxiom)
  override protected def attributesToString = s" ${tAxiom}"
}

object TerminologyEdge {

  def apply
  (from: TerminologyBox, to: TerminologyBox, tAxiom:TerminologyAxiom)
  = new TerminologyEdge[TerminologyBox](NodeProduct(from, to), tAxiom)

}