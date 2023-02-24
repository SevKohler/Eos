package org.bih.eos.yaml.cdt_configs;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.bih.eos.converter.cdt.DefaultConverterServices;
import org.bih.eos.converter.cdt.converter.configurable.generic.CDTConverterWithConfig;
import org.bih.eos.yaml.CdmFieldMapping;
import org.bih.eos.yaml.OmopMapping;

public abstract class CDTMappingConfig<B extends CDTConverterWithConfig> extends OmopMapping {

    private String basePath;
    protected CdmFieldMapping conceptId;

    @JsonCreator
    public CDTMappingConfig() {
    }

    public CDTMappingConfig(String type) {
        super(type);
    }

    public abstract B getConverterInstance(DefaultConverterServices defaultConverterServices);

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public CdmFieldMapping getConceptId() {
        return conceptId;
    }

    private void setConceptId(CdmFieldMapping conceptId) {
        this.conceptId = conceptId;
    }
}
