package org.bih.eos.services;

import org.bih.eos.jpabase.entity.Death;
import org.bih.eos.jpabase.entity.JPABaseEntity;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class PersistenceService {

    @PersistenceContext
    EntityManager entityManager;

    private List<JPABaseEntity> tranformedEntities = new ArrayList<>();
    HashMap<String, Integer> baseEntityMap = new HashMap<String, Integer>();
    private List<JPABaseEntity> persistedEntities = new ArrayList<>();
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(PersistenceService.class);

    @Transactional
    public void createBatch(List<JPABaseEntity> convertedEntities) {
        for (JPABaseEntity baseEntity : convertedEntities) {
            if (!baseEntity.getClass().equals(Death.class)) { //TODO this needs to be kicked OUT !
                tranformedEntities.add(baseEntity);
                increaseBaseEntityCounter(baseEntity.getClass().toString());
            }
        }
        if (tranformedEntities.size() >= 1000) {
            LOG.info("Batch load 1000: " + tranformedEntities.size());
            for (JPABaseEntity jpaBaseEntity : tranformedEntities) {
                entityManager.persist(jpaBaseEntity);
            }
            entityManager.flush();
            entityManager.clear();
            tranformedEntities = new ArrayList<>();
        }
    }

    @Transactional
    public void createLastBatch() {
        System.out.println("Last Batch!");
        if (tranformedEntities.size() != 0) {
            for (JPABaseEntity jpaBaseEntity : tranformedEntities) {
                entityManager.persist(jpaBaseEntity);
            }
        }
        tranformedEntities = new ArrayList<>();
    }

    @Transactional
    public List<JPABaseEntity> create(List<JPABaseEntity> convertedEntities) {
        List<JPABaseEntity> outputs = new ArrayList<>();
        for (JPABaseEntity baseEntity : convertedEntities) {
            if (!baseEntity.getClass().equals(Death.class)) { //TODO this needs to be kicked OUT !
                increaseBaseEntityCounter(baseEntity.getClass().toString());
                entityManager.persist(baseEntity);
                persistedEntities.add(baseEntity);
                outputs.add(baseEntity);
            }
        }
        return outputs;
    }

    private void increaseBaseEntityCounter(String baseEntityName) {
        baseEntityName = baseEntityName.replace("class org.bih.eos.jpabase.entity.", "");
        if (baseEntityMap.containsKey(baseEntityName)) {
            baseEntityMap.put(baseEntityName, baseEntityMap.get(baseEntityName) + 1);
        } else {
            baseEntityMap.put(baseEntityName, 0);
        }
        LOG.info("Current amount of created BaseEntities: " + baseEntityMap);
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
