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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="condition_occurrence")
public class ConditionOccurrence extends JPABaseEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="condition_occurrence_id_seq_gen")
	@SequenceGenerator(name="condition_occurrence_id_seq_gen", sequenceName="condition_occurrence_id_seq", allocationSize=1)
	@Column(name="condition_occurrence_id", nullable = false)
	@Access(AccessType.PROPERTY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="person_id", nullable= false)
	private Person person;

	@ManyToOne
	@JoinColumn(name = "condition_concept_id", nullable = false)
	private Concept conditionConcept;
	
	@Column(name="condition_start_date", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date conditionStartDate;

	@Column(name="condition_start_datetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date conditionStartDateTime;

	@Column(name="condition_end_date")
	@Temporal(TemporalType.DATE)
	private Date conditionEndDate;

	@Column(name="condition_end_datetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date conditionEndDateTime;

	@ManyToOne
	@JoinColumn(name = "condition_type_concept_id", nullable = false)
	private Concept conditionTypeConcept;

	@Column(name="stop_reason")
	private String stopReason;

	@ManyToOne
	@JoinColumn(name = "provider_id")
	private Provider provider;

	@ManyToOne
	@JoinColumn(name = "visit_occurrence_id")
	private VisitOccurrence visitOccurrence;

	@ManyToOne
	@JoinColumn(name = "visit_detail_id")
	private VisitDetail visitDetail;

	@Column(name="condition_source_value")
	private String conditionSourceValue;

	@ManyToOne
	@JoinColumn(name = "condition_source_concept_id")
	private Concept conditionSourceConcept;

	@Column(name="condition_status_source_value")
	private String conditionStatusSourceValue;

	@ManyToOne
	@JoinColumn(name = "condition_status_concept_id")
	private Concept conditionStatusConcept;

	

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Concept getConditionConcept() {
		return conditionConcept;
	}

	public void setConditionConcept(Concept conditionConcept) {
		this.conditionConcept = conditionConcept;
	}

	public Date getConditionStartDate() {
		return conditionStartDate;
	}

	public void setConditionStartDate(Date conditionStartDate) {
		this.conditionStartDate = conditionStartDate;
	}

	public Date getConditionStartDateTime() {
		return conditionStartDateTime;
	}

	public void setConditionStartDateTime(Date conditionStartDateTime) {
		this.conditionStartDateTime = conditionStartDateTime;
	}

	public Date getConditionEndDate() {
		return conditionEndDate;
	}

	public void setConditionEndDate(Date conditionEndDate) {
		this.conditionEndDate = conditionEndDate;
	}

	public Date getConditionEndDateTime() {
		return conditionEndDateTime;
	}

	public void setConditionEndDateTime(Date conditionEndDateTime) {
		this.conditionEndDateTime = conditionEndDateTime;
	}

	public Concept getConditionTypeConcept() {
		return conditionTypeConcept;
	}

	public void setConditionTypeConcept(Concept conditionTypeConcept) {
		this.conditionTypeConcept = conditionTypeConcept;
	}

	public String getStopReason() {
		return stopReason;
	}

	public void setStopReason(String stopReason) {
		this.stopReason = stopReason;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public VisitOccurrence getVisitOccurrence() {
		return visitOccurrence;
	}

	public void setVisitOccurrence(VisitOccurrence visitOccurrence) {
		this.visitOccurrence = visitOccurrence;
	}

	public VisitDetail getVisitDetail() {
		return this.visitDetail;
	}
	
	public void setVisitDetail(VisitDetail visitDetail) {
		this.visitDetail = visitDetail;
	}
	
	public String getConditionSourceValue() {
		return conditionSourceValue;
	}

	public void setConditionSourceValue(String conditionSourceValue) {
		this.conditionSourceValue = conditionSourceValue;
	}

	public Concept getConditionSourceConcept() {
		return conditionSourceConcept;
	}

	public void setConditionSourceConcept(Concept conditionSourceConcept) {
		this.conditionSourceConcept = conditionSourceConcept;
	}
	
	public String getConditionStatusSourceValue() {
		return conditionStatusSourceValue;
	}

	public void setConditionStatusSourceValue(String conditionStatusSourceValue) {
		this.conditionStatusSourceValue = conditionStatusSourceValue;
	}

	public Concept getConditionStatusConcept() {
		return conditionStatusConcept;
	}

	public void setConditionStatusConcept(Concept conditionStatusConcept) {
		this.conditionStatusConcept = conditionStatusConcept;
	}

	@Override
	public Long getIdAsLong() {
		return getId();
	}
}
