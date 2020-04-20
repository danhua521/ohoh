package com.nuena.order;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.nuena.huazo.entity.BrDoctadvice;
import com.nuena.huazo.entity.BrInpatientinfo;
import com.nuena.huazo.entity.BrRecdiagnose;
import com.nuena.huazo.entity.BrRechome;
import com.nuena.huazo.entity.BrRecoperation;
import com.nuena.huazo.entity.MrMedicalrecords;
import com.nuena.huazo.entity.MrMrcontent;
import com.nuena.huazo.service.impl.BrDoctadviceServiceImpl;
import com.nuena.huazo.service.impl.BrInpatientinfoServiceImpl;
import com.nuena.huazo.service.impl.BrRecdiagnoseServiceImpl;
import com.nuena.huazo.service.impl.BrRechomeServiceImpl;
import com.nuena.huazo.service.impl.BrRecoperationServiceImpl;
import com.nuena.huazo.service.impl.MrMedicalrecordsServiceImpl;
import com.nuena.huazo.service.impl.MrMrcontentServiceImpl;
import com.nuena.lantone.entity.BehospitalInfo;
import com.nuena.lantone.entity.DoctorAdvice;
import com.nuena.lantone.entity.HomeDiagnoseInfo;
import com.nuena.lantone.entity.HomeOperationInfo;
import com.nuena.lantone.entity.HomePage;
import com.nuena.lantone.entity.MedicalRecord;
import com.nuena.lantone.entity.MedicalRecordContent;
import com.nuena.lantone.service.impl.BehospitalInfoServiceImpl;
import com.nuena.lantone.service.impl.DoctorAdviceServiceImpl;
import com.nuena.lantone.service.impl.HomeDiagnoseInfoServiceImpl;
import com.nuena.lantone.service.impl.HomeOperationInfoServiceImpl;
import com.nuena.lantone.service.impl.HomePageServiceImpl;
import com.nuena.lantone.service.impl.MedicalRecordContentServiceImpl;
import com.nuena.lantone.service.impl.MedicalRecordServiceImpl;
import com.nuena.util.ListUtil;
import com.nuena.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description:
 * @author: rengb
 * @time: 2020/4/9 15:42
 */
@Component
public class TaiZhouFacade {

    @Autowired
    @Qualifier("brInpatientinfoServiceImpl")
    private BrInpatientinfoServiceImpl brInpatientinfoService;
    @Autowired
    @Qualifier("mrMedicalrecordsServiceImpl")
    private MrMedicalrecordsServiceImpl mrMedicalrecordsService;
    @Autowired
    @Qualifier("mrMrcontentServiceImpl")
    private MrMrcontentServiceImpl mrMrcontentService;
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
    @Qualifier("behospitalInfoServiceImpl")
    private BehospitalInfoServiceImpl behospitalInfoService;
    @Autowired
    @Qualifier("medicalRecordServiceImpl")
    private MedicalRecordServiceImpl medicalRecordService;
    @Autowired
    @Qualifier("medicalRecordContentServiceImpl")
    private MedicalRecordContentServiceImpl medicalRecordContentService;
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

    private List<String> brzyids = Lists.newArrayList(
            "366861_3",
            "257418_3",
            "619564_2",
            "629952_1",
            "628055_1",
            "641816_1",
            "641714_1",
            "521289_3",
            "271615_4",
            "361753_4",
            "236888_7",
            "424727_2",
            "640310_1",
            "619025_2",
            "635381_1",
            "579798_2",
            "373725_3",
            "432388_7",
            "468157_4",
            "595748_2",
            "639647_1",
            "633901_1",
            "637719_2",
            "639020_1",
            "632295_1",
            "252751_4",
            "639696_1",
            "644053_1",
            "641927_1",
            "639776_1",
            "215513_4",
            "499720_4",
            "519845_2",
            "526363_5",
            "538512_2",
            "627345_1",
            "615767_10",
            "629984_1",
            "634465_1",
            "636824_2",
            "642091_1",
            "644056_1",
            "627948_1",
            "210476_2",
            "639158_1",
            "386164_5",
            "431143_4",
            "456832_2",
            "563512_2",
            "575051_2",
            "587513_2",
            "624641_1",
            "627044_2",
            "627555_1",
            "627617_1",
            "627711_1",
            "634245_1",
            "314722_2",
            "377709_3",
            "532855_4",
            "539336_2",
            "628052_1",
            "628779_1",
            "628818_1",
            "640694_1",
            "631318_1",
            "631624_1",
            "631657_2",
            "636509_1",
            "636745_1"
    );

