package org.bih.eos.converter.cdt.converter.configurable;

import com.nedap.archie.rm.datavalues.DvCodedText;
import com.nedap.archie.rm.datavalues.DvIdentifier;
import com.nedap.archie.rm.datavalues.DvText;
import com.nedap.archie.rm.datavalues.quantity.DvQuantity;

import java.util.Optional;
import java.util.OptionalDouble;

public class DvGetter {

    public static Optional<String> getDvText(DvText value){
        if(value != null) {
            return Optional.of(value.getValue());
        }
        return Optional.empty();
    }

    public static OptionalDouble getDvQuantity(DvQuantity value){
        if(value != null) {
            return OptionalDouble.of(value.getMagnitude());
        }
        return OptionalDouble.empty();
    }

    public static Optional<String> getDvCodedText(DvCodedText value) {
        if(value != null) {
            return Optional.of(value.getValue());
        }
        return Optional.empty();
    }

    public static Optional<String> getDvIdentifier(DvIdentifier value) {
        if(value != null) {
            return Optional.of(value.getId());
        }
        return Optional.empty();
    }
}
