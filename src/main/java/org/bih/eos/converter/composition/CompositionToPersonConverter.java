package org.bih.eos.converter.composition;

import com.nedap.archie.rm.composition.Composition;
import com.nedap.archie.rm.composition.ContentItem;
import org.bih.eos.converter.cdt.DefaultConverterServices;
import org.bih.eos.converter.cdt.converter.CDTConverter;
import org.bih.eos.converter.cdt.converter.configurable.generic.CDTPersonConverter;
import org.bih.eos.converter.cdt.converter.custom.CustomCDTConverter;
import org.bih.eos.converter.dao.ConversionTrack;
import org.bih.eos.exceptions.UnprocessableEntityException;
import org.bih.eos.jpabase.model.entity.JPABaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CompositionToPersonConverter extends CompositionToEntityConverter<CDTConverter, Composition> {
    private static final Logger LOG = LoggerFactory.getLogger(CompositionToEntityConverter.class);

    public CompositionToPersonConverter(HashMap<String, List<CDTConverter>> conversionMap, DefaultConverterServices defaultConverterServices) {
        super(conversionMap, defaultConverterServices);
    }

    @Override
    public List<JPABaseEntity> convert(@NonNull Composition composition) {
        List<ConversionTrack> conversionTracker = new ArrayList<>();
        List<JPABaseEntity> conversionResults = new ArrayList<>(runCDTConverters(conversionTracker, composition));
        conversionResults = runCustomConverters(composition, conversionTracker, conversionResults);
        return conversionResults;
    }

    private List<JPABaseEntity> runCDTConverters(List<ConversionTrack> conversionTracker, Composition composition) {
        List<JPABaseEntity> cdtConverterResults = new ArrayList<>();
        CdtExecutionParameter cdtExecutionParameters = new CdtExecutionParameter();
        for (ContentItem contentItem : composition.getContent()) {
            cdtExecutionParameters.setArchetypeId(contentItem.getArchetypeNodeId());
            cdtExecutionParameters.setContentItem(contentItem);
            if (converterExists(cdtExecutionParameters.getArchetypeId())) {
                cdtConverterResults.addAll(executeCDTConverter(cdtExecutionParameters, conversionTracker));
            } else {
                LOG.warn("An archetype was found that is not supported, it will be ignored. \n Nevertheless child content will be mapped if not stated otherwise. \n Archetype id: " + cdtExecutionParameters.getArchetypeId());
            }
        }
        return cdtConverterResults;
    }

    private List<JPABaseEntity> executeCDTConverter(CdtExecutionParameter cdtExecutionParameters, List<ConversionTrack> conversionTracker) {
        List<JPABaseEntity> cdtConverterResults = new ArrayList<>();
        for (CDTConverter converter : conversionMap.get(cdtExecutionParameters.archetypeId)) {
            if (converter instanceof CDTPersonConverter) {
                List<JPABaseEntity> currentConversionResult =
                        ((CDTPersonConverter) converter).convert(cdtExecutionParameters.getContentItem());
                if (currentConversionResult != null && currentConversionResult.size() != 0) {
                    conversionTracker.addAll(((CDTPersonConverter) converter).getMappingResultTracker());
                    cdtConverterResults.addAll(currentConversionResult);
                }
            }
        }
        return cdtConverterResults;
    }

    private List<JPABaseEntity> runCustomConverters(Composition composition, List<ConversionTrack> conversionTrackMap, List<JPABaseEntity> conversionResults) { //TODO kind of duplicate from the on for CDT mappings
        for (ContentItem contentItem : composition.getContent()) {
            String archetypeId = contentItem.getArchetypeNodeId();
            if (converterExists(archetypeId)) {
                executeCustomConverters(conversionTrackMap, conversionResults, archetypeId, contentItem);
            } else {
                LOG.warn("An archetype was found that is not supported, it will be ignored. \n Nevertheless child content will be mapped if not stated otherwise. \n Archetype id: " + archetypeId);
            }
        }
        return conversionResults;
    }

    private void executeCustomConverters(List<ConversionTrack> conversionTrackMap, List<JPABaseEntity> conversionResults, String archetypeId, ContentItem contentItem) {
        for (CDTConverter converter : conversionMap.get(archetypeId)) {
            if (converter instanceof CustomCDTConverter) {
                throw new UnprocessableEntityException("CustomConverter are not supported for Person conversions");
            }
        }
    }

}
