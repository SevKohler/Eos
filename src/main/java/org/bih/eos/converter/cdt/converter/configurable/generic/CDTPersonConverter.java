package org.bih.eos.converter.cdt.converter.configurable.generic;

import com.nedap.archie.rm.archetyped.Locatable;
import org.bih.eos.converter.PathProcessor;
import org.bih.eos.converter.cdt.DefaultConverterServices;
import org.bih.eos.converter.cdt.conversion_entities.PersonEntity;
import org.bih.eos.converter.dao.ConversionTrack;
import org.bih.eos.jpabase.model.entity.JPABaseEntity;
import org.bih.eos.yaml.cdt_configs.person.PersonConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public abstract class CDTPersonConverter extends CDTConverterWithConfig {
    private List<ConversionTrack> conversionTracker;

    private static final Logger LOG = LoggerFactory.getLogger(CDTPersonConverter.class);

    //TODO entity classes refactoring so it uses values instead of setting JPA directly
    //TODO check if the fields are there before we map them ! empty stuff cannot be mapped obviously
    protected CDTPersonConverter(DefaultConverterServices defaultConverterServices, PersonConfig clinicalDataTableMapping) {
        super(defaultConverterServices, clinicalDataTableMapping);
    }

    public List<JPABaseEntity> convert(@NonNull Locatable locatable) {
        this.conversionTracker = new ArrayList<>();
        PersonConfig clinicalDataTableMapping = (PersonConfig) omopMapping;
        if (clinicalDataTableMapping.getBasePath() != null) {
            return convertMultipleEntities(locatable, clinicalDataTableMapping);
        } else {
            return convertSingleEntity(locatable);
        }
    }

    private List<JPABaseEntity> convertSingleEntity(Locatable locatable) {
        Optional<JPABaseEntity> converterResult = convertInternal(locatable);
        if (converterResult.isPresent()) {
            conversionTracker.add(new ConversionTrack(locatable, converterResult.get()));
        } else {
            LOG.info("The conversion has found empty fields in a mapping that where required, therefore this conversion is not executed. All other conversions are still processed.");
            return new ArrayList<>();
        }
        return List.of(converterResult.get());
    }

    private List<JPABaseEntity> convertMultipleEntities(Locatable locatable, PersonConfig clinicalDataTableMapping) {
        List<JPABaseEntity> baseEntityList = new ArrayList<>();
        for (Object childContentItem : PathProcessor.getMultipleItems(locatable, clinicalDataTableMapping.getBasePath())) {
            List<JPABaseEntity> conversionResult = convertSingleEntity((Locatable) childContentItem);
            baseEntityList.addAll(conversionResult);
        }
        return baseEntityList;
    }

    public Collection<? extends ConversionTrack> getMappingResultTracker() {
        return conversionTracker;
    }

    protected abstract Optional<JPABaseEntity> convertInternal(Locatable locatable);

    protected abstract PersonEntity convertRequiredFields(Locatable locatable, PersonConfig config, PersonEntity entity);

    protected abstract PersonEntity convertOptionalFields(Locatable contentItem, PersonConfig config, PersonEntity entity);


}



