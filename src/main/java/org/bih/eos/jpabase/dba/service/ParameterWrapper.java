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

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.bih.eos.jpabase.model.entity.JPABaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * ParameterWrapper for database operations.
 * 
 * paramterType stores variable type such as String, Short, etc.
 * constructPredicate() method should convert this to appropriate type for the
 * database.
 * 
 * parameters store column name(s).
 * 
 * operators store SQL comparisons
 * 
 * values store value(s) to be compared.
 * 
 * relationship store either "or" or "and" - relationship between parameters and
 * values default is "or"
 * 
 * Parameters and values both cannot be multiple at the same time. Either one
 * should be single.
 * 
 * eg) parameters: "givenName1", "givenName2" values: "TOM" operator: "like"
 * relationship: "or"
 * 
 * This should be read as, Column_givenName1 like "TOM" or Column_givenName2
 * like "TOM"
 * 
 * The operators should match with largest number of parameters or values. It
 * means, if there are 3 parameters (there should be only one value), then 3
 * operators are needed for each parameter. If there are 3 values (there should
 * be only one parameter), then 3 operators are needed for each value.
 * 
 * The order should be parameter(left)-operator-value(right). So, put the
 * operator in this order.
 * 
 * @author mc142
 *
 */
public class ParameterWrapper {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(ParameterWrapper.class);
	
	/** The parameter type. */
	private String parameterType;
	
	/** The parameters. */
	private List<String> parameters;
	
	/** The operators. */
	private List<String> operators;
	
	/** The values. */
	private List<String> values;
	
	/** The relationship. */
	private String relationship;
	
	/** The upper relationship. */
	private String upperRelationship;

	/**
	 * Instantiates a new parameter wrapper.
	 */
	public ParameterWrapper() {
	}

	/**
	 * Instantiates a new parameter wrapper.
	 *
	 * @param parameterType the parameter type
	 * @param parameters the parameters
	 * @param operators the operators
	 * @param values the values
	 * @param relationship the relationship
	 */
	public ParameterWrapper(String parameterType, List<String> parameters, List<String> operators, List<String> values,
			String relationship) {
		this.parameterType = parameterType;
		this.parameters = parameters;
		this.operators = operators;
		this.values = values;
		this.relationship = relationship;
		this.upperRelationship = null; // this is used only at the special case.
	}

	/**
	 * Gets the parameter type.
	 *
	 * @return the parameter type
	 */
	public String getParameterType() {
		return parameterType;
	}

