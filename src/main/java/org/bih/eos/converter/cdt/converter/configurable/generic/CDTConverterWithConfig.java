package org.bih.eos.converter.cdt.converter.configurable.generic;

import org.bih.eos.converter.cdm_field.concept.SourceConceptConverter;
import org.bih.eos.converter.cdm_field.concept.SourceValueConverter;
import org.bih.eos.converter.cdm_field.concept.StandardConceptConverter;
import org.bih.eos.converter.cdm_field.date.DateTimeConverter;
import org.bih.eos.converter.cdm_field.date.EndDateTimeConverter;
import org.bih.eos.converter.cdt.converter.CDTConverter;
import org.bih.eos.converter.cdt.DefaultConverterServices;
import org.bih.eos.yaml.OmopMapping;

public abstract class CDTConverterWithConfig extends CDTConverter {

    protected final DateTimeConverter dateTimeConverter;
    protected final EndDateTimeConverter endTimeConverter;
    protected final StandardConceptConverter standardConceptConverter;
    protected final SourceConceptConverter sourceConceptConverter;
    protected final SourceValueConverter sourceValueConverter;
    protected final OmopMapping omopMapping;

    //TODO check if the fields are there before we map them ! empty stuff cannot be mapped obviously
    protected CDTConverterWithConfig(DefaultConverterServices defaultConverterServices, OmopMapping omopMapping) {
        super(defaultConverterServices);
        this.omopMapping = omopMapping;
        this.dateTimeConverter = new DateTimeConverter();
        this.standardConceptConverter = new StandardConceptConverter(defaultConverterServices.getConceptService(), defaultConverterServices.getElementToConceptConverter());
        this.sourceValueConverter = new SourceValueConverter();
        this.sourceConceptConverter = new SourceConceptConverter(defaultConverterServices.getConceptService(), defaultConverterServices.getElementToConceptConverter());
        this.endTimeConverter = new EndDateTimeConverter();
    }

}
