package org.bih.eos.converter.composition;

import org.bih.eos.converter.cdt.DefaultConverterServices;
import org.bih.eos.converter.cdt.converter.CDTConverter;
import org.bih.eos.jpabase.model.entity.JPABaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import java.util.HashMap;
import java.util.List;

public abstract class CompositionToEntityConverter<AC extends CDTConverter, B> implements Converter<B, List<JPABaseEntity>> {

    private static final Logger LOG = LoggerFactory.getLogger(CompositionToEntityConverter.class);
     final HashMap<String, List<AC>> conversionMap;
     final DefaultConverterServices defaultConverterServices;

    public CompositionToEntityConverter(HashMap<String, List<AC>> conversionMap, DefaultConverterServices defaultConverterServices) {
        this.conversionMap = conversionMap;
        this.defaultConverterServices = defaultConverterServices;
    }

    boolean converterExists(String archetypeId) {
        return conversionMap.containsKey(archetypeId) && conversionMap.get(archetypeId).size()>0;
    }
}