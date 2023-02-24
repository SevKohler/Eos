package org.bih.eos.converter.cdt.converter.configurable.generic;

import org.bih.eos.converter.cdm_field.concept.SourceConceptConverter;
import org.bih.eos.converter.cdm_field.concept.StandardConceptConverter;
import org.bih.eos.converter.cdm_field.concept.UnitSourceValueConverter;
import org.bih.eos.converter.cdt.DefaultConverterServices;
import org.bih.eos.converter.cdt.conversion_entities.EntityWithSourceConcept;
import org.bih.eos.converter.cdm_field.concept.DVToUnitConceptConverter;
import org.bih.eos.yaml.cdt_configs.CDTMappingConfig;


public abstract class CDTUnitWithSourceConceptConverter<E extends EntityWithSourceConcept, CDTM extends CDTMappingConfig> extends CDTConverterWithSourceConcept<E, CDTM> {

    private final DVToUnitConceptConverter dvToUnitConceptConverter;
    private final StandardConceptConverter unitStandardConverter;
    private final SourceConceptConverter unitSourceConceptConverter;
    private final UnitSourceValueConverter unitSourceValueConverter;

    protected CDTUnitWithSourceConceptConverter(DefaultConverterServices defaultConverterServices, CDTM clinicalDataTableMapping) {
        super(defaultConverterServices, clinicalDataTableMapping);
        this.dvToUnitConceptConverter = new DVToUnitConceptConverter(defaultConverterServices.getConceptSearchService());
        this.unitStandardConverter = new StandardConceptConverter(defaultConverterServices.getConceptService(), dvToUnitConceptConverter);
        this.unitSourceConceptConverter = new SourceConceptConverter(defaultConverterServices.getConceptService(), dvToUnitConceptConverter);
        this.unitSourceValueConverter = new UnitSourceValueConverter();
    }

    public StandardConceptConverter getUnitStandardConverter() {
        return unitStandardConverter;
    }

    public SourceConceptConverter getUnitSourceConceptConverter() {
        return unitSourceConceptConverter;
    }

    public UnitSourceValueConverter getUnitSourceValueConverter() {
        return unitSourceValueConverter;
    }
}



