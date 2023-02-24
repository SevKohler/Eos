package org.bih.eos.jpabase.dba.service;

import org.bih.eos.jpabase.jpa.dao.SpecimenDao;
import org.bih.eos.jpabase.model.entity.Specimen;

public class SpecimenServiceImp extends BaseEntityServiceImp<Specimen, SpecimenDao> implements SpecimenService {

    /**
     * Instantiates a new Specimen Service
     */
    public SpecimenServiceImp() {
        super(Specimen.class);
    }
}