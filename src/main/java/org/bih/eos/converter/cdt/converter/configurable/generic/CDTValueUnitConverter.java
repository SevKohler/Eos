package org.bih.eos.converter.cdt.converter.configurable.generic;

import com.nedap.archie.rm.archetyped.Locatable;
import com.nedap.archie.rm.datastructures.Element;
import com.nedap.archie.rm.datavalues.DvCodedText;
import com.nedap.archie.rm.datavalues.quantity.DvOrdinal;

import org.bih.eos.converter.cdt.DefaultConverterServices;
import org.bih.eos.converter.cdt.conversion_entities.EntityWithSourceConcept;

import java.util.Optional;

import org.bih.eos.converter.PathProcessor;
import org.bih.eos.yaml.ConceptMap;
import org.bih.eos.yaml.ValueEntry;
import org.bih.eos.yaml.cdt_configs.CDTMappingConfig;


public abstract class CDTValueUnitConverter<E extends EntityWithSourceConcept, CDTM extends CDTMappingConfig> extends CDTUnitWithSourceConceptConverter<E, CDTM> {

	protected CDTValueUnitConverter(DefaultConverterServices defaultConverterServices, CDTM clinicalDataTableMapping) {
		super(defaultConverterServices, clinicalDataTableMapping);
	}

	protected E convertValue(Locatable contentItem, ValueEntry[] valueEntries, E entity) {
		for (ValueEntry valueEntry : valueEntries) {
			if (valueEntry.getCode() != null) {
				return convertValueCode(valueEntry.getCode(), entity);
			} else if (valueEntry.getPath() != null && PathProcessor.getItemAtPath(contentItem, valueEntry.getPath()).isPresent()) {
				return convertValuePath(contentItem, valueEntry.getPath(), entity);
			} else if(valueEntry.getConceptMap()!=null && PathProcessor.getItemAtPath(contentItem, valueEntry.getConceptMap().getPath()).isPresent()) {
				return convertConceptMap(contentItem,valueEntry.getConceptMap(), entity);
			}
		}
		return entity;
	}


	private E convertConceptMap(Locatable contentItem, ConceptMap conceptMap, E entity) {
		Optional<?> item=PathProcessor.getItemAtPath(contentItem,conceptMap.getPath());
		if (item.get() instanceof Element) {
			Element element = (Element) item.get();
			DvCodedText dvCodedText = null;
			if (element.getValue() instanceof DvCodedText) {
				dvCodedText = (DvCodedText) element.getValue();
			} else if(element.getValue() instanceof DvOrdinal) {
				DvOrdinal dvOrdinal = (DvOrdinal) element.getValue();
				if(dvOrdinal.getSymbol()!=null)
				{
					dvCodedText=dvOrdinal.getSymbol();
				}
			}
			if(dvCodedText!=null && dvCodedText.getDefiningCode().codeStringValid())
			{
				return convertValueCodeConceptMap(conceptMap.getMappings().get(dvCodedText.getDefiningCode().getCodeString()),entity);
			}
			else return null;
		}
		return null;
	}

	protected abstract E convertValuePath(Locatable contentItem, String path, E baseEntity);

	protected abstract E convertValueCode(Long code, E baseEntity);

    protected abstract E convertValueCodeConceptMap(Long code, E baseEntity);
}



