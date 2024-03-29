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

package org.bih.eos.jpabase.entity;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="concept_class")
public class ConceptClass extends JPABaseEntity {
	@Id
	@Column(name="concept_class_id", nullable=false)
	@Access(AccessType.PROPERTY)
	private String id;

	@Column(name="concept_class_name", nullable=false)
	private String conceptClassName;

	@ManyToOne
	@JoinColumn(name="concept_class_concept_id", nullable=false)
	private Concept conceptClassConcept;

	@Override
	public Long getIdAsLong() {
		// TODO Auto-generated method stub
		return null;
	}

	public ConceptClass() {
		super();
	}
	
	public ConceptClass(String id) {
		super();
		this.id = id;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getConceptClassName() {
		return this.conceptClassName;
	}
	
	public void setConceptClassName(String conceptClassName) {
		this.conceptClassName = conceptClassName;
	}
	
	public Concept getConceptClassConcept() {
		return conceptClassConcept;
	}
	
	public void setConceptClassConcept(Concept conceptClassConcept) {
		this.conceptClassConcept = conceptClassConcept;
	}
}
