package org.bih.eos.converter.cdt.converter.configurable.specific;

import com.nedap.archie.rm.archetyped.Locatable;
import org.bih.eos.converter.cdt.DefaultConverterServices;
import org.bih.eos.converter.cdt.conversion_entities.ConditionOccurrenceEntity;
import org.bih.eos.converter.cdt.converter.configurable.generic.CDTConverterWithSourceConcept;
import org.bih.eos.yaml.cdt_configs.condition_occurrence.ConditionOccurrenceConfig;
import org.bih.eos.yaml.ValueEntry;
import org.bih.eos.converter.dao.ConvertableContentItem;
import org.bih.eos.jpabase.model.entity.JPABaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class ConditionOccurenceConverter extends CDTConverterWithSourceConcept<ConditionOccurrenceEntity, ConditionOccurrenceConfig> {

    private static final Logger LOG = LoggerFactory.getLogger(ConditionOccurenceConverter.class);

    public ConditionOccurenceConverter(DefaultConverterServices defaultConverterServices, ConditionOccurrenceConfig conditionOccurrenceConfig) {
        super(defaultConverterServices, conditionOccurrenceConfig);
    }

    @Override
    public Optional<JPABaseEntity> convertInternal(ConvertableContentItem convertableContentItem) {
        ConditionOccurrenceConfig conditionOccurrenceConfig = (ConditionOccurrenceConfig) omopMapping;
        ConditionOccurrenceEntity conditionOccurrence = new ConditionOccurrenceEntity(conditionOccurrenceConfig, convertableContentItem.getPerson(), defaultConverterServices.getConceptService().findById(32817L));
        conditionOccurrence = convertRequiredFields(convertableContentItem, conditionOccurrenceConfig, conditionOccurrence);
        conditionOccurrence = convertOptionalFields(convertableContentItem.getContentItem(), conditionOccurrenceConfig, conditionOccurrence);
        return conditionOccurrence.toJpaEntity();
    }

    @Override
    protected ConditionOccurrenceEntity convertRequiredFields(ConvertableContentItem convertableContentItem, ConditionOccurrenceConfig conditionOccurrenceConfig, ConditionOccurrenceEntity conditionOccurrence) {
        Locatable contentItem = convertableContentItem.getContentItem();
        convertConceptCode(contentItem, conditionOccurrenceConfig.getConceptId().getAlternatives(), conditionOccurrence);
        convertConditionOccurrenceStartDate(contentItem, conditionOccurrenceConfig.getConditionStartDate().getAlternatives(), conditionOccurrence);
        getVisitOccurrence().ifPresent(conditionOccurrence::setVisitOccurrence);
        return conditionOccurrence;
    }

    @Override
    protected ConditionOccurrenceEntity convertOptionalFields(Locatable contentItem, ConditionOccurrenceConfig conditionOccurrenceConfig, ConditionOccurrenceEntity conditionOccurrence) {
        if (conditionOccurrenceConfig.getConditionEndDate().isPopulated()) {
            convertConditionOccurrenceEndDate(conditionOccurrence, contentItem, conditionOccurrenceConfig.getConditionEndDate().getAlternatives());
        }
        if (conditionOccurrenceConfig.getStopReason().isPopulated()) {
            convertStopReason(conditionOccurrence, contentItem, conditionOccurrenceConfig.getStopReason().getAlternatives());
        }
        if (conditionOccurrenceConfig.getProviderId().isPopulated()) {
            unsupportedMapping("provider_id");
        }
        if (conditionOccurrenceConfig.getVisitDetailId().isPopulated()) {
            unsupportedMapping("visit_detail_id");
        }
        if (conditionOccurrenceConfig.getConditionStatusConceptId().isPopulated()) {
            convertStatusConcept(contentItem, conditionOccurrenceConfig.getConditionStatusConceptId().getAlternatives(), conditionOccurrence);
        }
        return conditionOccurrence;
    }

    private void convertStopReason(ConditionOccurrenceEntity conditionOccurrence, Locatable contentItem, ValueEntry[] alternatives) {
        conditionOccurrence.setStopReason(getdVtoStringConverter().convert(contentItem, alternatives));
    }

    private void convertStatusConcept(Locatable contentItem, ValueEntry[] statusEntry, ConditionOccurrenceEntity conditionOccurrence) {
        conditionOccurrence.setStatus( standardConceptConverter.convert(contentItem, statusEntry),
                sourceValueConverter.convert(contentItem, statusEntry));
    }

    private void convertConditionOccurrenceStartDate(Locatable contentItem, ValueEntry[] conditionStartDate, ConditionOccurrenceEntity conditionOccurrence) {
        conditionOccurrence.setDateTime(dateTimeConverter.convert(contentItem, conditionStartDate));
    }

    private void convertConditionOccurrenceEndDate(ConditionOccurrenceEntity conditionOccurrence, Locatable contentItem, ValueEntry[] conditionEndDate) {
        conditionOccurrence.setEndDateTime(endTimeConverter.convert(contentItem, conditionEndDate));
    }

}