    @Transactional(transactionManager = "db1TransactionManager")
    public void dataTrans() {
        brzyidsQd();//brzyids确定，已经拉过的过滤掉
        if (ListUtil.isEmpty(brzyids)) {
            return;
        }
        dataTransZyWsNr();//拉住院信息、文书记录、文书内容
        dataTransYiZhu();//拉医嘱
        dataTransBasy();//拉病案首页
        dataTransBasyshzd();//拉病案首页的手术、病案首页的诊断
    }

    private void brzyidsQd() {
        QueryWrapper<BehospitalInfo> behospitalInfoQe = new QueryWrapper<>();
        behospitalInfoQe.in("behospital_code", brzyids);
        behospitalInfoQe.select("behospital_code");
        List<String> behospitalCodeList =
                behospitalInfoService.list(behospitalInfoQe)
                        .stream()
                        .map(i -> i.getBehospitalCode())
                        .collect(Collectors.toList());
        brzyids = brzyids.stream().filter(i -> !behospitalCodeList.contains(i)).collect(Collectors.toList());
    }

    private void dataTransZyWsNr() {
        QueryWrapper<BrInpatientinfo> brInpatientinfoQe = new QueryWrapper<>();
        brInpatientinfoQe.in("BRZYID", brzyids);
        List<BrInpatientinfo> brInpatientinfoList = brInpatientinfoService.list(brInpatientinfoQe);
        List<BehospitalInfo> behospitalInfos = Lists.newArrayList();
        brInpatientinfoList.forEach(brInpatientinfo -> {
            BehospitalInfo behospitalInfo = new BehospitalInfo();
            behospitalInfo.setBehospitalCode(brInpatientinfo.getBrzyid());
            behospitalInfo.setHospitalId(3l);
            behospitalInfo.setName(brInpatientinfo.getBrdaxm());

            if (brInpatientinfo.getBrdaxb().equals("M")) {
                behospitalInfo.setSex("男");
            } else if (brInpatientinfo.getBrdaxb().equals("F")) {
                behospitalInfo.setSex("女");
            } else {
                behospitalInfo.setSex("未知");
            }

            behospitalInfo.setBirthday(brInpatientinfo.getBrcsrq());
            behospitalInfo.setFileCode(brInpatientinfo.getBrdabh());
            behospitalInfo.setWardCode(brInpatientinfo.getZybqid());
            behospitalInfo.setWardName(brInpatientinfo.getZybqmc());
            behospitalInfo.setBehDeptId(brInpatientinfo.getZyksid());
            behospitalInfo.setBehDeptName(brInpatientinfo.getZyksmc());
            behospitalInfo.setBedCode(brInpatientinfo.getZycwid());
            behospitalInfo.setBedName(brInpatientinfo.getZycwhm());
            behospitalInfo.setInsuranceName(brInpatientinfo.getBrlbid());
            behospitalInfo.setBehospitalDate(brInpatientinfo.getBrryrq());
            behospitalInfo.setLeaveHospitalDate(brInpatientinfo.getBrcyrq());
            behospitalInfo.setDiagnoseIcd(brInpatientinfo.getJbdmid());
            behospitalInfo.setDiagnose(brInpatientinfo.getJbdmid());
            behospitalInfo.setDoctorId(brInpatientinfo.getZzysid());
            behospitalInfo.setDoctorName(brInpatientinfo.getZzysxm());
            behospitalInfos.add(behospitalInfo);
        });
        behospitalInfoService.saveBatch(behospitalInfos);

        QueryWrapper<MrMedicalrecords> mrMedicalrecordsQe = new QueryWrapper<>();
        mrMedicalrecordsQe.in("BRZYID", brzyids);
        List<MrMedicalrecords> mrMedicalrecordsList = mrMedicalrecordsService.list(mrMedicalrecordsQe);
        List<String> bljlIds = mrMedicalrecordsList.stream().map(i -> i.getBljlid()).collect(Collectors.toList());

        List<MrMrcontent> mrMrcontents = Lists.newArrayList();
        int index = 0;
        while (bljlIds.size() > 0 && index <= bljlIds.size() - 1) {
            QueryWrapper<MrMrcontent> mrMrcontentQe = new QueryWrapper<>();
            if (index + 1000 > bljlIds.size() - 1) {
                mrMrcontentQe.in("BLJLID", bljlIds.subList(index, bljlIds.size()));
            } else {
                mrMrcontentQe.in("BLJLID", bljlIds.subList(index, index + 1000));
            }
            mrMrcontents.addAll(mrMrcontentService.list(mrMrcontentQe));
            if (index + 1000 > bljlIds.size() - 1) {
                index = bljlIds.size();
            } else {
                index = index + 1000;
            }
        }

        List<String> bljlIds_ck = mrMrcontents.stream().map(i -> i.getBljlid()).collect(Collectors.toList());
        mrMedicalrecordsList = mrMedicalrecordsList.stream().filter(i -> bljlIds_ck.contains(i.getBljlid())).collect(Collectors.toList());
        List<MedicalRecord> medicalRecords = Lists.newArrayList();
        mrMedicalrecordsList.forEach(mrMedicalrecords -> {
            MedicalRecord medicalRecord = new MedicalRecord();
            medicalRecord.setRecId(mrMedicalrecords.getBljlid());
            medicalRecord.setHospitalId(3l);
            medicalRecord.setBehospitalCode(mrMedicalrecords.getBrzyid());
            medicalRecord.setOrgCode(mrMedicalrecords.getZzjgdm());
            medicalRecord.setRecTypeId(mrMedicalrecords.getBllbid());
            medicalRecord.setRecDate(mrMedicalrecords.getBcjlsj());
            medicalRecord.setRecTitle(mrMedicalrecords.getBljlmc());
            medicalRecords.add(medicalRecord);
        });
        medicalRecordService.saveBatch(medicalRecords);

        List<MedicalRecordContent> medicalRecordContents = Lists.newArrayList();
        for (MrMrcontent mrMrcontent : mrMrcontents) {
            MedicalRecordContent medicalRecordContent = new MedicalRecordContent();
            medicalRecordContent.setRecId(mrMrcontent.getBljlid());
            medicalRecordContent.setHospitalId(3l);
            medicalRecordContent.setContentText(mrMrcontent.getBljlnr());
            medicalRecordContents.add(medicalRecordContent);
        }
        medicalRecordContentService.saveBatch(medicalRecordContents);
    }

