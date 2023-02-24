package org.bih.eos.jpabase.dba.service;

import org.bih.eos.jpabase.model.entity.EHRToPerson;
import org.bih.eos.jpabase.model.entity.Person;

import java.util.List;
import java.util.Optional;

public interface EHRToPersonService  {
    public EHRToPerson save(EHRToPerson ehrToPerson);
    public List<EHRToPerson> findByPerson(Person person);
    Optional<EHRToPerson> findByEhrId(String id);
    public List<EHRToPerson> findAll();
}
