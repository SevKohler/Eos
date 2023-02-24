/*******************************************************************************
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

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(
        name="specimen",
        indexes = {
                @Index(name = "idx_specimen_concept_id", columnList = "specimen_concept_id"),
                @Index(name = "idx_specimen_person_id", columnList = "person_id")
        }
)@Inheritance(strategy = InheritanceType.JOINED)
public class Specimen extends JPABaseEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="specimen_seq_gen")
    @SequenceGenerator(name="specimen_seq_gen", sequenceName="specimen_id_seq", allocationSize=1)
    @Column(name = "specimen_id")
    @Access(AccessType.PROPERTY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @ManyToOne
    @JoinColumn(name = "specimen_concept_id", nullable = false)
    private Concept specimenConcept;

    @ManyToOne
    @JoinColumn(name = "specimen_type_concept_id", nullable = false)
    private Concept specimenTypeConcept;

    @Column(name = "specimen_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date specimenDate;

    @Column(name = "specimen_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date specimenDateTime;

    @Column(name = "quantity")
    private Double quantity;

    @ManyToOne
    @JoinColumn(name = "unit_concept_id")
    private Concept unitConcept;

    @ManyToOne
    @JoinColumn(name = "anatomic_site_concept_id")
    private Concept anatomicSiteConcept;

    @ManyToOne
    @JoinColumn(name = "disease_status_concept_id")
    private Concept diseaseStatusConcept;

    @Column(name = "specimen_source_id")
    private String specimenSource;

    @Column(name = "specimen_source_value")
    private String specimenSourceValue;

    @Column(name = "unit_source_value")
    private String unitSourceValue;

    @Column(name = "anatomic_site_source_value")
    private String anatomicSiteSourceValue;

    @Column(name = "disease_status_source_value")
    private String diseaseStatusSourceValue;

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

    public Concept getSpecimenConcept() {
        return specimenConcept;
    }

    public void setSpecimenConcept(Concept specimenConcept) {
        this.specimenConcept = specimenConcept;
    }

    public Concept getSpecimenTypeConcept() {
        return specimenTypeConcept;
    }

    public void setSpecimenTypeConcept(Concept specimenTypeConcept) {
        this.specimenTypeConcept = specimenTypeConcept;
    }

    public Date getSpecimenDate() {
        return specimenDate;
    }

    public void setSpecimenDate(Date specimenDate) {
        this.specimenDate = specimenDate;
    }

    public Date getSpecimenDateTime() {
        return specimenDateTime;
    }

    public void setSpecimenDateTime(Date specimenDateTime) {
        this.specimenDateTime = specimenDateTime;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Concept getUnitConcept() {
        return unitConcept;
    }

    public void setUnitConcept(Concept unitConcept) {
        this.unitConcept = unitConcept;
    }

    public Concept getAnatomicSiteConcept() {
        return anatomicSiteConcept;
    }

    public void setAnatomicSiteConcept(Concept anatomicSiteConcept) {
        this.anatomicSiteConcept = anatomicSiteConcept;
    }

    public Concept getDiseaseStatusConcept() {
        return diseaseStatusConcept;
    }

    public void setDiseaseStatusConcept(Concept diseaseStatusConcept) {
        this.diseaseStatusConcept = diseaseStatusConcept;
    }

    public String getSpecimenSource() {
        return specimenSource;
    }

    public void setSpecimenSource(String specimenSource) {
        this.specimenSource = specimenSource;
    }

    public String getSpecimenSourceValue() {
        return specimenSourceValue;
    }

    public void setSpecimenSourceValue(String specimenSourceValue) {
        this.specimenSourceValue = specimenSourceValue;
    }

    public String getUnitSourceValue() {
        return unitSourceValue;
    }

    public void setUnitSourceValue(String unitSourceValue) {
        this.unitSourceValue = unitSourceValue;
    }

    public String getAnatomicSiteSourceValue() {
        return anatomicSiteSourceValue;
    }

    public void setAnatomicSiteSourceValue(String anatomicSiteSourceValue) {
        this.anatomicSiteSourceValue = anatomicSiteSourceValue;
    }

    public String getDiseaseStatusSourceValue() {
        return diseaseStatusSourceValue;
    }

    public void setDiseaseStatusSourceValue(String diseaseStatusSourceValue) {
        this.diseaseStatusSourceValue = diseaseStatusSourceValue;
    }

    @Override
    public Long getIdAsLong() {
        return getId();
    }

}