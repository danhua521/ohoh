package com.nuena.lantone.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author rgb
 * @since 2020-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("med_home_page")
public class HomePage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 病案首页编号
     */
    @TableId("home_page_id")
    private String homePageId;

    /**
     * 医院ID
     */
    @TableField("hospital_id")
    private Long hospitalId;

    /**
     * 病人住院序号
     */
    @TableField("behospital_code")
    private String behospitalCode;

    /**
     * 组织机构id
     */
    @TableField("hospital_code")
    private String hospitalCode;

    /**
     * 医疗机构名称
     */
    @TableField("hospital_name")
    private String hospitalName;

    /**
     * 医疗机构代码
     */
    @TableField("org_code")
    private String orgCode;

    /**
     * 医疗付费方式
     */
    @TableField("pay_type")
    private String payType;

    /**
     * 健康卡号
     */
    @TableField("health_card")
    private String healthCard;

    /**
     * 住院次数
     */
    @TableField("behospital_num")
    private String behospitalNum;

    /**
     * 病案号
     */
    @TableField("file_code")
    private String fileCode;

    /**
     * 姓名
     */
    @TableField("name")
    private String name;

    /**
     * 性别
     */
    @TableField("sex")
    private String sex;

    /**
     * 出生日期
     */
    @TableField("birthday")
    private Date birthday;

    /**
     * 年龄
     */
    @TableField("age")
    private String age;

    /**
     * 年龄单位
     */
    @TableField("age_unit")
    private String ageUnit;

    /**
     * 国籍
     */
    @TableField("nationality")
    private String nationality;

    /**
     * 新生儿出生月数
     */
    @TableField("newborn_month")
    private String newbornMonth;

    /**
     * 新生儿出生天数
     */
    @TableField("newborn_day")
    private String newbornDay;

    /**
     * 新生儿出生体重
     */
    @TableField("newborn_weight")
    private String newbornWeight;

    /**
     * 新生儿入院体重
     */
    @TableField("newborn_behospital_weight")
    private String newbornBehospitalWeight;

    /**
     * 出生地
     */
    @TableField("born_address")
    private String bornAddress;

    /**
     * 籍贯
     */
    @TableField("born_place")
    private String bornPlace;

    /**
     * 民族
     */
    @TableField("nation")
    private String nation;

    /**
     * 身份证号
     */
    @TableField("identity_card_no")
    private String identityCardNo;

    /**
     * 职业
     */
    @TableField("job_type")
    private String jobType;

    /**
     * 婚姻
     */
    @TableField("marriage")
    private String marriage;

    /**
     * 现住址
     */
    @TableField("cur_address")
    private String curAddress;

    /**
     * 现住址电话
     */
    @TableField("cur_phone")
    private String curPhone;

    /**
     * 现住址邮编
     */
    @TableField("cur_post_code")
    private String curPostCode;

    /**
     * 户口地址
     */
    @TableField("residence_address")
    private String residenceAddress;

    /**
     * 户口地址邮编
     */
    @TableField("residence_post_code")
    private String residencePostCode;

    /**
     * 工作单位
     */
    @TableField("work_address")
    private String workAddress;

    /**
     * 工作单位电话
     */
    @TableField("work_phone")
    private String workPhone;

    /**
     * 工作单位邮编
     */
    @TableField("work_post_code")
    private String workPostCode;

    /**
     * 联系人姓名
     */
    @TableField("contact_name")
    private String contactName;

    /**
     * 联系人关系
     */
    @TableField("contact_relation")
    private String contactRelation;

    /**
     * 联系人地址
     */
    @TableField("contact_address")
    private String contactAddress;

    /**
     * 联系人电话
     */
    @TableField("contact_phone")
    private String contactPhone;

    /**
     * 入院途径
     */
    @TableField("behospital_way")
    private String behospitalWay;

    /**
     * 入院时间
     */
    @TableField("behospital_date")
    private Date behospitalDate;

    /**
     * 入院科别
     */
    @TableField("behospital_dept")
    private String behospitalDept;

    /**
     * 入院病房
     */
    @TableField("behospital_ward")
    private String behospitalWard;

    /**
     * 入院床位序号
     */
    @TableField("behospital_bed_id")
    private String behospitalBedId;

    /**
     * 入院床位号码
     */
    @TableField("behospital_bed_code")
    private String behospitalBedCode;

    /**
     * 转科科别
     */
    @TableField("change_dept")
    private String changeDept;

    /**
     * 出院时间
     */
    @TableField("leave_hospital_date")
    private Date leaveHospitalDate;

    /**
     * 出院科别
     */
    @TableField("leave_hospital_dept")
    private String leaveHospitalDept;

    /**
     * 出院病房
     */
    @TableField("leave_hospital_ward")
    private String leaveHospitalWard;

    /**
     * 出院床位序号
     */
    @TableField("leave_hospital_bed_id")
    private String leaveHospitalBedId;

    /**
     * 出院床位号码
     */
    @TableField("leave_hospital_bed_code")
    private String leaveHospitalBedCode;

    /**
     * 实际住院天数
     */
    @TableField("behospital_day_num")
    private String behospitalDayNum;

    /**
     * 门急诊诊断
     */
    @TableField("outpatient_emr_diagnose")
    private String outpatientEmrDiagnose;

    /**
     * 门急诊诊断编码
     */
    @TableField("outpatient_emr_diagnose_code")
    private String outpatientEmrDiagnoseCode;

    /**
     * 损伤中毒因素
     */
    @TableField("poison_factor")
    private String poisonFactor;

    /**
     * 损伤中毒因素编码
     */
    @TableField("poison_factor_code")
    private String poisonFactorCode;

    /**
     * 病理诊断
     */
    @TableField("pathology_diagnose")
    private String pathologyDiagnose;

    /**
     * 病理诊断编码
     */
    @TableField("pathology_diagnose_code")
    private String pathologyDiagnoseCode;

    /**
     * 病理诊断编号
     */
    @TableField("pathology_diagnose_id")
    private String pathologyDiagnoseId;

    /**
     * 药物过敏
     */
    @TableField("is_med_allergy")
    private String isMedAllergy;

    /**
     * 过敏药物
     */
    @TableField("med_allergy_name")
    private String medAllergyName;

    /**
     * 死亡患者尸检
     */
    @TableField("autopsy")
    private String autopsy;

    /**
     * 血型
     */
    @TableField("blood_type")
    private String bloodType;

    /**
     * Rh
     */
    @TableField("rh")
    private String rh;

    /**
     * 科主任
     */
    @TableField("dept_director")
    private String deptDirector;

    /**
     * 主任医师
     */
    @TableField("director_doctor")
    private String directorDoctor;

    /**
     * 主治医师
     */
    @TableField("attending_doctor")
    private String attendingDoctor;

    /**
     * 住院医师
     */
    @TableField("behospital_doctor")
    private String behospitalDoctor;

    /**
     * 责任护士
     */
    @TableField("response_nurse")
    private String responseNurse;

    /**
     * 进修医师
     */
    @TableField("study_doctor")
    private String studyDoctor;

    /**
     * 实习医师
     */
    @TableField("practice_doctor")
    private String practiceDoctor;

    /**
     * 编码员
     */
    @TableField("encode_man")
    private String encodeMan;

    /**
     * 病案质量
     */
    @TableField("home_page_quality")
    private String homePageQuality;

    /**
     * 质控医师
     */
    @TableField("qc_doctor")
    private String qcDoctor;

    /**
     * 质控护士
     */
    @TableField("qc_nurse")
    private String qcNurse;

    /**
     * 质控日期
     */
    @TableField("qc_date")
    private Date qcDate;

    /**
     * 离院方式
     */
    @TableField("leave_hospital_type")
    private String leaveHospitalType;

    /**
     * 接收机构名称
     */
    @TableField("accept_org_code")
    private String acceptOrgCode;

    /**
     * 31天内再住院计划
     */
    @TableField("again_behospital_plan")
    private String againBehospitalPlan;

    /**
     * 再住院目的
     */
    @TableField("again_behospital_goal")
    private String againBehospitalGoal;

    /**
     * 颅脑损伤患者昏迷前天数
     */
    @TableField("tbi_before_day")
    private String tbiBeforeDay;

    /**
     * 颅脑损伤患者昏迷前小时
     */
    @TableField("tbi_before_hour")
    private String tbiBeforeHour;

    /**
     * 颅脑损伤患者昏迷前分钟
     */
    @TableField("tbi_before_minute")
    private String tbiBeforeMinute;

    /**
     * 颅脑损伤患者昏迷后天数
     */
    @TableField("tbi_after_day")
    private String tbiAfterDay;

    /**
     * 颅脑损伤患者昏迷后小时
     */
    @TableField("tbi_after_hour")
    private String tbiAfterHour;

    /**
     * 颅脑损伤患者昏迷后分钟
     */
    @TableField("tbi_after_minute")
    private String tbiAfterMinute;

    /**
     * 总费用
     */
    @TableField("total_fee")
    private String totalFee;

    /**
     * 自付金额
     */
    @TableField("own_fee")
    private String ownFee;

    /**
     * 一般医疗服务费
     */
    @TableField("general_fee")
    private String generalFee;

    /**
     * 一般治疗服务费
     */
    @TableField("service_fee")
    private String serviceFee;

    /**
     * 护理费
     */
    @TableField("nurse_fee")
    private String nurseFee;

    /**
     * 其他费用
     */
    @TableField("other_fee")
    private String otherFee;

    /**
     * 病理诊断费
     */
    @TableField("pathology_fee")
    private String pathologyFee;

    /**
     * 实验室诊断费
     */
    @TableField("lab_fee")
    private String labFee;

    /**
     * 影像学诊断费
     */
    @TableField("pacs_fee")
    private String pacsFee;

    /**
     * 临床诊断项目费
     */
    @TableField("clinic_diagnose_fee")
    private String clinicDiagnoseFee;

    /**
     * 非手术治疗项目费
     */
    @TableField("not_operation_fee")
    private String notOperationFee;

    /**
     * 临床物理治疗费
     */
    @TableField("clinic_physic_fee")
    private String clinicPhysicFee;

    /**
     * 手术治疗费
     */
    @TableField("operation_treat_fee")
    private String operationTreatFee;

    /**
     * 麻醉费
     */
    @TableField("anaesthesia_fee")
    private String anaesthesiaFee;

    /**
     * 手术费
     */
    @TableField("operation_fee")
    private String operationFee;

    /**
     * 康复类
     */
    @TableField("health_type_fee")
    private String healthTypeFee;

    /**
     * 中医治疗费
     */
    @TableField("chn_treat_fee")
    private String chnTreatFee;

    /**
     * 西药费
     */
    @TableField("western_med_fee")
    private String westernMedFee;

    /**
     * 抗菌药物费用
     */
    @TableField("antibiosis_fee")
    private String antibiosisFee;

    /**
     * 中成药费
     */
    @TableField("chn_med_fee")
    private String chnMedFee;

    /**
     * 中草药费
     */
    @TableField("chn_herb_fee")
    private String chnHerbFee;

    /**
     * 血费
     */
    @TableField("blood_fee")
    private String bloodFee;

    /**
     * 白蛋白类制品费
     */
    @TableField("albumen_fee")
    private String albumenFee;

    /**
     * 球蛋白类制品费
     */
    @TableField("globulin_fee")
    private String globulinFee;

    /**
     * 凝血因子类制品费
     */
    @TableField("blood_factor_fee")
    private String bloodFactorFee;

    /**
     * 细胞因子类制品费
     */
    @TableField("cell_factor_fee")
    private String cellFactorFee;

    /**
     * 检查用一次性医用材料费
     */
    @TableField("check_material_fee")
    private String checkMaterialFee;

    /**
     * 治疗用一次性医用材料费
     */
    @TableField("treat_material_fee")
    private String treatMaterialFee;

    /**
     * 手术用一次性医用材料费
     */
    @TableField("operation_material_fee")
    private String operationMaterialFee;

    /**
     * 其他类其他费
     */
    @TableField("other_type_fee")
    private String otherTypeFee;

    /**
     * 单病种管理
     */
    @TableField("single_diag_manage")
    private String singleDiagManage;

    /**
     * 临床路径管理
     */
    @TableField("clinic_pathway_manage")
    private String clinicPathwayManage;

    /**
     * 门诊与住院
     */
    @TableField("is_outpatient_behospital")
    private String isOutpatientBehospital;

    /**
     * 入院与出院
     */
    @TableField("is_leave_behospital")
    private String isLeaveBehospital;

    /**
     * 术前与术后
     */
    @TableField("is_operation_before_after")
    private String isOperationBeforeAfter;

    /**
     * 临床与病理
     */
    @TableField("is_clinic_pathology")
    private String isClinicPathology;

    /**
     * 放射与病理
     */
    @TableField("is_radiate_pathology")
    private String isRadiatePathology;

    /**
     * 病人抢救次数
     */
    @TableField("rescue_num")
    private String rescueNum;

    /**
     * 病人抢救成功次数
     */
    @TableField("rescue_success_num")
    private String rescueSuccessNum;

    /**
     * 是否为自动出院
     */
    @TableField("is_auto_leavehospital")
    private String isAutoLeavehospital;

    /**
     * 转归情况
     */
    @TableField("return_to_type")
    private String returnToType;

    /**
     * 是否删除,N:未删除，Y:删除
     */
    @TableField("is_deleted")
    private String isDeleted;

    /**
     * 记录创建时间
     */
    @TableField("gmt_create")
    private Date gmtCreate;

    /**
     * 记录修改时间,如果时间是1970年则表示纪录未修改
     */
    @TableField("gmt_modified")
    private Date gmtModified;

    /**
     * 创建人，0表示无创建人值
     */
    @TableField("creator")
    private String creator;

    /**
     * 修改人,如果为0则表示纪录未修改
     */
    @TableField("modifier")
    private String modifier;


}
