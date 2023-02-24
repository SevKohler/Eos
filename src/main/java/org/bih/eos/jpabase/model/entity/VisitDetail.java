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

@Entity
@Table(name="visit_detail")
public class VisitDetail extends JPABaseEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="visit_detail_seq_gen")
	@SequenceGenerator(name="visit_detail_seq_gen", sequenceName="visit_detail_id_seq", allocationSize=1)
	@Column(name="visit_detail_id", nullable=false)
	@Access(AccessType.PROPERTY)
	private Long id;

	@ManyToOne()
	@JoinColumn(name="person_id", nullable=false)
	private Person person;
	
	@ManyToOne()
	@JoinColumn(name="visit_detail_concept_id", nullable=false)
	private Concept visitDetailConcept;
	
	@Column(name="visit_detail_start_date", nullable=false)
	private Date visitDetailStartDate;
	
	@Column(name="visit_detail_start_datetime")
	private Date visitDetailStartDateTime;
	
	@Column(name="visit_detail_end_date", nullable=false)
	private Date visitDetailEndDate;
	
	@Column(name="visit_detail_end_datetime")
	private Date visitDetailEndDateTime;
	
	@ManyToOne
	@JoinColumn(name="visit_detail_type_concept_id", nullable=false)
	private Concept visitDetailTypeConcept;
	
	@ManyToOne()
	@JoinColumn(name="provider_id")
	private Provider provider;
	
	@ManyToOne()
	@JoinColumn(name="care_site_id")
	private CareSite careSite;
	
	@ManyToOne
	@JoinColumn(name="admitting_source_concept_id")
	private Concept admittingSourceConcept;

	@ManyToOne
	@JoinColumn(name="discharge_to_concept_id")
	private Concept dischargeToConcept;
		
	@ManyToOne
	@JoinColumn(name="preceding_visit_detail_id")
	private VisitDetail precedingVisitDetail;

	@Column(name="visit_detail_source_value")
	private String visitDetailSourceValue;
	
	@ManyToOne
	@JoinColumn(name="visit_detail_source_concept_id")
	private Concept visitDetailSourceConcept;

	@Column(name="admitting_source_value")
	private String admittingSourceValue;
	
	@Column(name="discharge_to_source_value")
	private String dischargeToSourceValue;
	
	@ManyToOne
	@JoinColumn(name="visit_detail_parent_id")
	private VisitDetail visitDetailParent;

	@ManyToOne
	@JoinColumn(name="visit_occurrence_id", nullable=false)
	private VisitOccurrence visitOccurrence;

	public VisitDetail() {
	}
	
	public VisitDetail(Long id) {
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
	
	public Concept getVisitDetailConcept() {
		return visitDetailConcept;
	}
	
	public void setVisitDetailConcept(Concept visitDetailConcept) {
		this.visitDetailConcept = visitDetailConcept;
	}
	
	public Date getVisitDetailStartDate() {
		return visitDetailStartDate;
	}
	
	public void setVisitDetailStartDate(Date visitDetailStartDate) {
		this.visitDetailStartDate = visitDetailStartDate;
	}
	
	public Date getVisitDetailStartDateTime() {
		return visitDetailStartDateTime;
	}
	
	public void setVisitDetailStartDateTime(Date visitDetailStartDateTime) {
		this.visitDetailStartDateTime = visitDetailStartDateTime;
	}
	
	public Date getVisitDetailEndDate() {
		return visitDetailEndDate;
	}
	
	public void setVisitDetailEndDate(Date visitDetailEndDate) {
		this.visitDetailEndDate = visitDetailEndDate;
	}
	
	public Date getVisitDetailEndDateTime() {
		return visitDetailEndDateTime;
	}
	
	public void setVisitDetailEndDateTime(Date visitDetailEndDateTime) {
		this.visitDetailEndDateTime = visitDetailEndDateTime;
	}
	
	public Concept getVisitDetailTypeConcept() {
		return visitDetailTypeConcept;
	}
	
	public void setVisitDetailTypeConcept(Concept visitDetailTypeConcept) {
		this.visitDetailTypeConcept = visitDetailTypeConcept;
	}
	
	public Provider getProvider() {
		return provider;
	}
	
	public void setProvider(Provider provider) {
		this.provider = provider;
	}
	
	public CareSite getCareSite() {
		return careSite;
	}
	
	public void setCareSite(CareSite careSite) {
		this.careSite = careSite;
	}
	
	public Concept getAdmittingSourceConcept() {
		return admittingSourceConcept;
	}
	
	public void setAdmittingSourceConcept(Concept admittingSourceConcept) {
		this.admittingSourceConcept = admittingSourceConcept;
	}

	public Concept getDischargeToConcept() {
		return dischargeToConcept;
	}
	
	public void setDischargeToConcept(Concept dischargeToConcept) {
		this.dischargeToConcept = dischargeToConcept;
	}

	public VisitDetail getPrecedingVisitDetail() {
		return precedingVisitDetail;
	}
	
	public void setPrecedingVisitDetail(VisitDetail precedingVisitDetail) {
		this.precedingVisitDetail = precedingVisitDetail;
	}

	public String getVisitDetailSourceValue() {
		return visitDetailSourceValue;
	}
	
	public void setVisitDetailSourceValue(String visitDetailSourceValue) {
		this.visitDetailSourceValue = visitDetailSourceValue;
	}
	
	public Concept getVisitDetailSourceConcept() {
		return visitDetailSourceConcept;
	}
	
	public void setVisitDetailSourceConcept(Concept visitDetailSourceConcept) {
		this.visitDetailSourceConcept = visitDetailSourceConcept;
	}
	
	public String getAdmittingSourceValue() {
		return admittingSourceValue;
	}
	
	public void setAdmittingSourceValue(String admittingSourceValue) {
		this.admittingSourceValue = admittingSourceValue;
	}
	
	public String getDischargeToSourceValue() {
		return dischargeToSourceValue;
	}
	
	public void setDischargeToSourceValue(String dischargeToSourceValue) {
		this.dischargeToSourceValue = dischargeToSourceValue;
	}
	
	public VisitDetail getVisitDetailParent() {
		return visitDetailParent;
	}

	public void setVisitDetailParent(VisitDetail visitDetailParent) {
		this.visitDetailParent = visitDetailParent;
	}

	public VisitOccurrence getVisitOccurrence() {
		return visitOccurrence;
	}

	public void setVisitOccurrence(VisitOccurrence visitOccurrence) {
		this.visitOccurrence = visitOccurrence;
	}
	
	@Override
	public Long getIdAsLong() {
		return getId();
	}
	
}
