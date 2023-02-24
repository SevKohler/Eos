
package org.bih.eos.converter.cdm_field.numeric;

import com.nedap.archie.rm.datavalues.DataValue;
import com.nedap.archie.rm.datavalues.quantity.DvOrdinal;
import com.nedap.archie.rm.datavalues.quantity.DvProportion;
import com.nedap.archie.rm.datavalues.quantity.DvQuantity;
import org.bih.eos.converter.cdm_field.CDMConverter;

import java.util.Objects;
import java.util.Optional;

public class RangeHighConverter extends CDMConverter<Double> {

    public RangeHighConverter() {
        super(RangeHighConverter.class);
    }

    @Override
    protected Optional<Double> convertDvValue(DataValue value) {
        if (value.getClass() == DvQuantity.class) {
            return convertDvQuantity((DvQuantity) value);
        } else if (value.getClass() == DvProportion.class) {
            return convertDvProportion((DvProportion) value);
        } else if (value.getClass() == DvOrdinal.class) {
            return convertDvOrdinal((DvOrdinal) value);
        } else {
            LOG.warn("The data value class: " + value.getClass() + " is not supported for the Measurement range High mapping or normal range upper is not populated. Nothing is mapped for operator.");
            return Optional.empty();
        }
    }

    private Optional<Double> convertDvQuantity(DvQuantity quantity) {
        if (quantity.getNormalRange() != null) {
            try {
                return Optional.of(Objects.requireNonNull(quantity.getNormalRange().getUpper()).getMagnitude());
            } catch (NullPointerException npe) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    private Optional<Double> convertDvProportion(DvProportion proportion) {
        if (proportion.getNormalRange() != null) {
            try {
                return Optional.of(Objects.requireNonNull(proportion.getNormalRange().getUpper()).getMagnitude());
            } catch (NullPointerException npe) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    private Optional<Double> convertDvOrdinal(DvOrdinal ordinal) {
        if (ordinal.getNormalRange() != null) {
            try {
                Long result = Objects.requireNonNull(ordinal.getNormalRange().getUpper()).getValue();
                return Optional.of(result.doubleValue());
            } catch (NullPointerException npe) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    @Override
    protected Optional<Double> convertCode(Long code) {
        LOG.warn("Code is not supported for Range !");
        return Optional.empty();
    }
}
