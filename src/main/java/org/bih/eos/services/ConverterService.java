package org.bih.eos.services;

import com.nedap.archie.rm.composition.Composition;
import org.bih.eos.services.dao.ConvertableComposition;
import org.bih.eos.jpabase.model.entity.JPABaseEntity;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConverterService {
    private final ConversionService conversionService;
    private final PersistenceService persistenceService;

    public ConverterService(ConversionService conversionService, PersistenceService persistenceService) {
        this.conversionService = conversionService;
        this.persistenceService = persistenceService;
    }

    @SuppressWarnings({"Unchecked", "ConstantConditions"})
    public List<JPABaseEntity> convert(ConvertableComposition convertableComposition) {
        persistenceService.clearPersistedEntities();
        TypeDescriptor sourceType = TypeDescriptor.valueOf(ConvertableComposition.class);
        TypeDescriptor targetType = TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(JPABaseEntity.class));
        persistenceService.create((List<JPABaseEntity>) conversionService.convert(convertableComposition, sourceType, targetType));
        return persistenceService.getPersistedEntities();
    }

    public List<JPABaseEntity> convert(Composition composition) {
        persistenceService.clearPersistedEntities();
        TypeDescriptor sourceType = TypeDescriptor.valueOf(Composition.class);
        TypeDescriptor targetType = TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(JPABaseEntity.class));
        persistenceService.create((List<JPABaseEntity>) conversionService.convert(composition, sourceType, targetType));
        return persistenceService.getPersistedEntities();
    }

}
