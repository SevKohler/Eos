package org.bih.eos.services;

import org.bih.eos.jpabase.model.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersistenceService {

    @PersistenceContext
    EntityManager entityManager;
    private List<JPABaseEntity> persistedEntities = new ArrayList<>();

    @Transactional
    public List<JPABaseEntity> create(List<JPABaseEntity> convertedEntities) {
        List<JPABaseEntity> outputs = new ArrayList<>();
        for (JPABaseEntity baseEntity : convertedEntities) {
            entityManager.persist(baseEntity);
            persistedEntities.add(baseEntity);
            outputs.add(baseEntity);
        }
        return outputs;
    }

    @Transactional
    public JPABaseEntity create(JPABaseEntity convertedEntity) {
        entityManager.persist(convertedEntity);
        persistedEntities.add(convertedEntity);
        return convertedEntity;
    }

    protected List<JPABaseEntity> getPersistedEntities() {
        return persistedEntities;
    }

    protected void clearPersistedEntities() {
        this.persistedEntities = new ArrayList<>();
    }


}
