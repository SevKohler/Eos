package org.bih.eos.converter.cdm_field.concept;

import com.nedap.archie.rm.datavalues.DataValue;
import com.nedap.archie.rm.datavalues.quantity.DvProportion;
import com.nedap.archie.rm.datavalues.quantity.DvQuantity;
import org.bih.eos.converter.cdm_field.string.DvProportionTypeConverter;
import org.bih.eos.converter.cdm_field.VocabularyIdConverter;
import org.bih.eos.jpabase.dba.service.ParameterWrapper;
import org.bih.eos.jpabase.model.entity.Concept;
import org.bih.eos.services.ConceptSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DVToUnitConceptConverter extends DVTextCodeToConceptConverter {

    private static final Logger LOG = LoggerFactory.getLogger(DVToUnitConceptConverter.class);

    public DVToUnitConceptConverter(ConceptSearchService conceptSearchService) {
        super(conceptSearchService);
    }

    @Override
    public Optional<Concept> convertStandardConcept(DataValue dataValue) {
        if (dataValue.getClass() == DvQuantity.class) {
            return parseStandardConcept((DvQuantity) dataValue);
        } else if (dataValue.getClass() == DvProportion.class) {
            return parseStandardConceptDvProportion((DvProportion) dataValue);
        }
        LOG.info("Unit path conversion only supports input of DV_Quantity and DV_Proportion, another data value type was entered that is not supported. Therefore nothing is mapped");
        return Optional.empty();
    }

    @Override
    public Optional<Concept> convertSourceConcept(DataValue dataValue) {
        if (dataValue.getClass() == DvQuantity.class) {
            return parseSourceConcept((DvQuantity) dataValue);
        } else if (dataValue.getClass() == DvProportion.class) {
            return parseSourceConceptDvProportion((DvProportion) dataValue);
        }
        LOG.info("Unit path conversion only supports input of DV_Quantity and DV_Proportion, another data value type was entered that is not supported. Therefore nothing is mapped");
        return Optional.empty();
    }

    private Optional<Concept> parseStandardConceptDvProportion(DvProportion dvProportion) {
        if (DvProportionTypeConverter.convertType(dvProportion).isPresent()) {
            return parseStandardConcept(dvProportion);
        } else {
            return Optional.empty();
        }
    }

    private Optional<Concept> parseSourceConceptDvProportion(DvProportion dvProportion) {
        if (DvProportionTypeConverter.convertType(dvProportion).isPresent()) {
            return parseSourceConcept(dvProportion);
        } else {
            return Optional.empty();
        }
    }

    private Optional<Concept> parseStandardConcept(DvQuantity dvQuantity) {
        return conceptSearchService.searchSourceConcept(buildQuery(dvQuantity));
    }

    private Optional<Concept> parseStandardConcept(DvProportion dvProportion) {
        return conceptSearchService.searchSourceConcept(buildQuery(dvProportion));
    }

    private Optional<Concept> parseSourceConcept(DvQuantity dvQuantity) {
        return conceptSearchService.searchSourceConcept(buildQuery(dvQuantity));
    }

    private Optional<Concept> parseSourceConcept(DvProportion dvProportion) {
        return conceptSearchService.searchSourceConcept(buildQuery(dvProportion));
    }

    private ParameterWrapper buildQuery(DvQuantity dvQuantity) {
        if (dvQuantity.getUnitsSystem() != null) {
            String vocabularyId = VocabularyIdConverter.convert(dvQuantity.getUnitsSystem());
            return new ParameterWrapper(
                    "String",
                    Arrays.asList("vocabularyId", "conceptCode"),
                    Arrays.asList("=", "="),
                    Arrays.asList(vocabularyId, dvQuantity.getUnits()),
                    "and"
            );
        }
        return new ParameterWrapper(
                "String",
                List.of("conceptCode"),
                List.of("="),
                List.of(dvQuantity.getUnits()),
                "and"
        );
    }

    private ParameterWrapper buildQuery(DvProportion dvProportion) {
        return new ParameterWrapper(
                "String",
                Arrays.asList("conceptCode"),
                Arrays.asList("="),
                Arrays.asList(DvProportionTypeConverter.convertType(dvProportion).get()),
                "and"
        );
    }

}


