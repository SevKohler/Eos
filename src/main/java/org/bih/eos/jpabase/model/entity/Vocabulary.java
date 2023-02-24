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

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * 
 * @author Myung Choi
 */
@Entity
@Table(name="vocabulary")
@NamedQueries(value = { @NamedQuery( name = "findReferenceById", query = "select v.vocabularyReference from Vocabulary v where v.id like :value")})
public class Vocabulary extends JPABaseEntity {
	
	@Id
	@Column(name="vocabulary_id", nullable=false)
	@Access(AccessType.PROPERTY)
	private String id;
	
	@Column(name="vocabulary_name", nullable=false)
	private String vocabularyName;
	
	@Column(name="vocabulary_reference", nullable=false)
	private String vocabularyReference;
	
	@Column(name="vocabulary_version", nullable=false)
	private String vocabularyVersion;

	@ManyToOne
	@JoinColumn(name="vocabulary_concept_id", nullable=false)
	private Concept vocabularyConcept;

	public Vocabulary() {
		super();
	}

	public Vocabulary(String id) {
		super();
		this.id = id;
	}
	
	public Vocabulary(String id, String vocabularyName) {
		super();
		this.id = id;
		this.vocabularyName = vocabularyName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVocabularyName() {
		return vocabularyName;
	}

	public void setVocabularyName(String vocabularyName) {
		this.vocabularyName = vocabularyName;
	}

	public String getVocabularyReference() {
		return vocabularyReference;
	}
	
	public void setVocabularyReference(String vocabularyReference) {
		this.vocabularyReference = vocabularyReference;
	}

	public String getVocabularyVersion() {
		return vocabularyVersion;
	}
	
	public void setVocabularyVersion(String vocabularyVersion) {
		this.vocabularyVersion = vocabularyVersion;
	}
	
	public Concept getVocabularyConcept() {
		return vocabularyConcept;
	}
	
	public void setVocabularyConcept(Concept vocabularyConcept) {
		this.vocabularyConcept = vocabularyConcept;
	}

	@Override
	public Long getIdAsLong() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
