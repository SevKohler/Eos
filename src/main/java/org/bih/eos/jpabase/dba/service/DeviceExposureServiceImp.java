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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import org.bih.eos.jpabase.jpa.dao.DeviceExposureDao;
import org.bih.eos.jpabase.model.entity.DeviceExposure;


/**
 * The Class DeviceExposureServiceImp.
 */
@Service
public class DeviceExposureServiceImp extends BaseEntityServiceImp<DeviceExposure, DeviceExposureDao>
		implements DeviceExposureService {		
	final static Logger logger = LoggerFactory.getLogger(DrugExposureServiceImp.class);

	/**
	 * Instantiates a new device exposure service imp.
	 */
	public DeviceExposureServiceImp() {
		super(DeviceExposure.class);
	}

	/*
 	 * (non-Javadoc)
 	 * 
 	 * @see
 	 * edu.gatech.chai.omopv5.dba.service.FPersonService#searchByNameAndLocation(
 	 * java.lang.String, java.lang.String, java.lang.String,
 	 * edu.gatech.chai.omopv5.model.entity.Location)
 	 */
 //	@Transactional(readOnly = true)
 //	public DrugExposure searchBySql(String queryString, Map<String, String> parameters) {
 //		EntityManager em = getEntityDao().getEntityManager();
 //
 //		logger.debug("searchSql:" + queryString);
 //		TypedQuery<? extends DrugExposure> query = em.createQuery(queryString, DrugExposure.class);
 //		for (Map.Entry<String, String> entry : parameters.entrySet()) {
 //			query.setParameter(entry.getKey(), entry.getValue());
 //		}
 //
 //		List<? extends DrugExposure> results = query.getResultList();
 //		if (results.size() > 0) {
 //			return results.get(0);
 //		} else {
 //			return null;
 //		}
 //	}
}
