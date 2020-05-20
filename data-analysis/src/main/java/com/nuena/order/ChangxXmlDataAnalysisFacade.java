package com.nuena.order;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.nuena.lantone.entity.BehospitalInfo;
import com.nuena.lantone.entity.MedicalRecord;
import com.nuena.lantone.entity.MedicalRecordContent;
import com.nuena.lantone.entity.QcMode;
import com.nuena.lantone.entity.QcModelHospital;
import com.nuena.lantone.entity.RecordAnalyze;
import com.nuena.lantone.entity.RecordAnalyzeDetail;
import com.nuena.lantone.entity.RecordModule;
import com.nuena.lantone.service.impl.BehospitalInfoServiceImpl;
import com.nuena.lantone.service.impl.MedicalRecordContentServiceImpl;
import com.nuena.lantone.service.impl.MedicalRecordServiceImpl;
import com.nuena.lantone.service.impl.QcModeServiceImpl;
import com.nuena.lantone.service.impl.QcModelHospitalServiceImpl;
import com.nuena.lantone.service.impl.QcModuleInfoServiceImpl;
import com.nuena.lantone.service.impl.RecordAnalyzeDetailServiceImpl;
import com.nuena.lantone.service.impl.RecordAnalyzeServiceImpl;
import com.nuena.lantone.service.impl.RecordModuleServiceImpl;
import com.nuena.util.EncrypDES;
import com.nuena.util.ListUtil;
import com.nuena.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Component
public class ChangxXmlDataAnalysisFacade {

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
    @Autowired
    @Qualifier("qcModuleInfoServiceImpl")
    private QcModuleInfoServiceImpl qcModuleInfoService;
    @Autowired
    @Qualifier("recordModuleServiceImpl")
    private RecordModuleServiceImpl recordModuleService;
    @Autowired
    @Qualifier("behospitalInfoServiceImpl")
    private BehospitalInfoServiceImpl behospitalInfoService;
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
        qcModelHospitalQe.eq("hospital_id", 1l);
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
        Map<Set<String>, RecordAnalyze> alreadyExistsDataMap = Maps.newHashMap();
        QueryWrapper<RecordAnalyze> recordAnalyzeQe = new QueryWrapper<>();
        recordAnalyzeQe.eq("is_deleted", "N");
        recordAnalyzeQe.eq("hospital_id", 1l);
        recordAnalyzeQe.eq("mode_id", modelId);
        recordAnalyzeQe.eq("rec_type", recTitle);
        recordAnalyzeService.list(recordAnalyzeQe).forEach(i -> {
            alreadyExistsDataMap.put(Sets.newHashSet(i.getMapKeys().split(",")), i);
        });

        QueryWrapper<MedicalRecord> medicalRecordQe = new QueryWrapper<>();
        medicalRecordQe.eq("is_deleted", "N");
        medicalRecordQe.eq("hospital_id", 1l);
        medicalRecordQe.eq("rec_title", recTitle);
        medicalRecordQe.select("rec_id", "behospital_code");
        List<MedicalRecord> medicalRecordList = medicalRecordService.list(medicalRecordQe);
        if (ListUtil.isEmpty(medicalRecordList)) {
            return;
        }

        QueryWrapper<BehospitalInfo> behospitalInfoQe = new QueryWrapper<>();
        behospitalInfoQe.in(
                "behospital_code",
                medicalRecordList.stream().map(i -> i.getBehospitalCode()).filter(i -> StringUtil.isNotBlank(i)).distinct().collect(Collectors.toList())
        );
        behospitalInfoQe.eq("hospital_id", 1l);
        Map<String, String> behospitalCodeDeptInfoMap =
                behospitalInfoService.list(behospitalInfoQe)
                        .stream()
                        .collect(Collectors.toMap(i -> i.getBehospitalCode(), i -> i.getBehospitalCode() + "(" + i.getName() + "," + i.getBehDeptName() + ")"));

        List<String> recIds = medicalRecordList.stream().map(i -> i.getRecId()).distinct().collect(Collectors.toList());
        Map<String, String> recIdBehospitalCodeMap = medicalRecordList.stream().collect(Collectors.toMap(MedicalRecord::getRecId, i -> behospitalCodeDeptInfoMap.get(i.getBehospitalCode())));

        List<MedicalRecordContent> medicalRecordContentList = Lists.newArrayList();
        int index = 0;
        QueryWrapper<MedicalRecordContent> medicalRecordContentQe = new QueryWrapper<>();
        medicalRecordContentQe.eq("is_deleted", "N");
        medicalRecordContentQe.eq("hospital_id", 1l);
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

