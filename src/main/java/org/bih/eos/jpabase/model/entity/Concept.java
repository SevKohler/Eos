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
 *******************************************************************************/

package org.bih.eos.jpabase.model.entity;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "concept")
@Inheritance(strategy = InheritanceType.JOINED)
public class Concept extends JPABaseEntity {

	@Id
	@Column(name = "concept_id", nullable=false)
	@Access(AccessType.PROPERTY)
	private Long id;

	@Column(name = "concept_name", nullable=false)
	private String conceptName;

//	@ManyToOne
//	@JoinColumn(name="domain_id", referencedColumnName="domain_id", insertable=false, updatable=false)
//	private Domain domain;

	@Column(name = "domain_id", nullable=false)
	private String domainId;

//	@ManyToOne
//	@JoinColumn(name = "vocabulary_id", referencedColumnName="vocabulary_id")
//	private Vocabulary vocabulary;
	@Column(name = "vocabulary_id", nullable=false)
	private String vocabularyId;

	@Column(name = "concept_class_id", nullable=false)
	private String conceptClassId;

	@Column(name = "standard_concept")
	private Character standardConcept;

	@Column(name = "concept_code", nullable=false)
	private String conceptCode;

	@Column(name = "valid_start_date", nullable=false)
	private Date validStartDate;

	@Column(name = "valid_end_date", nullable=false)
	private Date validEndDate;

	@Column(name = "invalid_reason")
	private String invalidReason;

	public Concept() {
		super();
	}

	public Concept(Long id) {
		super();
		this.id = id;
	}

	public Concept(Long id, String conceptName) {
		super();
		this.id = id;
		this.conceptName = conceptName;
	}

	public Concept(Long id, String conceptName, String domainId, String conceptClassId, Character standardConcept,
			String vocabularyId, String conceptCode, Date validStartDate, Date validEndDate, String invalidReason) {
		super();
		this.id = id;
		this.conceptName = conceptName;
		this.domainId = domainId;
		this.conceptClassId = conceptClassId;
		this.standardConcept = standardConcept;
		this.vocabularyId = vocabularyId;
		this.conceptCode = conceptCode;
		this.validStartDate = validStartDate;
		this.validEndDate = validEndDate;
		this.invalidReason = invalidReason;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getConceptName() {
		return conceptName;
	}

	public void setConceptName(String conceptName) {
		this.conceptName = conceptName;
	}

	public String getDomainId() {
		return domainId;
	}

	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}

	public String getConceptClassId() {
		return conceptClassId;
	}

	public void setConceptClassId(String conceptClassId) {
		this.conceptClassId = conceptClassId;
	}

	public Character getStandardConcept() {
		return standardConcept;
	}

	public void setStandardConcept(Character standardConcept) {
		this.standardConcept = standardConcept;
	}

	public String getVocabularyId() {
		return vocabularyId;
	}

	public void setVocabularyId(String vocabularyId) {
		this.vocabularyId = vocabularyId;
	}

	public String getConceptCode() {
		return conceptCode;
	}

	public void setConceptCode(String conceptCode) {
		this.conceptCode = conceptCode;
	}

	public Date getValidStartDate() {
		return validStartDate;
	}

	public void setValidStartDate(Date validStartDate) {
		this.validStartDate = validStartDate;
	}

	public Date getValidEndDate() {
		return validEndDate;
	}

	public void setValidEndDate(Date validEndDate) {
		this.validEndDate = validEndDate;
	}

	public String getInvalidReason() {
		return invalidReason;
	}

	public void setInvalidReason(String invalidReason) {
		this.invalidReason = invalidReason;
	}

	@Override
	public String toString() {
		// Since this is an omop v.4 based model, all the information below is expected
		// to be not null.
		return this.getId() + ", " + this.getConceptName() + ", " + this.getDomainId() + ", " + this.getConceptClassId() + ", "
				+ this.getStandardConcept() + ", " + this.getVocabularyId() + ", " + this.getConceptCode() + ", "
				+ this.getValidStartDate() + ", " + this.getValidEndDate();
	}

	@Override
	public Long getIdAsLong() {
		return getId();
	}

}
