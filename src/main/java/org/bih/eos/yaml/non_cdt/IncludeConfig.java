package org.bih.eos.yaml.non_cdt;

import org.bih.eos.converter.cdt.DefaultConverterServices;
import org.bih.eos.converter.cdt.converter.CDTConverter;
import org.bih.eos.exceptions.UnprocessableEntityException;
import org.bih.eos.yaml.OmopMapping;

public class IncludeConfig<C extends CDTConverter> extends OmopMapping {
    String basePath;
    String archetypeId;

    public IncludeConfig() {
    }

    @Override
    public CDTConverter getConverterInstance(DefaultConverterServices defaultConverterServices) {
        throw new UnprocessableEntityException("Getting the converter instance of an Include is not possible !");
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getArchetypeId() {
        return archetypeId;
    }

    public void setArchetypeId(String archetypeId) {
        this.archetypeId = archetypeId;
    }
}