        List<RecordAnalyze> addRecordAnalyzeList = Lists.newArrayList();
        List<RecordModule> addRecordModuleList = Lists.newArrayList();
        List<RecordAnalyze> uptRecordAnalyzeList = Lists.newArrayList();
        List<RecordAnalyzeDetail> recordAnalyzeDetailList = Lists.newArrayList();
        Map<String, String> axRnMap = Maps.newHashMap();
        int type = alreadyExistsDataMap.size() + 1;
        RecordAnalyze alreadyExistsRecordAnalyze = null;
        String recordAnalyzeName = null, behospitalCodes = null;
        for (Set<String> keys : keysBehospitalCodesMap.keySet()) {
            alreadyExistsRecordAnalyze = alreadyExistsDataMap.get(keys);
            behospitalCodes = keysBehospitalCodesMap.get(keys);
            RecordAnalyze recordAnalyze = new RecordAnalyze();
            if (alreadyExistsRecordAnalyze == null) {
                recordAnalyzeName = modeMap.get(modelId) + "-" + recTitle + "-" + type;
                recordAnalyze.setName(recordAnalyzeName);
                recordAnalyze.setHospitalId(1l);
                recordAnalyze.setModeId(modelId);
                recordAnalyze.setModeName(modeMap.get(modelId));
                recordAnalyze.setRecType(recTitle);
                recordAnalyze.setBehospitalCodes(behospitalCodes);
                recordAnalyze.setMapType(type + "");
                recordAnalyze.setMapKeys(keys.stream().collect(Collectors.joining(",")));
                addRecordAnalyzeList.add(recordAnalyze);
                type++;

                RecordModule recordModule = new RecordModule();
                recordModule.setHospitalId(1l);
                recordModule.setRecTypeDetail(recordAnalyzeName);
                recordModule.setWithRecordAnalyzeNames(recordAnalyzeName);
                recordModule.setModeId(modelId);
                recordModule.setModeName(modeMap.get(modelId));
                recordModule.setBehospitalCodes(behospitalCodes);
                addRecordModuleList.add(recordModule);
            } else {
                recordAnalyze.setId(alreadyExistsRecordAnalyze.getId());
                recordAnalyze.setBehospitalCodes(behospitalCodes);
                uptRecordAnalyzeList.add(recordAnalyze);

                axRnMap.put(alreadyExistsRecordAnalyze.getName(), behospitalCodes);
            }
        }
        if (ListUtil.isNotEmpty(addRecordAnalyzeList)) {
            recordAnalyzeService.saveBatch(addRecordAnalyzeList);
            recordModuleService.saveBatch(addRecordModuleList);

            Map<Set<String>, Long> keysRecordAnalyzeIdMap = addRecordAnalyzeList
                    .stream()
                    .collect(
                            Collectors.toMap(
                                    i -> Sets.newHashSet(i.getMapKeys().split(",")),
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
        if (ListUtil.isNotEmpty(uptRecordAnalyzeList)) {
            recordAnalyzeService.updateBatchById(uptRecordAnalyzeList);

            QueryWrapper<RecordModule> uptRecordModuleQe = new QueryWrapper<>();
            uptRecordModuleQe.eq("hospital_id", 1l);
            uptRecordModuleQe.in("rec_type_detail", axRnMap.keySet());
            List<RecordModule> uptRecordModuleList = recordModuleService.list(uptRecordModuleQe);
            uptRecordModuleList.forEach(i -> {
                i.setBehospitalCodes(axRnMap.get(i.getRecTypeDetail()));
            });
            recordModuleService.updateBatchById(uptRecordModuleList);
        }
    }

    private Set<String> getKeys(String xml, String nodePath) throws Exception {
        Set<String> keys = Sets.newHashSet();
        String helpTip, controlName, key;
        Document doc = DocumentHelper.parseText(encrypDES.decryptor(xml));
        Element rootElement = null;
        if (doc.selectNodes(nodePath).size() == 0) {
            rootElement = (Element) doc.selectNodes("//DocObjContent").get(0);
        } else {
            rootElement = (Element) doc.selectNodes(nodePath).get(0);
        }
        List<Element> sonElements = rootElement.elements();
        for (Element sonElement : sonElements) {
            helpTip = sonElement.attributeValue("HelpTip");
            controlName = sonElement.attributeValue("ControlName");
            if (StringUtil.isBlank(helpTip) && StringUtil.isBlank(controlName)) {
                continue;
            }
            if (StringUtil.equals(helpTip, controlName)) {
                key = helpTip;
            } else {
                key = (StringUtil.isBlank(helpTip) ? "" : helpTip) + "++++" + (StringUtil.isBlank(controlName) ? "" : controlName);
            }
            if (!keyAvailability(key)) {
                continue;
            }
            keys.add(key);
        }
        return keys;
    }

    /**
     * 判断key是否可用
     *
     * @param key
     * @return
     */
    public static boolean keyAvailability(String key) {
        boolean flag = true;
        String[] regexs = {
                "\\d*[+]+\\d*"
        };
        for (String regex : regexs) {
            if (key.matches(regex)) {
                flag = false;
                break;
            }
        }
        return flag;
    }

}