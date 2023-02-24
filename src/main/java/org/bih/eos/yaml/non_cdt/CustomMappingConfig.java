package org.bih.eos.yaml.non_cdt;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import org.bih.eos.converter.cdt.converter.CDTConverter;
import org.bih.eos.converter.cdt.converter.custom.CustomCDTConverter;
import org.bih.eos.converter.cdt.converter.custom.FactRelationshipCustomConverter;
import org.bih.eos.converter.cdt.DefaultConverterServices;
import org.bih.eos.yaml.OmopMapping;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)

@JsonSubTypes({
        @JsonSubTypes.Type(value = FactRelationshipCustomConverter.class, name = "FactRelationshipCustomConverter")
}
)
public class CustomMappingConfig<C extends CDTConverter> extends OmopMapping {

    String name;

    public CustomMappingConfig(){}

    @Override
    public CustomCDTConverter getConverterInstance(DefaultConverterServices defaultConverterServices) {
            return new FactRelationshipCustomConverter(defaultConverterServices, this);
    }


    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }
}
