package org.bih.eos.services;

import com.nedap.archie.rm.composition.Composition;
import org.bih.eos.converter.cdt.conversion_entities.VisitOccurrenceEntity;
import org.bih.eos.services.dao.ConvertableComposition;
import org.bih.eos.jpabase.entity.JPABaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConverterService {
    private final ConversionService conversionService;
    private final PersistenceService persistenceService;

    private static final Logger LOG = LoggerFactory.getLogger(ConverterService.class);

    public ConverterService(ConversionService conversionService, PersistenceService persistenceService) {
        this.conversionService = conversionService;
        this.persistenceService = persistenceService;
    }

    @SuppressWarnings({"Unchecked", "ConstantConditions"})
    public List<JPABaseEntity> convert(ConvertableComposition convertableComposition) {
        logComposition(convertableComposition.getComposition());
        persistenceService.clearPersistedEntities();
        TypeDescriptor sourceType = TypeDescriptor.valueOf(ConvertableComposition.class);
        TypeDescriptor targetType = TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(JPABaseEntity.class));
        persistenceService.create((List<JPABaseEntity>) conversionService.convert(convertableComposition, sourceType, targetType));
        return persistenceService.getPersistedEntities();
    }

    private void logComposition(Composition composition) {
        if(composition.getUid() != null){
            LOG.info("Converting Composition with id: " + composition.getUid().getValue());
        }
    }

    public List<JPABaseEntity> convertBatch(ConvertableComposition convertableComposition) {
        logComposition(convertableComposition.getComposition());
        persistenceService.clearPersistedEntities();
        TypeDescriptor sourceType = TypeDescriptor.valueOf(ConvertableComposition.class);
        TypeDescriptor targetType = TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(JPABaseEntity.class));
        persistenceService.createBatch((List<JPABaseEntity>) conversionService.convert(convertableComposition, sourceType, targetType));
        return persistenceService.getPersistedEntities();
    }

    public List<JPABaseEntity> convertLastBatch() {
        persistenceService.clearPersistedEntities();
        persistenceService.createLastBatch();
        return persistenceService.getPersistedEntities();
    }
    public List<JPABaseEntity> convert(Composition composition) {
        logComposition(composition);
        persistenceService.clearPersistedEntities();
        TypeDescriptor sourceType = TypeDescriptor.valueOf(Composition.class);
        TypeDescriptor targetType = TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(JPABaseEntity.class));
        persistenceService.create((List<JPABaseEntity>) conversionService.convert(composition, sourceType, targetType));
        return persistenceService.getPersistedEntities();
    }

}
