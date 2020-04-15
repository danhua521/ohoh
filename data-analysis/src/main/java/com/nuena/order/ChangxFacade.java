package com.nuena.order;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.nuena.huazo.entity.BrDoctadvice;
import com.nuena.huazo.entity.BrRecdiagnose;
import com.nuena.huazo.entity.BrRecoperation;
import com.nuena.huazo.entity.MrMedicalrecords;
import com.nuena.huazo.entity.MrMrcontent;
import com.nuena.huazo.service.impl.BrDoctadviceServiceImpl;
import com.nuena.huazo.service.impl.BrRecdiagnoseServiceImpl;
import com.nuena.huazo.service.impl.BrRechomeServiceImpl;
import com.nuena.huazo.service.impl.BrRecoperationServiceImpl;
import com.nuena.huazo.service.impl.MrMedicalrecordsServiceImpl;
import com.nuena.huazo.service.impl.MrMrcontentServiceImpl;
import com.nuena.lantone.entity.DoctorAdvice;
import com.nuena.lantone.entity.HomeDiagnoseInfo;
import com.nuena.lantone.entity.HomeOperationInfo;
import com.nuena.lantone.entity.MedicalRecordContent;
import com.nuena.lantone.service.impl.DoctorAdviceServiceImpl;
import com.nuena.lantone.service.impl.HomeDiagnoseInfoServiceImpl;
import com.nuena.lantone.service.impl.HomeOperationInfoServiceImpl;
import com.nuena.lantone.service.impl.HomePageServiceImpl;
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

    @Autowired
    @Qualifier("brDoctadviceServiceImpl")
    private BrDoctadviceServiceImpl brDoctadviceService;
    @Autowired
    @Qualifier("brRechomeServiceImpl")
    private BrRechomeServiceImpl brRechomeService;
    @Autowired
    @Qualifier("brRecdiagnoseServiceImpl")
    private BrRecdiagnoseServiceImpl brRecdiagnoseService;
    @Autowired
    @Qualifier("brRecoperationServiceImpl")
    private BrRecoperationServiceImpl brRecoperationService;

    @Autowired
    @Qualifier("doctorAdviceServiceImpl")
    private DoctorAdviceServiceImpl doctorAdviceService;
    @Autowired
    @Qualifier("homePageServiceImpl")
    private HomePageServiceImpl homePageService;
    @Autowired
    @Qualifier("homeOperationInfoServiceImpl")
    private HomeOperationInfoServiceImpl homeOperationInfoService;
    @Autowired
    @Qualifier("homeDiagnoseInfoServiceImpl")
    private HomeDiagnoseInfoServiceImpl homeDiagnoseInfoService;


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

    @Transactional(transactionManager = "db1TransactionManager")
    public void dataTransYiZhu() {
        QueryWrapper<BrDoctadvice> brDoctadviceQe = new QueryWrapper<>();
        brDoctadviceQe.in("BRYZID", Arrays.asList("ZY010000658833", "ZY010000656373"));
        List<BrDoctadvice> brDoctadviceList = brDoctadviceService.list(brDoctadviceQe);
        List<DoctorAdvice> doctorAdviceList = Lists.newArrayList();
        for (BrDoctadvice brDoctadvice:brDoctadviceList){
            DoctorAdvice doctorAdvice = new DoctorAdvice();
            doctorAdvice.setDoctorAdviceId(brDoctadvice.getBryzid());
            doctorAdvice.setHospitalId(1l);
            doctorAdvice.setBehospitalCode(brDoctadvice.getBrzyid());
            doctorAdvice.setOrderDoctorName(brDoctadvice.getYskdpb());
            doctorAdvice.setFrequency(brDoctadvice.getYzplpb());
            doctorAdvice.setParentTypeId(brDoctadvice.getFlyzid());
            doctorAdvice.setDoctorAdviceType(brDoctadvice.getYzlxpb());
            doctorAdvice.setUsageNum(brDoctadvice.getYcsysl());
            doctorAdvice.setUsageUnit(brDoctadvice.getYcyldw());
            doctorAdvice.setDose(brDoctadvice.getYzdcjl());
            doctorAdvice.setDoseUnit(brDoctadvice.getDcjldw());
            doctorAdvice.setMedModeType(brDoctadvice.getGyfsid());
            doctorAdvice.setDaDealType(brDoctadvice.getYzcllx());
            doctorAdvice.setDaStartDate(brDoctadvice.getYzkssj());
            doctorAdvice.setDaItemName(brDoctadvice.getYzxmmc());
            doctorAdvice.setDaStatus(brDoctadvice.getYzztpb());
            doctorAdvice.setDaStopDate(brDoctadvice.getYzjssj());
            doctorAdvice.setDaGroupNo(brDoctadvice.getYztzxh());
            doctorAdvice.setDaPrescriptionType(brDoctadvice.getYzcflx());
            doctorAdvice.setDaMedType(brDoctadvice.getYzlylx());
            doctorAdvice.setDoctorNotice(brDoctadvice.getYsztsm());
            doctorAdvice.setDoctorId(brDoctadvice.getKdysid());
            doctorAdvice.setDoctorName(brDoctadvice.getKdysmc());

            doctorAdviceList.add(doctorAdvice);
        }
        doctorAdviceService.saveBatch(doctorAdviceList);
    }

    @Transactional(transactionManager = "db1TransactionManager")
    public void dataTransBasyshzd() {
        QueryWrapper<BrRecdiagnose> brRecdiagnoseQe = new QueryWrapper<>();
        brRecdiagnoseQe.in("BASYID",Arrays.asList("ZY010000658833", "ZY010000656373"));
        List<BrRecdiagnose> brRecdiagnoseList = brRecdiagnoseService.list(brRecdiagnoseQe);
        List<HomeDiagnoseInfo> homeDiagnoseInfoList = Lists.newArrayList();
        brRecdiagnoseList.forEach(brRecdiagnose -> {
            HomeDiagnoseInfo homeDiagnoseInfo = new HomeDiagnoseInfo();
            homeDiagnoseInfo.setHomePageId(brRecdiagnose.getBasyid());
            homeDiagnoseInfo.setHospitalId(1l);
            homeDiagnoseInfo.setDiagnoseOrderNo(brRecdiagnose.getBazdxh());
            homeDiagnoseInfo.setDiagnoseType(brRecdiagnose.getZdlbdm());
            homeDiagnoseInfo.setDiagnoseTypeShort(brRecdiagnose.getZczdpb());
            homeDiagnoseInfo.setDiagnoseName(brRecdiagnose.getZdjbmc());
            homeDiagnoseInfo.setBehospitalType(brRecdiagnose.getRyqkbm());
            homeDiagnoseInfo.setLeaveHospitalType(brRecdiagnose.getZgqkdm());
            homeDiagnoseInfo.setPathologyDiagnose(brRecdiagnose.getBlzdbh());
            homeDiagnoseInfo.setIcdCode(brRecdiagnose.getIcdm());
            homeDiagnoseInfoList.add(homeDiagnoseInfo);
        });
        homeDiagnoseInfoService.saveBatch(homeDiagnoseInfoList);

        QueryWrapper<BrRecoperation> brRecoperationQe = new QueryWrapper<>();
        brRecoperationQe.in("BASYID",Arrays.asList("ZY010000658833", "ZY010000656373"));
        List<BrRecoperation> brRecoperationList = brRecoperationService.list(brRecoperationQe);
        List<HomeOperationInfo> homeOperationInfoList = Lists.newArrayList();
        brRecoperationList.forEach(brRecoperation -> {
            HomeOperationInfo homeOperationInfo = new HomeOperationInfo();
            homeOperationInfo.setHomePageId(brRecoperation.getBasyid());
            homeOperationInfo.setHospitalId(1l);
            homeOperationInfo.setOperationOrderNo(brRecoperation.getBrssxh());
            homeOperationInfo.setOperationDate(brRecoperation.getBrssrq());
            homeOperationInfo.setOperationCode(brRecoperation.getSsdmid());
            homeOperationInfo.setOperationDoctorId(brRecoperation.getSsysmc());
            homeOperationInfo.setFirstAssistantId(brRecoperation.getYzhsmc());
            homeOperationInfo.setSecondAssistantId(brRecoperation.getEzhsmc());
            homeOperationInfo.setCutLevel(brRecoperation.getQkdjdm());
            homeOperationInfo.setHealingLevel(brRecoperation.getYhdjdm());
            homeOperationInfo.setOperationName(brRecoperation.getBrssmc());
            homeOperationInfo.setOperationLevel(brRecoperation.getSsjbid());
            homeOperationInfo.setAnaesthesiaName(brRecoperation.getMzffmc());
            homeOperationInfo.setShamOperationName(brRecoperation.getNssmc());
            homeOperationInfoList.add(homeOperationInfo);
        });
        homeOperationInfoService.saveBatch(homeOperationInfoList);
    }

}