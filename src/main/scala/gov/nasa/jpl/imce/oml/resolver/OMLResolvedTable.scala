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

package gov.nasa.jpl.imce.oml.resolver

import scala.collection.immutable.{Iterable, Map, Seq, Set}
import scala.StringContext
import scala.util.{Failure, Success, Try}
import scala.Predef.{ArrowAssoc,String}

/**
  * A global table of resolved OML Elements.
  *
  * @param extents The set of OML Extents resolved.
  * @param logicalElements The OML Extent of each OML LogicalElement resolved.
  * @param annotationPropertyValues The set of OML AnnotationPropertyValues across all OML Extents.
  * @param terminologyBoxOfTerminologyBoxAxiom The OML TerminologyBox of each OML TerminologyBoxAxiom resolved.
  * @param terminologyBoxOfTerminologyBoxStatement The OML TerminologyBox of each OML TerminologyBoxStatement resolved.
  * @param chainRuleOfRuleBodySegment The OML ChainRule of each OML RuleBodySegment resolved.
  * @param ruleBodySegmentOfSegmentPredicate The OML RuleBodySegment of each OML SegmentPredicate resolved.
  * @param ruleBodySegmentOfRuleBodySegment The OML RuleBodySegment of each OML RuleBodySegment resolved.
  * @param restrictionStructuredDataPropertyContextOfRestrictionStructuredDataPropertyTuple The OML RestrictionStructuredDataPropertyContext of each OML RestrictionStructuredDataPropertyTuple resolved.
  * @param restrictionStructuredDataPropertyContextOfRestrictionScalarDataPropertyValue The OML RestrictionStructuredDataPropertyContext of each OML RestrictionScalarDataPropertyValue resolved.
  * @param bundleOfTerminologyBundleAxiom The OML Bundle of each OML TerminologyBundleAxiom resolved.
  * @param bundleOfTerminologyBundleStatement The OML Bundle of each OML TerminologyBundleStatement resolved.
  * @param conceptTreeDisjunctionOfDisjointUnionOfConceptsAxiom The OML ConceptTreeDisjunction of each OML DisjointUnionOfConceptsAxiom resolved.
  * @param descriptionBoxOfDescriptionBoxRefinement The OML DescriptionBox of each OML DescriptionBoxRefinement resolved.
  * @param descriptionBoxOfDescriptionBoxExtendsClosedWorldDefinitions The OML DescriptionBox of each DescriptionBoxExtendsClosedWorldDefinitions resolved.
  * @param descriptionBoxOfConceptInstance The OML DescriptionBox of each OML ConceptInstance resolved.
  * @param descriptionBoxOfReifiedRelationshipInstance The OML DescriptionBox of each OML ReifiedRelationshipInstance resolved.
  * @param descriptionBoxOfReifiedRelationshipInstanceDomain The OML DescriptionBox of each OML ReifiedRelationshipInstanceDomain resolved.
  * @param descriptionBoxOfReifiedRelationshipInstanceRange The OML DescriptionBox of each OML ReifiedRelationshipInstanceRange resolved.
  * @param descriptionBoxOfUnreifiedRelationshipInstanceTuple The OML DescriptionBox of each OML UnreifiedRelationshipInstanceTuple resolved.
  * @param descriptionBoxOfSingletonInstanceScalarDataPropertyValue The OML DescriptionBox of each OML SingletonInstanceScalarDataPropertyValue resolved.
  * @param descriptionBoxOfSingletonInstanceStructuredDataPropertyValue The OML DescriptionBox of each OML SingletonInstanceStructuredDataPropertyValue resolved.
  * @param singletonInstanceStructuredDataPropertyContextOfStructuredDataPropertyTuple The OML SingletonInstanceStructuredDataPropertyContext of each OML StructuredDataPropertyTuple resolved.
  * @param singletonInstanceStructuredDataPropertyContextOfScalarDataPropertyValue The OML SingletonInstanceStructuredDataPropertyContext of each OML ScalarDataPropertyValue resolved.
  */
