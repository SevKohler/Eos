package org.bih.eos.converter.cdt.converter;

import org.bih.eos.converter.cdt.DefaultConverterServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CDTConverter {
    private static final Logger LOG = LoggerFactory.getLogger(CDTConverter.class);

    protected final DefaultConverterServices defaultConverterServices;

    protected CDTConverter(DefaultConverterServices defaultConverterServices) {
        this.defaultConverterServices = defaultConverterServices;
    }

    public void unsupportedMapping(String fieldName){
        LOG.warn( fieldName + " mapping is currently not supported! Mapping will be ignored, if not set optional: false");
    }

}
