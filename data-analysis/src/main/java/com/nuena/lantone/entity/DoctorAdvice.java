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
 * 病人医嘱
 * </p>
 *
 * @author rgb
 * @since 2020-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("med_doctor_advice")
public class DoctorAdvice implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 病人医嘱ID
     */
    @TableId("doctor_advice_id")
    private String doctorAdviceId;

    /**
     * 医院id
     */
    @TableField("hospital_id")
    private Long hospitalId;

    /**
     * 病人ID
     */
    @TableField("behospital_code")
    private String behospitalCode;

    /**
     * 医生开单判别
     */
    @TableField("order_doctor_name")
    private String orderDoctorName;

    /**
     * 医嘱频率判别
     */
    @TableField("frequency")
    private String frequency;

    /**
     * 父类医嘱ID
     */
    @TableField("parent_type_id")
    private String parentTypeId;

    /**
     * 医嘱类型判别(嘱托长嘱、长期医嘱等)
     */
    @TableField("doctor_advice_type")
    private String doctorAdviceType;

    /**
     * 一次使用数量
     */
    @TableField("usage_num")
    private String usageNum;

    /**
     * 一次用量单位
     */
    @TableField("usage_unit")
    private String usageUnit;

    /**
     * 医嘱单次剂量
     */
    @TableField("dose")
    private String dose;

    /**
     * 单次剂量单位
     */
    @TableField("dose_unit")
    private String doseUnit;

    /**
     * 给药方式
     */
    @TableField("med_mode_type")
    private String medModeType;

    /**
     * 医嘱频率
     */
    @TableField("da_frequency")
    private String daFrequency;

    /**
     * 医嘱处理类型
     */
    @TableField("da_deal_type")
    private String daDealType;

    /**
     * 医嘱开始时间
     */
    @TableField("da_start_date")
    private Date daStartDate;

    /**
     * 医嘱项目名称
     */
    @TableField("da_item_name")
    private String daItemName;

    /**
     * 医嘱状态判别
     */
    @TableField("da_status")
    private String daStatus;

    /**
     * 医嘱结束时间
     */
    @TableField("da_stop_date")
    private Date daStopDate;

    /**
     * 医嘱同组序号
     */
    @TableField("da_group_no")
    private String daGroupNo;

    /**
     * 医嘱处方类型(检验、描述医嘱、膳食、西药、护理等)
     */
    @TableField("da_prescription_type")
    private String daPrescriptionType;

    /**
     * 医嘱领药类型
     */
    @TableField("da_med_type")
    private String daMedType;

    /**
     * 医生嘱托
     */
    @TableField("doctor_notice")
    private String doctorNotice;

    /**
     * 开单医生ID
     */
    @TableField("doctor_id")
    private String doctorId;

    /**
     * 开单医生姓名
     */
    @TableField("doctor_name")
    private String doctorName;

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
