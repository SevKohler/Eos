package org.bih.eos.converter.cdm_field.string;

import com.nedap.archie.rm.datavalues.DataValue;
import com.nedap.archie.rm.datavalues.DvCodedText;
import com.nedap.archie.rm.datavalues.DvIdentifier;
import com.nedap.archie.rm.datavalues.DvText;
import com.nedap.archie.rm.datavalues.quantity.DvQuantity;
import org.bih.eos.converter.cdm_field.CDMConverter;
import org.bih.eos.converter.cdt.converter.configurable.DvGetter;

import java.util.Optional;
import java.util.OptionalDouble;

public class DVtoStringConverter extends CDMConverter<String> {

    public DVtoStringConverter() {
        super(DVtoStringConverter.class);
    }

    @Override
    protected Optional<String> convertDvValue(DataValue value) {
        if (value.getClass() == DvText.class) {
            return DvGetter.getDvText((DvText) value);
        } else if (value.getClass() == DvIdentifier.class) {
            return DvGetter.getDvIdentifier((DvIdentifier) value);
        } else if (value.getClass() == DvCodedText.class) {
            return DvGetter.getDvCodedText((DvCodedText) value);
        } else if (value.getClass() == DvQuantity.class) {
            return doubleToString(value);
        }
        return Optional.empty();
    }

    @Override
    protected Optional<String> convertCode(Long code) {
        LOG.warn("Concept is not supported for this field !");
        return Optional.empty();
    }

    private static Optional<String> doubleToString(DataValue valueItem) {
        OptionalDouble optionalDouble = DvGetter.getDvQuantity((DvQuantity) valueItem);
        if (optionalDouble.isPresent()) {
            return Optional.of("" + optionalDouble.getAsDouble());
        } else {
            return Optional.empty();
        }
    }

}
