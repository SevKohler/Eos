package org.bih.eos.converter.cdm_field.concept;

import com.nedap.archie.rm.datavalues.DataValue;
import com.nedap.archie.rm.datavalues.DvCodedText;
import com.nedap.archie.rm.datavalues.quantity.DvProportion;
import com.nedap.archie.rm.datavalues.quantity.DvQuantity;
import org.bih.eos.converter.cdm_field.CDMConverter;
import org.bih.eos.converter.cdt.DefaultConverterServices;
import org.bih.eos.jpabase.model.entity.Concept;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Optional;

public class OperatorConverter extends CDMConverter<Concept> {

    private static final Logger LOG = LoggerFactory.getLogger(OperatorConverter.class);

    private final HashMap<String, Long> measOperatorValues = new HashMap<>() {
        {
            put("=", 4172703L);
            put("<", 4171756L);
            put(">", 4172704L);
            put("<=", 4171754L);
            put(">=", 4171755L);
        }
    };
    private final DefaultConverterServices defaultConverterServices;

    public OperatorConverter(DefaultConverterServices defaultConverterServices) {
        super(OperatorConverter.class);
        this.defaultConverterServices = defaultConverterServices;
    }

    @Override
    protected Optional<Concept> convertDvValue(DataValue value) {
        if (value.getClass() == DvQuantity.class) {
            return convertOperatorDvQuantity((DvQuantity) value);
        } else if (value.getClass() == DvProportion.class) {
            return convertOperatorDvProportion((DvProportion) value);
        } else if (value.getClass() == DvCodedText.class) {
            return convertOperatorDvCodedText((DvCodedText) value);
        } else {
            LOG.warn("The data value class: " + value.getClass() + " is not supported for the Measurement Operator mapping or the magnitude status was not populated. Nothing is mapped for operator.");
            return Optional.empty();
        }
    }

    @Override
    protected Optional<Concept> convertCode(Long code) {
        if (measOperatorValues.containsValue(code)) {
            return Optional.of(defaultConverterServices.getConceptService().findById(code));
        } else {
            LOG.warn("The operator concept code: " + code + " is not a valid code for operator. Valid are: 4172703, 4171756, 4172704, 4171754, 4171755.  Nothing is mapped for operator.");
            return Optional.empty();
        }
    }

    private Optional<Concept> convertOperatorDvCodedText(DvCodedText value) {
        DVTextCodeToConceptConverter dvTextCodeToConceptConverter = new DVTextCodeToConceptConverter(defaultConverterServices.getConceptSearchService());
        Optional<Concept> standardConcept = dvTextCodeToConceptConverter.convertStandardConcept(value); // Meas value operators are always standard concepts
        if (standardConcept.isPresent() && measOperatorValues.containsValue(standardConcept.get().getId())) {
            return standardConcept;
        } else {
            return Optional.empty();
        }
    }

    private Optional<Concept> convertOperatorDvProportion(DvProportion proportion) {
        if (proportion.getMagnitudeStatus() != null) {
            return getConceptFromMagnitudeStatus(proportion.getMagnitudeStatus());
        }
        LOG.warn("The operator from magnitude status of the DvProportion" + proportion.getMagnitudeStatus() + " is not a valid operator. Valid are: =, >, <, =>, <=.  Nothing is mapped for operator.");
        return Optional.empty();
    }

    private Optional<Concept> convertOperatorDvQuantity(DvQuantity quantity) {
        if (quantity.getMagnitudeStatus() != null) {
            return getConceptFromMagnitudeStatus(quantity.getMagnitudeStatus());
        }
        LOG.warn("The operator from magnitude status of the DvQuantity" + quantity.getMagnitudeStatus() + " is not a valid operator. Valid are: =, >, <, =>, <=.  Nothing is mapped for operator.");
        return Optional.empty();
    }

    private Optional<Concept> getConceptFromMagnitudeStatus(String magnitudeStatus) {
        if (measOperatorValues.containsKey(magnitudeStatus)) {
            return Optional.of(defaultConverterServices.getConceptService().findById(measOperatorValues.get(magnitudeStatus))); // cant be null
        }
        return Optional.empty();
    }


}
