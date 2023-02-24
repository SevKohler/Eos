package org.bih.eos.converter.cdm_field;

import com.nedap.archie.rm.archetyped.Locatable;
import com.nedap.archie.rm.datastructures.Element;
import com.nedap.archie.rm.datavalues.DvText;
import com.nedap.archie.rm.datavalues.quantity.DvCount;
import com.nedap.archie.rm.datavalues.quantity.DvQuantity;
import org.bih.eos.converter.PathProcessor;
import org.bih.eos.yaml.ValueEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.OptionalDouble;

public class MultiplicationEntry {
    private static final Logger LOG = LoggerFactory.getLogger(MultiplicationEntry.class);

    public static OptionalDouble convert(Locatable contentItem, ValueEntry[] valueEntries) {
        OptionalDouble result = OptionalDouble.empty();
        for (ValueEntry valueEntry : valueEntries) {
            if (valueEntry.getPath() != null && PathProcessor.getItemAtPath(contentItem, valueEntry.getPath()).isPresent()) {
                result = convertMultiplicationEntry(contentItem, valueEntry.getPath(), result);
                if (result.isEmpty()) {
                    return OptionalDouble.empty();
                }
            }
        }
        return result;
    }

    private static OptionalDouble convertMultiplicationEntry(Locatable contentItem, String path, OptionalDouble result) {
        Element element = (Element) PathProcessor.getItemAtPath(contentItem, path).get();
        if (element.getValue()!=null) {
            if (element.getValue().getClass() == DvQuantity.class) {
                return convertDvQuantity(result, (DvQuantity) element.getValue());
            } else if (element.getValue().getClass() == DvCount.class) {
                return convertDvCount(result, (DvCount) element.getValue());
            } else if (element.getValue().getClass() == DvText.class) {
                return convertDvText(result, (DvText) element.getValue());
            } else {
                LOG.warn("Unsupported Element type entered for multiplication.");
                return OptionalDouble.empty();
            }
        }
        return OptionalDouble.empty();
    }

    private static OptionalDouble convertDvText(OptionalDouble result, DvText dvText) {
        try {
            if (result.isPresent()) {
                return OptionalDouble.of(result.getAsDouble() * Double.parseDouble(dvText.getValue()));
            } else {
                return OptionalDouble.of(Double.parseDouble(dvText.getValue()));
            }
        } catch (NumberFormatException ex) {
            return OptionalDouble.empty();
        }
    }

    private static OptionalDouble convertDvCount(OptionalDouble result, DvCount dvCount) {
        if (result.isPresent()) {
            return OptionalDouble.of(result.getAsDouble() * dvCount.getMagnitude());
        } else {
            return OptionalDouble.of(dvCount.getMagnitude());
        }
    }

    private static OptionalDouble convertDvQuantity(OptionalDouble result, DvQuantity dvQuantity) {
        if (result.isPresent()) {
            return OptionalDouble.of(result.getAsDouble() * dvQuantity.getMagnitude());
        } else {
            return OptionalDouble.of(dvQuantity.getMagnitude());
        }
    }

    public static boolean pathsNotEmpty(Locatable contentItem, ValueEntry[] multiplication) {
        for (ValueEntry valueEntry : multiplication) {
            if (valueEntry.getPath() == null || PathProcessor.getItemAtPath(contentItem, valueEntry.getPath()).isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
