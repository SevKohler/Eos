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
 *******************************************************************************/

package org.bih.eos.jpabase.model.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name="condition_era")
public class ConditionEra extends JPABaseEntity {

	@Id
	@Column(name = "condition_era", nullable=false)
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

	@Column(name="condition_end_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date conditionEndDateTime;

	@Column(name="condition_occurrence_count")
	private Long conditionOccurrenceCount;

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

	public Date getConditionEndDateTime() {
		return conditionEndDateTime;
	}

	public void setConditionEndDateTime(Date conditionEndDateTime) {
		this.conditionEndDateTime = conditionEndDateTime;
	}

	public Long getConditionOccurrenceCount() {
		return conditionOccurrenceCount;
	}

	public void setConditionOccurrenceCount(Long conditionOccurrenceCount) {
		this.conditionOccurrenceCount = conditionOccurrenceCount;
	}

	@Override
	public Long getIdAsLong() {
		return getId();
	}
}
