package org.bih.eos.converter.cdt.converter.configurable.generic;

import com.nedap.archie.rm.archetyped.Locatable;
import org.bih.eos.converter.cdt.conversion_entities.Entity;
import org.bih.eos.converter.dao.ConversionTrack;
import org.bih.eos.converter.cdt.DefaultConverterServices;
import org.bih.eos.jpabase.model.entity.JPABaseEntity;
import org.bih.eos.jpabase.model.entity.VisitOccurrence;
import org.bih.eos.yaml.cdt_configs.CDTMappingConfig;
import org.bih.eos.converter.dao.ConvertableContentItem;
import org.bih.eos.converter.PathProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public abstract class CDTMedicalDataConverter<E extends Entity,  CDTM extends CDTMappingConfig> extends CDTConverterWithConfig {
    private List<ConversionTrack> conversionTracker;
    private  Optional<VisitOccurrence> visitOccurrence;

    private static final Logger LOG = LoggerFactory.getLogger(CDTMedicalDataConverter.class);

    //TODO entity classes refactoring so it uses values instead of setting JPA directly
    //TODO check if the fields are there before we map them ! empty stuff cannot be mapped obviously
    protected CDTMedicalDataConverter(DefaultConverterServices defaultConverterServices, CDTM clinicalDataTableMapping) {
        super(defaultConverterServices, clinicalDataTableMapping);
    }

    public List<JPABaseEntity> convert(@NonNull ConvertableContentItem convertableContentItem, Optional<VisitOccurrence> visitOccurrence) {
        this.conversionTracker = new ArrayList<>();
        this.visitOccurrence = visitOccurrence;
        CDTM clinicalDataTableMapping = (CDTM) omopMapping;
        if (clinicalDataTableMapping.getBasePath() != null) {
            return convertMultipleEntities(convertableContentItem, clinicalDataTableMapping);
        } else {
            return convertSingleEntity(convertableContentItem);
        }
    }

    private List<JPABaseEntity> convertSingleEntity(ConvertableContentItem convertableContentItem) {
        Optional<JPABaseEntity> converterResult = convertInternal(convertableContentItem);
        if (converterResult.isPresent()) {
            conversionTracker.add(new ConversionTrack(convertableContentItem.getContentItem(), converterResult.get()));
        } else {
            LOG.info("The conversion has found empty fields in a mapping that was required, therefore this conversion is not executed. All other conversions are still processed.");
            return new ArrayList<>();
        }
        return List.of(converterResult.get());
    }

    private List<JPABaseEntity> convertMultipleEntities(ConvertableContentItem convertableContentItem, CDTM clinicalDataTableMapping) {
        List<JPABaseEntity> baseEntityList = new ArrayList<>();
        for (Object contentItem : PathProcessor.getMultipleItems(convertableContentItem.getContentItem(), clinicalDataTableMapping.getBasePath())) {
            List<JPABaseEntity> conversionResult = convertSingleEntity(new ConvertableContentItem((Locatable) contentItem, convertableContentItem.getPerson()));
            baseEntityList.addAll(conversionResult);
        }
        return baseEntityList;
    }


    public Collection<? extends ConversionTrack> getMappingResultTracker() {
        return conversionTracker;
    }

    protected abstract Optional<JPABaseEntity> convertInternal(ConvertableContentItem convertableContentItem);

    protected abstract E convertRequiredFields(ConvertableContentItem convertableContentItem, CDTM config, E entity);

    protected abstract E convertOptionalFields(Locatable contentItem, CDTM config, E entity);

    public Optional<VisitOccurrence> getVisitOccurrence() {
        return visitOccurrence;
    }

}



