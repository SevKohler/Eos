package org.bih.eos.yaml.cdt_configs.visit;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.bih.eos.converter.cdt.DefaultConverterServices;
import org.bih.eos.converter.cdt.converter.configurable.specific.VisitConverter;
import org.bih.eos.yaml.CdmFieldMapping;
import org.bih.eos.yaml.cdt_configs.CDTMappingConfig;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class VisitConfig extends CDTMappingConfig<VisitConverter> {

	private CdmFieldMapping startDateTime;
    private CdmFieldMapping endDateTime;
    private CdmFieldMapping visitConcept;
    private CdmFieldMapping providerId;
    private CdmFieldMapping careSiteId;
    private CdmFieldMapping locationId;

//    public VisitConfig(@JsonProperty(value = "start_datetime", required = true) CdmFieldMapping startDateTime,
//    		@JsonProperty(value = "visit_concept", required = true) CdmFieldMapping visitConcept) {
//    	this.startDateTime = startDateTime;
//    	this.visitConcept = visitConcept;
//    }

    public VisitConfig(@JsonProperty(value = "start_datetime", required = true) CdmFieldMapping startDateTime,
    		@JsonProperty(value = "end_datetime", required = true) CdmFieldMapping endDateTime,
    		@JsonProperty(value = "visit_concept", required = true) CdmFieldMapping visitConcept) {
    	this.startDateTime = startDateTime;
    	this.endDateTime = endDateTime;
    	this.visitConcept = visitConcept;
    }

    @Override
    public VisitConverter getConverterInstance(DefaultConverterServices defaultConverterServices) {
    	return new VisitConverter(defaultConverterServices, this);
    }

    public CdmFieldMapping getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(CdmFieldMapping startDateTime) {
        this.startDateTime = startDateTime;
    }

    public CdmFieldMapping getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(CdmFieldMapping endDateTime) {
        this.endDateTime = endDateTime;
    }

    public CdmFieldMapping getVisitConcept() {
        return visitConcept;
    }

    public void setVisitConcept(CdmFieldMapping visitConcept) {
        this.visitConcept = visitConcept;
    }

    public CdmFieldMapping getProviderId() {
        return providerId;
    }

    public void setProviderId(CdmFieldMapping providerId) {
        this.providerId = providerId;
    }

    public CdmFieldMapping getCareSiteId() {
        return careSiteId;
    }

    public void setCareSiteId(CdmFieldMapping careSiteId) {
        this.careSiteId = careSiteId;
    }

    public CdmFieldMapping getLocationId() {
        return locationId;
    }

    public void setLocationId(CdmFieldMapping locationId) {
        this.locationId = locationId;
    }
}