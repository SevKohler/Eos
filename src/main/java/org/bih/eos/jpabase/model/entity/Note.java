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
@Table(name="note")
public class Note extends JPABaseEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="note_seq_gen")
	@SequenceGenerator(name="note_seq_gen", sequenceName="note_id_seq", allocationSize=1)
	@Column(name="note_id")
	@Access(AccessType.PROPERTY)
	private Long id;

	@ManyToOne
	@JoinColumn(name="person_id", nullable=false)
	private Person person;

	@Column(name = "note_date", nullable=false)
	@Temporal(TemporalType.DATE)
	private Date noteDate;


	@Column(name = "note_datetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date noteDateTime;

	@ManyToOne
	@JoinColumn(name = "note_event_field_concept_id", nullable = false)
	private Concept noteEventFieldConcept;

	@ManyToOne
	@JoinColumn(name = "note_type_concept_id", nullable = false)
	private Concept noteTypeConcept;

	@ManyToOne
	@JoinColumn(name = "note_class_concept_id", nullable = false)
	private Concept noteClassConcept;

	@Column(name = "note_title")
	private String noteTitle;

	@Column(name = "note_text")
	private String noteText;
	
	@ManyToOne
	@JoinColumn(name = "encoding_concept_id", nullable = false)
	private Concept encodingConcept;

	@ManyToOne
	@JoinColumn(name = "language_concept_id", nullable = false)
	private Concept languageConcept;

	@ManyToOne
	@JoinColumn(name = "provider_id")
	private Provider provider;

	@ManyToOne
	@JoinColumn(name = "visit_occurrence_id")
	private VisitOccurrence visitOccurrence;

	@ManyToOne
	@JoinColumn(name = "visit_detail_id")
	private VisitDetail visitDetail;

	@Column(name = "note_source_value")
	private String noteSourceValue;

	public Note() {
		super();
	}
	
	public Note(Long id) {
		super();
		this.id = id;
	}

	public Concept getNoteEventFieldConcept() {
		return noteEventFieldConcept;
	}

	public void setNoteEventFieldConcept(Concept noteEventFieldConcept) {
		this.noteEventFieldConcept = noteEventFieldConcept;
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
	
	public Date getNoteDate() {
		return noteDate;
	}
	
	public void setNoteDate(Date noteDate) {
		this.noteDate = noteDate;
	}

	public Date getNoteDateTime() {
		return noteDateTime;
	}
	
	public void setNoteDateTime(Date noteDateTime) {
		this.noteDateTime = noteDateTime;
	}

	public Concept getNoteTypeConcept() {
		return noteTypeConcept;
	}
	
	public void setNoteTypeConcept(Concept noteTypeConcept) {
		this.noteTypeConcept = noteTypeConcept;
	}
	
	public Concept getNoteClassConcept() {
		return noteClassConcept;
	}
	
	public void setNoteClassConcept(Concept noteClassConcept) {
		this.noteClassConcept = noteClassConcept;
	}
	
	public String getNoteTitle() {
		return noteTitle;
	}
	
	public void setNoteTitle(String noteTitle) {
		this.noteTitle = noteTitle;
	}
	
	public String getNoteText() {
		return noteText;
	}
	
	public void setNoteText(String noteText) {
		this.noteText = noteText;
	}
	
	public Concept getEncodingConcept() {
		return encodingConcept;
	}
	
	public void setEncodingConcept(Concept encodingConcept) {
		this.encodingConcept = encodingConcept;
	}
	
	public Concept getLanguageConcept() {
		return languageConcept;
	}
	
	public void setLanguageConcept(Concept languageConcept) {
		this.languageConcept = languageConcept;
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
	
	public String getNoteSourceValue() {
		return noteSourceValue;
	}
	
	public void setNoteSourceValue(String noteSourceValue) {
		this.noteSourceValue = noteSourceValue;
	}
	
	@Override
	public Long getIdAsLong() {
		return id;
	}

}
