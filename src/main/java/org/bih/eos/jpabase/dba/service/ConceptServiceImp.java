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
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.bih.eos.jpabase.jpa.dao.ConceptDao;
import org.bih.eos.jpabase.model.entity.Concept;


/**
 * The Class ConceptServiceImp.
 */
@Service
public class ConceptServiceImp extends BaseEntityServiceImp<Concept, ConceptDao> implements ConceptService {
	
	/**
	 * Instantiates a new concept service imp.
	 */
	public ConceptServiceImp() {
		super(Concept.class);
	}
	
	/* (non-Javadoc)
	 * @see edu.gatech.chai.omopv5.dba.service.ConceptService#getIngredient(edu.gatech.chai.omopv5.model.entity.Concept)
	 */
	@Transactional(readOnly = true)
	public List<Concept> getIngredient(Concept concept) {
		EntityManager em = getEntityDao().getEntityManager();
		
		List<Concept> concepts = new ArrayList<Concept>();
		
		if ("Ingredient".equals(concept.getConceptClassId())) {
			// This is ingredient. Just return null
			return concepts;
		}
		
		String sqlQuery = null;
		if ("NDC".equals(concept.getVocabularyId())) {
			// Use JPQL
			sqlQuery = "select c "
					+ "FROM Concept src "
					+ "JOIN ConceptRelationship cr on src.id = cr.conceptId1 "
			        + "AND cr.relationship_id = 'Maps to' "
			        + "AND cr.invalid_reason is null "
			        + "JOIN Concept tar on cr.conceptId2 = tar.id "
			        + "AND tar.standardConcept = 'S' "
			        + "AND tar.invalidReason is null "
			        + "JOIN ConceptAncestor ca ON ca.ancestorConcept = tar.id "
			        + "JOIN Concept c ON ca.ancestorConcept = c.id "
			        + "WHERE src.conceptCode = :med_code "
			        + "AND 'NDC' = src.vocabulary "
			        + "AND c.vocabularyId = 'RxNorm' "
			        + "AND c.conceptClassId = 'Ingredient' "
			        + "AND src.invalidReason is null";
		} else if ("RxNorm".equals(concept.getVocabularyId())) {
			// when RxNorm.
			sqlQuery = "select c "
					+ "FROM Concept src " 
					+ "JOIN ConceptAncestor ca ON ca.descendantConcept = src.id "
					+ "JOIN Concept c ON ca.ancestorConcept = c.id "
					+ "WHERE src.conceptCode = :med_code "
					+ "AND 'RxNorm' = src.vocabularyId "
					+ "AND c.vocabularyId = 'RxNorm' "
					+ "AND c.conceptClassId = 'Ingredient' "
					+ "AND src.invalidReason is null "
					+ "AND c.invalidReason is null";
		} else {
			return concepts;
		}
		
		TypedQuery<Concept> query = em.createQuery(sqlQuery, Concept.class);
		query = query.setParameter("med_code", concept.getConceptCode());
		return query.getResultList();
//		List<Concept> results = query.getResultList();
//		if (results.size() > 0) {
//			return results.get(0);
//		} else {
//			return null;
//		}
	}

	/* (non-Javadoc)
	 * @see edu.gatech.chai.omopv5.dba.service.ConceptService#getLargestId()
	 */
	@Override
	public Long getLargestId() {
		EntityManager em = getEntityDao().getEntityManager();
		String sqlQuery = "SELECT id FROM Concept src ORDER BY id DESC";
		TypedQuery<Long> query = em.createQuery(sqlQuery, Long.class);
		query.setMaxResults(1);
		List<Long> ret = query.getResultList();

		if (ret.size() == 0)
			return null;
		else
			return ret.get(0);
	}
}
