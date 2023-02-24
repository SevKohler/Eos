package org.bih.eos.converter.cdt.conversion_entities;

import java.util.Optional;
import java.util.function.Consumer;

public class CDMFieldSetter {

    public static <T> Boolean setFieldIfPresent(Optional<T> inputValue, Boolean cdmFieldRequiredMissing, Consumer<T> setterConsumer){
        if (inputValue.isPresent()) {
            setterConsumer.accept(inputValue.get());
            return cdmFieldRequiredMissing;
        } else {
            return true;
        }
    }

    public static <T> Boolean setFieldIfPresent(Optional<T> inputValue, Boolean cdmFieldRequiredMissing, Boolean isOptional,  Consumer<T> setterConsumer){
        if (inputValue.isPresent()) {
            setterConsumer.accept(inputValue.get());
            return cdmFieldRequiredMissing;
        } else if(!isOptional){
            return true;
        }
        return cdmFieldRequiredMissing;
    }

}
