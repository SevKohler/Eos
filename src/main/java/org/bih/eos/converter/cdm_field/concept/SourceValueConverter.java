package org.bih.eos.converter.cdm_field.concept;

import com.nedap.archie.rm.datavalues.DataValue;
import com.nedap.archie.rm.datavalues.DvCodedText;
import com.nedap.archie.rm.datavalues.DvText;
import org.bih.eos.converter.cdm_field.CDMConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class SourceValueConverter extends CDMConverter<String> {
    private static final Logger LOG = LoggerFactory.getLogger(SourceValueConverter.class);

    public SourceValueConverter() {
        super(SourceValueConverter.class);
    }

    @Override
    protected Optional<String> convertDvValue(DataValue value) {
        if (value.getClass() == DvCodedText.class) {
            return Optional.of(((DvCodedText) value).getDefiningCode().getCodeString());
        } else if (value.getClass() == DvText.class) {
        	String text=((DvText) value).getValue();
        	if(text!=null && text.length() >= 50) {
    			LOG.warn("Text for source value exceeds 50 characters and will not fit on omop field, the mapping will be ignored (if not optional). Text value: {}", text);
    			return Optional.empty();
            }
            return Optional.of(text);
        }
        LOG.info("Conversion for this field supports only DV_Text or DV_Coded_Text. Therefore nothing is mapped.");
        return Optional.empty();
    }

    @Override
    protected Optional<String> convertCode(Long code) {
        return Optional.of("" + code);
    }

}