package com.nuena.order;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.nuena.lantone.entity.MedicalRecord;
import com.nuena.lantone.entity.MedicalRecordContent;
import com.nuena.lantone.entity.QcMode;
import com.nuena.lantone.entity.QcModelHospital;
import com.nuena.lantone.entity.QcModuleInfo;
import com.nuena.lantone.entity.RecordAnalyze;
import com.nuena.lantone.entity.RecordAnalyzeDetail;
import com.nuena.lantone.entity.RecordModule;
import com.nuena.lantone.service.impl.MedicalRecordContentServiceImpl;
import com.nuena.lantone.service.impl.MedicalRecordServiceImpl;
import com.nuena.lantone.service.impl.QcModeServiceImpl;
import com.nuena.lantone.service.impl.QcModelHospitalServiceImpl;
import com.nuena.lantone.service.impl.QcModuleInfoServiceImpl;
import com.nuena.lantone.service.impl.RecordAnalyzeDetailServiceImpl;
import com.nuena.lantone.service.impl.RecordAnalyzeServiceImpl;
import com.nuena.lantone.service.impl.RecordModuleServiceImpl;
import com.nuena.util.EncrypDES;
import com.nuena.util.ExcelUtil;
import com.nuena.util.FastJsonUtils;
import com.nuena.util.ListUtil;
import com.nuena.util.StringUtil;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
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
    @Autowired
    @Qualifier("qcModuleInfoServiceImpl")
    private QcModuleInfoServiceImpl qcModuleInfoService;
    @Autowired
    @Qualifier("recordModuleServiceImpl")
    private RecordModuleServiceImpl recordModuleService;
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
        QueryWrapper<RecordAnalyze> recordAnalyzeQe = new QueryWrapper<>();
        recordAnalyzeQe.eq("is_deleted", "N");
        recordAnalyzeQe.eq("hospital_id", 3l);
        recordAnalyzeQe.eq("mode_id", modelId);
        recordAnalyzeQe.eq("rec_type", recTitle);
        int alreadyExistsDataCount = recordAnalyzeService.count(recordAnalyzeQe);
        if (alreadyExistsDataCount > 0) {
            return;
        }

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
            recordAnalyze.setMapKeys(keys.stream().collect(Collectors.joining(",")));
            recordAnalyzeList.add(recordAnalyze);
            type++;
        }
        recordAnalyzeService.saveBatch(recordAnalyzeList);

        Map<Set<String>, Long> keysRecordAnalyzeIdMap = recordAnalyzeList
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

    public void createKeysDataExcel() {
        QueryWrapper<RecordAnalyze> recordAnalyzeQe = new QueryWrapper<>();
        recordAnalyzeQe.eq("hospital_id", 3l);
        recordAnalyzeQe.eq("is_deleted", "N");
        List<RecordAnalyze> recordAnalyzeList = recordAnalyzeService.list(recordAnalyzeQe);

        List<Long> recordAnalyzeIds = recordAnalyzeList.stream().map(i -> i.getId()).collect(Collectors.toList());
        QueryWrapper<RecordAnalyzeDetail> recordAnalyzeDetailQe = new QueryWrapper<>();
        recordAnalyzeDetailQe.in("record_analyze_id", recordAnalyzeIds);
        List<RecordAnalyzeDetail> recordAnalyzeDetailList = recordAnalyzeDetailService.list(recordAnalyzeDetailQe);

        Map<Long, List<String>> recordAnalyzeIdMapKeysMap = recordAnalyzeDetailList.stream().collect(Collectors.groupingBy(RecordAnalyzeDetail::getRecordAnalyzeId, Collectors.mapping(RecordAnalyzeDetail::getMapKey, Collectors.toList())));
        Map<String, List<Long>> modeNameRecordAnalyzeIdsMap = recordAnalyzeList.stream().collect(Collectors.groupingBy(RecordAnalyze::getModeName, Collectors.mapping(RecordAnalyze::getId, Collectors.toList())));
        Map<String, List<Map<String, String>>> modeNameMapKeyMapsMap = Maps.newHashMap();

        modeNameRecordAnalyzeIdsMap.keySet().forEach(modeName -> {
            List<String> mapKeys = Lists.newArrayList();
            List<Long> raIds = modeNameRecordAnalyzeIdsMap.get(modeName);
            raIds.forEach(raId -> {
                mapKeys.addAll(recordAnalyzeIdMapKeysMap.get(raId));
            });
            List<Map<String, String>> mapKeyMaps = Lists.newArrayList();
            mapKeys.stream().distinct().forEach(mapKey -> {
                Map<String, String> mapKeyMap = Maps.newHashMap();
                mapKeyMap.put("content", mapKey);
                mapKeyMap.put("content_", "\"" + mapKey + "=\",");
                mapKeyMaps.add(mapKeyMap);
            });
            modeNameMapKeyMapsMap.put(modeName, mapKeyMaps);
        });

        String[] headerNames = { "字段名称", "xml转化字段名称" };
        String[] dataMapKeys = { "content", "content_" };
        for (String modeName : modeNameMapKeyMapsMap.keySet()) {
            ExcelUtil.createExcel(false, false, "C:\\Users\\Administrator\\Desktop", "taizhou-keys", modeName.replaceAll("/", ""), headerNames, dataMapKeys, modeNameMapKeyMapsMap.get(modeName));
        }
    }

    public String getModeMappingInfo() {
        String ret = null;
        QueryWrapper<QcModuleInfo> qcModuleInfoQe = new QueryWrapper<>();
        qcModuleInfoQe.eq("is_deleted", "N");
        qcModuleInfoQe.eq("hospital_id", 3l);
        qcModuleInfoQe.isNotNull("mode_id");
        qcModuleInfoQe.isNotNull("record_module_id");
        qcModuleInfoQe.select("id", "record_module_id");
        List<QcModuleInfo> qcModuleInfoList = qcModuleInfoService.list(qcModuleInfoQe);
        if (ListUtil.isEmpty(qcModuleInfoList)) {
            return ret;
        }

        Map<Long, Long> recordModuleIdModuleInfoIdMap = qcModuleInfoList.stream().collect(Collectors.toMap(QcModuleInfo::getRecordModuleId, i -> i.getId()));
        List<Long> recordModuleIds = qcModuleInfoList.stream().map(i -> i.getRecordModuleId()).collect(Collectors.toList());
        List<RecordModule> recordModuleList = (List) recordModuleService.listByIds(recordModuleIds);

        List<String> recordAnalyzeNames = Lists.newArrayList();
        for (RecordModule recordModule : recordModuleList) {
            recordAnalyzeNames.addAll(Arrays.asList(recordModule.getWithRecordAnalyzeNames().split(",")));
        }

        QueryWrapper<RecordAnalyze> recordAnalyzeQe = new QueryWrapper<>();
        recordAnalyzeQe.eq("is_deleted", "N");
        recordAnalyzeQe.eq("hospital_id", 3l);
        recordAnalyzeQe.in("name", recordAnalyzeNames);
        recordAnalyzeQe.select("name", "map_keys");
        List<RecordAnalyze> recordAnalyzeList = recordAnalyzeService.list(recordAnalyzeQe);
        Map<String, String> recordAnalyzeNameMapKeysMap = recordAnalyzeList.stream().collect(Collectors.toMap(RecordAnalyze::getName, RecordAnalyze::getMapKeys));

        Map<String, Long> mapKeysModuleInfoIdMap = Maps.newHashMap();
        for (RecordModule recordModule : recordModuleList) {
            for (String recordAnalyzeName : recordModule.getWithRecordAnalyzeNames().split(",")) {
                mapKeysModuleInfoIdMap.put(recordAnalyzeNameMapKeysMap.get(recordAnalyzeName), recordModuleIdModuleInfoIdMap.get(recordModule.getId()));
            }
        }
        ret = FastJsonUtils.getBeanToJson(mapKeysModuleInfoIdMap);
        //        FileUtil.fileWrite("C:\\Users\\Administrator\\Desktop", "module_mapping.json", ret);
        return ret;
    }

}