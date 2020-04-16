package com.nuena.lantone.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
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
 * @since 2020-04-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("med_home_diagnose_info")
public class HomeDiagnoseInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 病案首页ID
     */
    @TableField("home_page_id")
    private String homePageId;

    /**
     * 医院ID
     */
    @TableField("hospital_id")
    private Long hospitalId;

    /**
     * 诊断序号
     */
    @TableField("diagnose_order_no")
    private String diagnoseOrderNo;

    /**
     * 诊断类别
     */
    @TableField("diagnose_type")
    private String diagnoseType;

    /**
     * 诊断判别
     */
    @TableField("diagnose_type_short")
    private String diagnoseTypeShort;

    /**
     * 诊断名称
     */
    @TableField("diagnose_name")
    private String diagnoseName;

    /**
     * 入院情况
     */
    @TableField("behospital_type")
    private String behospitalType;

    /**
     * 出院情况
     */
    @TableField("leave_hospital_type")
    private String leaveHospitalType;

    /**
     * 病理号
     */
    @TableField("pathology_diagnose")
    private String pathologyDiagnose;

    /**
     * 诊断编码
     */
    @TableField("icd_code")
    private String icdCode;

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
