package org.bih.eos.converter.cdt.converter.configurable.specific;

import com.nedap.archie.rm.archetyped.Locatable;
import org.bih.eos.converter.cdt.DefaultConverterServices;
import org.bih.eos.converter.cdt.conversion_entities.DrugExposureEntity;
import org.bih.eos.converter.cdm_field.numeric.DVToNumericCoverter;
import org.bih.eos.converter.cdt.converter.configurable.generic.CDTConverterWithSourceConcept;
import org.bih.eos.jpabase.model.entity.JPABaseEntity;
import org.bih.eos.yaml.cdt_configs.drug_exposure.*;
import org.bih.eos.yaml.ValueEntry;
import org.bih.eos.converter.dao.ConvertableContentItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class DrugExposureConverter extends CDTConverterWithSourceConcept<DrugExposureEntity, DrugExposureConfig> {

    private static final Logger LOG = LoggerFactory.getLogger(DrugExposureConverter.class);

    public DrugExposureConverter(DefaultConverterServices defaultConverterServices, DrugExposureConfig drugExposureConfig) {
        super(defaultConverterServices, drugExposureConfig);
    }

    @Override
    public Optional<JPABaseEntity> convertInternal(ConvertableContentItem convertableContentItem) {
        DrugExposureConfig drugExposureConfig = (DrugExposureConfig) omopMapping;
        DrugExposureEntity drugExposure = new DrugExposureEntity(drugExposureConfig, convertableContentItem.getPerson(), defaultConverterServices.getConceptService().findById(32817L));
        drugExposure = convertRequiredFields(convertableContentItem, drugExposureConfig, drugExposure);
        drugExposure = convertOptionalFields(convertableContentItem.getContentItem(), drugExposureConfig, drugExposure);
        return drugExposure.toJpaEntity();
    }

    @Override
    protected DrugExposureEntity convertRequiredFields(ConvertableContentItem convertableContentItem, DrugExposureConfig drugExposureConfig, DrugExposureEntity drugExposure) {
        Locatable contentItem = convertableContentItem.getContentItem();
        convertConceptCode(contentItem, drugExposureConfig.getConceptId().getAlternatives(), drugExposure);
        convertDrugExposureStartDate(contentItem, drugExposureConfig.getDrugExposureStartDate().getAlternatives(), drugExposure);
        convertDrugExposureEndDate(contentItem, drugExposureConfig.getDrugExposureEndDate().getAlternatives(), drugExposure);
        getVisitOccurrence().ifPresent(drugExposure::setVisitOccurrence);
        return drugExposure;
    }

    @Override
    protected DrugExposureEntity convertOptionalFields(Locatable contentItem, DrugExposureConfig drugExposureConfig, DrugExposureEntity drugExposure) {
        if (drugExposureConfig.getRouteConceptId().isPopulated()) {
            convertDrugRoutePath(contentItem, drugExposureConfig.getRouteConceptId().getAlternatives(), drugExposure);
        }
        if (drugExposureConfig.getQuantity().isPopulated()) {
            drugExposure.setQuantity(DVToNumericCoverter.convertToDouble(contentItem, drugExposureConfig.getQuantity().getAlternatives()));
        }
        if (drugExposureConfig.getVerbatimEndDate().isPopulated()) {
            convertVerbatimEndDate(contentItem, drugExposureConfig.getVerbatimEndDate().getAlternatives(), drugExposure);
        }
        if (drugExposureConfig.getStopReason().isPopulated()) {
            convertStopReason(contentItem, drugExposureConfig.getStopReason().getAlternatives(), drugExposure);
        }
        if (drugExposureConfig.getRefills().isPopulated()) {
            convertRefills(contentItem, drugExposureConfig.getRefills().getAlternatives(), drugExposure);
        }
        if (drugExposureConfig.getDaySupply().isPopulated()) {
            convertDaySupply(contentItem, drugExposureConfig.getDaySupply().getAlternatives(), drugExposure);
        }
        if (drugExposureConfig.getSig().isPopulated()) {
            convertSig(contentItem, drugExposureConfig.getSig().getAlternatives(), drugExposure);
        }
        if (drugExposureConfig.getLotNumber().isPopulated()) {
            convertLotNumber(contentItem, drugExposureConfig.getLotNumber().getAlternatives(), drugExposure);
        }
        if (drugExposureConfig.getProviderId().isPopulated()) {
            unsupportedMapping("provider_id");
        }
        if (drugExposureConfig.getVisitDetailId().isPopulated()) {
            unsupportedMapping("visit_detail_id");
        }

        return drugExposure;
    }

    private void convertVerbatimEndDate(Locatable contentItem, ValueEntry[] alternatives, DrugExposureEntity drugExposure) {
        drugExposure.setVerbatimEndDate(endTimeConverter.convert(contentItem, alternatives));
    }

    private void convertStopReason(Locatable contentItem, ValueEntry[] alternatives, DrugExposureEntity drugExposure) {
        drugExposure.setStopReason(getdVtoStringConverter().convert(contentItem, alternatives));
    }

    private void convertRefills(Locatable contentItem, ValueEntry[] alternatives, DrugExposureEntity drugExposure) {
        drugExposure.setRefills(DVToNumericCoverter.convertToInteger(contentItem, alternatives));
    }

    private void convertDaySupply(Locatable contentItem, ValueEntry[] alternatives, DrugExposureEntity drugExposure) {
        drugExposure.setDaySupply(DVToNumericCoverter.convertToInteger(contentItem, alternatives));
    }

    private void convertSig(Locatable contentItem, ValueEntry[] alternatives, DrugExposureEntity drugExposure) {
        drugExposure.setSig(getdVtoStringConverter().convert(contentItem, alternatives));
    }

    private void convertLotNumber(Locatable contentItem, ValueEntry[] alternatives, DrugExposureEntity drugExposure) {
        drugExposure.setLotNumber(getdVtoStringConverter().convert(contentItem, alternatives));
    }

    private void convertDrugExposureEndDate(Locatable contentItem, ValueEntry[] endTimes, DrugExposureEntity drugExposure) {
        drugExposure.setEndDateTime(endTimeConverter.convert(contentItem, endTimes));
    }

    private void convertDrugExposureStartDate(Locatable contentItem, ValueEntry[] startTimes, DrugExposureEntity drugExposure) {
        drugExposure.setDateTime(dateTimeConverter.convert(contentItem, startTimes));
    }

    private void convertDrugRoutePath(Locatable contentItem, ValueEntry[] drugRoutePath, DrugExposureEntity drugExposure) {
        drugExposure.setRoute(standardConceptConverter.convert(contentItem, drugRoutePath), (Optional<String>) sourceValueConverter.convert(contentItem, drugRoutePath));
    }


}
