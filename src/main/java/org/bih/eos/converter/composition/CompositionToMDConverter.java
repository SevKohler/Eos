package org.bih.eos.converter.composition;

import com.nedap.archie.rm.composition.Composition;
import com.nedap.archie.rm.composition.ContentItem;
import com.nedap.archie.rm.composition.Section;
import org.bih.eos.converter.cdt.converter.CDTConverter;
import org.bih.eos.converter.cdt.DefaultConverterServices;
import org.bih.eos.converter.cdt.converter.custom.CustomCDTConverter;
import org.bih.eos.converter.cdt.converter.configurable.generic.CDTMedicalDataConverter;
import org.bih.eos.converter.cdt.converter.nonconfigurable.VisitOccurrenceConverter;
import org.bih.eos.converter.dao.ConversionTrack;
import org.bih.eos.jpabase.model.entity.JPABaseEntity;
import org.bih.eos.jpabase.model.entity.Person;
import org.bih.eos.jpabase.model.entity.VisitOccurrence;
import org.bih.eos.services.dao.ConvertableComposition;
import org.bih.eos.converter.dao.ConvertableContentItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

import java.util.*;

public class CompositionToMDConverter extends CompositionToEntityConverter<CDTConverter, ConvertableComposition> {

    private static final Logger LOG = LoggerFactory.getLogger(CompositionToMDConverter.class);

    public CompositionToMDConverter(HashMap<String, List<CDTConverter>> conversionMap, DefaultConverterServices defaultConverterServices) {
        super(conversionMap, defaultConverterServices);
    }

    //Actually i should iterate the entire composition in order to find it instead of specific nesting since it could be linked else where.
    //TODO which seems hard since i need to check all types
    @Override
    public List<JPABaseEntity> convert(@NonNull ConvertableComposition convertableComposition) {
        List<ConversionTrack> conversionTracker = new ArrayList<>();
        List<JPABaseEntity> conversionResults = new ArrayList<>(runCDTConverters(conversionTracker, convertableComposition));
        conversionResults = runCustomConverters(convertableComposition, conversionTracker, conversionResults);
        return conversionResults;
    }

    private List<JPABaseEntity> runCDTConverters(List<ConversionTrack> conversionTracker, ConvertableComposition convertableComposition) {
        List<JPABaseEntity> cdtConverterResults = new ArrayList<>();
        Optional<VisitOccurrence> visitOccurrence = convertVisitOccurrence(convertableComposition.getComposition(), convertableComposition.getPerson());
        CdtExecutionParameterMedData cdtExecutionParameters = new CdtExecutionParameterMedData(convertableComposition.getPerson(), visitOccurrence);
        cdtConverterResults = iterateContent(convertableComposition.getComposition().getContent(), cdtExecutionParameters, conversionTracker, cdtConverterResults);
        return cdtConverterResults;
    }

    private List<JPABaseEntity> iterateContent(List<ContentItem> contentItems, CdtExecutionParameterMedData cdtExecutionParameters, List<ConversionTrack> conversionTracker, List<JPABaseEntity> cdtConverterResults) {
        for (ContentItem contentItem : contentItems) {
            cdtExecutionParameters.setArchetypeId(contentItem.getArchetypeNodeId());
            cdtExecutionParameters.setContentItem(contentItem);
            if (contentItem.getClass().equals(Section.class)) {
                cdtConverterResults.addAll(iterateContent(((Section) contentItem).getItems(), cdtExecutionParameters, conversionTracker, cdtConverterResults));
                return cdtConverterResults;
            }
            if (converterExists(cdtExecutionParameters.getArchetypeId())) {
                cdtConverterResults.addAll(executeCDTConverter(cdtExecutionParameters, conversionTracker));
                return cdtConverterResults;
            } else {
                LOG.warn("An archetype was found that is not supported, it will be ignored. \n Nevertheless child content will be mapped if not stated otherwise. \n Archetype id: " + cdtExecutionParameters.getArchetypeId());
            }
        }
        return cdtConverterResults;
    }

    private Optional<VisitOccurrence> convertVisitOccurrence(Composition composition, Person person) {
        VisitOccurrenceConverter visitOccurrenceConverter = new VisitOccurrenceConverter(defaultConverterServices);
        Optional<JPABaseEntity> visitOccurrence = visitOccurrenceConverter.convert(composition, person);
        return visitOccurrence.map(jpaBaseEntity -> (VisitOccurrence) defaultConverterServices.getPersistenceService().create(jpaBaseEntity));
    }

    private List<JPABaseEntity> executeCDTConverter(CdtExecutionParameterMedData cdtExecutionParameters, List<ConversionTrack> conversionTracker) {
        List<JPABaseEntity> cdtConverterResults = new ArrayList<>();
        for (CDTConverter converter : conversionMap.get(cdtExecutionParameters.archetypeId)) {
            if (converter instanceof CDTMedicalDataConverter) {
                List<JPABaseEntity> currentConversionResult =
                        ((CDTMedicalDataConverter<?, ?>) converter).convert(new ConvertableContentItem(cdtExecutionParameters), (Optional<VisitOccurrence>) cdtExecutionParameters.getVisitOccurrence());
                if (currentConversionResult != null && currentConversionResult.size() != 0) {
                    conversionTracker.addAll(((CDTMedicalDataConverter<?, ?>) converter).getMappingResultTracker());
                    cdtConverterResults.addAll(currentConversionResult);
                }
            }
        }
        return cdtConverterResults;
    }

    private List<JPABaseEntity> runCustomConverters(ConvertableComposition convertableComposition, List<ConversionTrack> conversionTrackMap, List<JPABaseEntity> conversionResults) { //TODO kind of duplicate from the on for CDT mappings
        for (ContentItem contentItem : convertableComposition.getComposition().getContent()) {
            String archetypeId = contentItem.getArchetypeNodeId();
            if (converterExists(archetypeId)) {
                conversionResults = executeCustomConverters(conversionTrackMap,
                        conversionResults,
                        archetypeId,
                        contentItem,
                        convertableComposition.getPerson());
            } else {
                LOG.warn("An archetype was found that is not supported, it will be ignored. \n Nevertheless child content will be mapped if not stated otherwise. \n Archetype id: " + archetypeId);
            }
        }
        return conversionResults;
    }

    private List<JPABaseEntity> executeCustomConverters(List<ConversionTrack> conversionTrackMap, List<JPABaseEntity> conversionResults, String archetypeId, ContentItem contentItem, Person person) {
        for (CDTConverter converter : conversionMap.get(archetypeId)) {
            if (converter instanceof CustomCDTConverter) {
                List<JPABaseEntity> currentConversionResult = ((CustomCDTConverter) converter).convert(new ConvertableContentItem(contentItem, person), conversionTrackMap, conversionResults);
                if (currentConversionResult != null && currentConversionResult.size() != 0) {
                    conversionResults = currentConversionResult;
                }
            }
        }
        return conversionResults;
    }


}