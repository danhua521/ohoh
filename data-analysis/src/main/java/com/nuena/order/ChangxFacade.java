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
import com.nuena.util.EncrypDES;
import com.nuena.util.GZIPUtils;
import com.nuena.util.ListUtil;
import com.nuena.util.ObjectUtil;
import com.nuena.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
    private EncrypDES encrypDES = null;

    private List<String> brzyids = Lists.newArrayList(
//            "ZY010000657048",
//            "ZY010000658408",
//            "ZY010000656294",
//            "ZY010000661382",
//            "ZY010000660411",
//            "ZY010000656080",
//            "ZY010000657879",
//            "ZY010000658750",
//            "ZY030000657511",
//            "ZY010000661066",
//            "ZY010000661377",
//            "ZY010000662190",
//            "ZY010000659514",
//            "ZY010000656358",
//            "ZY030000658309",
//            "ZY010000656424",
//            "ZY010000656616",
//            "ZY010000656688",
//            "ZY010000656763",
//            "ZY010000656996",
//            "ZY010000657709",
//            "ZY010000658826",
//            "ZY010000659091",
//            "ZY010000660659",
//            "ZY010000660870",
//            "ZY010000661465"



//                        "ZY010000661479",
//                        "ZY010000661878",
//                        "ZY010000662770",
//                        "ZY010000663130",
//                        "ZY010000663355",
//                        "ZY020000660037",
//                        "ZY010000656385",
//                        "ZY010000656039",
//                        "ZY020000660216",
//                        "ZY010000656283",
//                        "ZY010000656326",
//                        "ZY010000657520",
//                        "ZY010000657618",
//                        "ZY010000658008",
//                        "ZY010000658360",
//                        "ZY010000659178",
//                        "ZY010000659778",
//                        "ZY010000660245",
//                        "ZY010000660280",
//                        "ZY010000661214",
//                        "ZY010000661349",
//                        "ZY010000661464",
//                        "ZY010000661726",
//                        "ZY010000661781",
//                        "ZY010000662920",
//                        "ZY020000659270",
//                        "ZY010000656272",
//                        "ZY010000652665",
//                        "ZY040000656710",
//                        "ZY010000656309",
//                        "ZY010000656478",
//                        "ZY010000657997",
//                        "ZY010000658374",
//                        "ZY010000658976",
//                        "ZY010000659192",
//                        "ZY010000659791",
//                        "ZY010000659905",
//                        "ZY010000660571",
//                        "ZY010000660785",
//                        "ZY010000660952",
//                        "ZY010000661011",
//                        "ZY010000661288",
//                        "ZY010000661472",
//                        "ZY010000661531"




//                        "ZY010000662177",
//                        "ZY010000662377",
//                        "ZY010000663230",
//                        "ZY010000663850",
//                        "ZY020000653218",
//                        "ZY020000653268",
//                        "ZY020000653573",
//                        "ZY020000657529",
//                        "ZY020000659109",
//                        "ZY010000654181",
//                        "ZY010000650233",
//                        "ZY030000662080",
//                        "ZY010000657121",
//                        "ZY010000658041",
//                        "ZY010000658254",
//                        "ZY010000658265",
//                        "ZY010000658724",
//                        "ZY010000658758",
//                        "ZY010000658833",
//                        "ZY010000658987",
//                        "ZY010000659053",
//                        "ZY010000659576",
//                        "ZY010000659628",
//                        "ZY010000659633",
//                        "ZY010000659917",
//                        "ZY010000660035",
//                        "ZY010000660271",
//                        "ZY010000660309",
//                        "ZY010000660336",
//                        "ZY010000660340",
//                        "ZY010000660376",
//                        "ZY010000660724",
//                        "ZY010000660890",
//                        "ZY010000661359",
//                        "ZY010000661741",
//                        "ZY010000661850",
//                        "ZY010000661885",
//                        "ZY010000661891",
//                        "ZY010000661920",
//                        "ZY010000661983",
//                        "ZY010000662828",
//                        "ZY010000663158",
//                        "ZY010000663220",
//                        "ZY010000663502",
//                        "ZY010000663801",
//                        "ZY010000664146",
//                        "ZY020000656289",
//                        "ZY020000657623",
//                        "ZY020000657887",
//                        "ZY010000656405",
//                        "ZY010000650231",
//                        "ZY030000652539",
//                        "ZY010000654208",
//                        "ZY010000656011",
//                        "ZY010000656183",
//                        "ZY010000656317",
//                        "ZY010000656594",
//                        "ZY010000657008",
//                        "ZY010000657148",
//                        "ZY010000657455",
//                        "ZY010000657892",
//                        "ZY010000658221",
//                        "ZY010000658483",
//                        "ZY010000658536",
//                        "ZY010000658895",
//                        "ZY010000658918",
//                        "ZY010000659134",
//                        "ZY010000659270",
//                        "ZY010000659280",
//                        "ZY010000659456",
//                        "ZY010000659591",
//                        "ZY010000659658",
//                        "ZY010000659717",
//                        "ZY010000659723",
//                        "ZY010000659924",
//                        "ZY010000660113",
//                        "ZY010000660390",
//                        "ZY010000660985",
//                        "ZY010000661347",
//                        "ZY010000661486",
//                        "ZY010000661527",
//                        "ZY010000661571",
//                        "ZY010000662341",
//                        "ZY010000662842",
//                        "ZY010000663037",
//                        "ZY010000663842",
//                        "ZY010000664075",
//                        "ZY020000650233",
//                        "ZY020000656080",
//                        "ZY020000657359",
//                        "ZY020000661346",
//                        "ZY010000653216",
//                        "ZY010000653972",
//                        "ZY030000660897",
//                        "ZY010000657024",
//                        "ZY010000657031",
//                        "ZY010000657249",
//                        "ZY010000657652",
//                        "ZY010000658107",
//                        "ZY010000658407",
//                        "ZY010000658612",
//                        "ZY010000658641",
//                        "ZY010000659087",
//                        "ZY010000659101",
//                        "ZY010000659549",
//                        "ZY010000659677",
//                        "ZY010000659766",
//                        "ZY010000660002",
//                        "ZY010000660337",
//                        "ZY010000660407",
//                        "ZY010000660446",
//                        "ZY010000660577",
//                        "ZY010000661098",
//                        "ZY010000661273",
//                        "ZY010000661540"



//                        "ZY010000661796",
//                        "ZY010000662268",
//                        "ZY010000663077",
//                        "ZY010000663138",
//                        "ZY010000663139",
//                        "ZY010000663520",
//                        "ZY010000663682",
//                        "ZY010000663769",
//                        "ZY020000653741",
//                        "ZY020000656310",
//                        "ZY020000659497",
//                        "ZY030000652777",
//                        "ZY030000658617",
//                        "ZY010000654008",
//                        "ZY010000650197",
//                        "ZY010000653852",
//                        "ZY010000653979",
//                        "ZY010000656009",
//                        "ZY010000656024",
//                        "ZY010000656154",
//                        "ZY010000656312",
//                        "ZY010000656373",
//                        "ZY010000656592",
//                        "ZY010000656642",
//                        "ZY010000656745",
//                        "ZY010000656787",
//                        "ZY010000657153",
//                        "ZY010000657979",
//                        "ZY010000658074",
//                        "ZY010000658280",
//                        "ZY010000658532",
//                        "ZY010000658794",
//                        "ZY010000659362",
//                        "ZY010000659370",
//                        "ZY010000659651",
//                        "ZY010000659763",
//                        "ZY010000659981",
//                        "ZY010000660203",
//                        "ZY010000660631",
//                        "ZY010000661215",
//                        "ZY010000661226",
//                        "ZY010000662104",
//                        "ZY010000662526",
//                        "ZY010000662649",
//                        "ZY010000663311",
//                        "ZY010000663576",
//                        "ZY010000663648",
//                        "ZY010000664155",
//                        "ZY010000664265",
//                        "ZY020000651943",
//                        "ZY020000653824",
//                        "ZY020000656183",
//                        "ZY020000656900",
//                        "ZY020000657545",
//                        "ZY020000658477",
//                        "ZY020000662883",
//                        "ZY030000657913",
//                        "ZY030000658672",
//                        "ZY040000653009",
//                        "ZY040000653829",
//                        "ZY040000657660",
//                        "ZY010000653551",
//                        "ZY050000654238",
//                        "ZY010000654189",
//                        "ZY010000656017",
//                        "ZY010000656196",
//                        "ZY010000656319",
//                        "ZY010000657136",
//                        "ZY010000657660",
//                        "ZY010000657721",
//                        "ZY010000659864",
//                        "ZY010000660100",
//                        "ZY010000660345",
//                        "ZY010000660663",
//                        "ZY010000660813",
//                        "ZY010000661407",
//                        "ZY010000661843",
//                        "ZY010000662369",
//                        "ZY010000663031",
//                        "ZY010000663434",
//                        "ZY010000663569",
//                        "ZY010000663913",
//                        "ZY020000653658",
//                        "ZY020000653706",
//                        "ZY020000656012",
//                        "ZY020000658987",
//                        "ZY020000659195",
//                        "ZY020000659604",
//                        "ZY020000659686",
//                        "ZY020000660663",
//                        "ZY020000661250",
//                        "ZY020000661273",
//                        "ZY030000660381",
//                        "ZY010000653630",
//                        "ZY010000656133",
//                        "ZY030000659604",
//                        "ZY010000657275",
//                        "ZY010000657542",
//                        "ZY010000658271",
//                        "ZY010000659195",
//                        "ZY010000659397",
//                        "ZY010000660679",
//                        "ZY010000661067",
//                        "ZY010000661268",
//                        "ZY010000661317",
//                        "ZY010000661335",
//                        "ZY010000661490",
//                        "ZY010000662145",
//                        "ZY010000662150",
//                        "ZY010000662664",
//                        "ZY010000662970",
//                        "ZY010000663040",
//                        "ZY010000656310",
//                        "ZY010000657357",
//                        "ZY030000659357",
//                        "ZY010000659271",
//                        "ZY010000659297",
//                        "ZY010000659862",
//                        "ZY010000661145",
//                        "ZY010000661390",
//                        "ZY010000661581",
//                        "ZY010000662296",
//                        "ZY010000662302"




                        "ZY010000662974",
                        "ZY010000662992",
                        "ZY020000654186",
                        "ZY020000660914",
                        "ZY030000653829",
                        "ZY010000658521",
                        "ZY010000644352",
                        "ZY030000658116",
                        "ZY010000658523",
                        "ZY010000659307",
                        "ZY010000659572",
                        "ZY010000660168",
                        "ZY010000660202",
                        "ZY010000660267",
                        "ZY010000660684",
                        "ZY010000661864",
                        "ZY010000661912",
                        "ZY010000661930",
                        "ZY010000663222",
                        "ZY020000659879",
                        "ZY010000656576",
                        "ZY010000656881",
                        "ZY010000657079",
                        "ZY010000657894",
                        "ZY010000660499",
                        "ZY020000654133",
                        "ZY010000661132",
                        "ZY010000662028",
                        "ZY010000662585",
                        "ZY010000660895",
                        "ZY010000657047",
                        "ZY010000658635",
                        "ZY020000660477",
                        "ZY010000661678",
                        "ZY010000662332",
                        "ZY010000660477",
                        "ZY010000651607",
                        "ZY010000651943"
    );

    @Transactional(transactionManager = "db1TransactionManager")
    public void dataTrans() throws Exception {
        brzyidsQd();//brzyids确定，已经拉过的过滤掉
        if (ListUtil.isEmpty(brzyids)) {
            return;
        }
        encrypDES = new EncrypDES();
        dataTransZyWsNr();//拉住院信息、文书记录、文书内容
        dataTransYiZhu();//拉医嘱
        dataTransBasy();//拉病案首页
        dataTransBasyshzd();//拉病案首页的手术、病案首页的诊断
    }

    private void brzyidsQd() {
        QueryWrapper<BehospitalInfo> behospitalInfoQe = new QueryWrapper<>();
        behospitalInfoQe.in("hospital_id", 1l);
        behospitalInfoQe.in("behospital_code", brzyids);
        behospitalInfoQe.select("behospital_code");
        List<String> behospitalCodeList =
                behospitalInfoService.list(behospitalInfoQe)
                        .stream()
                        .map(i -> i.getBehospitalCode())
                        .collect(Collectors.toList());
        brzyids = brzyids.stream().filter(i -> !behospitalCodeList.contains(i)).collect(Collectors.toList());
    }

    private void dataTransZyWsNr() throws Exception {
        QueryWrapper<BrInpatientinfo> brInpatientinfoQe = new QueryWrapper<>();
        brInpatientinfoQe.in("BRZYID", brzyids);
        List<BrInpatientinfo> brInpatientinfoList = brInpatientinfoService.list(brInpatientinfoQe);
        List<BehospitalInfo> behospitalInfos = Lists.newArrayList();
        brInpatientinfoList.forEach(brInpatientinfo -> {
            BehospitalInfo behospitalInfo = new BehospitalInfo();
            behospitalInfo.setBehospitalCode(brInpatientinfo.getBrzyid());
            behospitalInfo.setHospitalId(1l);
            behospitalInfo.setName(StringUtil.isNotBlank(brInpatientinfo.getBrdaxm()) ? (brInpatientinfo.getBrdaxm().trim().substring(0, 1) + "**") : brInpatientinfo.getBrdaxm());

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
            medicalRecord.setHospitalId(1l);
            medicalRecord.setBehospitalCode(mrMedicalrecords.getBrzyid());
            medicalRecord.setOrgCode(mrMedicalrecords.getZzjgdm());
            medicalRecord.setRecTypeId(mrMedicalrecords.getBllbid());
            medicalRecord.setRecDate(mrMedicalrecords.getBcjlsj());
            medicalRecord.setRecTitle(mrMedicalrecords.getBljlmc());
            medicalRecords.add(medicalRecord);
        });
        medicalRecordService.saveBatch(medicalRecords);

        List<MedicalRecordContent> medicalRecordContents = Lists.newArrayList();
        Pattern pattern = Pattern.compile("[\\u4E00-\\u9FA5]+");
        String xmlnr = null;
        for (MrMrcontent mrMrcontent : mrMrcontents) {
            MedicalRecordContent medicalRecordContent = new MedicalRecordContent();
            medicalRecordContent.setRecId(mrMrcontent.getBljlid());
            medicalRecordContent.setHospitalId(1l);
            xmlnr = GZIPUtils.uncompressToString(mrMrcontent.getBljlnr().getBytes(1, (int) mrMrcontent.getBljlnr().length()), "UTF-8");
            xmlnr = xmlnr.replaceAll("<Binary>[\\S\\s]*</Binary>", "");
            if (pattern.matcher(xmlnr).find()) {
                medicalRecordContent.setXmlText(encrypDES.encrytor(xmlnr));
            }
            medicalRecordContents.add(medicalRecordContent);
        }
        medicalRecordContentService.saveBatch(medicalRecordContents);
    }

    private void dataTransYiZhu() {
        QueryWrapper<BrDoctadvice> brDoctadviceQe = new QueryWrapper<>();
        brDoctadviceQe.in("BRZYID", brzyids);
        List<BrDoctadvice> brDoctadviceList = brDoctadviceService.list(brDoctadviceQe);
        List<DoctorAdvice> doctorAdviceList = Lists.newArrayList();
        int index = 1;
        for (BrDoctadvice brDoctadvice : brDoctadviceList) {
            DoctorAdvice doctorAdvice = new DoctorAdvice();
            doctorAdvice.setDoctorAdviceId(brDoctadvice.getBrzyid() + "_" + index);
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
            doctorAdviceList.add(doctorAdvice);
            index++;
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
            homePage.setHospitalId(1l);
            homePage.setBehospitalCode(brRechome.getBrzyid());
            homePage.setHospitalCode(brRechome.getZzjgid());
            homePage.setHospitalName(brRechome.getZzjgmc());
            homePage.setOrgCode(brRechome.getZzjgbh());
            homePage.setPayType(brRechome.getYlfklb());
            homePage.setHealthCard(brRechome.getBrjkkh());
            homePage.setBehospitalNum(brRechome.getBrzycs());
            homePage.setFileCode(brRechome.getBrbabh());
            homePage.setName(StringUtil.isNotBlank(brRechome.getBrbaxm()) ? (brRechome.getBrbaxm().trim().substring(0, 1) + "**") : brRechome.getBrbaxm());
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
            homePage.setCurPhone(StringUtil.isNotBlank(brRechome.getBrlxdh()) ? (brRechome.getBrlxdh().trim().substring(0, 1) + "**") : brRechome.getBrlxdh());
            homePage.setCurPostCode(brRechome.getLxdzyb());
            homePage.setResidenceAddress(brRechome.getBrhkdz());
            homePage.setResidencePostCode(brRechome.getHkdzyb());
            homePage.setWorkAddress(brRechome.getGzdwmc());
            homePage.setWorkPhone(StringUtil.isNotBlank(brRechome.getGzdwdh()) ? (brRechome.getGzdwdh().trim().substring(0, 1) + "**") : brRechome.getGzdwdh());
            homePage.setWorkPostCode(brRechome.getGzdwyb());
            homePage.setContactName(StringUtil.isNotBlank(brRechome.getLxryxm()) ? (brRechome.getLxryxm().trim().substring(0, 1) + "**") : brRechome.getLxryxm());
            homePage.setContactRelation(brRechome.getLxrygx());
            homePage.setContactPhone(StringUtil.isNotBlank(brRechome.getLxrydh()) ? (brRechome.getLxrydh().trim().substring(0, 1) + "**") : brRechome.getLxrydh());
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
            homeOperationInfo.setHospitalId(1l);
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