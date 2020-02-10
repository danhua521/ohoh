package com.nuena.myzx.entity;

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
 * 名医在线—疾病库
 * </p>
 *
 * @author rgb
 * @since 2020-02-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("myzx_disease_lib")
public class MyzxDiseaseLib implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 疾病id
     */
    @TableField("dis_id")
    private String disId;

    /**
     * 疾病名称
     */
    @TableField("dis_name")
    private String disName;

    /**
     * 疾病url
     */
    @TableField("dis_url")
    private String disUrl;

    /**
     * 疾病html
     */
    @TableField("dis_html")
    private String disHtml;

    /**
     * 疾病html解析汇总
     */
    @TableField("dis_anay_content")
    private String disAnayContent;

    /**
     * 简介
     */
    @TableField("synopsis")
    private String synopsis;

    /**
     * 病因
     */
    @TableField("etiology")
    private String etiology;

    /**
     * 症状
     */
    @TableField("symptom")
    private String symptom;

    /**
     * 检查
     */
    @TableField("examine")
    private String examine;

    /**
     * 诊断鉴别
     */
    @TableField("discern")
    private String discern;

    /**
     * 并发症
     */
    @TableField("complication")
    private String complication;

    /**
     * 预防
     */
    @TableField("prevent")
    private String prevent;

    /**
     * 治疗
     */
    @TableField("treat")
    private String treat;

    @TableField("create_time")
    private Date createTime;

    @TableField("modify_time")
    private Date modifyTime;

    /**
     * 所有html是否下载完成  0-否  1-是
     */
    @TableField("is_htmls_load")
    private Integer isHtmlsLoad;

    /**
     * 所有html是否解析完成  0-否  1-是
     */
    @TableField("is_htmls_anay")
    private Integer isHtmlsAnay;

    @TableField("remark")
    private String remark;


}
