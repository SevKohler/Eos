package org.bih.eos.converter.cdm_field.concept;

import com.nedap.archie.rm.datavalues.DataValue;
import com.nedap.archie.rm.datavalues.quantity.DvProportion;
import com.nedap.archie.rm.datavalues.quantity.DvQuantity;
import org.bih.eos.converter.cdm_field.CDMConverter;
import org.bih.eos.converter.cdm_field.string.DvProportionTypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class UnitSourceValueConverter extends CDMConverter<String> {
    private static final Logger LOG = LoggerFactory.getLogger(UnitSourceValueConverter.class);

    public UnitSourceValueConverter() {
        super(UnitSourceValueConverter.class);
    }

    @Override
    protected Optional<String> convertDvValue(DataValue value) {
        if (value.getClass() == DvQuantity.class) {
            return getUnit((DvQuantity) value);
        } else if (value.getClass() == DvProportion.class) {
            return getUnit((DvProportion) value);
        }
        return Optional.empty();
    }

    @Override
    protected Optional<String> convertCode(Long code) {
        return Optional.of("" + code);
    }


    private Optional<String> getUnit(DvQuantity dvQuantity) {
        if (dvQuantity.getUnitsDisplayName() != null) {
            return Optional.of(dvQuantity.getUnitsDisplayName());
        } else if (dvQuantity.getUnits() != null) {
            return Optional.of(dvQuantity.getUnits());
        } else {
            return Optional.empty();
        }
    }

    private Optional<String> getUnit(DvProportion dvProportion) {
        return DvProportionTypeConverter.convertType(dvProportion);
    }
}