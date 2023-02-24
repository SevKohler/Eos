package org.bih.eos.converter.cdt.converter.configurable.specific;

import com.nedap.archie.rm.archetyped.Locatable;
import org.bih.eos.converter.cdt.DefaultConverterServices;
import org.bih.eos.converter.cdt.conversion_entities.DeviceExposureEntity;
import org.bih.eos.converter.cdm_field.numeric.DVToNumericCoverter;
import org.bih.eos.converter.cdt.converter.configurable.generic.CDTUnitWithSourceConceptConverter;
import org.bih.eos.converter.dao.ConvertableContentItem;
import org.bih.eos.jpabase.model.entity.JPABaseEntity;
import org.bih.eos.yaml.ValueEntry;
import org.bih.eos.yaml.cdt_configs.device_exposure.DeviceExposureConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class DeviceExposureConverter extends CDTUnitWithSourceConceptConverter<DeviceExposureEntity, DeviceExposureConfig> {

    private static final Logger LOG = LoggerFactory.getLogger(DeviceExposureConverter.class);

    public DeviceExposureConverter(DefaultConverterServices defaultConverterServices, DeviceExposureConfig clinicalDataTableMapping) {
        super(defaultConverterServices, clinicalDataTableMapping);
    }

    @Override
    protected Optional<JPABaseEntity> convertInternal(ConvertableContentItem convertableContentItem) {
        DeviceExposureConfig deviceExposureConfig = (DeviceExposureConfig) omopMapping;
        DeviceExposureEntity deviceExposure = new DeviceExposureEntity(deviceExposureConfig, convertableContentItem.getPerson(), defaultConverterServices.getConceptService().findById(32817L));
        deviceExposure = convertRequiredFields(convertableContentItem, deviceExposureConfig, deviceExposure);
        deviceExposure = convertOptionalFields(convertableContentItem.getContentItem(), deviceExposureConfig, deviceExposure);
        return deviceExposure.toJpaEntity();
    }

    @Override
    protected DeviceExposureEntity convertRequiredFields(ConvertableContentItem convertableContentItem, DeviceExposureConfig deviceExposureConfig, DeviceExposureEntity drugExposure) {
        Locatable contentItem = convertableContentItem.getContentItem();
        convertConceptCode(contentItem, deviceExposureConfig.getConceptId().getAlternatives(), drugExposure);
        convertDeviceExposureStartDate(contentItem, deviceExposureConfig.getDeviceExposureStartDate().getAlternatives(), drugExposure);
        getVisitOccurrence().ifPresent(drugExposure::setVisitOccurrence);
        return drugExposure;
    }

    @Override
    protected DeviceExposureEntity convertOptionalFields(Locatable contentItem, DeviceExposureConfig deviceExposureConfig, DeviceExposureEntity deviceExposure) {
        if (deviceExposureConfig.getDeviceExposureEndDate().isPopulated()) {
            convertExposureEndDate(contentItem, deviceExposureConfig.getDeviceExposureEndDate().getAlternatives(), deviceExposure);
        }
        if (deviceExposureConfig.getUniqueDeviceId().isPopulated()) {
            convertUniqueDeviceId(contentItem, deviceExposureConfig.getUniqueDeviceId().getAlternatives(), deviceExposure);
        }
        if (deviceExposureConfig.getProductionId().isPopulated()) {
            convertProductionId(contentItem, deviceExposureConfig.getProductionId().getAlternatives(), deviceExposure);
        }
        if (deviceExposureConfig.getQuantity().isPopulated()) {
            deviceExposure.setQuantity(DVToNumericCoverter.convertToDouble(contentItem, deviceExposureConfig.getQuantity().getAlternatives()));
        }
        if (deviceExposureConfig.getProviderId().isPopulated()) {
            unsupportedMapping("provider_id");
        }
        if (deviceExposureConfig.getVisitDetailId().isPopulated()) {
            unsupportedMapping("visit_detail_id");
        }
        if (deviceExposureConfig.getUnit().isPopulated()) {
            convertUnit(contentItem, deviceExposureConfig.getUnit().getAlternatives(), deviceExposure);
        }

        return deviceExposure;
    }

    private void convertExposureEndDate(Locatable contentItem, ValueEntry[] alternatives, DeviceExposureEntity deviceExposure) {
        deviceExposure.setEndDateTime(endTimeConverter.convert(contentItem, alternatives));
    }

    private void convertUniqueDeviceId(Locatable contentItem, ValueEntry[] alternatives, DeviceExposureEntity deviceExposure) {
        deviceExposure.setUniqueDeviceId(getdVtoStringConverter().convert(contentItem, alternatives));
    }

    private void convertProductionId(Locatable contentItem, ValueEntry[] alternatives, DeviceExposureEntity deviceExposure) {
        deviceExposure.setProdution(getdVtoStringConverter().convert(contentItem, alternatives));
    }

    private void convertUnit(Locatable contentItem, ValueEntry[] valueEntries, DeviceExposureEntity deviceExposure) {
        deviceExposure.setUnitConcept(getUnitStandardConverter().convert(contentItem, valueEntries),
                getUnitSourceConceptConverter().convert(contentItem, valueEntries),
                getUnitSourceValueConverter().convert(contentItem, valueEntries));
    }

    private void convertDeviceExposureStartDate(Locatable contentItem, ValueEntry[] startTimes, DeviceExposureEntity deviceExposure) {
        deviceExposure.setDateTime(dateTimeConverter.convert(contentItem, startTimes));
    }

}
