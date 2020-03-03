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
 * 久久健康网—症状-病因
 * </p>
 *
 * @author rgb
 * @since 2020-03-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jjjk_symptom_etiology")
public class JjjkSymptomEtiology implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 症状库主表id
     */
    @TableField("sym_lib_id")
    private Long symLibId;

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
