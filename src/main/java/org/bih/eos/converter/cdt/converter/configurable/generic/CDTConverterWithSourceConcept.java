package org.bih.eos.converter.cdt.converter.configurable.generic;

import com.nedap.archie.rm.archetyped.Locatable;
import org.bih.eos.converter.cdt.DefaultConverterServices;
import org.bih.eos.converter.cdt.conversion_entities.EntityWithSourceConcept;
import org.bih.eos.yaml.ValueEntry;
import org.bih.eos.yaml.cdt_configs.CDTMappingConfig;

public abstract class CDTConverterWithSourceConcept<E extends EntityWithSourceConcept,  CDTM extends CDTMappingConfig> extends CDTConverterWithStandardConcept<E, CDTM> {

    protected CDTConverterWithSourceConcept(DefaultConverterServices defaultConverterServices, CDTM clinicalDataTableMapping) {
        super(defaultConverterServices, clinicalDataTableMapping);
    }

    @Override
    protected void convertConceptCode(Locatable contentItem, ValueEntry[] valueEntries, E entity) {
        entity.setStandardConcept(standardConceptConverter.convert(contentItem, valueEntries));
        entity.setSourceConcept(sourceConceptConverter.convert(contentItem, valueEntries));
        entity.setEntitySourceValue(sourceValueConverter.convert(contentItem, valueEntries));
    }

}



