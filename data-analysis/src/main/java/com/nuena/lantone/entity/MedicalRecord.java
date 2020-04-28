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
 * @since 2020-04-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("med_medical_record")
public class MedicalRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("rec_id")
    private String recId;

    /**
     * 医院ID
     */
    @TableField("hospital_id")
    private Long hospitalId;

    /**
     * 病人住院ID
     */
    @TableField("behospital_code")
    private String behospitalCode;

    /**
     * 组织机构代码
     */
    @TableField("org_code")
    private String orgCode;

    /**
     * 病历类别编号
     */
    @TableField("rec_type_id")
    private String recTypeId;

    /**
     * 模块id
     */
    @TableField("mode_id")
    private Long modeId;

    /**
     * 病历日期
     */
    @TableField("rec_date")
    private String recDate;

    /**
     * 病历标题
     */
    @TableField("rec_title")
    private String recTitle;

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
