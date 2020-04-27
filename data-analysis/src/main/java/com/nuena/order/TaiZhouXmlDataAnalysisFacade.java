package com.nuena.order;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.nuena.lantone.entity.MedicalRecord;
import com.nuena.lantone.entity.MedicalRecordContent;
import com.nuena.lantone.entity.QcModelHospital;
import com.nuena.lantone.entity.RecordAnalyze;
import com.nuena.lantone.service.impl.MedicalRecordContentServiceImpl;
import com.nuena.lantone.service.impl.MedicalRecordServiceImpl;
import com.nuena.lantone.service.impl.QcModelHospitalServiceImpl;
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
    @Qualifier("qcModelHospitalServiceImpl")
    private QcModelHospitalServiceImpl qcModelHospitalService;
    private EncrypDES encrypDES = null;

    public void init() throws Exception {
        encrypDES = new EncrypDES();
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
        medicalRecordQe.select("rec_id");
        List<String> recIds = medicalRecordService.list(medicalRecordQe).stream().map(i -> i.getRecId()).distinct().collect(Collectors.toList());
        if (ListUtil.isEmpty(recIds)) {
            return;
        }

        List<String> xmlTexts = Lists.newArrayList();
        int index = 0;
        QueryWrapper<MedicalRecordContent> medicalRecordContentQe = new QueryWrapper<>();
        medicalRecordContentQe.eq("hospital_id", 3l);
        while (index <= recIds.size() - 1) {
            if (index + 1000 > recIds.size() - 1) {
                medicalRecordContentQe.in("rec_id", recIds.subList(index, recIds.size()));
            } else {
                medicalRecordContentQe.in("rec_id", recIds.subList(index, index + 1000));
            }
            xmlTexts.addAll(medicalRecordContentService.list(medicalRecordContentQe).stream().map(i -> i.getXmlText()).collect(Collectors.toList()));
            if (index + 1000 > recIds.size() - 1) {
                index = recIds.size();
            } else {
                index = index + 1000;
            }
        }
        if (ListUtil.isEmpty(xmlTexts)) {
            return;
        }

        List<Set<String>> keysList = Lists.newArrayList();
        for (String xml : xmlTexts) {
            keysList.add(getKeys(xml, nodePath));
        }

        List<Set<String>> newKeysList = keysList.stream().distinct().collect(Collectors.toList());
        List<RecordAnalyze> recordAnalyzeList = Lists.newArrayList();
        int type = 1;
        for (Set<String> newKeys : newKeysList) {
            for (String newKey : newKeys) {
                RecordAnalyze recordAnalyze = new RecordAnalyze();
                recordAnalyze.setHospitalId(3l);
                recordAnalyze.setModeId(modelId);
                recordAnalyze.setRecTitle(recTitle);
                recordAnalyze.setMapType(type + "");
                recordAnalyze.setMapKey(newKey);
                recordAnalyzeList.add(recordAnalyze);
            }
            type++;
        }
        if (ListUtil.isNotEmpty(recordAnalyzeList)) {
            recordAnalyzeService.saveBatch(recordAnalyzeList);
        }
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