package org.bih.eos.jpabase.service;

import org.bih.eos.jpabase.jpa.dao.EHRToPersonRepository;
import org.bih.eos.jpabase.entity.EHRToPerson;
import org.bih.eos.jpabase.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class EHRToPersonServiceImp implements EHRToPersonService {

    @Autowired
    private EHRToPersonRepository repository;

    @Override
    @Transactional
    public EHRToPerson save(EHRToPerson ehrToPerson) {
        return repository.save(ehrToPerson);
    }

    public Optional<EHRToPerson> findByEhrId(String id) {
        return repository.findById(id);
    }

    @Override
    public List<EHRToPerson> findByPerson(Person person) {
        return repository.findByPerson(person);
    }

    @Override
    public List<EHRToPerson> findAll() {
        return repository.findAll();
    }
}