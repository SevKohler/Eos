package org.bih.eos.converter.cdt.converter.configurable.specific;

import com.nedap.archie.rm.archetyped.Locatable;
import org.bih.eos.converter.cdt.DefaultConverterServices;
import org.bih.eos.converter.cdt.conversion_entities.ProcedureOccurrenceEntity;
import org.bih.eos.converter.cdm_field.numeric.DVToNumericCoverter;
import org.bih.eos.converter.cdt.converter.configurable.generic.CDTConverterWithSourceConcept;
import org.bih.eos.converter.dao.ConvertableContentItem;
import org.bih.eos.jpabase.model.entity.Concept;
import org.bih.eos.jpabase.model.entity.JPABaseEntity;
import org.bih.eos.yaml.ValueEntry;
import org.bih.eos.yaml.cdt_configs.procedure_occurrence.ProcedureOccurrenceConfig;

import java.util.Date;
import java.util.Optional;

public class ProcedureOccurrenceConverter extends CDTConverterWithSourceConcept<ProcedureOccurrenceEntity, ProcedureOccurrenceConfig> {

    public ProcedureOccurrenceConverter(DefaultConverterServices defaultConverterServices, ProcedureOccurrenceConfig clinicalDataTableMapping) {
        super(defaultConverterServices, clinicalDataTableMapping);
    }

    @Override
    protected Optional<JPABaseEntity> convertInternal(ConvertableContentItem convertableContentItem) {
        ProcedureOccurrenceConfig procedureOccurrenceConfig = (ProcedureOccurrenceConfig) omopMapping;
        ProcedureOccurrenceEntity procedureOccurrenceEntity = new ProcedureOccurrenceEntity(procedureOccurrenceConfig, convertableContentItem.getPerson(), defaultConverterServices.getConceptService().findById(32817L));
        procedureOccurrenceEntity = convertRequiredFields(convertableContentItem, procedureOccurrenceConfig, procedureOccurrenceEntity);
        procedureOccurrenceEntity = convertOptionalFields(convertableContentItem.getContentItem(), procedureOccurrenceConfig, procedureOccurrenceEntity);
        return procedureOccurrenceEntity.toJpaEntity();
    }

    @Override
    protected ProcedureOccurrenceEntity convertRequiredFields(ConvertableContentItem convertableContentItem, ProcedureOccurrenceConfig config, ProcedureOccurrenceEntity entity) {
        Locatable contentItem = convertableContentItem.getContentItem();
        convertConceptCode(contentItem, config.getConceptId().getAlternatives(), entity);
        convertProcedureOccurrenceStartDate(contentItem, config.getProcedureDate().getAlternatives(), entity);
        getVisitOccurrence().ifPresent(entity::setVisitOccurrence);
        return entity;
    }

    private void convertProcedureOccurrenceStartDate(Locatable contentItem, ValueEntry[] alternatives, ProcedureOccurrenceEntity entity) {
        entity.setDateTime((Optional<Date>) dateTimeConverter.convert(contentItem, alternatives));
    }

    @Override
    protected ProcedureOccurrenceEntity convertOptionalFields(Locatable contentItem, ProcedureOccurrenceConfig config, ProcedureOccurrenceEntity entity) {
        if (config.getProcedureOccurrenceEndDate().isPopulated()) {
            convertProcedureOccurrenceEndDate(entity, contentItem, config.getProcedureOccurrenceEndDate().getAlternatives());
        }
        if (config.getQuantity().isPopulated()) {
            entity.setQuantity(DVToNumericCoverter.convertToDouble(contentItem, config.getQuantity().getAlternatives()));
        }
        if (config.getModifier().isPopulated()) {
            convertModifier(entity, contentItem, config.getModifier().getAlternatives());
        }
        if (config.getProviderId().isPopulated()) {
            unsupportedMapping("provider_id");
        }
        if (config.getVisitDetailId().isPopulated()) {
            unsupportedMapping("visit_detail_id");
        }
        return entity;
    }

    private void convertModifier(ProcedureOccurrenceEntity procedureOccurrence, Locatable contentItem, ValueEntry[] alternatives) {
        procedureOccurrence.setModifier((Optional<Concept>) standardConceptConverter.convert(contentItem, alternatives),
                (Optional<String>) sourceValueConverter.convert(contentItem, alternatives));
    }

    private void convertProcedureOccurrenceEndDate(ProcedureOccurrenceEntity procedureOccurrenceEntity, Locatable contentItem, ValueEntry[] procedureEndDate) {
        procedureOccurrenceEntity.setEndDateTime((Optional<Date>) endTimeConverter.convert(contentItem, procedureEndDate));
    }
}
