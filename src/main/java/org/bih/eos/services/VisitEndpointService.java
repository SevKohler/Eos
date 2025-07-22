package org.bih.eos.services;

import java.util.List;

import org.bih.eos.controller.dao.Ehrs;
import org.bih.eos.jpabase.entity.PersonVisit;

public interface VisitEndpointService {

	List<PersonVisit> findAll(String aql);
	List<PersonVisit> findEHRVisits(String aql, Ehrs ehrList);

}