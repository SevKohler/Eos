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
@Table(name="device_exposure")
public class DeviceExposure extends JPABaseEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="device_exposure_seq_gen")
	@SequenceGenerator(name="device_exposure_seq_gen", sequenceName="device_exposure_id_seq", allocationSize=1)
	@Column(name="device_exposure_id", updatable= false)
	@Access(AccessType.PROPERTY)
	private Long id;

	@ManyToOne
	@JoinColumn(name="person_id", nullable=false)
	private Person person;

	@ManyToOne
	@JoinColumn(name="device_concept_id", nullable=false)
	private Concept deviceConcept;

	@Column(name="device_exposure_start_date", nullable=false)
	@Temporal(TemporalType.DATE)
	private Date deviceExposureStartDate;
	
	@Column(name="device_exposure_start_datetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date deviceExposureStartDateTime;

	@Column(name="device_exposure_end_date")
	@Temporal(TemporalType.DATE)
	private Date deviceExposureEndDate;

	@Column(name="device_exposure_end_datetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date deviceExposureEndDateTime;

	@ManyToOne
	@JoinColumn(name="device_type_concept_id", nullable=false)
	private Concept deviceTypeConcept;

	@Column(name="unique_device_id")
	private String uniqueDeviceId;
	
	@Column(name="quantity")
	private Integer quantity;
	
	@ManyToOne
	@JoinColumn(name="provider_id")
	private Provider provider;
	
	@ManyToOne
	@JoinColumn(name="visit_occurrence_id")
	private VisitOccurrence visitOccurrence;
	
	@ManyToOne
	@JoinColumn(name = "visit_detail_id")
	private VisitDetail visitDetail;

	@Column(name="device_source_value")
	private String deviceSourceValue;

	@Column(name="production_id")
	private String productionId;
	
	@ManyToOne
	@JoinColumn(name="device_source_concept_id")
	private Concept deviceSourceConcept;

	@ManyToOne
	@JoinColumn(name = "unit_concept_id")
	private Concept unitConcept;

	@ManyToOne
	@JoinColumn(name = "unit_source_concept_id")
	private Concept unitSourceConcept;

	@Column(name="unit_source_value")
	private String unitSourceValue;

	public Long getId() {
		return id;
	}
	
	public void setId (Long id) {
		this.id = id;
	}

	public Person getPerson () {
		return person;
	}
	
	public void setPerson (Person person) {
		this.person = person;
	}
	
	public Concept getDeviceConcept () {
		return deviceConcept;
	}
	
	public void setDeviceConcept (Concept deviceConcept) {
		this.deviceConcept = deviceConcept;
	}
	
	public Date getDeviceExposureStartDate () {
		return deviceExposureStartDate;
	}
	
	public void setDeviceExposureStartDate (Date deviceExposureStartDate) {
		this.deviceExposureStartDate = deviceExposureStartDate;
	}
	
	public Date getDeviceExposureStartDateTime () {
		return deviceExposureStartDateTime;
	}
	
	public void setDeviceExposureStartDateTime (Date deviceExposureStartDateTime) {
		this.deviceExposureStartDateTime = deviceExposureStartDateTime;
	}
	
	public Date getDeviceExposureEndDate () {
		return deviceExposureEndDate;
	}
	
	public void setDeviceExposureEndDate (Date deviceExposureEndDate) {
		this.deviceExposureEndDate = deviceExposureEndDate;
	}

	public Date getDeviceExposureEndDateTime () {
		return deviceExposureEndDateTime;
	}
	
	public void setDeviceExposureEndDateTime (Date deviceExposureEndDateTime) {
		this.deviceExposureEndDateTime = deviceExposureEndDateTime;
	}

	public Concept getDeviceTypeConcept () {
		return deviceTypeConcept;
	}
	
	public void setDeviceTypeConcept (Concept deviceTypeConcept) {
		this.deviceTypeConcept = deviceTypeConcept;
	}
	
	public String getUniqueDeviceId () {
		return uniqueDeviceId;
	}
	
	public void setUniqueDeviceId (String uniqueDeviceId) {
		this.uniqueDeviceId = uniqueDeviceId;
	}
	
	public Integer getQuantity() {
		return this.quantity;
	}
	
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	public Provider getProvider () {
		return provider;
	}
	
	public void setProvider (Provider provider) {
		this.provider = provider;
	}
	
	public VisitOccurrence getVisitOccurrence () {
		return visitOccurrence;
	}
	
	public void setVisitOccurrence (VisitOccurrence visitOccurrence) {
		this.visitOccurrence = visitOccurrence;
	}
	
	public VisitDetail getVisitDetail() {
		return this.visitDetail;
	}
	
	public void setVisitDetail(VisitDetail visitDetail) {
		this.visitDetail = visitDetail;
	}
	
	public String getDeviceSourceValue () {
		return this.deviceSourceValue;
	}
	
	public void setDeviceSourceValue (String deviceSourceValue) {
		this.deviceSourceValue = deviceSourceValue;
	}
	
	public Concept getDeviceSourceConcept () {
		return this.deviceSourceConcept;
	}
	
	public void setDeviceSourceConcept (Concept deviceSourceConcept) {
		this.deviceSourceConcept = deviceSourceConcept;
	}

	public String getProductionId() {
		return productionId;
	}

	public void setProductionId(String productionId) {
		this.productionId = productionId;
	}

	public Concept getUnitConcept() {
		return unitConcept;
	}

	public void setUnitConcept(Concept unitConcept) {
		this.unitConcept = unitConcept;
	}

	public Concept getUnitSourceConcept() {
		return unitSourceConcept;
	}

	public void setUnitSourceConcept(Concept unitSourceConcept) {
		this.unitSourceConcept = unitSourceConcept;
	}

	public String getUnitSourceValue() {
		return unitSourceValue;
	}

	public void setUnitSourceValue(String unitSourceValue) {
		this.unitSourceValue = unitSourceValue;
	}

	@Override
	public Long getIdAsLong() {
		return getId();
	}

}
