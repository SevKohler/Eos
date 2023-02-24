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

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="relationship")
public class Relationship extends JPABaseEntity {
	@Id
	@Column(name="relationship_id")
	@Access(AccessType.PROPERTY)
	private String id;

	@Column(name="relationship_name")
	private String relationshipName;

	@Column(name="is_hierarchical")
	private Character isHierarchical;
	
	@Column(name="defines_ancestry")
	private Character definesAncestry;

	@Column(name="reverse_relationship_id")
	private String reverseRelationshipId;

	@ManyToOne
	@JoinColumn(name="relationship_concept_id")
	private Concept relationshipConcept;
	
	public Relationship() {
		super();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRelationshipName() {
		return relationshipName;
	}

	public void setRelationshipName(String relationshipName) {
		this.relationshipName = relationshipName;
	}

	public Character getIsHierarchical() {
		return isHierarchical;
	}
	
	public void setIsHierarchical(Character isHierarchical) {
		this.isHierarchical = isHierarchical;
	}

	public Character getDefinesAncestry(){
		return definesAncestry;
	}
	public void setDefinesAncestry(Character definesAncestry) {
		this.definesAncestry = definesAncestry;
	}

	public String getReverseRelationshipId() {
		return reverseRelationshipId;
	}
	
	public void setReverseRelationshipId(String reverseRelationshipId) {
		this.reverseRelationshipId = reverseRelationshipId;
	}
	
	public Concept getRelationshipConcept() {
		return relationshipConcept;
	}
	
	public void setRelationshipConcept(Concept relationshipConcept) {
		this.relationshipConcept = relationshipConcept;
	}

}
