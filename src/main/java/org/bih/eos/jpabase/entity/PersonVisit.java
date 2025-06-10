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

package org.bih.eos.jpabase.entity;


import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "person_visit")
public class PersonVisit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
	
    
    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "visit_occurrence_id", referencedColumnName = "visit_occurrence_id")
    private VisitOccurrence visitOccurrence;
    
    @Column(name = "ehr_id", nullable = false)
    private String ehrId;

    @Column(name = "source_visit", nullable = false)
    private String sourceVisit;
	
    @Column(name = "visit_start_date", nullable = false)
    private Date visitStartDate;

    @Column(name = "visit_start_datetime")
    private Date visitStartDateTime;

    @Column(name = "visit_end_date", nullable = false)
    private Date visitEndDate;

    @Column(name = "visit_end_datetime")
    private Date visitEndDateTime;

    public Date getVisitStartDate() {
        return visitStartDate;
    }

    public void setVisitStartDate(Date visitStartDate) {
        this.visitStartDate = visitStartDate;
    }

    public Date getVisitStartDateTime() {
        return visitStartDateTime;
    }

    public void setVisitStartDateTime(Date visitStartDateTime) {
        this.visitStartDateTime = visitStartDateTime;
    }

    public Date getVisitEndDate() {
        return visitEndDate;
    }

    public void setVisitEndDate(Date visitEndDate) {
        this.visitEndDate = visitEndDate;
    }

    public Date getVisitEndDateTime() {
        return visitEndDateTime;
    }

    public void setVisitEndDateTime(Date visitEndDateTime) {
        this.visitEndDateTime = visitEndDateTime;
    }

    public String getEhrId() {
		return ehrId;
	}

	public void setEhrId(String ehrId) {
		this.ehrId = ehrId;
	}

	public String getSourceVisit() {
		return sourceVisit;
	}

	public void setSourceVisit(String sourceVisit) {
		this.sourceVisit = sourceVisit;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public VisitOccurrence getVisitOccurrence() {
		return visitOccurrence;
	}

	public void setVisitOccurrence(VisitOccurrence visitOccurrence) {
		this.visitOccurrence = visitOccurrence;
	}

	@Override
    public String toString() {
        return "PersonVisit{" +
                "ehrId='" + getEhrId() + '\'' +
                ", sourceVisit='" + getSourceVisit() + '\'' +
                ", visitStartDate=" + visitStartDate +
                ", visitEndDate=" + visitEndDate +
                '}';
    }
}