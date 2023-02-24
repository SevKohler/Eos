package org.bih.eos.yaml.cdt_configs.person;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.bih.eos.converter.cdt.DefaultConverterServices;
import org.bih.eos.converter.cdt.converter.configurable.specific.PersonConverter;
import org.bih.eos.yaml.CdmFieldMapping;
import org.bih.eos.yaml.cdt_configs.CDTMappingConfig;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PersonConfig extends CDTMappingConfig<PersonConverter> {

    private CdmFieldMapping genderConcept;
    private CdmFieldMapping yearOfBirth;
    private CdmFieldMapping raceConcept;
    private CdmFieldMapping ethnicityConcept;
    private PersonBirthDatetime birthDatetime = new PersonBirthDatetime();
    private PersonLocation locationId = new PersonLocation();
    private PersonProvider providerId = new PersonProvider();
    private PersonCareSite careSiteId = new PersonCareSite();

    //month_of_birth
    // day of birth
    // birth_datetime
    // location_id
    // provider_id
    // care_site_id

    public PersonConfig(@JsonProperty(value = "gender_concept", required = true) CdmFieldMapping genderConcept,
                          @JsonProperty(value = "year_of_birth", required = true) CdmFieldMapping yearOfBirth,
                          @JsonProperty(value = "race_concept_id") CdmFieldMapping raceConceptId,
                          @JsonProperty(value = "ethnicity_concept_id", required = true) CdmFieldMapping ethnicityConcept) {
        this.genderConcept = genderConcept;
        this.yearOfBirth = yearOfBirth;
        this.raceConcept = raceConceptId;
        this.ethnicityConcept = ethnicityConcept;
    }

    @Override
    public PersonConverter getConverterInstance(DefaultConverterServices defaultConverterServices) {
        return new PersonConverter(defaultConverterServices, this);
    }

    public CdmFieldMapping getGenderConcept() {
        return genderConcept;
    }

    private void setGenderConcept(CdmFieldMapping genderConcept) {
        this.genderConcept = genderConcept;
    }

    public CdmFieldMapping getYearOfBirth() {
        return yearOfBirth;
    }

    private void setYearOfBirth(CdmFieldMapping yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public CdmFieldMapping getRaceConcept() {
        return raceConcept;
    }

    private void setRaceConcept(CdmFieldMapping raceConcept) {
        this.raceConcept = raceConcept;
    }

    public CdmFieldMapping getEthnicityConcept() {
        return ethnicityConcept;
    }

    private void setEthnicityConcept(CdmFieldMapping ethnicityConcept) {
        this.ethnicityConcept = ethnicityConcept;
    }

    public PersonLocation getLocationId() {
        return locationId;
    }

    private void setLocationId(PersonLocation locationId) {
        this.locationId = locationId;
    }

    public PersonProvider getProviderId() {
        return providerId;
    }

    private void setProviderId(PersonProvider providerId) {
        this.providerId = providerId;
    }

    public PersonCareSite getCareSiteId() {
        return careSiteId;
    }

    private void setCareSiteId(PersonCareSite careSiteId) {
        this.careSiteId = careSiteId;
    }

    public PersonBirthDatetime getBirthDatetime() {
        return birthDatetime;
    }

    private void setBirthDatetime(PersonBirthDatetime birthDatetime) {
        this.birthDatetime = birthDatetime;
    }
}
