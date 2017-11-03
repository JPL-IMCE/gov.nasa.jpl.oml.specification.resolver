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

package gov.nasa.jpl.imce.oml

import scala.collection.immutable.{::, List, Nil, Seq, Set, Vector}
import scala.reflect.ClassTag
import scala.{Boolean,Ordering}
import scala.Predef.require
import scalax.collection.Graph
import scalax.collection.GraphEdge.DiEdge
import scalaz._
import Scalaz._

package object graphs {

  def subGraphPrecedence
  [N: ClassTag, E[M] <: DiEdge[M]]
  (g: Graph[N, E])
  (lt: Graph[N, E], gt: Graph[N, E])
  (implicit nOrder: Ordering[N])
  : Boolean
  = {
    require(lt.nonEmpty)
    require(gt.nonEmpty)

    val before =
      lt.toOuterNodes.exists { ln =>
        val n1 = g.get(ln)
        gt.toOuterNodes.exists { rn =>
          val n2 = g.get(rn)
          n1.isPredecessorOf(n2)
        }
      }

    if (before)
      true
    else {
      val inverse =
        lt.toOuterNodes.exists { ln =>
          val n1 = g.get(ln)
          gt.toOuterNodes.exists { rn =>
            val n2 = g.get(rn)
            n2.isPredecessorOf(n1)
          }
        }

      if (inverse)
        false
      else {
        val lns = lt.toOuterNodes.to[Vector].sorted
        val rns = gt.toOuterNodes.to[Vector].sorted
        nOrder.compare(lns.head, rns.head) <= 0
      }
    }
  }

  @scala.annotation.tailrec
  final def hierarchicalTopologicalSort[N: ClassTag, E[M] <: DiEdge[M]]
  (queue: Seq[Graph[N, E]], result: Seq[N] = Seq.empty)
  (implicit nOrder: Ordering[N])
  : Set[java.lang.Throwable] \/ Seq[N]
  = queue match {
    case Nil =>
      result.right
    case g :: gs =>

      if (g.isAcyclic) {
        val gsorted = g.topologicalSort().right.get.toOuter.toOuter.to[Seq]
        hierarchicalTopologicalSort(gs, result ++ gsorted)
      } else {
        val sccs1 = g.strongComponentTraverser()
        val sccs2 = sccs1.map(_.toGraph)
        val sccs3 = sccs2.to[List]
        val sccs4 = sccs3.filter(_.nonEmpty)
        val sccs = sccs4.sortWith(subGraphPrecedence(g))

        sccs match {
          case Nil =>
            result.right[Set[java.lang.Throwable]]

          case n :: ns =>
            if (n.isAcyclic) {
              val rs: Seq[N] = n.toOuterNodes.to[Seq]
              hierarchicalTopologicalSort(ns ++ gs, result ++ rs)
            } else {
              hierarchicalTopologicalSort(sccs ++ gs, result)
            }
        }
      }
  }

}
