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

package org.bih.eos.jpabase.model.entity;

import javax.persistence.*;

@Entity
@Table(name="fact_relationship")
@IdClass(CompositeKey.class)
public class FactRelationship extends JPABaseEntity {

	@Id
	@Column(name="domain_concept_id_1")
	private Long domainConceptId1;

	@Id
	@Column(name="fact_id_1")
	private Long factId1;

	@Id
	@Column(name="domain_concept_id_2")
	private Long domainConceptId2;

	@Id
	@Column(name="fact_id_2")
	private Long factId2;

	@Id
	@ManyToOne
	@JoinColumn(name="relationship_concept_id")
	private Concept relationshipConcept;
	
	public Long getDomainConceptId1() {
		return this.domainConceptId1;
	}
	
	public void setDomainConceptId1(Long domainConceptId1) {
		this.domainConceptId1 = domainConceptId1;
	}
	
	public Long getFactId1() {
		return this.factId1;
	}
	
	public void setFactId1(Long factId1) {
		this.factId1 = factId1;
	}
	
	public Long getDomainConceptId2() {
		return this.domainConceptId2;
	}
	
	public void setDomainConceptId2(Long domainConceptId2) {
		this.domainConceptId2 = domainConceptId2;
	}


	public Long getFactId2() {
		return this.factId2;
	}
	
	public void setFactId2(Long factId2) {
		this.factId2 = factId2;
	}
	
	public Concept getRelationshipConcept() {
		return this.relationshipConcept;
	}
	
	public void setRelationshipConcept(Concept relationshipConcept) {
		this.relationshipConcept = relationshipConcept;
	}
	
	@Override
	public Long getIdAsLong() {
		return this.domainConceptId1;
	}
}
