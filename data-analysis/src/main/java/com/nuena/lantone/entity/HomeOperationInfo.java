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
 * @since 2020-04-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("med_home_operation_info")
public class HomeOperationInfo implements Serializable {

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
     * 手术序号
     */
    @TableField("operation_order_no")
    private String operationOrderNo;

    /**
     * 手术日期
     */
    @TableField("operation_date")
    private Date operationDate;

    /**
     * 手术编码
     */
    @TableField("operation_code")
    private String operationCode;

    /**
     * 手术医生
     */
    @TableField("operation_doctor_id")
    private String operationDoctorId;

    /**
     * 一助医生
     */
    @TableField("first_assistant_id")
    private String firstAssistantId;

    /**
     * 二助医生
     */
    @TableField("second_assistant_id")
    private String secondAssistantId;

    /**
     * 切口等级
     */
    @TableField("cut_level")
    private String cutLevel;

    /**
     * 愈合等级
     */
    @TableField("healing_level")
    private String healingLevel;

    /**
     * 手术名称
     */
    @TableField("operation_name")
    private String operationName;

    /**
     * 手术级别
     */
    @TableField("operation_level")
    private String operationLevel;

    /**
     * 麻醉方式
     */
    @TableField("anaesthesia_name")
    private String anaesthesiaName;

    /**
     * 拟手术名称
     */
    @TableField("sham_operation_name")
    private String shamOperationName;

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
