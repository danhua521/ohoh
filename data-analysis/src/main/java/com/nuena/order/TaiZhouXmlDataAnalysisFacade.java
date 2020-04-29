package com.nuena.order;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.nuena.lantone.entity.MedicalRecord;
import com.nuena.lantone.entity.MedicalRecordContent;
import com.nuena.lantone.entity.QcMode;
import com.nuena.lantone.entity.QcModelHospital;
import com.nuena.lantone.entity.RecordAnalyze;
import com.nuena.lantone.entity.RecordAnalyzeDetail;
import com.nuena.lantone.service.impl.MedicalRecordContentServiceImpl;
import com.nuena.lantone.service.impl.MedicalRecordServiceImpl;
import com.nuena.lantone.service.impl.QcModeServiceImpl;
import com.nuena.lantone.service.impl.QcModelHospitalServiceImpl;
import com.nuena.lantone.service.impl.RecordAnalyzeDetailServiceImpl;
import com.nuena.lantone.service.impl.RecordAnalyzeServiceImpl;
import com.nuena.util.EncrypDES;
import com.nuena.util.ListUtil;
import com.nuena.util.StringUtil;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description: 台州xml数据分析
 * @author: rengb
 * @time: 2020/4/9 15:42
 */
@Component
public class TaiZhouXmlDataAnalysisFacade {

    @Autowired
    @Qualifier("medicalRecordServiceImpl")
    private MedicalRecordServiceImpl medicalRecordService;
    @Autowired
    @Qualifier("medicalRecordContentServiceImpl")
    private MedicalRecordContentServiceImpl medicalRecordContentService;
    @Autowired
    @Qualifier("recordAnalyzeServiceImpl")
    private RecordAnalyzeServiceImpl recordAnalyzeService;
    @Autowired
    @Qualifier("recordAnalyzeDetailServiceImpl")
    private RecordAnalyzeDetailServiceImpl recordAnalyzeDetailService;
    @Autowired
    @Qualifier("qcModelHospitalServiceImpl")
    private QcModelHospitalServiceImpl qcModelHospitalService;
    @Autowired
    @Qualifier("qcModeServiceImpl")
    private QcModeServiceImpl qcModeService;
    private EncrypDES encrypDES = null;
    private Map<Long, String> modeMap = null;

    public void init() throws Exception {
        encrypDES = new EncrypDES();

        QueryWrapper<QcMode> qcModeQe = new QueryWrapper<>();
        qcModeQe.eq("is_deleted", "N");
        modeMap = qcModeService.list(qcModeQe).stream().collect(Collectors.toMap(i -> i.getId(), i -> i.getName()));
    }

    public List<String> getRecTitles(long modelId) {
        QueryWrapper<QcModelHospital> qcModelHospitalQe = new QueryWrapper<>();
        qcModelHospitalQe.eq("hospital_id", 3l);
        qcModelHospitalQe.eq("stand_model_id", modelId);
        qcModelHospitalQe.select("hospital_model_name");
        List<String> recTitles = qcModelHospitalService.list(qcModelHospitalQe)
                .stream()
                .map(i -> i.getHospitalModelName())
                .filter(i -> StringUtil.isNotBlank(i))
                .distinct()
                .collect(Collectors.toList());
        return recTitles;
    }

