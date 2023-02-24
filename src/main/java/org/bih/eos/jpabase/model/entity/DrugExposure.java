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
import javax.persistence.FetchType;
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
@Table(name="drug_exposure")
public class DrugExposure extends JPABaseEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="drug_exposure_seq_gen")
	@SequenceGenerator(name="drug_exposure_seq_gen", sequenceName="drug_exposure_id_seq", allocationSize=1)
	@Column(name="drug_exposure_id", updatable= false)
	@Access(AccessType.PROPERTY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "person_id", nullable = false)
	private Person person;
	
	@ManyToOne
	@JoinColumn(name = "drug_concept_id", nullable = false)
	private Concept drugConcept;
	
	@Column(name ="drug_exposure_start_date", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date drugExposureStartDate;
	
	@Column(name ="drug_exposure_start_datetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date drugExposureStartDateTime;
	
	@Column(name ="drug_exposure_end_date", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date drugExposureEndDate;
	
	@Column(name ="drug_exposure_end_datetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date drugExposureEndDateTime;
	
	@Column(name ="verbatim_end_date")
	@Temporal(TemporalType.DATE)
	private Date verbatimEndDate;
	
	@ManyToOne
	@JoinColumn(name = "drug_type_concept_id", nullable = false)
	private Concept drugTypeConcept;
	
	@Column(name = "stop_reason")
	private String stopReason;

	@Column(name = "refills")
	private Integer refills;

	@Column(name = "quantity")
	private Double quantity;

	@Column(name = "days_supply")
	private Integer daysSupply;
	
	@Column (name = "sig")
	private String sig;
	
	@ManyToOne
	@JoinColumn(name = "route_concept_id")
	private Concept routeConcept;
	
	@Column(name = "lot_number")
	private String lotNumber;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "provider_id")
	private Provider provider;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "visit_occurrence_id")
	private VisitOccurrence visitOccurrence;

	@ManyToOne
	@JoinColumn(name = "visit_detail_id")
	private VisitDetail visitDetail;

	@Column(name = "drug_source_value")
	private String drugSourceValue;
	
	@ManyToOne
	@JoinColumn(name = "drug_source_concept_id")
	private Concept drugSourceConcept;
	
	@Column(name = "route_source_value")
	private String routeSourceValue;
	
	@Column(name = "dose_unit_source_value")
	private String doseUnitSourceValue;
	
	
	public DrugExposure() {
	}

	public DrugExposure(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Person getPerson() {
		return this.person;
	}
	
	public void setPerson(Person person) {
		this.person = person;
	}
	
	public Concept getDrugConcept() {
		return this.drugConcept;
	}
	
	public void setDrugConcept(Concept drugConcept) {
		this.drugConcept = drugConcept;
	}
	
	public Date getDrugExposureStartDate() {
		return this.drugExposureStartDate;
	}

	public void setDrugExposureStartDate(Date drugExposureStartDate) {
		this.drugExposureStartDate = drugExposureStartDate;
	}

	public Date getDrugExposureStartDateTime() {
		return this.drugExposureStartDateTime;
	}

	public void setDrugExposureStartDateTime(Date drugExposureStartDateTime) {
		this.drugExposureStartDateTime = drugExposureStartDateTime;
	}

	public Date getDrugExposureEndDate() {
		return this.drugExposureEndDate;
	}

	public void setDrugExposureEndDate(Date drugExposureEndDate) {
		this.drugExposureEndDate = drugExposureEndDate;
	}
	
	public Date getDrugExposureEndDateTime() {
		return this.drugExposureEndDateTime;
	}

	public void setDrugExposureEndDateTime(Date drugExposureEndDateTime) {
		this.drugExposureEndDateTime = drugExposureEndDateTime;
	}
	
	public Date getVerbatimEndDate() {
		return this.verbatimEndDate;
	}

	public void setVerbatimEndDate(Date verbatimEndDate) {
		this.verbatimEndDate = verbatimEndDate;
	}
	
	public Concept getDrugTypeConcept() {
		return this.drugTypeConcept;
	}

	public void setDrugTypeConcept(Concept drugTypeConcept) {
		this.drugTypeConcept = drugTypeConcept;
	}
	
	public String getStopReason() {
		return this.stopReason;
	}
	
	public void setStopReason(String stopReason) {
		this.stopReason = stopReason;
	}

	public Integer getRefills() {
		return this.refills;
	}
	public void setRefills(Integer refills) {
		this.refills = refills;
	}

	public Double getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Integer getDaysSupply() {
		return this.daysSupply;
	}
	
	public void setDaysSupply(Integer daysSupply) {
		this.daysSupply = daysSupply;
	}
	
	public String getSig() {
		return this.sig;
	}
	
	public void setSig(String sig) {
		this.sig = sig;
	}
	
	public Concept getRouteConcept() {
		return this.routeConcept;
	}
	
	public void setRouteConcept(Concept routeConcept) {
		this.routeConcept = routeConcept;
	}
	
	public String getLotNumber() {
		return this.lotNumber;
	}
	
	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}
	
	public Provider getProvider() {
		return this.provider;
	}
	
	public void setProvider(Provider provider) {
		this.provider = provider;
	}
	
	public VisitOccurrence getVisitOccurrence() {
		return this.visitOccurrence;
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
	
	public String getDrugSourceValue() {
		return drugSourceValue;
	}
	
	public void setDrugSourceValue(String drugSourceValue) {
		this.drugSourceValue = drugSourceValue;
	}
	
	public Concept getDrugSourceConcept() {
		return this.drugSourceConcept;
	}
	
	public void setDrugSourceConcept(Concept drugSourceConcept) {
		this.drugSourceConcept = drugSourceConcept;
	}

	public String getRouteSourceValue(){
		return this.routeSourceValue;
	}
	
	public void setRouteSourceValue(String routeSourceValue) {
		this.routeSourceValue = routeSourceValue;
	}
	
	public String getDoseUnitSourceValue() {
		return this.doseUnitSourceValue;
	}
	
	public void setDoseUnitSourceValue(String doseUnitSourceValue) {
		this.doseUnitSourceValue = doseUnitSourceValue;
	}
	

	@Override
	public Long getIdAsLong() {
		return getId();
	}
}
