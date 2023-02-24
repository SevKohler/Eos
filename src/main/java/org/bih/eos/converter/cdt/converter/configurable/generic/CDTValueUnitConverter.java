package org.bih.eos.converter.cdt.converter.configurable.generic;

import com.nedap.archie.rm.archetyped.Locatable;
import org.bih.eos.converter.cdt.DefaultConverterServices;
import org.bih.eos.converter.cdt.conversion_entities.EntityWithSourceConcept;
import org.bih.eos.converter.PathProcessor;
import org.bih.eos.yaml.ValueEntry;
import org.bih.eos.yaml.cdt_configs.CDTMappingConfig;


public abstract class CDTValueUnitConverter<E extends EntityWithSourceConcept, CDTM extends CDTMappingConfig> extends CDTUnitWithSourceConceptConverter<E, CDTM> {

    protected CDTValueUnitConverter(DefaultConverterServices defaultConverterServices, CDTM clinicalDataTableMapping) {
        super(defaultConverterServices, clinicalDataTableMapping);
    }

    protected E convertValue(Locatable contentItem, ValueEntry[] valueEntries, E entity) {
        for (ValueEntry valueEntry : valueEntries) {
            if (valueEntry.getCode() != null) {
                return convertValueCode(valueEntry.getCode(), entity);
            } else if (valueEntry.getPath() != null && PathProcessor.getItemAtPath(contentItem, valueEntry.getPath()).isPresent()) {
                return convertValuePath(contentItem, valueEntry.getPath(), entity);
            }
        }
        return entity;
    }

    protected abstract E convertValuePath(Locatable contentItem, String path, E baseEntity);

    protected abstract E convertValueCode(Long code, E baseEntity);
}



