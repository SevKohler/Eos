package org.bih.eos.converter.cdm_field;

import com.nedap.archie.rm.archetyped.Locatable;
import com.nedap.archie.rm.datastructures.Element;
import com.nedap.archie.rm.datavalues.DataValue;
import com.nedap.archie.rm.datavalues.DvCodedText;

import org.bih.eos.yaml.ConceptMap;
import org.bih.eos.yaml.ValueEntry;
import org.bih.eos.converter.PathProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public abstract class CDMConverter<T> {
    public final Logger LOG;

    public CDMConverter(Class clazz) {
       this.LOG = LoggerFactory.getLogger(clazz);
    }

    public Optional<T> convert(Locatable contentItem, ValueEntry[] valueEntries) {
        Optional<T> result = runConversion(contentItem, valueEntries);
        if(result.isEmpty()){
            LOG.warn("For the ValueEntries: "+ getValueEntries(valueEntries) + " no Value was found. If required archetype mapping is ignored.");
        }
        return result;
    }

    private Optional<T> runConversion(Locatable contentItem, ValueEntry[] valueEntries) {
        for (ValueEntry valueEntry : valueEntries) {
            if (valueEntry.getCode() != null) {
                return convertCode(valueEntry.getCode());
            } else if (valueEntry.getPath() != null && PathProcessor.getItemAtPath(contentItem, valueEntry.getPath()).isPresent()) {
                Optional<T> convertedPath = convertPath(contentItem, valueEntry.getPath());
                if(convertedPath.isPresent()){
                    return convertedPath;
                }
            } else if(valueEntry.getConceptMap()!=null && PathProcessor.getItemAtPath(contentItem, valueEntry.getConceptMap().getPath()).isPresent()) {
            	return convertConceptMap(contentItem,valueEntry.getConceptMap());
            }
        }
        return Optional.empty();
    }

    private String getValueEntries(ValueEntry[] valueEntries) {
        String s = "";
        for(ValueEntry valueEntry: valueEntries){
            if(valueEntry.getPath()!=null){
                s += valueEntry.getPath();
            }else{
                s += valueEntry.getCode();
            }
        }
        return s;
    }

    protected Optional<T> convertPath(Locatable contentItem, String path){
        return PathProcessor.getItemAtPath(contentItem, path)
                .flatMap(this::convertPathItem);
    }

    private Optional<T> convertPathItem(Object itemAtPath) {
        if (itemAtPath.getClass() == Element.class && (((Element) itemAtPath).getValue() != null)) {
            return convertDvValue(((Element) itemAtPath).getValue());
        }else if(DataValue.class.isAssignableFrom(itemAtPath.getClass())){
            return convertDvValue((DataValue) itemAtPath);
        }
        return Optional.empty();
    }

    private Optional<T> convertConceptMap(Locatable itemAtPath, ConceptMap conceptMap) {
    	Optional<T> elementAtPath = convertPath(itemAtPath, conceptMap.getPath());
    	if(elementAtPath.isPresent() && elementAtPath.get() instanceof Element element) {
    		if (element.getValue() instanceof DvCodedText dvCodedText) {
    			if(dvCodedText.getDefiningCode().codeStringValid())
    			{
    				T value = (T) conceptMap.getMappings().get(dvCodedText.getDefiningCode().getCodeString());
    				return Optional.ofNullable(value);
    			}
    			else return Optional.empty();

    		}
    		//TODO: check if DVOrdinal is a real possibility

    	}
 
        return Optional.empty();
    }

     protected abstract Optional<T> convertDvValue(DataValue value);

    protected abstract Optional<T> convertCode(Long code);

}
