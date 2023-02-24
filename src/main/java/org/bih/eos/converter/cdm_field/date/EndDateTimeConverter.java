package org.bih.eos.converter.cdm_field.date;

import com.nedap.archie.rm.archetyped.Locatable;
import org.bih.eos.converter.PathProcessor;

import java.util.Date;
import java.util.Optional;

public class EndDateTimeConverter extends DateTimeConverter {

    @Override
    protected Optional<Date> convertPath(Locatable contentItem, String path) {
        return new DVToDateConverter().convertEndTime(PathProcessor.getItemAtPath(contentItem, path).get());
    }

}
