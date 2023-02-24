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

package org.bih.eos.jpabase.jpa.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.bih.eos.jpabase.model.entity.JPABaseEntity;
import org.springframework.stereotype.Repository;

@Repository
public abstract class JPABaseEntityDao<T extends JPABaseEntity> implements IDao<T> {
	@PersistenceContext
	private EntityManager em;

	public EntityManager getEntityManager() {
		return em;
	}
	
	public void add(T baseEntity) {
		em.persist(baseEntity);
	}
	
	public T findById(Class<T> entityClass, Long id) {
		return em.find(entityClass, id);
	}
	
	public void merge(T baseEntity) {
		em.merge(baseEntity);
	}

	public void rollback() {
		em.getTransaction().rollback();
	}

	public Long delete(Class<T> entityClass, Long id) {
		T entity = findById(entityClass, id);
		if (entity != null) {
			em.remove(entity);
			return id;
		} else {
			return 0L;
		}
	}
	
}