case class OMLResolvedTable
(extents
  : Seq[api.Extent] = Seq.empty,
 logicalElements
  : Map[api.LogicalElement, api.Extent] = Map.empty,
 annotationPropertyValues
  : Seq[api.AnnotationPropertyValue] = Seq.empty,
 terminologyBoxOfTerminologyBoxAxiom
  : Map[api.TerminologyBoxAxiom, api.TerminologyBox] = Map.empty,
 terminologyBoxOfTerminologyBoxStatement
  : Map[api.TerminologyBoxStatement, api.TerminologyBox] = Map.empty,
 chainRuleOfRuleBodySegment
  : Map[api.RuleBodySegment, api.ChainRule] = Map.empty,
 ruleBodySegmentOfSegmentPredicate
  : Map[api.SegmentPredicate, api.RuleBodySegment] = Map.empty,
 ruleBodySegmentOfRuleBodySegment
  : Map[api.RuleBodySegment, api.RuleBodySegment] = Map.empty,
 restrictionStructuredDataPropertyContextOfRestrictionStructuredDataPropertyTuple
  : Map[api.RestrictionStructuredDataPropertyTuple, api.RestrictionStructuredDataPropertyContext] = Map.empty,
 restrictionStructuredDataPropertyContextOfRestrictionScalarDataPropertyValue
  : Map[api.RestrictionScalarDataPropertyValue, api.RestrictionStructuredDataPropertyContext] = Map.empty,
 bundleOfTerminologyBundleAxiom
  : Map[api.TerminologyBundleAxiom, api.Bundle] = Map.empty,
 bundleOfTerminologyBundleStatement
  : Map[api.TerminologyBundleStatement, api.Bundle] = Map.empty,
 conceptTreeDisjunctionOfDisjointUnionOfConceptsAxiom
  : Map[api.DisjointUnionOfConceptsAxiom, api.ConceptTreeDisjunction] = Map.empty,
 descriptionBoxOfDescriptionBoxRefinement
  : Map[api.DescriptionBoxRefinement, api.DescriptionBox] = Map.empty,
 descriptionBoxOfDescriptionBoxExtendsClosedWorldDefinitions
  : Map[api.DescriptionBoxExtendsClosedWorldDefinitions, api.DescriptionBox] = Map.empty,
 descriptionBoxOfConceptInstance
  : Map[api.ConceptInstance, api.DescriptionBox] = Map.empty,
 descriptionBoxOfReifiedRelationshipInstance
  : Map[api.ReifiedRelationshipInstance, api.DescriptionBox] = Map.empty,
 descriptionBoxOfReifiedRelationshipInstanceDomain
  : Map[api.ReifiedRelationshipInstanceDomain, api.DescriptionBox] = Map.empty,
 descriptionBoxOfReifiedRelationshipInstanceRange
  : Map[api.ReifiedRelationshipInstanceRange, api.DescriptionBox] = Map.empty,
 descriptionBoxOfUnreifiedRelationshipInstanceTuple
  : Map[api.UnreifiedRelationshipInstanceTuple, api.DescriptionBox] = Map.empty,
 descriptionBoxOfSingletonInstanceScalarDataPropertyValue
  : Map[api.SingletonInstanceScalarDataPropertyValue, api.DescriptionBox] = Map.empty,
 descriptionBoxOfSingletonInstanceStructuredDataPropertyValue
  : Map[api.SingletonInstanceStructuredDataPropertyValue, api.DescriptionBox] = Map.empty,
 singletonInstanceStructuredDataPropertyContextOfStructuredDataPropertyTuple
  : Map[api.StructuredDataPropertyTuple, api.SingletonInstanceStructuredDataPropertyContext] = Map.empty,
 singletonInstanceStructuredDataPropertyContextOfScalarDataPropertyValue
  : Map[api.ScalarDataPropertyValue, api.SingletonInstanceStructuredDataPropertyContext] = Map.empty
) {

  import Filterable._

  /**
    * Convenience method for Extent-based queries.
    *
    * Examples:
    *
    * {{{
    * val ort: OMLResolvedTable = ...
    * val r: api.Resource = ...
    * val airi: Option[String] = ort.getViaExtent(r, api.Resource.abbrevIRI)
    * }}}
    *
    * {{{
    * val ort: OMLResolvedTable = ...
    * val t: api.Term = ...
    * val airi: Option[String] = ort.getViaExtent(t, api.Resource.abbrevIRI)
    * }}}
    *
    * @param u Element to be queried
    * @param uv Extent-based query function
    * @tparam U A subtype of gov.nasa.jpl.imce.oml.resolver.api.Element
    * @tparam V Return type of the Extent-based query function
    * @return The result of invoking the Extent-based query on the element with its corresponding extent.
    */
  def getViaExtent[U <: api.LogicalElement,V](u: U, uv: (U, api.Extent) => V): V = uv(u, logicalElements(u))

  def allAspects
  : Iterable[api.Aspect]
  = terminologyBoxOfTerminologyBoxStatement.selectByKindOf {
    case (a: api.Aspect, _) => a
  }

  def allConcepts
  : Iterable[api.Concept]
  = terminologyBoxOfTerminologyBoxStatement.selectByKindOf {
    case (c: api.Concept, _) => c
  }

  def allReifiedRelationships
  : Iterable[api.ReifiedRelationship]
  = terminologyBoxOfTerminologyBoxStatement.selectByKindOf {
    case (rr: api.ReifiedRelationship, _) => rr
  }

  def allAspectSpecializationAxioms
  : Iterable[api.AspectSpecializationAxiom]
  = terminologyBoxOfTerminologyBoxStatement.selectByKindOf {
    case (ax: api.AspectSpecializationAxiom, _) => ax
  }

  def allConceptSpecializationAxioms
  : Iterable[api.ConceptSpecializationAxiom]
  = terminologyBoxOfTerminologyBoxStatement.selectByKindOf {
    case (ax: api.ConceptSpecializationAxiom, _) => ax
  }

  def allReifiedRelationshipSpecializationAxioms
  : Iterable[api.ReifiedRelationshipSpecializationAxiom]
  = terminologyBoxOfTerminologyBoxStatement.selectByKindOf {
    case (ax: api.ReifiedRelationshipSpecializationAxiom, _) => ax
  }

  def allSubDataPropertyOfAxioms
  : Iterable[api.SubDataPropertyOfAxiom]
  = terminologyBoxOfTerminologyBoxStatement.selectByKindOf {
    case (ax: api.SubDataPropertyOfAxiom, _) => ax
  }

  def allSubObjectPropertyOfAxioms
  : Iterable[api.SubObjectPropertyOfAxiom]
  = terminologyBoxOfTerminologyBoxStatement.selectByKindOf {
    case (ax: api.SubObjectPropertyOfAxiom, _) => ax
  }

  override def toString: String =
    s"OMLResolvedTable(" +
      s"${extents.size} extents, " +
      s"${logicalElements.size} elements, " +
      s"${annotationPropertyValues.size} annotations," +
      s"...)"
}

