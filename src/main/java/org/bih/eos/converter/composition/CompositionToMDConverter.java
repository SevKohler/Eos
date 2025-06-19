package org.bih.eos.converter.composition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.bih.eos.config.VisitConverterProperties;
import org.bih.eos.converter.PathProcessor;
import org.bih.eos.converter.cdt.DefaultConverterServices;
import org.bih.eos.converter.cdt.converter.CDTConverter;
import org.bih.eos.converter.cdt.converter.configurable.generic.CDTMedicalDataConverter;
import org.bih.eos.converter.cdt.converter.custom.CustomCDTConverter;
import org.bih.eos.converter.cdt.converter.nonconfigurable.VisitOccurrenceConverter;
import org.bih.eos.converter.dao.ConversionTrack;
import org.bih.eos.converter.dao.ConvertableContentItem;
import org.bih.eos.jpabase.entity.EHRToPerson;
import org.bih.eos.jpabase.entity.JPABaseEntity;
import org.bih.eos.jpabase.entity.Person;
import org.bih.eos.jpabase.entity.PersonVisit;
import org.bih.eos.jpabase.entity.VisitOccurrence;
import org.bih.eos.jpabase.jpa.dao.PersonVisitRepository;
import org.bih.eos.services.dao.ConvertableComposition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

import com.nedap.archie.rm.composition.Composition;
import com.nedap.archie.rm.composition.ContentItem;
import com.nedap.archie.rm.composition.Section;

public class CompositionToMDConverter extends CompositionToEntityConverter<CDTConverter, ConvertableComposition> {
	HashMap<String, Integer> archetypeList = new HashMap<String, Integer>();

	private final VisitConverterProperties visitProperties;
	private final PersonVisitRepository personVisitRepository;

	private static final Logger LOG = LoggerFactory.getLogger(CompositionToMDConverter.class);

	public CompositionToMDConverter(HashMap<String, List<CDTConverter>> conversionMap, DefaultConverterServices defaultConverterServices, VisitConverterProperties visitProperties, PersonVisitRepository personVisitRepository) {
		super(conversionMap, defaultConverterServices);
		this.visitProperties=visitProperties;
		this.personVisitRepository=personVisitRepository;
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
		//First try to generate the visitOccurrence from sourceId, and if that fails, just get a default one
		Optional<VisitOccurrence> visitOccurrence;
		Optional<Composition> optionalComposition=Optional.of(convertableComposition.getComposition());
		Optional<EHRToPerson> optionalEhrToPerson=Optional.of(convertableComposition.getEhrToPerson());
		visitOccurrence=getVisitFromSourceId(optionalComposition,optionalEhrToPerson);
		if(visitOccurrence.isEmpty())
		{
			visitOccurrence = convertVisitOccurrence(convertableComposition.getComposition(), convertableComposition.getPerson());
		}
		CdtExecutionParameterMedData cdtExecutionParameters = new CdtExecutionParameterMedData(convertableComposition.getPerson(), visitOccurrence);
		cdtConverterResults = iterateContent(convertableComposition.getComposition().getContent(), cdtExecutionParameters, conversionTracker, cdtConverterResults);
		LOG.info("Mapped amount of all transformed archetypes: " + archetypeList.values().stream().mapToInt(i -> i.intValue()).sum() + ", including following archetypes: " + archetypeList);
		return cdtConverterResults;
	}

	private Optional<VisitOccurrence> getVisitFromSourceId(Optional<Composition> optionalComposition, Optional<EHRToPerson> optionalEhrToPerson) {
		String templateid=visitProperties.getTemplateid();
		String visitsource=visitProperties.getVisitsource();
		String ehrid=null;

		Optional<VisitOccurrence> opVisit=Optional.empty();
		if(optionalEhrToPerson.isEmpty() || optionalEhrToPerson.get().getEhrId()==null)
		{
			LOG.warn("Warning ehr_id not found for composition");
			return Optional.empty();
		}
		ehrid=optionalEhrToPerson.get().getEhrId();
		if(StringUtils.isBlank(visitsource))
		{
			LOG.info("No path defined for visits, ignoring");
			return Optional.empty();
		}
		if(optionalComposition.isEmpty())
		{
			LOG.info("No composition, ignoring");
			return Optional.empty();
		}
		Composition composition = optionalComposition.get();
		if(!StringUtils.isBlank(templateid) &&
				composition.getArchetypeDetails()!=null &&
				composition.getArchetypeDetails().getTemplateId()!=null &&
				!templateid.equals(composition.getArchetypeDetails().getTemplateId().getValue()))
		{
			LOG.info("No template_id defined in configuration");
			return Optional.empty();
		}

		Optional<?> sourceVisitValue = PathProcessor.getItemAtPath(composition, visitsource);
		if(sourceVisitValue.isPresent() && sourceVisitValue.get() instanceof String)
		{
			Optional<PersonVisit> optionalPersonVisit=personVisitRepository.findByEhrIdAndSourceVisit(ehrid,(String)sourceVisitValue.get());
			if(optionalPersonVisit.get()!=null) 
			{
				PersonVisit personVisit= optionalPersonVisit.get();
				opVisit=Optional.ofNullable(personVisit.getVisitOccurrence());
			}

		}
		return opVisit;
	}

	private List<JPABaseEntity> iterateContent(List<ContentItem> contentItems, CdtExecutionParameterMedData cdtExecutionParameters, List<ConversionTrack> conversionTracker, List<JPABaseEntity> cdtConverterResults) {
		for (ContentItem contentItem : contentItems) {
			cdtExecutionParameters.setArchetypeId(contentItem.getArchetypeNodeId());
			cdtExecutionParameters.setContentItem(contentItem);
			if (contentItem.getClass().equals(Section.class)) {
				cdtConverterResults.addAll(iterateContent(((Section) contentItem).getItems(), cdtExecutionParameters, conversionTracker, cdtConverterResults));
			}
			if (converterExists(cdtExecutionParameters.getArchetypeId())) {
				cdtConverterResults.addAll(executeCDTConverter(cdtExecutionParameters, conversionTracker));
			} else {
				LOG.warn("An archetype was found that is not supported, it will be ignored. \n Nevertheless child content will be mapped if not stated otherwise. \n Archetype id: " + cdtExecutionParameters.getArchetypeId());
			}
		}
		return cdtConverterResults;
	}

	private void increaseArchetypeCounter(String archetypeNodeId) {
		if (archetypeList.containsKey(archetypeNodeId)) {
			archetypeList.put(archetypeNodeId, archetypeList.get(archetypeNodeId) + 1);
		} else {
			archetypeList.put(archetypeNodeId, 1);
		}
	}

	private Optional<VisitOccurrence> convertVisitOccurrence(Composition composition, Person person) {
		VisitOccurrenceConverter visitOccurrenceConverter = new VisitOccurrenceConverter(defaultConverterServices);
		Optional<JPABaseEntity> visitOccurrence = visitOccurrenceConverter.convert(composition, person);
		return visitOccurrence.map(jpaBaseEntity -> (VisitOccurrence) defaultConverterServices.getPersistenceService().create(jpaBaseEntity));
	}

	private List<JPABaseEntity> executeCDTConverter(CdtExecutionParameterMedData cdtExecutionParameters, List<ConversionTrack> conversionTracker) {
		List<JPABaseEntity> cdtConverterResults = new ArrayList<>();
		increaseArchetypeCounter(cdtExecutionParameters.archetypeId);
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