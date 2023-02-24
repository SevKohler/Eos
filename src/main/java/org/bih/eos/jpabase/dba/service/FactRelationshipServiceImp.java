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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.bih.eos.jpabase.model.entity.JPABaseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.bih.eos.jpabase.jpa.dao.FactRelationshipDao;
import org.bih.eos.jpabase.model.entity.Concept;
import org.bih.eos.jpabase.model.entity.FactRelationship;
import org.bih.eos.jpabase.model.entity.Note;


/**
 * The Class FactRelationshipServiceImp.
 */
@Service
public class FactRelationshipServiceImp extends BaseEntityServiceImp<FactRelationship, FactRelationshipDao>
		implements FactRelationshipService {

	/**
	 * Instantiates a new fact relationship service imp.
	 */
	public FactRelationshipServiceImp() {
		super(FactRelationship.class);
	}

	/* (non-Javadoc)
	 * @see edu.gatech.chai.omopv5.dba.service.FactRelationshipService#searchMeasurementUsingMethod(java.lang.Long)
	 */
	@Transactional(readOnly = true)
	public <V extends JPABaseEntity> List<V> searchMeasurementUsingMethod(Long domainId) {
		List<V> retVal = new ArrayList<V>();

		EntityManager em = getEntityDao().getEntityManager();

		// Concept ID:
		// 21 = Measurement, 27 = Observation,
		// 44818800 = Using finding method
		// 58 = Type Concept, 26 = Note Type
		// 44818721 = Contains
		String query = "SELECT t FROM FactRelationship t WHERE domain_concept_id_1 = 21 AND relationship_concept_id = 44818800 AND fact_id_1 = :fact1";
		List<FactRelationship> results = em.createQuery(query, FactRelationship.class).setParameter("fact1", domainId)
				.getResultList();

		if (results.size() > 0) {
			for (FactRelationship result : results) {
				
				Long domainConcept2 = result.getDomainConceptId2();
				Long fact2 = result.getFactId2();

				if (domainConcept2 == 58L) {
					Concept obsConcept = new Concept(fact2);
					retVal.add((V) obsConcept);
				} else if (domainConcept2 == 26L) {
					Note obsNote = new Note(fact2);
					retVal.add((V) obsNote);
				}
			}
		}

		return retVal;
	}

	/* (non-Javadoc)
	 * @see edu.gatech.chai.omopv5.dba.service.FactRelationshipService#searchMeasurementContainsComments(java.lang.Long)
	 */
	@Transactional(readOnly = true)
	public List<Note> searchMeasurementContainsComments(Long domainId) {
		List<Note> retVal = new ArrayList<Note>();

		EntityManager em = getEntityDao().getEntityManager();

		// 44818721 = Contains
		String query = "SELECT t FROM FactRelationship t WHERE domain_concept_id_1 = 21 AND fact_id_1 = :fact1 AND domain_concept_id_2 = 26 AND relationship_concept_id = 44818721";
		List<FactRelationship> results = em.createQuery(query, FactRelationship.class).setParameter("fact1", domainId)
				.getResultList();
		if (results.size() > 0) {
			for (FactRelationship result : results) {
				Long fact2 = result.getFactId2();
				retVal.add(new Note(fact2));
			}
		}

		return retVal;
	}

	/* (non-Javadoc)
	 * @see edu.gatech.chai.omopv5.dba.service.FactRelationshipService#searchFactRelationship(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@Transactional(readOnly = true)
	public List<FactRelationship> searchFactRelationship(Long domainConcept1, Long factId1, Long domainConcept2,
			Long factId2, Long relationshipId) {

		EntityManager em = getEntityDao().getEntityManager();

		String query;
		List<FactRelationship> factRelationships;
		if (factId2 != null) {
			query = "SELECT t FROM FactRelationship t WHERE domain_concept_id_1 = :domain1 AND fact_id_1 = :fact1 AND domain_concept_id_2 = :domain2 AND fact_id_2 = :fact2 AND relationship_concept_id = :relationship";
			factRelationships = em.createQuery(query, FactRelationship.class)
					.setParameter("domain1", domainConcept1.intValue()).setParameter("domain2", domainConcept2.intValue())
					.setParameter("fact1", factId1.intValue()).setParameter("fact2", factId2.intValue())
					.setParameter("relationship", relationshipId.intValue()).getResultList();
		} else {
			query = "SELECT t FROM FactRelationship t WHERE domain_concept_id_1 = :domain1 AND fact_id_1 = :fact1 AND domain_concept_id_2 = :domain2 AND relationship_concept_id = :relationship";
			factRelationships = em.createQuery(query, FactRelationship.class)
					.setParameter("domain1", domainConcept1.intValue()).setParameter("domain2", domainConcept2.intValue())
					.setParameter("fact1", factId1.intValue())
					.setParameter("relationship", relationshipId.intValue()).getResultList();
		}

		return factRelationships;
	}

}
