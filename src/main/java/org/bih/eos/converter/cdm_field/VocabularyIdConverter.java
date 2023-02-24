package org.bih.eos.converter.cdm_field;

import java.util.HashMap;

public class VocabularyIdConverter {

    private static final String SNOMED = "SNOMED";
    private static final String ATC = "ATC";
    private static final String LOINC = "LOINC";

    static HashMap<String, String> terminologyIdsToVocabularyIds = new HashMap<>() {{
        put("http://snomed.info/sct", SNOMED);
        put("SNOMED CT", SNOMED);
        put("http://fhir.de/CodeSystem/bfarm/atc", ATC);
        put("http://loinc.org", LOINC);
    }};

    public static String convert(String terminologyId){
        return terminologyIdsToVocabularyIds.getOrDefault(terminologyId, terminologyId);
    }
}
