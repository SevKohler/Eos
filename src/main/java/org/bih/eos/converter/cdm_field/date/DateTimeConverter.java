package org.bih.eos.converter.cdm_field.date;

import com.nedap.archie.rm.archetyped.Locatable;
import com.nedap.archie.rm.datavalues.DataValue;
import org.bih.eos.converter.cdm_field.CDMConverter;
import org.bih.eos.converter.PathProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Optional;

public class DateTimeConverter extends CDMConverter<Date> {

    private static final Logger LOG = LoggerFactory.getLogger(DateTimeConverter.class);

    public DateTimeConverter() {
        super(DateTimeConverter.class);
    }

    @Override
    protected Optional<Date> convertCode(Long code) {
        LOG.warn("Code is not supported for a Date mapping ! Nothing is set.");
        return Optional.empty();

    /*       Concept concept = conceptService.findById(code);
               try {
            for (Method setter : setters) {
                if (SourceValueParser.parse((Element) conceptItem).isPresent()) {
                    setter.invoke(entity, getValueAtPath(conceptItem));
                }
            }
        } catch (IllegalAccessException | InvocationTargetException exception) {
            LOG.error("error in reflection for entity" + entity.toString());
        }*/
    }

    @Override
    protected Optional<Date> convertPath(Locatable contentItem, String path) {
        return new DVToDateConverter().convert(PathProcessor.getItemAtPath(contentItem, path).get());
    }

    @Override
    protected Optional<Date> convertDvValue(DataValue value) {
        // ignore
        return Optional.empty();
    }


}
