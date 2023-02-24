package org.bih.eos.converter.cdt.converter.configurable.generic;

import com.nedap.archie.rm.archetyped.Locatable;
import org.bih.eos.converter.cdm_field.string.DVtoStringConverter;
import org.bih.eos.converter.cdt.DefaultConverterServices;
import org.bih.eos.converter.cdt.conversion_entities.EntityWithStandardConcept;
import org.bih.eos.yaml.ValueEntry;
import org.bih.eos.yaml.cdt_configs.CDTMappingConfig;

public abstract class CDTConverterWithStandardConcept<E extends EntityWithStandardConcept,  CDTM extends CDTMappingConfig> extends CDTMedicalDataConverter<E, CDTM> {
    private final DVtoStringConverter dVtoStringConverter = new DVtoStringConverter();

    protected CDTConverterWithStandardConcept(DefaultConverterServices defaultConverterServices, CDTM clinicalDataTableMapping) {
        super(defaultConverterServices, clinicalDataTableMapping);
    }

    protected void convertConceptCode(Locatable contentItem, ValueEntry[] valueEntries, E entity) {
        entity.setStandardConcept(standardConceptConverter.convert(contentItem, valueEntries));
        entity.setEntitySourceValue(sourceValueConverter.convert(contentItem, valueEntries));
    }

    public DVtoStringConverter getdVtoStringConverter() {
        return dVtoStringConverter;
    }

}



