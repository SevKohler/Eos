package org.bih.eos.yaml.cdt_configs.death;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.bih.eos.converter.cdt.DefaultConverterServices;
import org.bih.eos.converter.cdt.converter.configurable.specific.DeathConverter;
import org.bih.eos.yaml.CdmFieldMapping;
import org.bih.eos.yaml.cdt_configs.CDTMappingConfig;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class DeathConfig extends CDTMappingConfig<DeathConverter> {

    private CdmFieldMapping deathDate;
    private DeathCause cause = new DeathCause();

    public DeathConfig(@JsonProperty(value = "death_date", required = true) CdmFieldMapping deathDate){
        this.deathDate = deathDate;
    }

    @Override
    public DeathConverter getConverterInstance(DefaultConverterServices defaultConverterServices) {
        return new DeathConverter(defaultConverterServices, this);
    }

    public CdmFieldMapping getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(CdmFieldMapping deathDate) {
        this.deathDate = deathDate;
    }

    public DeathCause getCause() {
        return cause;
    }

    public void setCause(DeathCause cause) {
        this.cause = cause;
    }
}