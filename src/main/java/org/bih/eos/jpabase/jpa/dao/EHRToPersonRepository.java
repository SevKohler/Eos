package org.bih.eos.jpabase.jpa.dao;

import org.bih.eos.jpabase.model.entity.EHRToPerson;
import org.bih.eos.jpabase.model.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
@Transactional
public interface EHRToPersonRepository extends JpaRepository<EHRToPerson, String> {

    List<EHRToPerson> findByPerson(Person person);
}
