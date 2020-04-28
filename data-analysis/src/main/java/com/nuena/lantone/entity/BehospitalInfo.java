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
 * 住院病历信息
 * </p>
 *
 * @author rgb
 * @since 2020-04-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("med_behospital_info")
public class BehospitalInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 病人住院ID
     */
    @TableId("behospital_code")
    private String behospitalCode;

    /**
     * 医院ID
     */
    @TableField("hospital_id")
    private Long hospitalId;

    /**
     * 姓名
     */
    @TableField("name")
    private String name;

    /**
     * 性别（男，女）
     */
    @TableField("sex")
    private String sex;

    /**
     * 出生日期
     */
    @TableField("birthday")
    private Date birthday;

    /**
     * 档案号
     */
    @TableField("file_code")
    private String fileCode;

    /**
     * 质控类型
     */
    @TableField("qc_type_id")
    private Long qcTypeId;

    /**
     * 病区编码
     */
    @TableField("ward_code")
    private String wardCode;

    /**
     * 病区名称
     */
    @TableField("ward_name")
    private String wardName;

    /**
     * 住院科室ID
     */
    @TableField("beh_dept_id")
    private String behDeptId;

    /**
     * 住院科室名称
     */
    @TableField("beh_dept_name")
    private String behDeptName;

    /**
     * 床位号
     */
    @TableField("bed_code")
    private String bedCode;

    /**
     * 床位名称
     */
    @TableField("bed_name")
    private String bedName;

    /**
     * 医保类别
     */
    @TableField("insurance_name")
    private String insuranceName;

    /**
     * 职业
     */
    @TableField("job_type")
    private String jobType;

    /**
     * 入院时间
     */
    @TableField("behospital_date")
    private Date behospitalDate;

    /**
     * 出院时间
     */
    @TableField("leave_hospital_date")
    private Date leaveHospitalDate;

    /**
     * 疾病ICD编码
     */
    @TableField("diagnose_icd")
    private String diagnoseIcd;

    /**
     * 疾病名称
     */
    @TableField("diagnose")
    private String diagnose;

    /**
     * 住院医生ID
     */
    @TableField("beh_doctor_id")
    private String behDoctorId;

    /**
     * 住院医生姓名
     */
    @TableField("beh_doctor_name")
    private String behDoctorName;

    /**
     * 主治医生ID
     */
    @TableField("doctor_id")
    private String doctorId;

    /**
     * 主治医生姓名
     */
    @TableField("doctor_name")
    private String doctorName;

    /**
     * 主任医生ID
     */
    @TableField("director_doctor_id")
    private String directorDoctorId;

    /**
     * 主任医生姓名
     */
    @TableField("director_doctor_name")
    private String directorDoctorName;

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
