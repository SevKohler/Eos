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
package org.bih.eos.jpabase.dba.service;

import java.util.List;
import java.util.Map;

import org.bih.eos.jpabase.model.entity.JPABaseEntity;
import org.bih.eos.jpabase.model.entity.Measurement;
import org.bih.eos.jpabase.model.entity.Observation;
import org.bih.eos.jpabase.model.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.bih.eos.jpabase.jpa.dao.ConditionOccurrenceDao;
import org.bih.eos.jpabase.jpa.dao.PersonDao;
import org.bih.eos.jpabase.jpa.dao.MeasurementDao;
import org.bih.eos.jpabase.jpa.dao.ObservationDao;
import org.bih.eos.jpabase.jpa.dao.ProcedureOccurrenceDao;


/**
 * The Class TransactionServiceImp.
 */
@Service
public class TransactionServiceImp implements TransactionService {

//	@Autowired
//	private TransactionDao transactionDao;
	
	/** The person dao. */
@Autowired
	private PersonDao fPersonDao;
	
	/** The condition dao. */
	@Autowired
	private ConditionOccurrenceDao conditionDao;
	
	/** The procedure dao. */
	@Autowired
	private ProcedureOccurrenceDao procedureDao;
	
	/** The measurement dao. */
	@Autowired
	private MeasurementDao measurementDao;
	
	/** The observation dao. */
	@Autowired
	private ObservationDao observationDao;

//	public TransactionDao getEntityDao() {
//		return transactionDao;
//	}
	
	/* (non-Javadoc)
 * @see edu.gatech.chai.omopv5.dba.service.TransactionService#writeTransaction(java.util.Map)
 */
@Transactional
	public int writeTransaction(Map<String, List<JPABaseEntity>> transactionMap) {
//		EntityManager em = transactionDao.getEntityManager();

		System.out.println("At the writeTransaction");
		// It's not efficient. But, we need to process patients first. so...
		for (String key : transactionMap.keySet()) {
			String[] keyInfo = key.split("\\^");
			System.out.println("working on patient key=" + key + " with keyInfo length=" + keyInfo.length);
			if (keyInfo.length != 2) {
				// something is wrong.
				return -1;
			}
			String entityName = keyInfo[1];
			if (entityName.equals("Person")) {
				// process it.
				List<JPABaseEntity> entityClasses = transactionMap.get(key);
				if (entityClasses != null) {
					// We have patients, process this. 
					for (JPABaseEntity entity : entityClasses) {
						Person fPerson = (Person) entity;
						fPersonDao.add(fPerson);
					}
				}

			} else {
				continue;
			}			
		}
		
		for (String key : transactionMap.keySet()) {
			String[] keyInfo = key.split("\\^");
			System.out.println("working on key=" + key + " with keyInfo length=" + keyInfo.length);
			if (keyInfo.length != 2) {
				// something is wrong.
				return -1;
			}
			// 2nd part of keyInfo should be the entity table name.
			String entityName = keyInfo[1];
			if (entityName.equals("Person")) {
				// we have alreay done this.
				continue;
			}
			
			List<JPABaseEntity> entities = transactionMap.get(key);
			if (entities == null) {
				System.out.println("null entities for transactionMap.....");
				continue;
			}
			System.out.println("About to process " + entityName + " from Service");
			
			String subjectKey = keyInfo[0]+"^"+"Person";
			
			// list of the entity classes
			try {
				for (JPABaseEntity entity : entities) {
					if (entityName.equals("Measurement")) {
						System.out.println("Adding Measurement with subjectKey ("+key+") to OMOP");
						// Get patient information from subject key.
						Person subjectEntity = (Person) transactionMap.get(subjectKey).get(0);
						if (subjectEntity == null) {
							// This is an error. We must have subject.
							System.out.println("Person info not available for the Measurement");
							throw new Exception("Person info not available for the Measurement");
						}
						
						Measurement measurement = (Measurement) entity;
						measurement.setPerson(subjectEntity);
						
						measurementDao.add(measurement);
					} else if (entityName.equals("Observation")) {
						System.out.println("Adding Observation to OMOP");
						Person subjectEntity = (Person) transactionMap.get(subjectKey).get(0);
						if (subjectEntity == null) {
							// This is an error. We must have subject.
							System.out.println("Person info not available for the Observation");
							throw new Exception("Person info not available for the Observation");
						}
						
						Observation observation = (Observation) entity;
						observation.setPerson(subjectEntity);

						observationDao.add(observation);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				return -1;
			}
		}
		return 0;
	}	
}