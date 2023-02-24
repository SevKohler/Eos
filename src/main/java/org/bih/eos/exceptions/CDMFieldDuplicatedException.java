package org.bih.eos.exceptions;

import org.springframework.core.convert.ConversionException;

public class CDMFieldDuplicatedException extends ConversionException {
    public CDMFieldDuplicatedException(String message) {
        super("A CDM field mapping was entered as required and optional please delete one duplicate of: "+message);
    }
}
