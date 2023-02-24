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
		name="procedure_occurrence",
		indexes = { 
				@Index(name = "idx_procedure_concept_id", columnList = "procedure_concept_id"), 
				@Index(name = "idx_procedure_person_id", columnList = "person_id")
				}
		)
public class ProcedureOccurrence extends JPABaseEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="procedure_occurrence_seq_gen")
	@SequenceGenerator(name="procedure_occurrence_seq_gen", sequenceName="procedure_occurrence_id_seq", allocationSize=1)
	@Column(name = "procedure_occurrence_id")
	@Access(AccessType.PROPERTY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "person_id", nullable = false)
	private Person person;

	@ManyToOne
	@JoinColumn(name = "procedure_concept_id")
	private Concept procedureConcept;

	@Column(name = "procedure_date", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date procedureDate;

	@Column(name = "procedure_datetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date procedureDateTime;


	@Column(name = "procedure_end_date")
	@Temporal(TemporalType.DATE)
	private Date procedureEndDate;

	@Column(name = "procedure_end_datetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date procedureEndDateTime;

	@ManyToOne
	@JoinColumn(name = "procedure_type_concept_id", nullable = false)
	private Concept procedureTypeConcept;

	@ManyToOne
	@JoinColumn(name = "modifier_concept_id")
	private Concept modifierConcept;
	
	@Column(name="quantity")
	private Integer quantity;
	
	@ManyToOne
	@JoinColumn(name = "provider_id")
	private Provider provider;

	@ManyToOne
	@JoinColumn(name = "visit_occurrence_id")
	private VisitOccurrence visitOccurrence;

	@ManyToOne
	@JoinColumn(name = "visit_detail_id")
	private VisitDetail visitDetail;

	@Column(name = "procedure_source_value")
	private String procedureSourceValue;

	@ManyToOne
	@JoinColumn(name = "procedure_source_concept_id")
	private Concept procedureSourceConcept;

	@Column(name = "modifier_source_value")
	private String modifierSourceValue;

	public ProcedureOccurrence() {
		super();
	}

	public ProcedureOccurrence(Long id) {
		super();
		this.id = id;
	}

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

	public Concept getProcedureConcept() {
		return procedureConcept;
	}

	public void setProcedureConcept(Concept procedureConcept) {
		this.procedureConcept = procedureConcept;
	}

	public Date getProcedureDate() {
		return procedureDate;
	}

	public void setProcedureDate(Date procedureStartDate) {
		this.procedureDate = procedureStartDate;
	}

	public Date getProcedureDateTime() {
		return procedureDateTime;
	}

	public void setProcedureDateTime(Date procedureStartDateTime) {
		this.procedureDateTime = procedureStartDateTime;
	}

	public Date getProcedureEndDate() {
		return procedureEndDate;
	}

	public void setProcedureEndDate(Date procedureEndDate) {
		this.procedureEndDate = procedureEndDate;
	}

	public Date getProcedureEndDateTime() {
		return procedureEndDateTime;
	}

	public void setProcedureEndDateTime(Date procedureEndDateTime) {
		this.procedureEndDateTime = procedureEndDateTime;
	}

	public Concept getProcedureTypeConcept() {
		return procedureTypeConcept;
	}

	public void setProcedureTypeConcept(Concept procedureTypeConcept) {
		this.procedureTypeConcept = procedureTypeConcept;
	}

	public Concept getModifierConcept() {
		return modifierConcept;
	}

	public void setModifierConcept(Concept modifierConcept) {
		this.modifierConcept = modifierConcept;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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
		return visitDetail;
	}

	public void setVisitDetail(VisitDetail visitDetail) {
		this.visitDetail = visitDetail;
	}

	public String getProcedureSourceValue() {
		return procedureSourceValue;
	}

	public void setProcedureSourceValue(String procedureSourceValue) {
		this.procedureSourceValue = procedureSourceValue;
	}

	public Concept getProcedureSourceConcept() {
		return procedureSourceConcept;
	}

	public void setProcedureSourceConcept(Concept procedureSourceConcept) {
		this.procedureSourceConcept = procedureSourceConcept;
	}

	public String getModifierSourceValue() {
		return modifierSourceValue;
	}

	public void setModifierSourceValue(String modifierSourceValue) {
		this.modifierSourceValue = modifierSourceValue;
	}

	@Override
	public Long getIdAsLong() {
		return getId();
	}

}
