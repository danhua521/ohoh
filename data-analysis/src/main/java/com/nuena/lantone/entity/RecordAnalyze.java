package com.nuena.lantone.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 医院病历文书类型细分类主表
 * </p>
 *
 * @author rgb
 * @since 2020-04-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("med_record_analyze")
public class RecordAnalyze implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 医院ID
     */
    @TableField("hospital_id")
    private Long hospitalId;

    /**
     * 医院病历文书类型细分类名称
     */
    @TableField("name")
    private String name;

    /**
     * 数据模块ID
     */
    @TableField("mode_id")
    private Long modeId;

    /**
     * 数据模块名称
     */
    @TableField("mode_name")
    private String modeName;

    /**
     * 医院病历文书类型
     */
    @TableField("rec_type")
    private String recType;

    /**
     * 病历号示例
     */
    @TableField("behospital_codes")
    private String behospitalCodes;

    /**
     * 医院病历文书类型细分类
     */
    @TableField("map_type")
    private String mapType;

    /**
     * key集合，用英文逗号隔开
     */
    @TableField("map_keys")
    private String mapKeys;

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

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;


}
