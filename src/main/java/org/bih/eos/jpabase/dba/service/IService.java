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


/**
 * The Interface IService.
 *
 * @param <v> the generic type
 */
public interface IService<v extends JPABaseEntity> {
	
	/** The version. */
	static String version = "5.x";
	
	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the v
	 */
	v findById (Long id);
	
	/**
	 * Removes the by id.
	 *
	 * @param id the id
	 * @return the long
	 */
	public Long removeById (Long id);
	
	/**
	 * Search by column string.
	 *
	 * @param column the column
	 * @param value the value
	 * @return the list
	 */
	List<v> searchByColumnString (String column, String value);
	
	/**
	 * Search by column string.
	 *
	 * @param column the column
	 * @param value the value
	 * @return the list
	 */
	List<v> searchByColumnString (String column, Long value);
	
	/**
	 * Search with params.
	 *
	 * @param fromIndex the from index
	 * @param toIndex the to index
	 * @param paramList the param list
	 * @param sort the sort
	 * @return the list
	 */
	public List<v> searchWithParams(int fromIndex, int toIndex, List<ParameterWrapper> paramList, String sort);
	
	/**
	 * Search without params.
	 *
	 * @param fromIndex the from index
	 * @param toIndex the to index
	 * @param sort the sort
	 * @return the list
	 */
	public List<v> searchWithoutParams(int fromIndex, int toIndex, String sort);
	
	/**
 	 * Search with SQL command
 	 * @param queryString
 	 * @param parameters
 	 * @return the list
 	 */
	public List<v> searchBySql(int fromIndex, int toIndex, String queryString, Map<String, String> parameters, String sort);

	public Long getSize(String queryString, Map<String, String> parameters);

	/**
	 * Creates the.
	 *
	 * @param entity the entity
	 * @return the v
	 */
	v create(v entity);
	
	/**
	 * Update.
	 *
	 * @param entity the entity
	 * @return the v
	 */
	v update(v entity);
	
	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
	Long getSize();
	
	/**
	 * Gets the size.
	 *
	 * @param paramList the param list
	 * @return the size
	 */
	Long getSize(List<ParameterWrapper> paramList);
}