    @Transactional(transactionManager = "db1TransactionManager")
    public void analysisByRecTitle(long modelId, String recTitle, String nodePath) throws Exception {
        QueryWrapper<MedicalRecord> medicalRecordQe = new QueryWrapper<>();
        medicalRecordQe.eq("is_deleted", "N");
        medicalRecordQe.eq("hospital_id", 3l);
        medicalRecordQe.eq("rec_title", recTitle);
        medicalRecordQe.select("rec_id", "behospital_code");
        List<MedicalRecord> medicalRecordList = medicalRecordService.list(medicalRecordQe);
        if (ListUtil.isEmpty(medicalRecordList)) {
            return;
        }

        List<String> recIds = medicalRecordList.stream().map(i -> i.getRecId()).distinct().collect(Collectors.toList());
        Map<String, String> recIdBehospitalCodeMap = medicalRecordList.stream().collect(Collectors.toMap(MedicalRecord::getRecId, i -> i.getBehospitalCode()));

        List<MedicalRecordContent> medicalRecordContentList = Lists.newArrayList();
        int index = 0;
        QueryWrapper<MedicalRecordContent> medicalRecordContentQe = new QueryWrapper<>();
        medicalRecordContentQe.eq("is_deleted", "N");
        medicalRecordContentQe.eq("hospital_id", 3l);
        medicalRecordQe.select("rec_id", "xml_text");
        while (index <= recIds.size() - 1) {
            if (index + 1000 > recIds.size() - 1) {
                medicalRecordContentQe.in("rec_id", recIds.subList(index, recIds.size()));
            } else {
                medicalRecordContentQe.in("rec_id", recIds.subList(index, index + 1000));
            }
            medicalRecordContentList.addAll(medicalRecordContentService.list(medicalRecordContentQe));
            if (index + 1000 > recIds.size() - 1) {
                index = recIds.size();
            } else {
                index = index + 1000;
            }
        }
        if (ListUtil.isEmpty(medicalRecordContentList)) {
            return;
        }

        List<Map.Entry<Set<String>, String>> keysBehospitalCodeEntryList = Lists.newArrayList();
        String xmlText, behospitalCode;
        for (MedicalRecordContent medicalRecordContent : medicalRecordContentList) {
            xmlText = medicalRecordContent.getXmlText();
            behospitalCode = recIdBehospitalCodeMap.get(medicalRecordContent.getRecId());
            if (StringUtil.isBlank(xmlText) || StringUtil.isBlank(behospitalCode)) {
                continue;
            }
            keysBehospitalCodeEntryList.add(
                    Maps.immutableEntry(
                            getKeys(xmlText, nodePath),
                            behospitalCode
                    )
            );
        }
        if (ListUtil.isEmpty(keysBehospitalCodeEntryList)) {
            return;
        }
        Map<Set<String>, String> keysBehospitalCodesMap = keysBehospitalCodeEntryList.stream().collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.mapping(Map.Entry::getValue, Collectors.joining(","))));

        List<RecordAnalyze> recordAnalyzeList = Lists.newArrayList();
        List<RecordAnalyzeDetail> recordAnalyzeDetailList = Lists.newArrayList();
        int type = 1;
        for (Set<String> keys : keysBehospitalCodesMap.keySet()) {
            String behospitalCodes = keysBehospitalCodesMap.get(keys);
            RecordAnalyze recordAnalyze = new RecordAnalyze();
            recordAnalyze.setName(modeMap.get(modelId) + "-" + recTitle + "-" + type);
            recordAnalyze.setHospitalId(3l);
            recordAnalyze.setModeId(modelId);
            recordAnalyze.setModeName(modeMap.get(modelId));
            recordAnalyze.setRecType(recTitle);
            recordAnalyze.setBehospitalCodes(behospitalCodes);
            recordAnalyze.setMapType(type + "");
            recordAnalyze.setKeys(keys.stream().collect(Collectors.joining(",")));
            recordAnalyzeList.add(recordAnalyze);
            type++;
        }
        recordAnalyzeService.saveBatch(recordAnalyzeList);

        Map<Set<String>, Long> keysRecordAnalyzeIdMap = recordAnalyzeList
                .stream()
                .collect(
                        Collectors.toMap(
                                i -> Sets.newHashSet(i.getKeys().split(",")),
                                i -> i.getId()
                        )
                );

        Long recordAnalyzeId = null;
        for (Set<String> keys : keysBehospitalCodesMap.keySet()) {
            recordAnalyzeId = keysRecordAnalyzeIdMap.get(keys);
            for (String key : keys) {
                RecordAnalyzeDetail recordAnalyzeDetail = new RecordAnalyzeDetail();
                recordAnalyzeDetail.setRecordAnalyzeId(recordAnalyzeId);
                recordAnalyzeDetail.setMapKey(key);
                recordAnalyzeDetailList.add(recordAnalyzeDetail);
            }
        }
        recordAnalyzeDetailService.saveBatch(recordAnalyzeDetailList);
    }

    private Set<String> getKeys(String xml, String nodePath) throws Exception {
        Set<String> keys = Sets.newHashSet();
        Document doc = DocumentHelper.parseText(encrypDES.decryptor(xml));
        Element rootElement = (Element) doc.selectNodes(nodePath).get(0);
        List<Element> sonElements = rootElement.elements();
        String ename;
        for (Element sonElement : sonElements) {
            ename = sonElement.attributeValue("ename");
            if (StringUtil.isNotBlank(ename)) {
                keys.add(ename);
            }
        }
        return keys;
    }

}