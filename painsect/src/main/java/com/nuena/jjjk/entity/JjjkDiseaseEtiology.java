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
 * 久久健康网—疾病-病因
 * </p>
 *
 * @author rgb
 * @since 2020-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jjjk_disease_etiology")
public class JjjkDiseaseEtiology implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 疾病库主表id
     */
    @TableField("dis_lib_id")
    private Long disLibId;

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

    @TableField("create_time")
    private Date createTime;

    @TableField("modify_time")
    private Date modifyTime;

    @TableField("remark")
    private String remark;


}
