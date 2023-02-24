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

@Entity
@Table(name = "ehr_to_person")
public class EHRToPerson {

    @Id
    @Column(name = "ehr_id")
    @Access(AccessType.PROPERTY)
    private String ehrId;

    @OneToOne
    @JoinColumn(name="person_id")
    private Person person;

    public String getEhrId() {
        return ehrId;
    }

    public void setEhrId(String ehrId) {
        this.ehrId = ehrId;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}