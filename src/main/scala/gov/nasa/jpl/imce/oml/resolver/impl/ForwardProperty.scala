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

package gov.nasa.jpl.imce.oml.resolver.impl

import gov.nasa.jpl.imce.oml._

case class ForwardProperty private[impl] 
	(
	 override val uuid: resolver.api.taggedTypes.ForwardPropertyUUID,
	 override val name: gov.nasa.jpl.imce.oml.tables.taggedTypes.LocalName,
	 override val reifiedRelationship: resolver.api.ReifiedRelationship
)
extends resolver.api.ForwardProperty
  with RestrictableRelationship
{

  override def iri
  ()(implicit extent: resolver.api.Extent)
	  : scala.Option[gov.nasa.jpl.imce.oml.tables.taggedTypes.IRI]
	  = {
	    extent
	    	    .terminologyBoxOfTerminologyBoxStatement
	    	    .get(reifiedRelationship)
	    	    .flatMap(tbox => tbox.iri().map(i =>  gov.nasa.jpl.imce.oml.tables.taggedTypes.iri(i + "#" + name)))
	  }

  override def abbrevIRI
  ()(implicit extent: resolver.api.Extent)
	  : scala.Option[scala.Predef.String]
	  = {
	    extent
	    	    .terminologyBoxOfTerminologyBoxStatement
	    	    .get(reifiedRelationship)
	    	    .map(tbox => tbox.nsPrefix+":"+name)
	  }

  override def relation
  ()
	  : resolver.api.EntityRelationship
	  = {
	    reifiedRelationship
	  }

  override def moduleContext
  ()(implicit extent: resolver.api.Extent)
	  : scala.Option[resolver.api.Module]
	  = {
	    reifiedRelationship.moduleContext()
	  }

  override def canEqual(that: scala.Any): scala.Boolean = that match {
	  case _: ForwardProperty => true
 	  case _ => false
  }

  override val hashCode
  : scala.Int
  = (uuid, name, reifiedRelationship).##

  override def equals(other: scala.Any): scala.Boolean = other match {
    case that: ForwardProperty =>
      (that canEqual this) &&
      (this.uuid == that.uuid) &&
      (this.name == that.name) &&
      (this.reifiedRelationship == that.reifiedRelationship)

    case _ =>
      false
  }
}
