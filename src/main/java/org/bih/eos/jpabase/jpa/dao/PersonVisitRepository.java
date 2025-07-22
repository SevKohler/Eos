package org.bih.eos.jpabase.jpa.dao;

import java.util.Optional;

import org.bih.eos.jpabase.entity.PersonVisit;
import org.bih.eos.jpabase.entity.PersonVisitId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonVisitRepository extends JpaRepository<PersonVisit, PersonVisitId> {
    Optional<PersonVisit> findByEhrIdAndSourceVisit(String ehrId, String sourceVisit);
}