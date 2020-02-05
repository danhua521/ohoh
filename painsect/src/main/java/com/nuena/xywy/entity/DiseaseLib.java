package com.nuena.xywy.entity;

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
 * 寻医问药网—疾病库
 * </p>
 *
 * @author rgb
 * @since 2020-02-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("xywy_disease_lib")
public class DiseaseLib implements Serializable {

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
     * 并发症--url
     */
    @TableField("complication_url")
    private String complicationUrl;

    /**
     * 并发症--html
     */
    @TableField("complication_html")
    private String complicationHtml;

    /**
     * 并发症--html解析出的内容
     */
    @TableField("complication_anaytxt")
    private String complicationAnaytxt;

    /**
     * 症状--url
     */
    @TableField("symptom_url")
    private String symptomUrl;

    /**
     * 症状--html
     */
    @TableField("symptom_html")
    private String symptomHtml;

    /**
     * 症状--html解析出的内容
     */
    @TableField("symptom_anaytxt")
    private String symptomAnaytxt;

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
     * 诊断鉴别--url
     */
    @TableField("discern_url")
    private String discernUrl;

    /**
     * 诊断鉴别--html
     */
    @TableField("discern_html")
    private String discernHtml;

    /**
     * 诊断鉴别--html解析出的内容
     */
    @TableField("discern_anaytxt")
    private String discernAnaytxt;

    /**
     * 治疗--url
     */
    @TableField("treat_url")
    private String treatUrl;

    /**
     * 治疗--html
     */
    @TableField("treat_html")
    private String treatHtml;

    /**
     * 治疗--html解析出的内容
     */
    @TableField("treat_anaytxt")
    private String treatAnaytxt;

    /**
     * 护理--url
     */
    @TableField("nurse_url")
    private String nurseUrl;

    /**
     * 护理--html
     */
    @TableField("nurse_html")
    private String nurseHtml;

    /**
     * 护理--html解析出的内容
     */
    @TableField("nurse_anaytxt")
    private String nurseAnaytxt;

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
