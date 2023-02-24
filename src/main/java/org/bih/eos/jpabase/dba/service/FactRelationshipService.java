/*******************************************************************************
 * Copyright (c) 2019 Georgia Tech Research Institute
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
 *
 * 
 * Copyright (c) 2023 Berlin Institute of Health
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
 *
 *
 *******************************************************************************/
package org.bih.eos.jpabase.dba.service;

import java.util.List;

import org.bih.eos.jpabase.model.entity.JPABaseEntity;
import org.bih.eos.jpabase.model.entity.FactRelationship;
import org.bih.eos.jpabase.model.entity.Note;


/**
 * The Interface FactRelationshipService.
 */
public interface FactRelationshipService extends IService<FactRelationship> {
	
	/**
	 * Search measurement using method.
	 *
	 * @param <V> the value type
	 * @param conceptId the concept id
	 * @return the list
	 */
	public <V extends JPABaseEntity> List<V> searchMeasurementUsingMethod(Long conceptId);

	/**
	 * Search measurement contains comments.
	 *
	 * @param conceptId the concept id
	 * @return the list
	 */
	public List<Note> searchMeasurementContainsComments(Long conceptId);

	/**
	 * Search fact relationship.
	 *
	 * @param domainConcept1 the domain concept 1
	 * @param factId1 the fact id 1
	 * @param domainConcept2 the domain concept 2
	 * @param factId2 the fact id 2
	 * @param relationshipId the relationship id
	 * @return the list
	 */
	public List<FactRelationship> searchFactRelationship(Long domainConcept1, Long factId1, Long domainConcept2,
			Long factId2, Long relationshipId);
}