    private void dataTransYiZhu() {
        QueryWrapper<BrDoctadvice> brDoctadviceQe = new QueryWrapper<>();
        brDoctadviceQe.in("BRZYID", brzyids);
        List<BrDoctadvice> brDoctadviceList = brDoctadviceService.list(brDoctadviceQe);
        List<DoctorAdvice> doctorAdviceList = Lists.newArrayList();
        for (BrDoctadvice brDoctadvice : brDoctadviceList) {
            DoctorAdvice doctorAdvice = new DoctorAdvice();
            doctorAdvice.setDoctorAdviceId(brDoctadvice.getBryzid());
            doctorAdvice.setHospitalId(3l);
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
            doctorAdvice.setDaFrequency(brDoctadvice.getYzplid());
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
            doctorAdvice.setDoctorName(brDoctadvice.getKdysxm());
            doctorAdviceList.add(doctorAdvice);
        }
        if (ListUtil.isNotEmpty(doctorAdviceList)) {
            doctorAdviceService.saveBatch(doctorAdviceList);
        }
    }

    private void dataTransBasy() {
        QueryWrapper<BrRechome> brRechomeQe = new QueryWrapper<>();
        brRechomeQe.in("BRZYID", brzyids);
        List<BrRechome> brRechomeList = brRechomeService.list(brRechomeQe);
        List<HomePage> homePageList = Lists.newArrayList();
        brRechomeList.forEach(brRechome -> {
            HomePage homePage = new HomePage();
            homePage.setHomePageId(brRechome.getBasyid());
            homePage.setHospitalId(3l);
            homePage.setBehospitalCode(brRechome.getBrzyid());
            homePage.setHospitalCode(brRechome.getZzjgid());
            homePage.setHospitalName(brRechome.getZzjgmc());
            homePage.setOrgCode(brRechome.getZzjgbh());
            homePage.setPayType(brRechome.getYlfklb());
            homePage.setHealthCard(brRechome.getBrjkkh());
            homePage.setBehospitalNum(brRechome.getBrzycs());
            homePage.setFileCode(brRechome.getBrbabh());
            homePage.setName(brRechome.getBrbaxm());
            homePage.setSex(brRechome.getBrbaxb());
            homePage.setBirthday(brRechome.getBrcsrq());
            homePage.setAge(brRechome.getBrdqnl());
            homePage.setAgeUnit(brRechome.getBrnldw());
            homePage.setNationality(brRechome.getBrbagj());
            homePage.setNewbornMonth(brRechome.getYenlys());
            homePage.setNewbornDay(brRechome.getYenlts());
            homePage.setNewbornWeight(brRechome.getYecstz());
            homePage.setNewbornBehospitalWeight(brRechome.getYerytz());
            homePage.setBornAddress(brRechome.getBrcsdz());
            homePage.setBornPlace(brRechome.getBrbajg());
            homePage.setNation(brRechome.getBrbamz());
            homePage.setIdentityCardNo(brRechome.getBrsfzh());
            homePage.setJobType(brRechome.getBrbasf());
            homePage.setMarriage(brRechome.getBrhyzk());
            homePage.setCurAddress(brRechome.getBrlxdz());
            homePage.setCurPhone(brRechome.getBrlxdh());
            homePage.setCurPostCode(brRechome.getLxdzyb());
            homePage.setResidenceAddress(brRechome.getBrhkdz());
            homePage.setResidencePostCode(brRechome.getHkdzyb());
            homePage.setWorkAddress(brRechome.getGzdwmc());
            homePage.setWorkPhone(brRechome.getGzdwdh());
            homePage.setWorkPostCode(brRechome.getGzdwyb());
            homePage.setContactName(brRechome.getLxryxm());
            homePage.setContactRelation(brRechome.getLxrygx());
            homePage.setContactPhone(brRechome.getLxrydh());
            homePage.setContactAddress(brRechome.getLxrydz());
            homePage.setBehospitalWay(brRechome.getRylydm());
            homePage.setBehospitalDate(brRechome.getBrryrq());
            homePage.setBehospitalDept(brRechome.getRyksmc());
            homePage.setBehospitalWard(brRechome.getRybqmc());
            homePage.setBehospitalBedId(brRechome.getRycwid());
            homePage.setBehospitalBedCode(brRechome.getRycwhm());
            homePage.setChangeDept(brRechome.getBrzkkb());
            homePage.setLeaveHospitalDate(brRechome.getBrcyrq());
            homePage.setLeaveHospitalDept(brRechome.getCyksmc());
            homePage.setLeaveHospitalWard(brRechome.getCybqmc());
            homePage.setLeaveHospitalBedId(brRechome.getCycwid());
            homePage.setLeaveHospitalBedCode(brRechome.getCycwhm());
            homePage.setBehospitalDayNum(brRechome.getSjzyts());
            homePage.setOutpatientEmrDiagnoseCode(brRechome.getMzzddm());
            homePage.setOutpatientEmrDiagnose(brRechome.getBrmzzd());
            homePage.setPoisonFactorCode(brRechome.getSszdysbm());
            homePage.setPoisonFactor(brRechome.getSszdysmc());
            homePage.setPathologyDiagnoseCode(brRechome.getBlzdbm());
            homePage.setPathologyDiagnose(brRechome.getBlzdmc());
            homePage.setPathologyDiagnoseId(brRechome.getBlzdbh());
            homePage.setIsMedAllergy(brRechome.getYwywgm());
            homePage.setMedAllergyName(brRechome.getBrgmyw());
            homePage.setAutopsy(brRechome.getBrsfsj());
            homePage.setBloodType(brRechome.getBrbaxx());
            homePage.setRh(brRechome.getBrbarh());
            homePage.setDeptDirector(brRechome.getKzr());
            homePage.setDirectorDoctor(brRechome.getZzys());
            homePage.setAttendingDoctor(brRechome.getZzys());
            homePage.setBehospitalDoctor(brRechome.getZzys());
            homePage.setResponseNurse(brRechome.getZrhs());
            homePage.setStudyDoctor(brRechome.getJxys());
            homePage.setPracticeDoctor(brRechome.getSxys());
            homePage.setEncodeMan(brRechome.getBmy());
            homePage.setHomePageQuality(brRechome.getBrbazl());
            homePage.setQcDoctor(brRechome.getBazkys());
            homePage.setQcNurse(brRechome.getBazkhs());
            homePage.setQcDate(brRechome.getBazkrq());
            homePage.setLeaveHospitalType(brRechome.getBrlyfs());
            homePage.setAcceptOrgCode(brRechome.getZyjgmc());
            homePage.setAgainBehospitalPlan(brRechome.getSsyzzy());
            homePage.setAgainBehospitalGoal(brRechome.getSsyzzymd());
            homePage.setTbiBeforeDay(brRechome.getRyqhmts());
            homePage.setTbiBeforeHour(brRechome.getRyqhmxs());
            homePage.setTbiBeforeMinute(brRechome.getRyqhmfz());
            homePage.setTbiAfterDay(brRechome.getRyhhmts());
            homePage.setTbiAfterHour(brRechome.getRyhhmxs());
            homePage.setTbiAfterMinute(brRechome.getRyhhmfz());
            homePage.setTotalFee(brRechome.getZfy());
            homePage.setOwnFee(brRechome.getZfje());
            homePage.setGeneralFee(brRechome.getYbylfwf());
            homePage.setServiceFee(brRechome.getYbzlczf());
            homePage.setNurseFee(brRechome.getHlf());
            homePage.setOtherFee(brRechome.getQtfy());
            homePage.setPathologyFee(brRechome.getBlzdf());
            homePage.setLabFee(brRechome.getSyszdf());
            homePage.setPacsFee(brRechome.getYxxzdf());
            homePage.setClinicDiagnoseFee(brRechome.getLczdxmf());
            homePage.setNotOperationFee(brRechome.getFsszlxmf());
            homePage.setClinicPhysicFee(brRechome.getLcwlzlf());
            homePage.setOperationTreatFee(brRechome.getSszlf());
            homePage.setAnaesthesiaFee(brRechome.getMzf());
            homePage.setOperationFee(brRechome.getSsf());
            homePage.setHealthTypeFee(brRechome.getKff());
            homePage.setChnTreatFee(brRechome.getZyzlf());
            homePage.setWesternMedFee(brRechome.getXyf());
            homePage.setAntibiosisFee(brRechome.getKjywf());
            homePage.setChnMedFee(brRechome.getZcyf());
            homePage.setChnHerbFee(brRechome.getCyf());
            homePage.setBloodFee(brRechome.getXf());
            homePage.setAlbumenFee(brRechome.getBdblzpf());
            homePage.setGlobulinFee(brRechome.getQdblzpf());
            homePage.setBloodFactorFee(brRechome.getNxyzlzpf());
            homePage.setCellFactorFee(brRechome.getXbyzlzpf());
            homePage.setCheckMaterialFee(brRechome.getJcyycxyyclf());
            homePage.setTreatMaterialFee(brRechome.getZlyycxyyclf());
            homePage.setOperationMaterialFee(brRechome.getSsyycxyyclf());
            homePage.setOtherTypeFee(brRechome.getQtf());
            homePage.setSingleDiagManage(brRechome.getDbzgl());
            homePage.setClinicPathwayManage(brRechome.getSslcljgl());
            homePage.setIsOutpatientBehospital(brRechome.getMzzyfh());
            homePage.setIsLeaveBehospital(brRechome.getRycyfh());
            homePage.setIsOperationBeforeAfter(brRechome.getSqshfh());
            homePage.setIsClinicPathology(brRechome.getLcblfh());
            homePage.setIsRadiatePathology(brRechome.getFsblfh());
            homePage.setRescueNum(brRechome.getBrqjcs());
            homePage.setRescueSuccessNum(brRechome.getQjcgcs());
            homePage.setIsAutoLeavehospital(brRechome.getZdcypb());
            homePage.setReturnToType(brRechome.getCyqkdm());
            homePageList.add(ObjectUtil.attributeNullHandle(homePage));
        });
        if (ListUtil.isNotEmpty(homePageList)) {
            homePageService.saveBatch(homePageList);
        }
    }

