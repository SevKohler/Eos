package org.bih.eos.converter.cdt.converter.custom;

import com.nedap.archie.rm.datastructures.Element;
import com.nedap.archie.rm.datavalues.DvIdentifier;
import com.nedap.archie.rm.datavalues.DvURI;
import org.bih.eos.converter.dao.ConversionTrack;
import org.bih.eos.converter.dao.ConvertableContentItem;
import org.bih.eos.converter.cdt.DefaultConverterServices;
import org.bih.eos.jpabase.model.entity.JPABaseEntity;
import org.bih.eos.jpabase.model.entity.FactRelationship;
import org.bih.eos.jpabase.model.entity.Measurement;
import org.bih.eos.jpabase.model.entity.Specimen;
import org.bih.eos.yaml.OmopMapping;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

public class FactRelationshipCustomConverter extends CustomCDTConverter {

    public FactRelationshipCustomConverter(DefaultConverterServices
                                                   defaultConverterServices,
                                           OmopMapping omopMapping) {
        super(defaultConverterServices, omopMapping);
    }

    @Override
    public List<JPABaseEntity> convert(@NonNull ConvertableContentItem convertableContentItem, List<ConversionTrack> conversionTrack, List<JPABaseEntity> conversionResult) {
        List<ConversionTrack> convertedMeasurements = getMeasurementContentItems(conversionTrack);
        List<ConversionTrack> convertedSpecimens = getConvertedSpecimen(conversionTrack);
        conversionResult = searchAndConvertFactRelationships(conversionResult, convertedMeasurements, convertedSpecimens);
        return conversionResult;
    }

    private List<JPABaseEntity> searchAndConvertFactRelationships(List<JPABaseEntity> conversionResult, List<ConversionTrack> convertedMeasurements, List<ConversionTrack> convertedSpecimens) {
        for (ConversionTrack specimenTrack : convertedSpecimens) {
            for (ConversionTrack measurementTrack : convertedMeasurements) {
                if (checkIfItemsPresent(specimenTrack, measurementTrack) && specimenIdsMatch(specimenTrack, measurementTrack)) {
                    conversionResult = convertFactRelationship(conversionResult, specimenTrack, measurementTrack);
                }
            }
        }
        return conversionResult;
    }

    private List<JPABaseEntity> convertFactRelationship(List<JPABaseEntity> conversionResult, ConversionTrack specimenTrack, ConversionTrack measurementTrack) {
        FactRelationship factRelationship = new FactRelationship();
        Specimen specimen = (Specimen) getInstance(specimenTrack);
        Measurement measurement = (Measurement) getInstance(measurementTrack);
        conversionResult = deleteDuplicates(conversionResult, specimen, measurement);
        factRelationship.setFactId1(specimen.getId());
        factRelationship.setFactId2(measurement.getId());
        factRelationship.setDomainConceptId1(specimen.getSpecimenConcept().getId());
        factRelationship.setDomainConceptId2(measurement.getMeasurementConcept().getId());
        factRelationship.setRelationshipConcept(defaultConverterServices.getConceptService().findById(32669L));
        conversionResult.add(factRelationship);
        return conversionResult;
    }

    private JPABaseEntity getInstance(ConversionTrack conversionTrack) {
        if (!conversionTrack.isPersisted()) {
            conversionTrack.setPersistedTrue();
            return persist(conversionTrack.getConversionResult());
        } else {
            return conversionTrack.getConversionResult();
        }
    }

    private List<JPABaseEntity> deleteDuplicates(List<JPABaseEntity> conversionResult, Specimen specimen, Measurement measurement) {
        for (JPABaseEntity baseEntity : conversionResult) {
            if (baseEntity.equals(specimen)) {
                conversionResult.remove(specimen);
                return deleteDuplicates(conversionResult, specimen, measurement);
            } else if (baseEntity.equals(measurement)) {
                conversionResult.remove(measurement);
                return deleteDuplicates(conversionResult, specimen, measurement);
            }
        }
        return conversionResult;
    }

    private List<ConversionTrack> getMeasurementContentItems(List<ConversionTrack> conversionTrack) {
        List<ConversionTrack> convertedMeasurements = new ArrayList<>();
        for (ConversionTrack track : conversionTrack) {
            if (track.getConversionResult() instanceof Measurement) {
                convertedMeasurements.add(track);
            }
        }
        return convertedMeasurements;
    }

    private List<ConversionTrack> getConvertedSpecimen(List<ConversionTrack> conversionTrack) {
        List<ConversionTrack> convertedSpecimen = new ArrayList<>();
        for (ConversionTrack track : conversionTrack) {
            if (track.getConversionResult() instanceof Specimen) {
                convertedSpecimen.add(track);
            }
        }
        return convertedSpecimen;
    }

    private boolean specimenIdsMatch(ConversionTrack specimenTrack, ConversionTrack measurementTrack) {
        String specimenIdSpecimen = getSpecimenProbenId((Element) specimenTrack.getInputContentItem().itemAtPath("/items[at0001]"));
        String measurementSpecimenId = getAnalytProbenId((Element) measurementTrack.getInputContentItem().itemAtPath("/items[at0026]"));
        return specimenIdSpecimen.equals(measurementSpecimenId);
    }

    private String getSpecimenProbenId(Element element) {
        if (element.getValue()!=null) {
            return ((DvIdentifier) element.getValue()).getId();
        } else {
            return "unknown1"; //TODO fix bad workaround
        }
    }

    private String getAnalytProbenId(Element element) {
        if (element.getValue()!=null) {
            if (element.getValue().getClass() == DvIdentifier.class) {
                return ((DvIdentifier) element.getValue()).getId();
            } else {
                return ((DvURI) element.getValue()).getValue().toString();
            }
        } else {
            return "unknown2"; //TODO fix bad workaround
        }
    }

    private boolean checkIfItemsPresent(ConversionTrack specimenTrack, ConversionTrack measurementTrack) {
        return specimenTrack.getInputContentItem().itemAtPath("/items[at0001]") != null &&
                measurementTrack.getInputContentItem().itemAtPath("/items[at0026]") != null;
    }

}
