package org.bih.eos.converter.cdt.converter.configurable.specific;

import com.nedap.archie.rm.archetyped.Locatable;
import org.bih.eos.converter.cdt.DefaultConverterServices;
import org.bih.eos.converter.cdt.conversion_entities.SpecimenEntity;
import org.bih.eos.converter.cdm_field.numeric.DVToNumericCoverter;
import org.bih.eos.converter.cdt.converter.configurable.generic.CDTUnitWithStandardConceptConverter;
import org.bih.eos.jpabase.model.entity.Concept;
import org.bih.eos.jpabase.model.entity.JPABaseEntity;
import org.bih.eos.yaml.ValueEntry;
import org.bih.eos.yaml.cdt_configs.specimen.SpecimenConfig;
import org.bih.eos.converter.dao.ConvertableContentItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Optional;

public class SpecimenConverter extends CDTUnitWithStandardConceptConverter<SpecimenEntity, SpecimenConfig> {

    private static final Logger LOG = LoggerFactory.getLogger(SpecimenConverter.class);

    public SpecimenConverter(DefaultConverterServices defaultConverterServices, SpecimenConfig specimenConfig) {
        super(defaultConverterServices, specimenConfig);
    }

    @Override
    public Optional<JPABaseEntity> convertInternal(ConvertableContentItem convertableContentItem) {
        SpecimenConfig specimenConfig = (SpecimenConfig) omopMapping;
        SpecimenEntity specimen = new SpecimenEntity(specimenConfig, convertableContentItem.getPerson(), defaultConverterServices.getConceptService().findById(32817L));
        specimen = convertRequiredFields(convertableContentItem, specimenConfig, specimen);
        specimen = convertOptionalFields(convertableContentItem.getContentItem(), specimenConfig, specimen);
        return specimen.toJpaEntity();
    }

    @Override
    protected SpecimenEntity convertRequiredFields(ConvertableContentItem convertableContentItem, SpecimenConfig specimenConfig, SpecimenEntity specimen) {
        Locatable contentItem = convertableContentItem.getContentItem();
        convertConceptCode(contentItem, specimenConfig.getConceptId().getAlternatives(), specimen);
        convertDateTime(contentItem, specimenConfig.getSpecimenDate().getAlternatives(), specimen);
        return specimen;
    }

    @Override
    protected SpecimenEntity convertOptionalFields(Locatable contentItem, SpecimenConfig config, SpecimenEntity specimen) {
        if(config.getUnit().isPopulated()){
            convertUnitCode(contentItem, config.getUnit().getAlternatives(), specimen);
        }
        if(config.getQuantity().isPopulated()){
            specimen.setQuantity(DVToNumericCoverter.convertToDouble(contentItem, config.getQuantity().getAlternatives()));
        }
        if(config.getAnatomicSiteConcept().isPopulated()){
            convertAnatomicSite(contentItem, config.getAnatomicSiteConcept().getAlternatives(), specimen);
        }
        if(config.getDiseaseStatusConcept().isPopulated()){
            convertDiseaseStatus(contentItem, config.getAnatomicSiteConcept().getAlternatives(), specimen);
        }
        if(config.getSpecimenSourceId().isPopulated()){
            specimen.setSpecimenSourceId(getdVtoStringConverter().convert(contentItem, config.getSpecimenSourceId().getAlternatives()));
        }
        return specimen;
    }

    private void convertDiseaseStatus(Locatable contentItem, ValueEntry[] alternatives, SpecimenEntity specimen) {
        specimen.setDiseaseStatusConcept((Optional<Concept>) standardConceptConverter.convert(contentItem, alternatives),
                (Optional<String>) sourceValueConverter.convert(contentItem, alternatives));
    }

    private void convertAnatomicSite(Locatable contentItem, ValueEntry[] alternatives, SpecimenEntity specimen) {
        specimen.setAnatomicSiteConcept((Optional<Concept>) standardConceptConverter.convert(contentItem, alternatives),
                (Optional<String>) sourceValueConverter.convert(contentItem, alternatives));
    }

    private void convertUnitCode(Locatable contentItem, ValueEntry[] valueEntries, SpecimenEntity specimen) {
        specimen.setUnit((Optional<Concept>) getUnitStandardConverter().convert(contentItem, valueEntries), (Optional<String>) getUnitSourceValueConverter().convert(contentItem, valueEntries));
    }

    private void convertDateTime(Locatable contentItem, ValueEntry[] specimenDate, SpecimenEntity specimen) {
        specimen.setDateTime((Optional<Date>) dateTimeConverter.convert(contentItem, specimenDate));
    }

}
