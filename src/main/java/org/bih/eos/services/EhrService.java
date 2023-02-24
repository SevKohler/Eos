package org.bih.eos.services;

import com.nedap.archie.rm.composition.Composition;
import org.bih.eos.controller.dao.Ehrs;
import org.bih.eos.jpabase.dba.service.EHRToPersonService;
import org.bih.eos.jpabase.model.entity.EHRToPerson;
import org.bih.eos.jpabase.model.entity.JPABaseEntity;
import org.bih.eos.services.dao.ConvertableComposition;
import org.bih.eos.services.dao.ConversionResponse;
import org.ehrbase.client.aql.query.NativeQuery;
import org.ehrbase.client.aql.query.Query;
import org.ehrbase.client.aql.record.Record1;
import org.ehrbase.client.openehrclient.OpenEhrClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EhrService {

    private final EHRToPersonService ehrToPersonService;
    private final OpenEhrClient openEhrClient;
    private final ConverterService converterService;
    private ConversionResponse output;

    public EhrService(EHRToPersonService ehrToPersonService, OpenEhrClient openEhrClient, ConverterService converterService) {
        this.ehrToPersonService = ehrToPersonService;
        this.openEhrClient = openEhrClient;
        this.converterService = converterService;
    }

    public String convertAll() {
        output = new ConversionResponse();
        List<EHRToPerson> ehrToPersonList = ehrToPersonService.findAll();
        for (EHRToPerson ehrToPerson : ehrToPersonList) {
            convertEhrContent(ehrToPerson);
        }
        return output.getJson();
    }

    public String convertEhrList(Ehrs ehrList) {
        output = new ConversionResponse();
        for (String ehrId : ehrList.getEhrIds()) {
            Optional<EHRToPerson> ehrToPersonOptional = ehrToPersonService.findByEhrId(ehrId);
            ehrToPersonOptional.ifPresent(this::convertEhrContent);
        }
        return output.getJson();
    }

    private void convertEhrContent(EHRToPerson ehrToPerson) {
        loadCompositions(ehrToPerson);
    }

    public void loadCompositions(EHRToPerson ehrToPerson) {
        List<Record1<Composition>> result = executeAqlQuery(0, ehrToPerson.getEhrId());
        convert(result, ehrToPerson);
        batchLoad(result, ehrToPerson, 0);
    }

    private void batchLoad(List<Record1<Composition>> result, EHRToPerson ehrToPerson, int offset) {
        if (result.size() == 50) {
            offset += 50;
            List<Record1<Composition>> resultNew = executeAqlQuery(offset, ehrToPerson.getEhrId());
            convert(resultNew, ehrToPerson);
            batchLoad(resultNew, ehrToPerson, offset);
        }
    }

    private void convert(List<Record1<Composition>> resultNew, EHRToPerson ehrToPerson) {
        for (Record1<Composition> compositionRecord1 : resultNew) {
            if (compositionRecord1.value1() != null) {
                List<JPABaseEntity> converterResult = converterService.convert(new ConvertableComposition(compositionRecord1.value1(), ehrToPerson.getPerson()));
                output.increaseOneComposition(ehrToPerson.getEhrId(), converterResult.size());
            }
        }
    }

    private NativeQuery<Record1<Composition>> buildCompositionQuery(int offset, String ehrId) {
        return Query.buildNativeQuery("SELECT c from EHR e CONTAINS Composition c WHERE e/ehr_id/value = '" + ehrId + "' LIMIT 50 OFFSET " + offset + " ORDER BY e/ehr_id/value DESC", Composition.class);
    }

    private List<Record1<Composition>> executeAqlQuery(int offset, String ehrId) {
        Query<Record1<Composition>> query = buildCompositionQuery(0, ehrId);
        try {
            return openEhrClient.aqlEndpoint().execute(query);
        } catch (NullPointerException nullPointerException) {
            return new ArrayList<>();
        }
    }
}
