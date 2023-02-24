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
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(
		name="observation",
		indexes = { 
			@Index(name = "idx_observation_concept_id", columnList = "observation_concept_id"), 
			@Index(name = "idx_observation_person_id", columnList = "person_id")
			}
		)
public class Observation extends JPABaseEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="observation_seq_gen")
	@SequenceGenerator(name="observation_seq_gen", sequenceName="observation_id_seq", allocationSize=1)
	@Column(name = "observation_id")
	@Access(AccessType.PROPERTY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "person_id", nullable = false)
	private Person person;

	@ManyToOne
	@JoinColumn(name = "observation_concept_id", nullable = false)
	private Concept observationConcept;

	@Column(name = "observation_date")
	@Temporal(TemporalType.DATE)
	private Date observationDate;

	@Column(name = "observation_datetime", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date observationDateTime;

	@ManyToOne
	@JoinColumn(name = "observation_type_concept_id", nullable = false)
	private Concept observationTypeConcept;

	@Column(name = "value_as_number")
	private Double valueAsNumber;

	@Column(name = "value_as_string")
	private String valueAsString;

	@ManyToOne
	@JoinColumn(name = "value_as_concept_id")
	private Concept valueAsConcept;

	@ManyToOne
	@JoinColumn(name = "qualifier_concept_id")
	private Concept qualifierConcept;

	@ManyToOne
	@JoinColumn(name = "unit_concept_id")
	private Concept unitConcept;

	@ManyToOne
	@JoinColumn(name = "provider_id")
	private Provider provider;

	@ManyToOne
	@JoinColumn(name = "visit_occurrence_id")
	private VisitOccurrence visitOccurrence;

	@ManyToOne
	@JoinColumn(name = "visit_detail_id")
	private VisitDetail visitDetail;

	@Column(name = "observation_source_value")
	private String observationSourceValue;

	@ManyToOne
	@JoinColumn(name = "observation_source_concept_id")
	private Concept observationSourceConcept;

	@Column(name = "unit_source_value")
	private String unitSourceValue;

	@Column(name = "qualifier_source_value")
	private String qualifierSourceValue;

	@ManyToOne
	@JoinColumn(name = "obs_event_field_concept_id")
	private Concept obsEventFieldConcept;

	@Column(name="observation_event_id ")
	private Integer observationEventId ;

	@Column(name = "value_source_value")
	private String valueSourceValue;


	
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

	public Concept getObservationConcept() {
		return observationConcept;
	}

	public void setObservationConcept(Concept observationConcept) {
		this.observationConcept = observationConcept;
	}

	public Date getObservationDate() {
		return observationDate;
	}

	public void setObservationDate(Date observationDate) {
		this.observationDate = observationDate;
	}

	public Date getObservationDateTime() {
		return observationDateTime;
	}

	public void setObservationDateTime(Date observationDateTime) {
		this.observationDateTime = observationDateTime;
	}

	public Concept getObservationTypeConcept() {
		return observationTypeConcept;
	}

	public void setObservationTypeConcept(Concept observationTypeConcept) {
		this.observationTypeConcept = observationTypeConcept;
	}

	public Double getValueAsNumber() {
		return valueAsNumber;
	}

	public void setValueAsNumber(Double valueAsNumber) {
		this.valueAsNumber = valueAsNumber;
	}

	public String getValueAsString() {
		return valueAsString;
	}

	public void setValueAsString(String valueAsString) {
		this.valueAsString = valueAsString;
	}

	public Concept getValueAsConcept() {
		return valueAsConcept;
	}

	public void setValueAsConcept(Concept valueAsConcept) {
		this.valueAsConcept = valueAsConcept;
	}

	public Concept getQualifierConcept () {
		return qualifierConcept;
	}
	
	public void setQualifierConcept (Concept qualifierConcept) {
		this.qualifierConcept = qualifierConcept;
	}
	
	public Concept getUnitConcept() {
		return unitConcept;
	}

	public void setUnitConcept(Concept unitConcept) {
		this.unitConcept = unitConcept;
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
	
	public String getObservationSourceValue() {
		return observationSourceValue;
	}

	public void setObservationSourceValue(String observationSourceValue) {
		this.observationSourceValue = observationSourceValue;
	}

	public Concept getObservationSourceConcept() {
		return observationSourceConcept;
	}
	
	public void setObservationSourceConcept(Concept observationSourceConcept) {
		this.observationSourceConcept = observationSourceConcept;
	}
	
	public String getUnitSourceValue() {
		return unitSourceValue;
	}

	public void setUnitSourceValue(String unitSourceValue) {
		this.unitSourceValue = unitSourceValue;
	}

	public String getQualifierSourceValue () {
		return qualifierSourceValue;
	}
	
	public void setQualifierSourceValue (String qualifierSourceValue) {
		this.qualifierSourceValue = qualifierSourceValue;
	}
	
	public Concept getObsEventFieldConcept() {
		return obsEventFieldConcept;
	}
	
	public void setObsEventFieldConcept(Concept obsEventFieldConcept) {
		this.obsEventFieldConcept = obsEventFieldConcept;
	}

	public String getValueSourceValue() {
		return valueSourceValue;
	}

	public void setValueSourceValue(String valueSourceValue) {
		this.valueSourceValue = valueSourceValue;
	}

	public Integer getObservationEventId() {
		return observationEventId;
	}

	public void setObservationEventId(Integer observationEventId) {
		this.observationEventId = observationEventId;
	}

	@Override
	public Long getIdAsLong() {
		return getId();
	}

}
