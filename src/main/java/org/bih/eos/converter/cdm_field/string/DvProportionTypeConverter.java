package org.bih.eos.converter.cdm_field.string;

import com.nedap.archie.rm.datavalues.quantity.DvProportion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class DvProportionTypeConverter {
    private static final Logger LOG = LoggerFactory.getLogger(DvProportionTypeConverter.class);

    public static Optional<String> convertType(DvProportion dvProportion){
        if(dvProportion.getType()==2){
            return Optional.of("%");
        }else{
            LOG.info("Only type 2 is currently supported for dv proportion. Other types wont be converted, the conversion ignored.");
            return Optional.empty();
        }
    }
}
