package org.bih.eos.converter.cdt.converter.configurable.specific;

import com.nedap.archie.rm.archetyped.Locatable;
import org.bih.eos.converter.cdt.DefaultConverterServices;
import org.bih.eos.converter.cdt.conversion_entities.VisitOccurrenceEntity;
import org.bih.eos.converter.cdt.conversion_entities.VisitOccurrenceEntityWithConfig;
import org.bih.eos.converter.cdt.converter.configurable.generic.CDTMedicalDataConverter;
import org.bih.eos.converter.dao.ConvertableContentItem;
import org.bih.eos.jpabase.entity.JPABaseEntity;
import org.bih.eos.yaml.ValueEntry;
import org.bih.eos.yaml.cdt_configs.visit.VisitConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Optional;

public class VisitConverter extends CDTMedicalDataConverter<VisitOccurrenceEntity, VisitConfig> {

    private static final Logger LOG = LoggerFactory.getLogger(VisitConverter.class);

    public VisitConverter(DefaultConverterServices defaultConverterServices, VisitConfig config) {
        super(defaultConverterServices, config);
    }

    @Override
    protected Optional<JPABaseEntity> convertInternal(ConvertableContentItem convertableContentItem) {
    	VisitConfig config = (VisitConfig) omopMapping;
        VisitOccurrenceEntity entity = new VisitOccurrenceEntityWithConfig(config, convertableContentItem.getPerson(),defaultConverterServices.getConceptService().findById(32817L));
        entity = convertRequiredFields(convertableContentItem, config, entity);
        entity = convertOptionalFields(convertableContentItem.getContentItem(), config, entity);
        return entity.toJpaEntity();
    }

    @Override
    protected VisitOccurrenceEntity convertRequiredFields(ConvertableContentItem convertableContentItem, VisitConfig config, VisitOccurrenceEntity entity) {
        Locatable contentItem = convertableContentItem.getContentItem();
        convertStartDateTime(contentItem, config.getStartDateTime().getAlternatives(), entity);
        convertVisitConceptId(contentItem, config.getVisitConcept().getAlternatives(), entity);

        if (config.getEndDateTime()!= null) {
        	Optional<Date> startDate = dateTimeConverter.convert(contentItem, config.getStartDateTime().getAlternatives());
            Optional<Date> endDate = dateTimeConverter.convert(contentItem, config.getEndDateTime().getAlternatives());
            entity.setEndDate(startDate,endDate);
        }

        
        return entity;
    }

    private void convertStartDateTime(Locatable contentItem, ValueEntry[] dateTime, VisitOccurrenceEntity entity) {
        Optional<Date> date = dateTimeConverter.convert(contentItem, dateTime);
        entity.setDateTime(date);
    }

    private void convertVisitConceptId(Locatable contentItem, ValueEntry[] visitConcept, VisitOccurrenceEntity entity) {
        entity.setStandardConcept(standardConceptConverter.convert(contentItem, visitConcept));
    }
    
    private void convertVisitTypeConceptId (Locatable contentItem, ValueEntry[] visitTypeConcept, VisitOccurrenceEntity entity) {
        entity.setTypeConcept((Optional) standardConceptConverter.convert(contentItem, visitTypeConcept));

    }
    
    @Override
    protected VisitOccurrenceEntity convertOptionalFields(Locatable contentItem, VisitConfig config, VisitOccurrenceEntity entity) {
        return entity;
    }
}