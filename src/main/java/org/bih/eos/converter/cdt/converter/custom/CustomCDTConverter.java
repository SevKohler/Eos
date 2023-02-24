package org.bih.eos.converter.cdt.converter.custom;

import org.bih.eos.converter.cdt.converter.configurable.generic.CDTConverterWithConfig;
import org.bih.eos.converter.dao.ConversionTrack;
import org.bih.eos.converter.dao.ConvertableContentItem;
import org.bih.eos.jpabase.model.entity.JPABaseEntity;
import org.bih.eos.converter.cdt.DefaultConverterServices;
import org.bih.eos.yaml.OmopMapping;
import org.springframework.lang.NonNull;

import java.util.List;

public abstract class CustomCDTConverter extends CDTConverterWithConfig {

    protected CustomCDTConverter(DefaultConverterServices defaultConverterServices,
                                 OmopMapping omopMapping) {
        super(defaultConverterServices, omopMapping);
    }

    public abstract List<JPABaseEntity> convert(@NonNull ConvertableContentItem convertableContentItem, List<ConversionTrack> convertedCouple, List<JPABaseEntity> mappedOmopEntities);

    protected List<JPABaseEntity> persist(List<JPABaseEntity> convertedEntities) {
        return defaultConverterServices.getPersistenceService().create(convertedEntities);
    }

    protected JPABaseEntity persist(JPABaseEntity convertedEntity) {
        return defaultConverterServices.getPersistenceService().create(convertedEntity);
    }

}



