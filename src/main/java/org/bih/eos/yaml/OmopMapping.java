package org.bih.eos.yaml;

import com.fasterxml.jackson.annotation.*;
import org.bih.eos.converter.cdt.DefaultConverterServices;
import org.bih.eos.converter.cdt.converter.CDTConverter;
import org.bih.eos.yaml.cdt_configs.condition_occurrence.ConditionOccurrenceConfig;
import org.bih.eos.yaml.cdt_configs.death.DeathConfig;
import org.bih.eos.yaml.cdt_configs.device_exposure.DeviceExposureConfig;
import org.bih.eos.yaml.cdt_configs.drug_exposure.DrugExposureConfig;
import org.bih.eos.yaml.cdt_configs.measurement.MeasurementConfig;
import org.bih.eos.yaml.cdt_configs.observation.ObservationConfig;
import org.bih.eos.yaml.cdt_configs.procedure_occurrence.ProcedureOccurrenceConfig;
import org.bih.eos.yaml.cdt_configs.person.PersonConfig;
import org.bih.eos.yaml.cdt_configs.specimen.SpecimenConfig;
import org.bih.eos.yaml.non_cdt.CustomMappingConfig;
import org.bih.eos.yaml.non_cdt.IncludeConfig;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = MeasurementConfig.class, name = "Measurement"),
        @JsonSubTypes.Type(value = DrugExposureConfig.class, name = "DrugExposure"),
        @JsonSubTypes.Type(value = ConditionOccurrenceConfig.class, name = "ConditionOccurrence"),
        @JsonSubTypes.Type(value = ObservationConfig.class, name = "Observation"),
        @JsonSubTypes.Type(value = SpecimenConfig.class, name = "Specimen"),
        @JsonSubTypes.Type(value = ProcedureOccurrenceConfig.class, name = "ProcedureOccurrence"),
        @JsonSubTypes.Type(value = PersonConfig.class, name = "Person"),
        @JsonSubTypes.Type(value = DeathConfig.class, name = "Death"),
        @JsonSubTypes.Type(value = DeviceExposureConfig.class, name = "DeviceExposure"),
        @JsonSubTypes.Type(value = IncludeConfig.class, name = "Include"),
        @JsonSubTypes.Type(value = CustomMappingConfig.class, name = "CustomMapping")
}
)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public abstract class OmopMapping implements Cloneable {

    private String type;

    public OmopMapping() {
    }

    public OmopMapping(String type) {
        this.type = type;
    }

    public abstract CDTConverter getConverterInstance(DefaultConverterServices defaultConverterServices);

    public String getType() {
        return type;
    }

    private void setType(String type) {
        this.type = type;
    }

    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }

}
