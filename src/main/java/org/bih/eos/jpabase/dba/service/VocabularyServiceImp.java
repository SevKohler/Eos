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

package org.bih.eos.jpabase.dba.service;

import org.springframework.stereotype.Service;

import org.bih.eos.jpabase.jpa.dao.VocabularyDao;
import org.bih.eos.jpabase.model.entity.Vocabulary;


/**
 * The Class VocabularyServiceImp.
 */
@Service
public class VocabularyServiceImp extends BaseEntityServiceImp<Vocabulary, VocabularyDao> implements VocabularyService {

	/**
	 * Instantiates a new vocabulary service imp.
	 */
	public VocabularyServiceImp() {
		super(Vocabulary.class);
	}
	
	/* (non-Javadoc)
	 * @see edu.gatech.chai.omopv5.dba.service.VocabularyService#findById(java.lang.String)
	 */
	public Vocabulary findById(String id) {
		return getEntityDao().findById(getEntityClass(), id);
	}

	/* (non-Javadoc)
	 * @see edu.gatech.chai.omopv5.dba.service.VocabularyService#removeById(java.lang.String)
	 */
	public String removeById(String id) {
		return getEntityDao().delete(getEntityClass(), id);
	}
}
