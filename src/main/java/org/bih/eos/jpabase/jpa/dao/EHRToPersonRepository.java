package org.bih.eos.jpabase.jpa.dao;

import org.bih.eos.jpabase.entity.EHRToPerson;
import org.bih.eos.jpabase.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.transaction.Transactional;
import java.util.List;
@Transactional
public interface EHRToPersonRepository extends JpaRepository<EHRToPerson, String> {

    List<EHRToPerson> findByPerson(Person person);
}
