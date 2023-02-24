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

import javax.persistence.EntityManager;

import org.bih.eos.jpabase.jpa.dao.LocationDao;
import org.bih.eos.jpabase.model.entity.Location;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * The Class LocationServiceImp.
 */
@Service
public class LocationServiceImp extends BaseEntityServiceImp<Location, LocationDao> implements LocationService {

	/**
	 * Instantiates a new location service imp.
	 */
	public LocationServiceImp() {
		super(Location.class);
	}
	
	/* (non-Javadoc)
	 * @see edu.gatech.chai.omopv5.dba.service.LocationService#searchByAddress(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Transactional(readOnly = true)
	public Location searchByAddress(String line1, String line2, String city, String state, String zipCode) {
		EntityManager em = getEntityDao().getEntityManager();
		String query;
		List<Location> results;
		
		if (line2 != null) {
			query = query = "SELECT t FROM Location t WHERE address1 LIKE :line1 AND address2 LIKE :line2 AND city LIKE :city AND state LIKE :state AND zip LIKE :zip";
			results = em.createQuery(query, Location.class)
					.setParameter("line1", line1)
					.setParameter("line2", line2)
					.setParameter("city", city)
					.setParameter("state", state)
					.setParameter("zip", zipCode)
					.getResultList();
		} else { 
			query = "SELECT t FROM Location t WHERE address1 LIKE :line1 AND city LIKE :city AND state LIKE :state AND zip LIKE :zip";
			results = em.createQuery(query, Location.class)
					.setParameter("line1", line1)
					.setParameter("city", city)
					.setParameter("state", state)
					.setParameter("zip", zipCode)
					.getResultList();
		}

		System.out.println("loadEntityByLocaiton:"+query+" result size:"+results.size());
		if (results.size() > 0)
			return results.get(0);
		else
			return null;

	}

}