object OMLResolvedTable {

  /**
    * Aggregates resolved OML Tables into a global table of resolved OML elements.
    *
    * @param omltr Resolved OML Tables
    */
  def aggregateResolvedTables(omltr: OMLTablesResolver)
  : Try[OMLResolvedTable]
  = if (!omltr.queue.isEmpty) {
    val message = "Incomplete resolution of OML Tables:\n" + omltr.queue.show
    Failure(OMLResolutionError(message))
  } else {
    def aggregate[A, B](rmap: api.Extent => Map[A, B]): Map[A, B] = omltr.allContexts.flatMap(rmap).toMap

    def addElementsOfExtent(acc: Map[api.LogicalElement, api.Extent], ext: api.Extent): Map[api.LogicalElement, api.Extent]
    = {
      import ext._
      val elements: Set[api.LogicalElement] =
        Set.empty[api.LogicalElement] ++
          terminologyGraphs.values.to[Set] ++
          bundles.values.to[Set] ++
          descriptionBoxes.values.to[Set] ++
          firstSegment.keys.to[Set] ++
          predicate.keys.to[Set] ++
          predicate.values.to[Set] ++
          structuredDataPropertyRestrictions.keys.to[Set] ++
          scalarDataPropertyRestrictions.keys.to[Set] ++
          terminologyBoxOfTerminologyBoxAxiom.keys.to[Set] ++
          terminologyBoxOfTerminologyBoxStatement.keys.to[Set] ++
          chainRuleOfRuleBodySegment.keys.to[Set] ++
          ruleBodySegmentOfSegmentPredicate.keys.to[Set] ++
          ruleBodySegmentOfRuleBodySegment.keys.to[Set] ++
          restrictionStructuredDataPropertyContextOfRestrictionStructuredDataPropertyTuple.keys.to[Set] ++
          restrictionStructuredDataPropertyContextOfRestrictionScalarDataPropertyValue.keys.to[Set] ++
          bundleOfTerminologyBundleAxiom.keys.to[Set] ++
          bundleOfTerminologyBundleStatement.keys.to[Set] ++
          conceptTreeDisjunctionOfDisjointUnionOfConceptsAxiom.keys.to[Set] ++
          descriptionBoxOfDescriptionBoxRefinement.keys.to[Set] ++
          descriptionBoxOfDescriptionBoxExtendsClosedWorldDefinitions.keys.to[Set] ++
          descriptionBoxOfConceptInstance.keys.to[Set] ++
          descriptionBoxOfReifiedRelationshipInstance.keys.to[Set] ++
          descriptionBoxOfReifiedRelationshipInstanceDomain.keys.to[Set] ++
          descriptionBoxOfReifiedRelationshipInstanceRange.keys.to[Set] ++
          descriptionBoxOfUnreifiedRelationshipInstanceTuple.keys.to[Set] ++
          descriptionBoxOfSingletonInstanceScalarDataPropertyValue.keys.to[Set] ++
          descriptionBoxOfSingletonInstanceStructuredDataPropertyValue.keys.to[Set] ++
          singletonInstanceStructuredDataPropertyContextOfStructuredDataPropertyTuple.keys.to[Set] ++
          singletonInstanceStructuredDataPropertyContextOfScalarDataPropertyValue.keys.to[Set]
      elements.par.aggregate(acc)(seqop = {
        case (prev, e) => prev + (e -> ext)
      }, combop = _ ++ _)
    }

    Success(
      OMLResolvedTable(
        extents =
          omltr.allContexts,
        logicalElements =
          omltr.allContexts.to[Set].par.aggregate[Map[api.LogicalElement, api.Extent]](Map.empty)(
            seqop = addElementsOfExtent,
            combop = _ ++ _),
        annotationPropertyValues =
          omltr.allContexts.flatMap(_.logicalElementOfAnnotationPropertyValue.keys),
        terminologyBoxOfTerminologyBoxAxiom =
          aggregate(_.terminologyBoxOfTerminologyBoxAxiom),
        terminologyBoxOfTerminologyBoxStatement =
          aggregate(_.terminologyBoxOfTerminologyBoxStatement),
        chainRuleOfRuleBodySegment =
          aggregate(_.chainRuleOfRuleBodySegment),
        ruleBodySegmentOfSegmentPredicate =
          aggregate(_.ruleBodySegmentOfSegmentPredicate),
        ruleBodySegmentOfRuleBodySegment =
          aggregate(_.ruleBodySegmentOfRuleBodySegment),
        restrictionStructuredDataPropertyContextOfRestrictionStructuredDataPropertyTuple =
          aggregate(_.restrictionStructuredDataPropertyContextOfRestrictionStructuredDataPropertyTuple),
        restrictionStructuredDataPropertyContextOfRestrictionScalarDataPropertyValue =
          aggregate(_.restrictionStructuredDataPropertyContextOfRestrictionScalarDataPropertyValue),
        bundleOfTerminologyBundleAxiom =
          aggregate(_.bundleOfTerminologyBundleAxiom),
        bundleOfTerminologyBundleStatement =
          aggregate(_.bundleOfTerminologyBundleStatement),
        conceptTreeDisjunctionOfDisjointUnionOfConceptsAxiom =
          aggregate(_.conceptTreeDisjunctionOfDisjointUnionOfConceptsAxiom),
        descriptionBoxOfDescriptionBoxRefinement =
          aggregate(_.descriptionBoxOfDescriptionBoxRefinement),
        descriptionBoxOfDescriptionBoxExtendsClosedWorldDefinitions =
          aggregate(_.descriptionBoxOfDescriptionBoxExtendsClosedWorldDefinitions),
        descriptionBoxOfConceptInstance =
          aggregate(_.descriptionBoxOfConceptInstance),
        descriptionBoxOfReifiedRelationshipInstance =
          aggregate(_.descriptionBoxOfReifiedRelationshipInstance),
        descriptionBoxOfReifiedRelationshipInstanceDomain =
          aggregate(_.descriptionBoxOfReifiedRelationshipInstanceDomain),
        descriptionBoxOfReifiedRelationshipInstanceRange =
          aggregate(_.descriptionBoxOfReifiedRelationshipInstanceRange),
        descriptionBoxOfUnreifiedRelationshipInstanceTuple =
          aggregate(_.descriptionBoxOfUnreifiedRelationshipInstanceTuple),
        descriptionBoxOfSingletonInstanceScalarDataPropertyValue =
          aggregate(_.descriptionBoxOfSingletonInstanceScalarDataPropertyValue),
        descriptionBoxOfSingletonInstanceStructuredDataPropertyValue =
          aggregate(_.descriptionBoxOfSingletonInstanceStructuredDataPropertyValue),
        singletonInstanceStructuredDataPropertyContextOfStructuredDataPropertyTuple =
          aggregate(_.singletonInstanceStructuredDataPropertyContextOfStructuredDataPropertyTuple),
        singletonInstanceStructuredDataPropertyContextOfScalarDataPropertyValue =
          aggregate(_.singletonInstanceStructuredDataPropertyContextOfScalarDataPropertyValue)
      )
    )
  }

}
