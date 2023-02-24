package org.bih.eos.exceptions;

import org.springframework.core.convert.ConversionException;

public class UnprocessableEntityException extends ConversionException {
    public UnprocessableEntityException(String message) {
        super(message);
    }
}
