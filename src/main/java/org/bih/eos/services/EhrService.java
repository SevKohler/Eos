package org.bih.eos.services;

import com.nedap.archie.rm.composition.Composition;
import org.bih.eos.controller.dao.Ehrs;
import org.bih.eos.jpabase.service.EHRToPersonService;
import org.bih.eos.jpabase.entity.EHRToPerson;
import org.bih.eos.jpabase.entity.JPABaseEntity;
import org.bih.eos.services.dao.ConvertableComposition;
import org.bih.eos.services.dao.ConversionResponse;
import org.ehrbase.client.aql.query.NativeQuery;
import org.ehrbase.client.aql.query.Query;
import org.ehrbase.client.aql.record.Record2;
import org.ehrbase.client.openehrclient.OpenEhrClient;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class EhrService {
    private final long offsetLimit = 10000;
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(EhrService.class);
    private final EHRToPersonService ehrToPersonService;
    private final OpenEhrClient openEhrClient;
    private final ConverterService converterService;
    private ConversionResponse output;
    int ehrBatchAmount = 40;

    public EhrService(EHRToPersonService ehrToPersonService, OpenEhrClient openEhrClient, ConverterService converterService) {
        this.ehrToPersonService = ehrToPersonService;
        this.openEhrClient = openEhrClient;
        this.converterService = converterService;
    }

    public String convertAll() {
        List<EHRToPerson> ehrToPersonList = ehrToPersonService.findAll();
        return convertList(ehrToPersonList);
    }

    public String convertSpecific(Ehrs ehrList) {
        List<EHRToPerson> ehrToPersonList = new ArrayList<>();
        for (String ehrId : ehrList.getEhrIds()) {
            Optional<EHRToPerson> ehrToPersonOptional = ehrToPersonService.findByEhrId(ehrId);
            ehrToPersonOptional.ifPresent(ehrToPersonList::add);
        }
        return convertList(ehrToPersonList);
    }

    public String convertList(List<EHRToPerson> ehrToPersonList){
        output = new ConversionResponse();
        List<EHRToPerson> ehrToPersonBatch = new ArrayList<>();
        for (EHRToPerson ehrToPerson : ehrToPersonList) {
            ehrToPersonBatch.add(ehrToPerson);
            if (ehrToPersonBatch.size() == ehrBatchAmount) {
                loadCompositions(ehrToPersonBatch);
                ehrToPersonBatch = new ArrayList<>();
            }
        }
        if (ehrToPersonBatch.size() != 0) {
            loadCompositions(ehrToPersonBatch);
        }
        converterService.convertLastBatch();
        return output.getJson();
    }

    public void loadCompositions(List<EHRToPerson> ehrToPersonList) {
        List<Record2<String, Composition>> queryResult = executeAqlQuery(0, ehrToPersonList);
        HashMap<String, List<Composition>> sortedEhrCompositions = prepareQueryOutput(queryResult);
        for (EHRToPerson ehrToPerson: ehrToPersonList){
            if(sortedEhrCompositions.containsKey(ehrToPerson.getEhrId())){
                convert(sortedEhrCompositions.get(ehrToPerson.getEhrId()), ehrToPerson);
                batchLoad(queryResult, ehrToPersonList, 0);
            }
        }
    }

    private HashMap<String, List<Composition>> prepareQueryOutput(List<Record2<String, Composition>> result){
        HashMap<String, List<Composition>> sortedEhrCompositions = new HashMap<>();
        for (Record2<String, Composition> queryResult : result) {
            if (!sortedEhrCompositions.containsKey(queryResult.value1())) {
                List<Composition> compositions = new ArrayList<>();
                compositions.add(queryResult.value2());
                sortedEhrCompositions.put(queryResult.value1(), compositions);
            } else {
                sortedEhrCompositions.get(queryResult.value1()).add(queryResult.value2());
            }
        }
        return sortedEhrCompositions;
    }

    private void batchLoad(List<Record2<String, Composition>> queryResultOld, List<EHRToPerson> ehrToPersonList, long offset) {
        if (queryResultOld.size() == offsetLimit) {
            offset += offsetLimit;
            List<Record2<String, Composition>> queryResultNew = executeAqlQuery(offset, ehrToPersonList);
            HashMap<String, List<Composition>> sortedEhrCompositions = prepareQueryOutput(queryResultNew);
            for (EHRToPerson ehrToPerson: ehrToPersonList){
                if(sortedEhrCompositions.containsKey(ehrToPerson.getEhrId())){
                    convert(sortedEhrCompositions.get(ehrToPerson.getEhrId()), ehrToPerson);
                    batchLoad(queryResultNew, ehrToPersonList, offset);
                }
            }
        }
    }

    public void convert(List<Composition> compositionList, EHRToPerson ehrToPerson) {
        for (Composition composition : compositionList) {
            if (composition != null) {
                List<JPABaseEntity> converterResult = converterService.convertBatch(new ConvertableComposition(composition, ehrToPerson.getPerson()));
                output.increaseOneComposition(ehrToPerson.getEhrId(), converterResult.size());
            }
        }
        LOG.info("Current status of the mappings: " + output.getJson());
    }

    private NativeQuery<Record2<String, Composition>> buildCompositionQueryEhr(long offset, List<EHRToPerson> ehrToPersonList) {
        StringBuilder whereStatements = new StringBuilder();
        for (EHRToPerson ehrToPerson : ehrToPersonList) {
            whereStatements.append(" e/ehr_id/value = '").append(ehrToPerson.getEhrId()).append("' OR");
        }
        whereStatements = new StringBuilder(whereStatements.substring(0, whereStatements.length() - 2));
        return Query.buildNativeQuery("SELECT e/ehr_id/value, c from EHR e CONTAINS Composition c WHERE " + whereStatements + " LIMIT " + offsetLimit + " OFFSET " + offset + " ORDER BY e/ehr_id/value DESC", String.class, Composition.class);
    }

    private List<Record2<String, Composition>> executeAqlQuery(long offset, List<EHRToPerson> ehrIds) {
        NativeQuery<Record2<String, Composition>> query = buildCompositionQueryEhr(offset, ehrIds);
        try {
            return openEhrClient.aqlEndpoint().execute(query);
        } catch (NullPointerException nullPointerException) {
            return new ArrayList<>();
        }
    }
}

//['ca1e0191-bcba-4570-affb-b6f176a870d4', '77f6774b-c8aa-4826-8415-afcb25bc4001']
//---------Creating Compositions-------------
//Loading Composition files ...
//Compositions uploaded: 1