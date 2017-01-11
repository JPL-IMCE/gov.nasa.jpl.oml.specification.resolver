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

import gov.nasa.jpl.imce.omf.schema._

case class ConceptDesignationTerminologyAxiom private[impl] 
(
 override val uuid: java.util.UUID,
 override val designatedConcept: resolver.api.Concept,
 override val designatedTerminology: resolver.api.TerminologyBox,
 override val designationTerminologyGraph: resolver.api.TerminologyGraph
)
extends resolver.api.ConceptDesignationTerminologyAxiom
  with TerminologyBoxAxiom
{

  /*
   * The designationTerminologyGraph is the source
   */
  override def source
  ()
  : resolver.api.TerminologyBox
  = {
    designationTerminologyGraph
  }
  
  
/*
   * The TerminologyBox that asserts the designatedConcept is the target
   */
  override def target
  ()
  : resolver.api.TerminologyBox
  = {
    designatedTerminology
  }
  

}