//package com.nuena.order;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.google.common.collect.Lists;
//import com.nuena.huazo.entity.BrDoctadvice;
//import com.nuena.huazo.entity.BrInpatientinfo;
//import com.nuena.huazo.entity.BrRecdiagnose;
//import com.nuena.huazo.entity.BrRechome;
//import com.nuena.huazo.entity.BrRecoperation;
//import com.nuena.huazo.entity.MrMedicalrecords;
//import com.nuena.huazo.entity.MrMrcontent;
//import com.nuena.huazo.service.impl.BrDoctadviceServiceImpl;
//import com.nuena.huazo.service.impl.BrInpatientinfoServiceImpl;
//import com.nuena.huazo.service.impl.BrRecdiagnoseServiceImpl;
//import com.nuena.huazo.service.impl.BrRechomeServiceImpl;
//import com.nuena.huazo.service.impl.BrRecoperationServiceImpl;
//import com.nuena.huazo.service.impl.MrMedicalrecordsServiceImpl;
//import com.nuena.huazo.service.impl.MrMrcontentServiceImpl;
//import com.nuena.lantone.entity.BehospitalInfo;
//import com.nuena.lantone.entity.DoctorAdvice;
//import com.nuena.lantone.entity.HomeDiagnoseInfo;
//import com.nuena.lantone.entity.HomeOperationInfo;
//import com.nuena.lantone.entity.HomePage;
//import com.nuena.lantone.entity.MedicalRecord;
//import com.nuena.lantone.entity.MedicalRecordContent;
//import com.nuena.lantone.service.impl.BehospitalInfoServiceImpl;
//import com.nuena.lantone.service.impl.DoctorAdviceServiceImpl;
//import com.nuena.lantone.service.impl.HomeDiagnoseInfoServiceImpl;
//import com.nuena.lantone.service.impl.HomeOperationInfoServiceImpl;
//import com.nuena.lantone.service.impl.HomePageServiceImpl;
//import com.nuena.lantone.service.impl.MedicalRecordContentServiceImpl;
//import com.nuena.lantone.service.impl.MedicalRecordServiceImpl;
//import com.nuena.util.EncrypDES;
//import com.nuena.util.ListUtil;
//import com.nuena.util.ObjectUtil;
//import com.nuena.util.StringUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * @Description:
// * @author: rengb
// * @time: 2020/4/9 15:42
// */
//@Component
//public class TaiZhouFacade {
//
//    @Autowired
//    @Qualifier("brInpatientinfoServiceImpl")
//    private BrInpatientinfoServiceImpl brInpatientinfoService;
//    @Autowired
//    @Qualifier("mrMedicalrecordsServiceImpl")
//    private MrMedicalrecordsServiceImpl mrMedicalrecordsService;
//    @Autowired
//    @Qualifier("mrMrcontentServiceImpl")
//    private MrMrcontentServiceImpl mrMrcontentService;
//    @Autowired
//    @Qualifier("brDoctadviceServiceImpl")
//    private BrDoctadviceServiceImpl brDoctadviceService;
//    @Autowired
//    @Qualifier("brRechomeServiceImpl")
//    private BrRechomeServiceImpl brRechomeService;
//    @Autowired
//    @Qualifier("brRecdiagnoseServiceImpl")
//    private BrRecdiagnoseServiceImpl brRecdiagnoseService;
//    @Autowired
//    @Qualifier("brRecoperationServiceImpl")
//    private BrRecoperationServiceImpl brRecoperationService;
//
//
//    @Autowired
//    @Qualifier("behospitalInfoServiceImpl")
//    private BehospitalInfoServiceImpl behospitalInfoService;
//    @Autowired
//    @Qualifier("medicalRecordServiceImpl")
//    private MedicalRecordServiceImpl medicalRecordService;
//    @Autowired
//    @Qualifier("medicalRecordContentServiceImpl")
//    private MedicalRecordContentServiceImpl medicalRecordContentService;
//    @Autowired
//    @Qualifier("doctorAdviceServiceImpl")
//    private DoctorAdviceServiceImpl doctorAdviceService;
//    @Autowired
//    @Qualifier("homePageServiceImpl")
//    private HomePageServiceImpl homePageService;
//    @Autowired
//    @Qualifier("homeOperationInfoServiceImpl")
//    private HomeOperationInfoServiceImpl homeOperationInfoService;
//    @Autowired
//    @Qualifier("homeDiagnoseInfoServiceImpl")
//    private HomeDiagnoseInfoServiceImpl homeDiagnoseInfoService;
//    private EncrypDES encrypDES = null;
//
//    private List<String> brzyids = Lists.newArrayList(
//            "637683_1",
//            "637697_1",
//            "637744_1",
//            "637762_1",
//            "637772_1",
//            "637782_1",
//            "637812_1",
//            "637912_1",
//            "637918_1",
//            "637924_1",
//            "637970_1",
//            "637976_1",
//            "638110_1",
//            "638144_1",
//            "638366_1",
//            "638398_1",
//            "638466_1",
//            "638477_1",
//            "638521_1",
//            "638527_1",
//            "638663_1",
//            "638863_1",
//            "639244_1",
//            "639374_1",
//            "639418_1",
//            "639549_1",
//            "639997_1",
//            "640200_1",
//            "640396_1",
//            "640560_1",
//            "640656_1",
//            "640806_2",
//            "641093_1",
//            "641209_1",
//            "641411_1",
//            "641572_1",
//            "641664_1",
//            "642102_1",
//            "642326_1",
//            "642360_1",
//            "643375_1",
//            "643564_1",
//            "644296_1",
//            "604607_3",
//            "605083_2",
//            "605385_4",
//            "605682_2",
//            "609797_2",
//            "614122_2",
//            "615057_4",
//            "615057_5",
//            "615775_4",
//            "616047_3",
//            "616972_2",
//            "617087_2",
//            "619511_2",
//            "621551_1",
//            "622344_1",
//            "622477_1",
//            "623234_1",
//            "623427_2",
//            "623980_2",
//            "625722_1",
//            "627003_2",
//            "627956_1",
//            "628264_1",
//            "629279_1",
//            "629353_1",
//            "630088_2",
//            "631625_2",
//            "631812_1",
//            "631844_1",
//            "632062_1",
//            "632480_1",
//            "632732_1",
//            "632831_2",
//            "632941_1",
//            "633281_1",
//            "635064_2",
//            "635546_1",
//            "635638_1",
//            "635642_1",
//            "636054_1",
//            "636097_1",
//            "636141_1",
//            "636352_1",
//            "636617_1",
//            "636898_1",
//            "636926_1",
//            "636931_1",
//            "636978_1",
//            "637028_1",
//            "637060_1",
//            "637075_1",
//            "637080_1",
//            "637103_1",
//            "637116_1",
//            "637244_1",
//            "637280_1",
//            "637313_1",
//            "637319_1",
//            "637320_1",
//            "637332_1",
//            "637363_1",
//            "637367_1",
//            "637369_1",
//            "637374_1",
//            "211676_8",
//            "254618_8",
//            "261290_2",
//            "343738_7",
//            "364476_4",
//            "372829_4",
//            "386667_2",
//            "401432_2",
//            "402393_3",
//            "407710_8",
//            "422158_3",
//            "423175_5",
//            "430645_5",
//            "431068_2",
//            "431711_2",
//            "439810_3",
//            "443027_3",
//            "468428_2",
//            "473610_3",
//            "477253_3",
//            "480633_2",
//            "485932_5",
//            "492253_3",
//            "497301_2",
//            "502889_2",
//            "506246_5",
//            "510112_2",
//            "515620_4",
//            "523129_4",
//            "525015_2",
//            "526209_4",
//            "526366_7",
//            "526511_3",
//            "528869_2",
//            "530886_4",
//            "538322_2",
//            "538766_4",
//            "542876_3",
//            "547961_2",
//            "548911_5",
//            "549055_9",
//            "551420_3",
//            "558420_4",
//            "559161_2",
//            "561900_6",
//            "561900_7",
//            "563425_2",
//            "568881_3",
//            "578546_3",
//            "579094_2",
//            "585229_8",
//            "585825_2",
//            "587668_1",
//            "588135_2",
//            "588269_2",
//            "588278_2",
//            "589656_2",
//            "592828_2",
//            "594131_2",
//            "597759_4",
//            "597836_2",
//            "598803_3",
//            "604605_2",
//            "604607_2",
//            "637169_1",
//            "637329_1",
//            "637352_1",
//            "637362_1",
//            "637383_1",
//            "637453_1",
//            "637463_1",
//            "637508_1",
//            "637511_1",
//            "637529_1",
//            "637693_1",
//            "637699_1",
//            "637728_1",
//            "637733_1",
//            "637739_1",
//            "637741_1",
//            "637748_1",
//            "637820_1",
//            "637847_1",
//            "637968_1",
//            "638012_1",
//            "638261_1",
//            "638309_1",
//            "638414_1",
//            "638629_1",
//            "638741_1",
//            "638988_1",
//            "639242_1",
//            "639256_1",
//            "639502_1",
//            "641672_1",
//            "641937_1",
//            "642406_1",
//            "643106_1",
//            "643149_1",
//            "643700_1",
//            "644578_1",
//            "645180_1",
//            "645219_1",
//            "162233_13",
//            "180115_13",
//            "183163_14",
//            "211365_13",
//            "307940_24",
//            "370063_20",
//            "446956_12",
//            "453219_11",
//            "457012_11",
//            "487513_20",
//            "514026_14",
//            "591423_12",
//            "215513_5",
//            "223344_6",
//            "311675_8",
//            "317261_3",
//            "346498_3",
//            "347715_5",
//            "351874_3",
//            "353430_4",
//            "357990_2",
//            "400307_2",
//            "402696_2",
//            "404280_4",
//            "435342_6",
//            "436529_8",
//            "439918_3",
//            "470491_5",
//            "471295_5",
//            "484971_6",
//            "488584_3",
//            "501918_2",
//            "502909_3",
//            "512531_4",
//            "522446_2",
//            "522672_2",
//            "527754_2",
//            "532145_2",
//            "543322_2",
//            "548441_2",
//            "562536_2",
//            "563449_1",
//            "567645_1",
//            "569252_2",
//            "570648_2",
//            "571089_2",
//            "573327_2",
//            "577264_2",
//            "579786_2",
//            "581629_2",
//            "583092_2",
//            "584522_2",
//            "587093_2",
//            "593182_2",
//            "606320_2",
//            "614360_2",
//            "614445_2",
//            "615522_2",
//            "624520_3",
//            "626474_2",
//            "626702_2",
//            "627948_2",
//            "628936_1",
//            "629197_1",
//            "629201_1",
//            "629205_1",
//            "629639_1",
//            "630700_1",
//            "631341_2",
//            "635497_1",
//            "635746_2",
//            "635814_1",
//            "636676_2",
//            "636694_1",
//            "636730_1",
//            "637105_1",
//            "182311_2",
//            "645235_1",
//            "432471_4",
//            "453415_5",
//            "464503_2",
//            "506886_6",
//            "521301_9",
//            "531741_3",
//            "531901_2",
//            "534098_2",
//            "541772_2",
//            "561698_1",
//            "564583_1",
//            "567027_1",
//            "574404_2",
//            "592891_2",
//            "615269_2",
//            "618325_6",
//            "618535_2",
//            "620988_2",
//            "632173_4",
//            "635849_1",
//            "635967_1",
//            "636322_1",
//            "637349_1",
//            "637417_2",
//            "637598_1",
//            "637622_2",
//            "637908_1",
//            "637962_1",
//            "638044_1",
//            "638128_1",
//            "638209_1",
//            "641552_1",
//            "641726_1",
//            "642167_1",
//            "416993_3",
//            "373826_2",
//            "593091_11",
//            "560393_3",
//            "567925_2",
//            "576090_2",
//            "581833_2",
//            "621543_8",
//            "625574_2",
//            "630932_3",
//            "636559_1",
//            "637739_2",
//            "637758_1",
//            "637760_1",
//            "637789_1",
//            "638028_1",
//            "642071_1",
//            "644731_1",
//            "644747_1",
//            "644901_1",
//            "644999_1",
//            "645193_1",
//            "407554_3",
//            "435032_2",
//            "451091_3",
//            "493755_2",
//            "258924_12",
//            "637710_1",
//            "637787_1",
//            "612152_9",
//            "594743_4",
//            "643658_1",
//            "637714_1",
//            "554853_1",
//            "447880_10",
//            "645392_1",
//            "527527_2",
//            "551230_1",
//            "547609_1",
//            "504419_2",
//            "552110_1",
//            "637897_1",
//            "570952_1",
//            "631659_1",
//            "558562_1"
//    );
//
//    @Transactional(transactionManager = "db1TransactionManager")
//    public void dataTrans() throws Exception {
//        brzyidsQd();//brzyids确定，已经拉过的过滤掉
//        if (ListUtil.isEmpty(brzyids)) {
//            return;
//        }
//        encrypDES = new EncrypDES();
//        dataTransZyWsNr();//拉住院信息、文书记录、文书内容
//        dataTransYiZhu();//拉医嘱
//        dataTransBasy();//拉病案首页
//        dataTransBasyshzd();//拉病案首页的手术、病案首页的诊断
//    }
//
//    private void brzyidsQd() {
//        QueryWrapper<BehospitalInfo> behospitalInfoQe = new QueryWrapper<>();
//        behospitalInfoQe.in("behospital_code", brzyids);
//        behospitalInfoQe.select("behospital_code");
//        List<String> behospitalCodeList =
//                behospitalInfoService.list(behospitalInfoQe)
//                        .stream()
//                        .map(i -> i.getBehospitalCode())
//                        .collect(Collectors.toList());
//        brzyids = brzyids.stream().filter(i -> !behospitalCodeList.contains(i)).collect(Collectors.toList());
//    }
//
//    private void dataTransZyWsNr() throws Exception {
//        QueryWrapper<BrInpatientinfo> brInpatientinfoQe = new QueryWrapper<>();
//        brInpatientinfoQe.in("BRZYID", brzyids);
//        List<BrInpatientinfo> brInpatientinfoList = brInpatientinfoService.list(brInpatientinfoQe);
//        List<BehospitalInfo> behospitalInfos = Lists.newArrayList();
//        brInpatientinfoList.forEach(brInpatientinfo -> {
//            BehospitalInfo behospitalInfo = new BehospitalInfo();
//            behospitalInfo.setBehospitalCode(brInpatientinfo.getBrzyid());
//            behospitalInfo.setHospitalId(3l);
//            behospitalInfo.setName(StringUtil.isNotBlank(brInpatientinfo.getBrdaxm()) ? (brInpatientinfo.getBrdaxm().trim().substring(0, 1) + "**") : brInpatientinfo.getBrdaxm());
//
//            if (brInpatientinfo.getBrdaxb().equals("M")) {
//                behospitalInfo.setSex("男");
//            } else if (brInpatientinfo.getBrdaxb().equals("F")) {
//                behospitalInfo.setSex("女");
//            } else {
//                behospitalInfo.setSex("未知");
//            }
//
//            behospitalInfo.setBirthday(brInpatientinfo.getBrcsrq());
//            behospitalInfo.setFileCode(brInpatientinfo.getBrdabh());
//            behospitalInfo.setWardCode(brInpatientinfo.getZybqid());
//            behospitalInfo.setWardName(brInpatientinfo.getZybqmc());
//            behospitalInfo.setBehDeptId(brInpatientinfo.getZyksid());
//            behospitalInfo.setBehDeptName(brInpatientinfo.getZyksmc());
//            behospitalInfo.setBedCode(brInpatientinfo.getZycwid());
//            behospitalInfo.setBedName(brInpatientinfo.getZycwhm());
//            behospitalInfo.setInsuranceName(brInpatientinfo.getBrlbid());
//            behospitalInfo.setBehospitalDate(brInpatientinfo.getBrryrq());
//            behospitalInfo.setLeaveHospitalDate(brInpatientinfo.getBrcyrq());
//            behospitalInfo.setDiagnoseIcd(brInpatientinfo.getJbdmid());
//            behospitalInfo.setDiagnose(brInpatientinfo.getJbdmid());
//            behospitalInfo.setDoctorId(brInpatientinfo.getZzysid());
//            behospitalInfo.setDoctorName(brInpatientinfo.getZzysxm());
//            behospitalInfos.add(behospitalInfo);
//        });
//        behospitalInfoService.saveBatch(behospitalInfos);
//
//        QueryWrapper<MrMedicalrecords> mrMedicalrecordsQe = new QueryWrapper<>();
//        mrMedicalrecordsQe.in("BRZYID", brzyids);
//        List<MrMedicalrecords> mrMedicalrecordsList = mrMedicalrecordsService.list(mrMedicalrecordsQe);
//        List<String> bljlIds = mrMedicalrecordsList.stream().map(i -> i.getBljlid()).collect(Collectors.toList());
//
//        List<MrMrcontent> mrMrcontents = Lists.newArrayList();
//        int index = 0;
//        while (bljlIds.size() > 0 && index <= bljlIds.size() - 1) {
//            QueryWrapper<MrMrcontent> mrMrcontentQe = new QueryWrapper<>();
//            if (index + 1000 > bljlIds.size() - 1) {
//                mrMrcontentQe.in("BLJLID", bljlIds.subList(index, bljlIds.size()));
//            } else {
//                mrMrcontentQe.in("BLJLID", bljlIds.subList(index, index + 1000));
//            }
//            mrMrcontents.addAll(mrMrcontentService.list(mrMrcontentQe));
//            if (index + 1000 > bljlIds.size() - 1) {
//                index = bljlIds.size();
//            } else {
//                index = index + 1000;
//            }
//        }
//
//        List<String> bljlIds_ck = mrMrcontents.stream().map(i -> i.getBljlid()).collect(Collectors.toList());
//        mrMedicalrecordsList = mrMedicalrecordsList.stream().filter(i -> bljlIds_ck.contains(i.getBljlid())).collect(Collectors.toList());
//        List<MedicalRecord> medicalRecords = Lists.newArrayList();
//        mrMedicalrecordsList.forEach(mrMedicalrecords -> {
//            MedicalRecord medicalRecord = new MedicalRecord();
//            medicalRecord.setRecId(mrMedicalrecords.getBljlid());
//            medicalRecord.setHospitalId(3l);
//            medicalRecord.setBehospitalCode(mrMedicalrecords.getBrzyid());
//            medicalRecord.setOrgCode(mrMedicalrecords.getZzjgdm());
//            medicalRecord.setRecTypeId(mrMedicalrecords.getBllbid());
//            medicalRecord.setRecDate(mrMedicalrecords.getBcjlsj());
//            medicalRecord.setRecTitle(mrMedicalrecords.getBljlmc());
//            medicalRecords.add(medicalRecord);
//        });
//        medicalRecordService.saveBatch(medicalRecords);
//
//        List<MedicalRecordContent> medicalRecordContents = Lists.newArrayList();
//        for (MrMrcontent mrMrcontent : mrMrcontents) {
//            MedicalRecordContent medicalRecordContent = new MedicalRecordContent();
//            medicalRecordContent.setRecId(mrMrcontent.getBljlid());
//            medicalRecordContent.setHospitalId(3l);
//            medicalRecordContent.setXmlText(encrypDES.encrytor(mrMrcontent.getBljlnr()));
//            medicalRecordContents.add(medicalRecordContent);
//        }
//        medicalRecordContentService.saveBatch(medicalRecordContents);
//    }
//
//    private void dataTransYiZhu() {
//        QueryWrapper<BrDoctadvice> brDoctadviceQe = new QueryWrapper<>();
//        brDoctadviceQe.in("BRZYID", brzyids);
//        List<BrDoctadvice> brDoctadviceList = brDoctadviceService.list(brDoctadviceQe);
//        List<DoctorAdvice> doctorAdviceList = Lists.newArrayList();
//        for (BrDoctadvice brDoctadvice : brDoctadviceList) {
//            DoctorAdvice doctorAdvice = new DoctorAdvice();
//            doctorAdvice.setDoctorAdviceId(brDoctadvice.getBryzid());
//            doctorAdvice.setHospitalId(3l);
//            doctorAdvice.setBehospitalCode(brDoctadvice.getBrzyid());
//            doctorAdvice.setOrderDoctorName(brDoctadvice.getYskdpb());
//            doctorAdvice.setFrequency(brDoctadvice.getYzplpb());
//            doctorAdvice.setParentTypeId(brDoctadvice.getFlyzid());
//            doctorAdvice.setDoctorAdviceType(brDoctadvice.getYzlxpb());
//            doctorAdvice.setUsageNum(brDoctadvice.getYcsysl());
//            doctorAdvice.setUsageUnit(brDoctadvice.getYcyldw());
//            doctorAdvice.setDose(brDoctadvice.getYzdcjl());
//            doctorAdvice.setDoseUnit(brDoctadvice.getDcjldw());
//            doctorAdvice.setMedModeType(brDoctadvice.getGyfsid());
//            doctorAdvice.setDaFrequency(brDoctadvice.getYzplid());
//            doctorAdvice.setDaDealType(brDoctadvice.getYzcllx());
//            doctorAdvice.setDaStartDate(brDoctadvice.getYzkssj());
//            doctorAdvice.setDaItemName(brDoctadvice.getYzxmmc());
//            doctorAdvice.setDaStatus(brDoctadvice.getYzztpb());
//            doctorAdvice.setDaStopDate(brDoctadvice.getYzjssj());
//            doctorAdvice.setDaGroupNo(brDoctadvice.getYztzxh());
//            doctorAdvice.setDaPrescriptionType(brDoctadvice.getYzcflx());
//            doctorAdvice.setDaMedType(brDoctadvice.getYzlylx());
//            doctorAdvice.setDoctorNotice(brDoctadvice.getYsztsm());
//            doctorAdvice.setDoctorId(brDoctadvice.getKdysid());
//            doctorAdvice.setDoctorName(brDoctadvice.getKdysxm());
//            doctorAdviceList.add(doctorAdvice);
//        }
//        if (ListUtil.isNotEmpty(doctorAdviceList)) {
//            doctorAdviceService.saveBatch(doctorAdviceList);
//        }
//    }
//
//    private void dataTransBasy() {
//        QueryWrapper<BrRechome> brRechomeQe = new QueryWrapper<>();
//        brRechomeQe.in("BRZYID", brzyids);
//        List<BrRechome> brRechomeList = brRechomeService.list(brRechomeQe);
//        List<HomePage> homePageList = Lists.newArrayList();
//        brRechomeList.forEach(brRechome -> {
//            HomePage homePage = new HomePage();
//            homePage.setHomePageId(brRechome.getBasyid());
//            homePage.setHospitalId(3l);
//            homePage.setBehospitalCode(brRechome.getBrzyid());
//            homePage.setHospitalCode(brRechome.getZzjgid());
//            homePage.setHospitalName(brRechome.getZzjgmc());
//            homePage.setOrgCode(brRechome.getZzjgbh());
//            homePage.setPayType(brRechome.getYlfklb());
//            homePage.setHealthCard(brRechome.getBrjkkh());
//            homePage.setBehospitalNum(brRechome.getBrzycs());
//            homePage.setFileCode(brRechome.getBrbabh());
//            homePage.setName(StringUtil.isNotBlank(brRechome.getBrbaxm()) ? (brRechome.getBrbaxm().trim().substring(0, 1) + "**") : brRechome.getBrbaxm());
//            homePage.setSex(brRechome.getBrbaxb());
//            homePage.setBirthday(brRechome.getBrcsrq());
//            homePage.setAge(brRechome.getBrdqnl());
//            homePage.setAgeUnit(brRechome.getBrnldw());
//            homePage.setNationality(brRechome.getBrbagj());
//            homePage.setNewbornMonth(brRechome.getYenlys());
//            homePage.setNewbornDay(brRechome.getYenlts());
//            homePage.setNewbornWeight(brRechome.getYecstz());
//            homePage.setNewbornBehospitalWeight(brRechome.getYerytz());
//            homePage.setBornAddress(brRechome.getBrcsdz());
//            homePage.setBornPlace(brRechome.getBrbajg());
//            homePage.setNation(brRechome.getBrbamz());
//            homePage.setIdentityCardNo(brRechome.getBrsfzh());
//            homePage.setJobType(brRechome.getBrbasf());
//            homePage.setMarriage(brRechome.getBrhyzk());
//            homePage.setCurAddress(brRechome.getBrlxdz());
//            homePage.setCurPhone(StringUtil.isNotBlank(brRechome.getBrlxdh()) ? (brRechome.getBrlxdh().trim().substring(0, 1) + "**") : brRechome.getBrlxdh());
//            homePage.setCurPostCode(brRechome.getLxdzyb());
//            homePage.setResidenceAddress(brRechome.getBrhkdz());
//            homePage.setResidencePostCode(brRechome.getHkdzyb());
//            homePage.setWorkAddress(brRechome.getGzdwmc());
//            homePage.setWorkPhone(StringUtil.isNotBlank(brRechome.getGzdwdh()) ? (brRechome.getGzdwdh().trim().substring(0, 1) + "**") : brRechome.getGzdwdh());
//            homePage.setWorkPostCode(brRechome.getGzdwyb());
//            homePage.setContactName(StringUtil.isNotBlank(brRechome.getLxryxm()) ? (brRechome.getLxryxm().trim().substring(0, 1) + "**") : brRechome.getLxryxm());
//            homePage.setContactRelation(brRechome.getLxrygx());
//            homePage.setContactPhone(StringUtil.isNotBlank(brRechome.getLxrydh()) ? (brRechome.getLxrydh().trim().substring(0, 1) + "**") : brRechome.getLxrydh());
//            homePage.setContactAddress(brRechome.getLxrydz());
//            homePage.setBehospitalWay(brRechome.getRylydm());
//            homePage.setBehospitalDate(brRechome.getBrryrq());
//            homePage.setBehospitalDept(brRechome.getRyksmc());
//            homePage.setBehospitalWard(brRechome.getRybqmc());
//            homePage.setBehospitalBedId(brRechome.getRycwid());
//            homePage.setBehospitalBedCode(brRechome.getRycwhm());
//            homePage.setChangeDept(brRechome.getBrzkkb());
//            homePage.setLeaveHospitalDate(brRechome.getBrcyrq());
//            homePage.setLeaveHospitalDept(brRechome.getCyksmc());
//            homePage.setLeaveHospitalWard(brRechome.getCybqmc());
//            homePage.setLeaveHospitalBedId(brRechome.getCycwid());
//            homePage.setLeaveHospitalBedCode(brRechome.getCycwhm());
//            homePage.setBehospitalDayNum(brRechome.getSjzyts());
//            homePage.setOutpatientEmrDiagnoseCode(brRechome.getMzzddm());
//            homePage.setOutpatientEmrDiagnose(brRechome.getBrmzzd());
//            homePage.setPoisonFactorCode(brRechome.getSszdysbm());
//            homePage.setPoisonFactor(brRechome.getSszdysmc());
//            homePage.setPathologyDiagnoseCode(brRechome.getBlzdbm());
//            homePage.setPathologyDiagnose(brRechome.getBlzdmc());
//            homePage.setPathologyDiagnoseId(brRechome.getBlzdbh());
//            homePage.setIsMedAllergy(brRechome.getYwywgm());
//            homePage.setMedAllergyName(brRechome.getBrgmyw());
//            homePage.setAutopsy(brRechome.getBrsfsj());
//            homePage.setBloodType(brRechome.getBrbaxx());
//            homePage.setRh(brRechome.getBrbarh());
//            homePage.setDeptDirector(brRechome.getKzr());
//            homePage.setDirectorDoctor(brRechome.getZzys());
//            homePage.setAttendingDoctor(brRechome.getZzys());
//            homePage.setBehospitalDoctor(brRechome.getZzys());
//            homePage.setResponseNurse(brRechome.getZrhs());
//            homePage.setStudyDoctor(brRechome.getJxys());
//            homePage.setPracticeDoctor(brRechome.getSxys());
//            homePage.setEncodeMan(brRechome.getBmy());
//            homePage.setHomePageQuality(brRechome.getBrbazl());
//            homePage.setQcDoctor(brRechome.getBazkys());
//            homePage.setQcNurse(brRechome.getBazkhs());
//            homePage.setQcDate(brRechome.getBazkrq());
//            homePage.setLeaveHospitalType(brRechome.getBrlyfs());
//            homePage.setAcceptOrgCode(brRechome.getZyjgmc());
//            homePage.setAgainBehospitalPlan(brRechome.getSsyzzy());
//            homePage.setAgainBehospitalGoal(brRechome.getSsyzzymd());
//            homePage.setTbiBeforeDay(brRechome.getRyqhmts());
//            homePage.setTbiBeforeHour(brRechome.getRyqhmxs());
//            homePage.setTbiBeforeMinute(brRechome.getRyqhmfz());
//            homePage.setTbiAfterDay(brRechome.getRyhhmts());
//            homePage.setTbiAfterHour(brRechome.getRyhhmxs());
//            homePage.setTbiAfterMinute(brRechome.getRyhhmfz());
//            homePage.setTotalFee(brRechome.getZfy());
//            homePage.setOwnFee(brRechome.getZfje());
//            homePage.setGeneralFee(brRechome.getYbylfwf());
//            homePage.setServiceFee(brRechome.getYbzlczf());
//            homePage.setNurseFee(brRechome.getHlf());
//            homePage.setOtherFee(brRechome.getQtfy());
//            homePage.setPathologyFee(brRechome.getBlzdf());
//            homePage.setLabFee(brRechome.getSyszdf());
//            homePage.setPacsFee(brRechome.getYxxzdf());
//            homePage.setClinicDiagnoseFee(brRechome.getLczdxmf());
//            homePage.setNotOperationFee(brRechome.getFsszlxmf());
//            homePage.setClinicPhysicFee(brRechome.getLcwlzlf());
//            homePage.setOperationTreatFee(brRechome.getSszlf());
//            homePage.setAnaesthesiaFee(brRechome.getMzf());
//            homePage.setOperationFee(brRechome.getSsf());
//            homePage.setHealthTypeFee(brRechome.getKff());
//            homePage.setChnTreatFee(brRechome.getZyzlf());
//            homePage.setWesternMedFee(brRechome.getXyf());
//            homePage.setAntibiosisFee(brRechome.getKjywf());
//            homePage.setChnMedFee(brRechome.getZcyf());
//            homePage.setChnHerbFee(brRechome.getCyf());
//            homePage.setBloodFee(brRechome.getXf());
//            homePage.setAlbumenFee(brRechome.getBdblzpf());
//            homePage.setGlobulinFee(brRechome.getQdblzpf());
//            homePage.setBloodFactorFee(brRechome.getNxyzlzpf());
//            homePage.setCellFactorFee(brRechome.getXbyzlzpf());
//            homePage.setCheckMaterialFee(brRechome.getJcyycxyyclf());
//            homePage.setTreatMaterialFee(brRechome.getZlyycxyyclf());
//            homePage.setOperationMaterialFee(brRechome.getSsyycxyyclf());
//            homePage.setOtherTypeFee(brRechome.getQtf());
//            homePage.setSingleDiagManage(brRechome.getDbzgl());
//            homePage.setClinicPathwayManage(brRechome.getSslcljgl());
//            homePage.setIsOutpatientBehospital(brRechome.getMzzyfh());
//            homePage.setIsLeaveBehospital(brRechome.getRycyfh());
//            homePage.setIsOperationBeforeAfter(brRechome.getSqshfh());
//            homePage.setIsClinicPathology(brRechome.getLcblfh());
//            homePage.setIsRadiatePathology(brRechome.getFsblfh());
//            homePage.setRescueNum(brRechome.getBrqjcs());
//            homePage.setRescueSuccessNum(brRechome.getQjcgcs());
//            homePage.setIsAutoLeavehospital(brRechome.getZdcypb());
//            homePage.setReturnToType(brRechome.getCyqkdm());
//            homePageList.add(ObjectUtil.attributeNullHandle(homePage));
//        });
//        if (ListUtil.isNotEmpty(homePageList)) {
//            homePageService.saveBatch(homePageList);
//        }
//    }
//
//    private void dataTransBasyshzd() {
//        QueryWrapper<BrRechome> brRechomeQe = new QueryWrapper<>();
//        brRechomeQe.in("BRZYID", brzyids);
//        List<BrRechome> brRechomeList = brRechomeService.list(brRechomeQe);
//        List<String> basyids = brRechomeList.stream().map(i -> i.getBasyid()).distinct().collect(Collectors.toList());
//        if (ListUtil.isEmpty(basyids)) {
//            return;
//        }
//
//        QueryWrapper<BrRecdiagnose> brRecdiagnoseQe = new QueryWrapper<>();
//        brRecdiagnoseQe.in("BASYID", basyids);
//        List<BrRecdiagnose> brRecdiagnoseList = brRecdiagnoseService.list(brRecdiagnoseQe);
//        List<HomeDiagnoseInfo> homeDiagnoseInfoList = Lists.newArrayList();
//        brRecdiagnoseList.forEach(brRecdiagnose -> {
//            HomeDiagnoseInfo homeDiagnoseInfo = new HomeDiagnoseInfo();
//            homeDiagnoseInfo.setHomePageId(brRecdiagnose.getBasyid());
//            homeDiagnoseInfo.setHospitalId(3l);
//            homeDiagnoseInfo.setDiagnoseOrderNo(brRecdiagnose.getBazdxh());
//            homeDiagnoseInfo.setDiagnoseType(brRecdiagnose.getZdlbdm());
//            homeDiagnoseInfo.setDiagnoseTypeShort(brRecdiagnose.getZczdpb());
//            homeDiagnoseInfo.setDiagnoseName(brRecdiagnose.getZdjbmc());
//            homeDiagnoseInfo.setBehospitalType(brRecdiagnose.getRyqkbm());
//            homeDiagnoseInfo.setLeaveHospitalType(brRecdiagnose.getZgqkdm());
//            homeDiagnoseInfo.setPathologyDiagnose(brRecdiagnose.getBlzdbh());
//            homeDiagnoseInfo.setIcdCode(brRecdiagnose.getIcdm());
//            homeDiagnoseInfoList.add(homeDiagnoseInfo);
//        });
//        if (ListUtil.isNotEmpty(homeDiagnoseInfoList)) {
//            homeDiagnoseInfoService.saveBatch(homeDiagnoseInfoList);
//        }
//
//        QueryWrapper<BrRecoperation> brRecoperationQe = new QueryWrapper<>();
//        brRecoperationQe.in("BASYID", basyids);
//        List<BrRecoperation> brRecoperationList = brRecoperationService.list(brRecoperationQe);
//        List<HomeOperationInfo> homeOperationInfoList = Lists.newArrayList();
//        brRecoperationList.forEach(brRecoperation -> {
//            HomeOperationInfo homeOperationInfo = new HomeOperationInfo();
//            homeOperationInfo.setHomePageId(brRecoperation.getBasyid());
//            homeOperationInfo.setHospitalId(3l);
//            homeOperationInfo.setOperationOrderNo(brRecoperation.getBrssxh());
//            homeOperationInfo.setOperationDate(brRecoperation.getBrssrq());
//            homeOperationInfo.setOperationCode(brRecoperation.getSsdmid());
//            homeOperationInfo.setOperationDoctorId(brRecoperation.getSsysid());
//            homeOperationInfo.setFirstAssistantId(brRecoperation.getYzhsid());
//            homeOperationInfo.setSecondAssistantId(brRecoperation.getEzhsid());
//            homeOperationInfo.setCutLevel(brRecoperation.getQkdjdm());
//            homeOperationInfo.setHealingLevel(brRecoperation.getYhdjdm());
//            homeOperationInfo.setOperationName(brRecoperation.getBrssmc());
//            homeOperationInfo.setOperationLevel(brRecoperation.getSsjbid());
//            homeOperationInfo.setAnaesthesiaName(brRecoperation.getMzffmc());
//            homeOperationInfo.setShamOperationName(brRecoperation.getNssmc());
//            homeOperationInfoList.add(homeOperationInfo);
//        });
//        if (ListUtil.isNotEmpty(homeOperationInfoList)) {
//            homeOperationInfoService.saveBatch(homeOperationInfoList);
//        }
//    }
//
//}