	/**
	 * Sets the parameter type.
	 *
	 * @param parameterType the new parameter type
	 */
	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}

	/**
	 * Gets the parameters.
	 *
	 * @return the parameters
	 */
	public List<String> getParameters() {
		return parameters;
	}

	/**
	 * Sets the parameters.
	 *
	 * @param parameters the new parameters
	 */
	public void setParameters(List<String> parameters) {
		this.parameters = parameters;
	}

	/**
	 * Gets the operators.
	 *
	 * @return the operators
	 */
	public List<String> getOperators() {
		return operators;
	}

	/**
	 * Sets the operators.
	 *
	 * @param operators the new operators
	 */
	public void setOperators(List<String> operators) {
		this.operators = operators;
	}

	/**
	 * Gets the values.
	 *
	 * @return the values
	 */
	public List<String> getValues() {
		return values;
	}

	/**
	 * Sets the values.
	 *
	 * @param values the new values
	 */
	public void setValues(List<String> values) {
		this.values = values;
	}

	/**
	 * Gets the relationship.
	 *
	 * @return the relationship
	 */
	public String getRelationship() {
		return relationship;
	}

	/**
	 * Sets the relationship.
	 *
	 * @param relationship the new relationship
	 */
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	/**
	 * Gets the upper relationship.
	 *
	 * @return the upper relationship
	 */
	public String getUpperRelationship() {
		return upperRelationship;
	}

	/**
	 * Sets the upper relationship.
	 *
	 * @param upperRelationship the new upper relationship
	 */
	public void setUpperRelationship(String upperRelationship) {
		this.upperRelationship = upperRelationship;
	}

	/**
	 * Construct predicate.
	 *
	 * @param builder the builder
	 * @param paramList the param list
	 * @param rootUser the root user
	 * @return the list
	 */
	public static List<Predicate> constructPredicate(CriteriaBuilder builder, List<ParameterWrapper> paramList,
			Root<? extends JPABaseEntity> rootUser) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		Predicate where = null;

		// paramList has FHIR parameters mapped Omop parameters (or columns).
		for (ParameterWrapper param : paramList) {
			Predicate subWhere;
			if (param.getRelationship() == null || param.getRelationship().equalsIgnoreCase("or"))
				subWhere = builder.disjunction();
			else
				subWhere = builder.conjunction();

			switch (param.getParameterType()) {
			case "String":
				logger.debug("String parameter type found.");
				String attributeName = null;
				String valueName = null;
				for (Iterator<String> attributeIter = param.getParameters().iterator(), operIter = param.getOperators()
						.iterator(), valueIter = param.getValues()
								.iterator(); (attributeIter.hasNext() || valueIter.hasNext()) && operIter.hasNext();) {

					if (attributeIter.hasNext())
						attributeName = attributeIter.next();
					if (valueIter.hasNext())
						valueName = valueIter.next();
					String oper = operIter.next();
					logger.debug("--- Attribute name:" + attributeName);
					logger.debug("--- value:" + valueName);
					logger.debug("--- operator:" + oper);
					Path<String> path;
					String[] columnPath = attributeName.split("\\.");
					if (columnPath.length == 2) {
						path = rootUser.get(columnPath[0]).get(columnPath[1]);
					} else if (columnPath.length == 3) {
						path = rootUser.get(columnPath[0]).get(columnPath[1]).get(columnPath[2]);
					} else {
						path = rootUser.get(attributeName);
					}

					if (oper.equalsIgnoreCase("like")) {
						if (param.getRelationship() == null || param.getRelationship().equals("or")) {
							subWhere = builder.or(subWhere, builder.like(builder.lower(path), valueName.toLowerCase()));
						} else {
							subWhere = builder.and(subWhere, builder.like(builder.lower(path), valueName.toLowerCase()));
						}
					} else if (oper.equalsIgnoreCase("=")) {
						if (param.getRelationship() == null || param.getRelationship().equals("or")) {
							subWhere = builder.or(subWhere, builder.equal(path, valueName));
						} else {
							subWhere = builder.and(subWhere, builder.equal(path, valueName));
						}
					} else if (oper.equalsIgnoreCase("!=")) {
						if (param.getRelationship() == null || param.getRelationship().equals("or")) {
							subWhere = builder.or(subWhere, builder.notEqual(path, valueName));
						} else {
							subWhere = builder.and(subWhere, builder.notEqual(path, valueName));
						}
					} else {
						if (param.getRelationship() == null || param.getRelationship().equals("or")) {
							subWhere = builder.or(subWhere, builder.notLike(builder.lower(path), valueName.toLowerCase()));
						} else {
							subWhere = builder.and(subWhere, builder.notLike(builder.lower(path), valueName.toLowerCase()));
						}
					}
				}
				break;
			case "Code:In":
				logger.debug("Code:In parameter type found.");
				attributeName = null;
				valueName = null;
				for (Iterator<String> attributeIter = param.getParameters().iterator(), operIter = param.getOperators()
						.iterator(), valueIter = param.getValues()
								.iterator(); (attributeIter.hasNext() || valueIter.hasNext()) && operIter.hasNext();) {

					if (attributeIter.hasNext())
						attributeName = attributeIter.next();
					if (valueIter.hasNext())
						valueName = valueIter.next();
					String oper = operIter.next();
					logger.debug("--- Attribute name:" + attributeName);
					logger.debug("--- value:" + valueName);
					logger.debug("--- operator:" + oper);
					Path<String> path;
					String[] columnPath = attributeName.split("\\.");
					if (columnPath.length == 2) {
						path = rootUser.get(columnPath[0]).get(columnPath[1]);
					} else if (columnPath.length == 3) {
						path = rootUser.get(columnPath[0]).get(columnPath[1]).get(columnPath[2]);
					} else {
						path = rootUser.get(attributeName);
					}

					if (oper.equalsIgnoreCase("like")) {
						if (param.getRelationship() == null || param.getRelationship().equals("or")) {
							subWhere = builder.or(subWhere, builder.like(path, valueName));
						} else {
							subWhere = builder.and(subWhere, builder.like(path, valueName));
						}
					} else if (oper.equalsIgnoreCase("=")) {
						if (param.getRelationship() == null || param.getRelationship().equals("or")) {
							subWhere = builder.or(subWhere, builder.equal(path, valueName));
						} else {
							subWhere = builder.and(subWhere, builder.equal(path, valueName));
						}
					} else if (oper.equalsIgnoreCase("!=")) {
						if (param.getRelationship() == null || param.getRelationship().equals("or")) {
							subWhere = builder.or(subWhere, builder.notEqual(path, valueName));
						} else {
							subWhere = builder.and(subWhere, builder.notEqual(path, valueName));
						}
					} else if (oper.equalsIgnoreCase("in")) {
						if (param.getRelationship() == null || param.getRelationship().equals("or")) {
							subWhere = builder.or(subWhere, builder.like(path, valueName));
						} else {
							subWhere = builder.and(subWhere, builder.like(path, valueName));
						}
					} else {
						if (param.getRelationship() == null || param.getRelationship().equals("or")) {
							subWhere = builder.or(subWhere, builder.notLike(path, valueName));
						} else {
							subWhere = builder.and(subWhere, builder.notLike(path, valueName));
						}
					}
				}
				break;
			case "Date":
				logger.debug("Numeric parameter type found.");
				attributeName = null;
				valueName = null;
				for (Iterator<String> attributeIter = param.getParameters().iterator(), operIter = param.getOperators()
						.iterator(), valueIter = param.getValues()
								.iterator(); (attributeIter.hasNext() || valueIter.hasNext()) && operIter.hasNext();) {

					if (attributeIter.hasNext())
						attributeName = attributeIter.next();
					if (valueIter.hasNext())
						valueName = valueIter.next();
					String oper = operIter.next();
					logger.debug("--- Attribute name:" + attributeName);
					logger.debug("--- value:" + valueName);
					logger.debug("--- operator:" + oper);
					Long dateInMili = Long.valueOf(valueName);
					Date value = new Date(dateInMili);

					Path<Date> path;
					String[] columnPath = attributeName.split("\\.");
					if (columnPath.length == 2) {
						path = rootUser.get(columnPath[0]).get(columnPath[1]);
					} else if (columnPath.length == 3) {
						path = rootUser.get(columnPath[0]).get(columnPath[1]).get(columnPath[2]);
					} else {
						path = rootUser.get(attributeName);
					}

					if (oper.equalsIgnoreCase("=")) {
						if (param.getRelationship() == null || param.getRelationship().equals("or")) {
							subWhere = builder.or(subWhere, builder.equal(path, value));
						} else {
							subWhere = builder.and(subWhere, builder.equal(path, value));
						}
					} else if (oper.equalsIgnoreCase("!=")) {
						if (param.getRelationship() == null || param.getRelationship().equals("or")) {
							subWhere = builder.or(subWhere, builder.notEqual(path, value));
						} else {
							subWhere = builder.and(subWhere, builder.notEqual(path, value));
						}
					} else if (oper.equalsIgnoreCase("<"))
						if (param.getRelationship() == null || param.getRelationship().equals("or")) {
							subWhere = builder.or(subWhere, builder.lessThan(path, value));
						} else {
							subWhere = builder.and(subWhere, builder.lessThan(path, value));
						}
					else if (oper.equalsIgnoreCase("<="))
						if (param.getRelationship() == null || param.getRelationship().equals("or")) {
							subWhere = builder.or(subWhere, builder.lessThanOrEqualTo(path, value));
						} else {
							subWhere = builder.and(subWhere, builder.lessThanOrEqualTo(path, value));
						}
					else if (oper.equalsIgnoreCase(">")) {
						if (param.getRelationship() == null || param.getRelationship().equals("or")) {
							subWhere = builder.or(subWhere, builder.greaterThan(path, value));
						} else {
							subWhere = builder.and(subWhere, builder.greaterThan(path, value));
						}
					} else { // (param.getOperator().equalsIgnoreCase(">="))
						if (param.getRelationship() == null || param.getRelationship().equals("or")) {
							subWhere = builder.or(subWhere, builder.greaterThanOrEqualTo(path, value));
						} else {
							subWhere = builder.and(subWhere, builder.greaterThanOrEqualTo(path, value));
						}
					}
				}
				break;
			case "Short":
			case "Long":
			case "Double":
			case "Integer":
				subWhere = numbericPredicateBuidler(builder, param, rootUser, subWhere, param.getParameterType());
				break;
			}

			if (subWhere == null) {
				logger.error("where clause contains error");
				where = null;
				break;
			}
			
			if (param.getUpperRelationship() != null && param.getUpperRelationship().equalsIgnoreCase("or")) {
				if (where == null) where = builder.disjunction();
				where = builder.or(where, subWhere);
			} else {
				if (where == null) where = builder.conjunction();
				where = builder.and(where, subWhere);
			}
		}

		if (where == null)
			return null;
		
		predicates.add(where);
		return predicates;
	}

	/**
	 * Numberic predicate buidler.
	 *
	 * @param builder the builder
	 * @param param the param
	 * @param rootUser the root user
	 * @param subWhere the sub where
	 * @param paramType the param type
	 * @return the predicate
	 */
	public static Predicate numbericPredicateBuidler(CriteriaBuilder builder, ParameterWrapper param,
                                                     Root<? extends JPABaseEntity> rootUser, Predicate subWhere, String paramType) {
		logger.debug("Numeric parameter type found.");
		// We may have multiple columns to compare with 'or'. If
		// so, get them now.
		// for (String columnName : param.getParameters(),
		// String oper: param.getOperators()) {
		String attributeName = null;
		String valueName = null;
		for (Iterator<String> attributeIter = param.getParameters().iterator(), operIter = param.getOperators()
				.iterator(), valueIter = param.getValues().iterator(); (attributeIter.hasNext() || valueIter.hasNext())
						&& operIter.hasNext();) {

			if (attributeIter.hasNext()) {
				attributeName = attributeIter.next();
// TODO: 				
//				// if attributename contains foreign table
//				// Current version supports VisitOccurrence.
//				String[] attributes = attributeName.split(":");
//				if (attributes.length == 2) {
//					attributeName = attributes[1];
//					if ("VisitOccurrence".equals(attributes[0])) {
//						
//						Join<? extends BaseEntity, VisitOccurrence> visitOccurrenceJoin = rootUser.join(attributeName);
//					} else {
//						return null;
//					}
//				}
			}
			if (valueIter.hasNext())
				valueName = valueIter.next();
			String oper = operIter.next();
			logger.debug("--- Attribute name:" + attributeName);
			logger.debug("--- value:" + valueName);
			logger.debug("--- operator:" + oper);
			Number value;
			if (paramType.equals("Short")) {
				value = Short.valueOf(valueName);
			} else if (paramType.equals("Long")) {
				value = Long.valueOf(valueName);
			} else if (paramType.equals("Double")) {
				value = Double.valueOf(valueName);
			} else {
				value = Integer.valueOf(valueName);
			}

			Path<Number> path;
			String[] columnPath = attributeName.split("\\.");
			if (columnPath.length == 2) {
				path = rootUser.get(columnPath[0]).get(columnPath[1]);
			} else if (columnPath.length == 3) {
				path = rootUser.get(columnPath[0]).get(columnPath[1]).get(columnPath[2]);
			} else {
				path = rootUser.get(attributeName);
			}

			if (oper.equalsIgnoreCase("=")) {
				if (param.getRelationship() == null || param.getRelationship().equals("or")) {
					subWhere = builder.or(subWhere, builder.equal(path, value));
				} else {
					subWhere = builder.and(subWhere, builder.equal(path, value));
				}
			} else if (oper.equalsIgnoreCase("!=")) {
				if (param.getRelationship() == null || param.getRelationship().equals("or")) {
					subWhere = builder.or(subWhere, builder.notEqual(path, value));
				} else {
					subWhere = builder.and(subWhere, builder.notEqual(path, value));
				}
			} else if (oper.equalsIgnoreCase("<"))
				if (param.getRelationship() == null || param.getRelationship().equals("or")) {
					subWhere = builder.or(subWhere, builder.lt(path, value));
				} else {
					subWhere = builder.and(subWhere, builder.lt(path, value));
				}
			else if (oper.equalsIgnoreCase("<="))
				if (param.getRelationship() == null || param.getRelationship().equals("or")) {
					subWhere = builder.or(subWhere, builder.le(path, value));
				} else {
					subWhere = builder.and(subWhere, builder.le(path, value));
				}
			else if (oper.equalsIgnoreCase(">")) {
				if (param.getRelationship() == null || param.getRelationship().equals("or")) {
					subWhere = builder.or(subWhere, builder.gt(path, value));
				} else {
					subWhere = builder.and(subWhere, builder.gt(path, value));
				}
			} else { // (param.getOperator().equalsIgnoreCase(">="))
				if (param.getRelationship() == null || param.getRelationship().equals("or")) {
					subWhere = builder.or(subWhere, builder.ge(path, value));
				} else {
					subWhere = builder.and(subWhere, builder.ge(path, value));
				}
			}
		}

		return subWhere;
	}

}
