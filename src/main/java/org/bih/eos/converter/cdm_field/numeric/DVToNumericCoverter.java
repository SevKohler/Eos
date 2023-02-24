package org.bih.eos.converter.cdm_field.numeric;

import com.nedap.archie.rm.archetyped.Locatable;
import com.nedap.archie.rm.datastructures.Element;
import com.nedap.archie.rm.datavalues.DvText;
import com.nedap.archie.rm.datavalues.quantity.DvQuantity;
import org.bih.eos.converter.cdm_field.MultiplicationEntry;
import org.bih.eos.converter.PathProcessor;
import org.bih.eos.yaml.ValueEntry;

import java.util.Optional;
import java.util.OptionalDouble;

public class DVToNumericCoverter {

    public static Optional<Integer> convertToInteger(Locatable contentItem, ValueEntry[] valueEntries) {
        OptionalDouble result = convertToDouble(contentItem, valueEntries);
        if (result.isPresent()) {
            Double x = result.getAsDouble();
            return Optional.of(x.intValue());
        } else {
            return Optional.empty();
        }
    }

    public static OptionalDouble convertToDouble(Locatable contentItem, ValueEntry[] valueEntries) {
        for (ValueEntry valueEntry : valueEntries) {
            if (valueEntry.getCode() != null) {
                return convertQuantityCode(valueEntry.getCode());
            } else if (valueEntry.getPath() != null && PathProcessor.getItemAtPath(contentItem, valueEntry.getPath()).isPresent()) {
                return convertPath(PathProcessor.getItemAtPath(contentItem, valueEntry.getPath()).get());
            } else if (valueEntry.getMultiplication() != null && MultiplicationEntry.pathsNotEmpty(contentItem, valueEntry.getMultiplication())) {
                return convertMultiplication(contentItem, valueEntry.getMultiplication());
            }
        }
        return OptionalDouble.empty();
    }

    private static OptionalDouble convertMultiplication(Locatable contentItem, ValueEntry[] multiplication) {
        return MultiplicationEntry.convert(contentItem, multiplication);
    }

    private static OptionalDouble convertPath(Object itemAtPath) {
        Element element = (Element) itemAtPath;
        if (element.getValue() != null) {
            if (element.getValue().getClass() == DvQuantity.class) {
                DvQuantity dvQuantity = (DvQuantity) element.getValue();
                return OptionalDouble.of(dvQuantity.getMagnitude());
            } else if (element.getValue().getClass() == DvText.class) {
                DvText dvText = (DvText) element.getValue();
                try {
                    return OptionalDouble.of(Double.parseDouble(dvText.getValue()));
                } catch (NumberFormatException ex) {
                    //ignore
                }
            }
        }
        return OptionalDouble.empty();
    }

    private static OptionalDouble convertQuantityCode(Long code) {
        return OptionalDouble.of(code);
    }


}

