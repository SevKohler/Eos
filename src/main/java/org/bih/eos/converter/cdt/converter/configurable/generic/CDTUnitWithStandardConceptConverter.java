package org.bih.eos.converter.cdt.converter.configurable.generic;

import org.bih.eos.converter.cdm_field.concept.SourceConceptConverter;
import org.bih.eos.converter.cdm_field.concept.StandardConceptConverter;
import org.bih.eos.converter.cdm_field.concept.UnitSourceValueConverter;
import org.bih.eos.converter.cdt.DefaultConverterServices;
import org.bih.eos.converter.cdt.conversion_entities.EntityWithStandardConcept;
import org.bih.eos.converter.cdm_field.concept.DVToUnitConceptConverter;
import org.bih.eos.yaml.cdt_configs.CDTMappingConfig;


public abstract class CDTUnitWithStandardConceptConverter<E extends EntityWithStandardConcept, CDTM extends CDTMappingConfig> extends CDTConverterWithStandardConcept<E, CDTM> {

    private final DVToUnitConceptConverter elementToUnitConceptConverter;
    private final StandardConceptConverter unitStandardConverter;
    private final SourceConceptConverter unitSourceConceptConverter;
    private final UnitSourceValueConverter unitSourceValueConverter;

    protected CDTUnitWithStandardConceptConverter(DefaultConverterServices defaultConverterServices, CDTM clinicalDataTableMapping) {
        super(defaultConverterServices, clinicalDataTableMapping);
        this.elementToUnitConceptConverter = new DVToUnitConceptConverter(defaultConverterServices.getConceptSearchService());
        this.unitStandardConverter = new StandardConceptConverter(defaultConverterServices.getConceptService(), elementToUnitConceptConverter);
        this.unitSourceConceptConverter = new SourceConceptConverter(defaultConverterServices.getConceptService(), elementToUnitConceptConverter);
        this.unitSourceValueConverter = new UnitSourceValueConverter();
    }


    public DVToUnitConceptConverter getElementToUnitConceptConverter() {
        return elementToUnitConceptConverter;
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



