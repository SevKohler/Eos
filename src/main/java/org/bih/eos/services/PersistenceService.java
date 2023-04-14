package org.bih.eos.services;

import org.bih.eos.jpabase.entity.JPABaseEntity;
import org.bih.eos.jpabase.entity.Measurement;
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

    private List<JPABaseEntity> transformedEntities = new ArrayList<>();
    HashMap<String, Integer> baseEntityMap = new HashMap<String, Integer>();
    private List<JPABaseEntity> persistedEntities = new ArrayList<>();
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(PersistenceService.class);

    @Transactional
    public void createBatch(List<JPABaseEntity> convertedEntities) {
        transformedEntities.addAll(convertedEntities);
        if (transformedEntities.size() >= 1000) {
            LOG.info("Batch load 1000: " + transformedEntities.size());
            persistJpaEntities();
            LOG.info("Mapped amount of database Entities: " + baseEntityMap.values().stream().mapToInt(i -> i.intValue()).sum() + ", including following entities: " + baseEntityMap);
            entityManager.flush();
            entityManager.clear();
            transformedEntities = new ArrayList<>();
        }
    }

    @Transactional
    public void createLastBatch() {
        if (transformedEntities.size() != 0) {
            persistJpaEntities();
            LOG.info("Mapped amount of database Entities: " + baseEntityMap.values().stream().mapToInt(i -> i.intValue()).sum() + ", including following entities: " + baseEntityMap);
            entityManager.flush();
            entityManager.clear();
            transformedEntities = new ArrayList<>();
        }
        baseEntityMap = new HashMap<>();
    }


    private void persistJpaEntities() {
        for (JPABaseEntity jpaBaseEntity : transformedEntities) {
            entityManager.persist(jpaBaseEntity);
            increaseBaseEntityCounter(jpaBaseEntity.getClass().toString());
            persistedEntities.add(jpaBaseEntity);
        }
    }


    @Transactional
    public List<JPABaseEntity> create(List<JPABaseEntity> convertedEntities) {
        List<JPABaseEntity> outputs = new ArrayList<>();
        for (JPABaseEntity baseEntity : convertedEntities) {
            entityManager.persist(baseEntity);
            increaseBaseEntityCounter(baseEntity.getClass().toString());
            persistedEntities.add(baseEntity);
            outputs.add(baseEntity);
        }
        return outputs;
    }

    //['df8033b1-1dde-433c-ab0e-1f6fed8924ff']
    private void increaseBaseEntityCounter(String baseEntityName) {
        baseEntityName = baseEntityName.replace("class org.bih.eos.jpabase.entity.", "");
        if (baseEntityMap.containsKey(baseEntityName)) {
            baseEntityMap.put(baseEntityName, baseEntityMap.get(baseEntityName) + 1);
        } else {
            baseEntityMap.put(baseEntityName, 1);
        }
    }

    @Transactional
    public JPABaseEntity create(JPABaseEntity convertedEntity) {
        entityManager.persist(convertedEntity);
        persistedEntities.add(convertedEntity);
        increaseBaseEntityCounter(convertedEntity.getClass().toString());
        return convertedEntity;
    }

    protected List<JPABaseEntity> getPersistedEntities() {
        return persistedEntities;
    }

    protected void clearPersistedEntities() {
        this.persistedEntities = new ArrayList<>();
    }


}