    private void dataTransBasyshzd() {
        QueryWrapper<BrRechome> brRechomeQe = new QueryWrapper<>();
        brRechomeQe.in("BRZYID", brzyids);
        List<BrRechome> brRechomeList = brRechomeService.list(brRechomeQe);
        List<String> basyids = brRechomeList.stream().map(i -> i.getBasyid()).distinct().collect(Collectors.toList());
        if (ListUtil.isEmpty(basyids)) {
            return;
        }

        QueryWrapper<BrRecdiagnose> brRecdiagnoseQe = new QueryWrapper<>();
        brRecdiagnoseQe.in("BASYID", basyids);
        List<BrRecdiagnose> brRecdiagnoseList = brRecdiagnoseService.list(brRecdiagnoseQe);
        List<HomeDiagnoseInfo> homeDiagnoseInfoList = Lists.newArrayList();
        brRecdiagnoseList.forEach(brRecdiagnose -> {
            HomeDiagnoseInfo homeDiagnoseInfo = new HomeDiagnoseInfo();
            homeDiagnoseInfo.setHomePageId(brRecdiagnose.getBasyid());
            homeDiagnoseInfo.setHospitalId(3l);
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
        if (ListUtil.isNotEmpty(homeDiagnoseInfoList)) {
            homeDiagnoseInfoService.saveBatch(homeDiagnoseInfoList);
        }

        QueryWrapper<BrRecoperation> brRecoperationQe = new QueryWrapper<>();
        brRecoperationQe.in("BASYID", basyids);
        List<BrRecoperation> brRecoperationList = brRecoperationService.list(brRecoperationQe);
        List<HomeOperationInfo> homeOperationInfoList = Lists.newArrayList();
        brRecoperationList.forEach(brRecoperation -> {
            HomeOperationInfo homeOperationInfo = new HomeOperationInfo();
            homeOperationInfo.setHomePageId(brRecoperation.getBasyid());
            homeOperationInfo.setHospitalId(3l);
            homeOperationInfo.setOperationOrderNo(brRecoperation.getBrssxh());
            homeOperationInfo.setOperationDate(brRecoperation.getBrssrq());
            homeOperationInfo.setOperationCode(brRecoperation.getSsdmid());
            homeOperationInfo.setOperationDoctorId(brRecoperation.getSsysid());
            homeOperationInfo.setFirstAssistantId(brRecoperation.getYzhsid());
            homeOperationInfo.setSecondAssistantId(brRecoperation.getEzhsid());
            homeOperationInfo.setCutLevel(brRecoperation.getQkdjdm());
            homeOperationInfo.setHealingLevel(brRecoperation.getYhdjdm());
            homeOperationInfo.setOperationName(brRecoperation.getBrssmc());
            homeOperationInfo.setOperationLevel(brRecoperation.getSsjbid());
            homeOperationInfo.setAnaesthesiaName(brRecoperation.getMzffmc());
            homeOperationInfo.setShamOperationName(brRecoperation.getNssmc());
            homeOperationInfoList.add(homeOperationInfo);
        });
        if (ListUtil.isNotEmpty(homeOperationInfoList)) {
            homeOperationInfoService.saveBatch(homeOperationInfoList);
        }
    }

}