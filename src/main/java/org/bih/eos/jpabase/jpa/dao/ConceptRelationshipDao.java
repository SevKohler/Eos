package org.bih.eos.jpabase.jpa.dao;
/*******************************************************************************
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
 *******************************************************************************/


import org.bih.eos.jpabase.model.entity.ConceptRelationship;
import org.bih.eos.jpabase.model.entity.ConceptRelationshipPK;
import org.springframework.stereotype.Repository;

@Repository
public class ConceptRelationshipDao extends JPABaseEntityDao<ConceptRelationship> {
	public ConceptRelationship findById(Class<ConceptRelationship> entityClass, ConceptRelationshipPK conceptRelationshipPk) {
		return getEntityManager().find(entityClass, conceptRelationshipPk);
	}
	
	public void delete(Class<ConceptRelationship> entityClass, ConceptRelationshipPK conceptRelationshipPk) {
		ConceptRelationship entity = findById(entityClass, conceptRelationshipPk);
		if (entity != null) {
			getEntityManager().remove(entity);
		} 
	}
	
}
