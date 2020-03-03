package com.nuena.jjjk.entity;

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
 * 久久健康网—症状库
 * </p>
 *
 * @author rgb
 * @since 2020-03-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jjjk_symptom_lib")
public class JjjkSymptomLib implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 症状id
     */
    @TableField("sym_id")
    private String symId;

    /**
     * 症状名称
     */
    @TableField("sym_name")
    private String symName;

    /**
     * 简介--url
     */
    @TableField("synopsis_url")
    private String synopsisUrl;

    /**
     * 简介--html
     */
    @TableField("synopsis_html")
    private String synopsisHtml;

    /**
     * 简介--html解析出的内容
     */
    @TableField("synopsis_anaytxt")
    private String synopsisAnaytxt;

    /**
     * 病因--url
     */
    @TableField("etiology_url")
    private String etiologyUrl;

    /**
     * 病因--html
     */
    @TableField("etiology_html")
    private String etiologyHtml;

    /**
     * 病因--html解析出的内容
     */
    @TableField("etiology_anaytxt")
    private String etiologyAnaytxt;

    /**
     * 预防--url
     */
    @TableField("prevent_url")
    private String preventUrl;

    /**
     * 预防--html
     */
    @TableField("prevent_html")
    private String preventHtml;

    /**
     * 预防--html解析出的内容
     */
    @TableField("prevent_anaytxt")
    private String preventAnaytxt;

    /**
     * 检查--url
     */
    @TableField("examine_url")
    private String examineUrl;

    /**
     * 检查--html
     */
    @TableField("examine_html")
    private String examineHtml;

    /**
     * 检查--html解析出的内容
     */
    @TableField("examine_anaytxt")
    private String examineAnaytxt;

    /**
     * 饮食保健--url
     */
    @TableField("health_url")
    private String healthUrl;

    /**
     * 饮食保健--html
     */
    @TableField("health_html")
    private String healthHtml;

    /**
     * 饮食保健--html解析出的内容
     */
    @TableField("health_anaytxt")
    private String healthAnaytxt;

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
