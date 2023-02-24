package org.bih.eos.converter.cdt.converter.configurable.specific;

import com.nedap.archie.rm.archetyped.Locatable;
import org.bih.eos.converter.cdt.DefaultConverterServices;
import org.bih.eos.converter.cdt.conversion_entities.DeathEntity;
import org.bih.eos.converter.cdt.converter.configurable.generic.CDTMedicalDataConverter;
import org.bih.eos.converter.dao.ConvertableContentItem;
import org.bih.eos.jpabase.model.entity.Concept;
import org.bih.eos.jpabase.model.entity.JPABaseEntity;
import org.bih.eos.yaml.ValueEntry;
import org.bih.eos.yaml.cdt_configs.death.DeathConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Optional;

public class DeathConverter extends CDTMedicalDataConverter<DeathEntity, DeathConfig> {

    private static final Logger LOG = LoggerFactory.getLogger(DeathConverter.class);

    public DeathConverter(DefaultConverterServices defaultConverterServices, DeathConfig deathConfig) {
        super(defaultConverterServices, deathConfig);
    }

    @Override
    protected Optional<JPABaseEntity> convertInternal(ConvertableContentItem convertableContentItem) {
        DeathConfig deathConfig = (DeathConfig) omopMapping;
        DeathEntity deathEntity = new DeathEntity(deathConfig, convertableContentItem.getPerson(), defaultConverterServices.getConceptService().findById(32817L));
        deathEntity = convertRequiredFields(convertableContentItem, deathConfig, deathEntity);
        deathEntity = convertOptionalFields(convertableContentItem.getContentItem(), deathConfig, deathEntity);
        return deathEntity.toJpaEntity();
    }

    @Override
    protected DeathEntity convertRequiredFields(ConvertableContentItem convertableContentItem, DeathConfig deathConfig, DeathEntity deathEntity) {
        Locatable contentItem = convertableContentItem.getContentItem();
        convertDeathDate(contentItem, deathConfig.getDeathDate().getAlternatives(), deathEntity);
        return deathEntity;
    }

    private void convertDeathDate(Locatable contentItem, ValueEntry[] dateTime, DeathEntity deathEntity) {
        Optional<Date> date = (Optional<Date>) dateTimeConverter.convert(contentItem, dateTime);
        deathEntity.setDateTime(date);
    }

    @Override
    protected DeathEntity convertOptionalFields(Locatable contentItem, DeathConfig deathConfig, DeathEntity deathEntity)  {
        if (deathConfig.getCause().isPopulated()) {
            convertCause(contentItem, deathConfig.getCause().getAlternatives(), deathEntity);
        }
        return deathEntity;
    }

    private void convertCause(Locatable contentItem, ValueEntry[] valueEntries, DeathEntity deathEntity) {
        deathEntity.setCauseStandardConcept((Optional<Concept>) standardConceptConverter.convert(contentItem, valueEntries));
        deathEntity.setCauseSourceConcept((Optional<Concept>) sourceConceptConverter.convert(contentItem, valueEntries));
        deathEntity.setCauseSourceValue((Optional<String>) sourceValueConverter.convert(contentItem, valueEntries));
    }

}
