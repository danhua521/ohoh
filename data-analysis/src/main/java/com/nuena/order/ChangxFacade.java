package com.nuena.order;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.nuena.huazo.entity.MrMedicalrecords;
import com.nuena.huazo.entity.MrMrcontent;
import com.nuena.huazo.service.impl.MrMedicalrecordsServiceImpl;
import com.nuena.huazo.service.impl.MrMrcontentServiceImpl;
import com.nuena.lantone.entity.MedicalRecordContent;
import com.nuena.lantone.service.impl.MedicalRecordContentServiceImpl;
import com.nuena.util.GZIPUtils;
import com.nuena.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Description:
 * @author: rengb
 * @time: 2020/4/9 15:42
 */
@Component
public class ChangxFacade {

    @Autowired
    @Qualifier("mrMrcontentServiceImpl")
    private MrMrcontentServiceImpl mrMrcontentService;
    @Autowired
    @Qualifier("mrMedicalrecordsServiceImpl")
    private MrMedicalrecordsServiceImpl mrMedicalrecordsService;
    @Autowired
    @Qualifier("medicalRecordContentServiceImpl")
    private MedicalRecordContentServiceImpl medicalRecordContentService;

    public List<MrMrcontent> getNoJmData() {
        QueryWrapper<MrMrcontent> mrMrcontentQe = new QueryWrapper<>();
        mrMrcontentQe.select("bljlid", "bljlnr");
        mrMrcontentQe.last("where XMLNR is null and ROWNUM<31");
        return mrMrcontentService.list(mrMrcontentQe);
    }

    @Transactional(transactionManager = "db2TransactionManager")
    public void noJmDataToJm(List<MrMrcontent> mrMrcontents) throws Exception {
        List<MrMrcontent> uptMrMrcontents = Lists.newArrayList();
        Pattern pattern = Pattern.compile("[\\u4E00-\\u9FA5]+");
        for (MrMrcontent mrMrcontent : mrMrcontents) {
            String xmlnr = GZIPUtils.uncompressToString(mrMrcontent.getBljlnr().getBytes(1, (int) mrMrcontent.getBljlnr().length()));
            MrMrcontent uptMrMrcontent = new MrMrcontent();
            uptMrMrcontent.setBljlid(mrMrcontent.getBljlid());
            if (pattern.matcher(xmlnr).find()) {
                uptMrMrcontent.setXmlnr(xmlnr);
            } else {
                uptMrMrcontent.setXmlnr("无法解密");
            }
            uptMrMrcontents.add(uptMrMrcontent);
        }
        if (ListUtil.isNotEmpty(uptMrMrcontents)) {
            mrMrcontentService.updateBatchById(uptMrMrcontents);
        }
        uptMrMrcontents.clear();
    }

    @Transactional(transactionManager = "db1TransactionManager")
    public void dataTrans() throws Exception {
        QueryWrapper<MrMedicalrecords> mrMedicalrecordsQe = new QueryWrapper<>();
        mrMedicalrecordsQe.in("BRZYID", Arrays.asList("ZY010000658833", "ZY010000656373"));
        List<MrMedicalrecords> mrMedicalrecordsList = mrMedicalrecordsService.list(mrMedicalrecordsQe);
        List<String> bljlIds = mrMedicalrecordsList.stream().map(i -> i.getBljlid()).collect(Collectors.toList());

        QueryWrapper<MrMrcontent> mrMrcontentQe = new QueryWrapper<>();
        mrMrcontentQe.in("BLJLID", bljlIds);
        List<MrMrcontent> mrMrcontents = mrMrcontentService.list(mrMrcontentQe);
        List<MedicalRecordContent> medicalRecordContents = Lists.newArrayList();
        Pattern pattern = Pattern.compile("[\\u4E00-\\u9FA5]+");
        for (MrMrcontent mrMrcontent : mrMrcontents) {
            MedicalRecordContent medicalRecordContent = new MedicalRecordContent();
            medicalRecordContent.setRecId(mrMrcontent.getBljlid());
            medicalRecordContent.setHospitalId(1l);
            medicalRecordContent.setContentBlob(mrMrcontent.getBljlnr());
            String xmlnr = GZIPUtils.uncompressToString(mrMrcontent.getBljlnr().getBytes(1, (int) mrMrcontent.getBljlnr().length()),"UTF-8");
            xmlnr = xmlnr.replaceAll("<Binary>[\\S\\s]*</Binary>","");
            if (pattern.matcher(xmlnr).find()) {
                medicalRecordContent.setContentText(xmlnr);
            }
            medicalRecordContents.add(medicalRecordContent);
        }
        medicalRecordContentService.saveBatch(medicalRecordContents);
    }

    public void testtt(){
        QueryWrapper<MedicalRecordContent> medicalRecordContentQe = new QueryWrapper<>();
        List<MedicalRecordContent> medicalRecordContents = medicalRecordContentService.list(medicalRecordContentQe);
        System.out.println(medicalRecordContents.get(0).getContentText());
    }

}