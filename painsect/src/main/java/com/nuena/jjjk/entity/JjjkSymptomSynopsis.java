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
 * 久久健康网—症状-简介
 * </p>
 *
 * @author rgb
 * @since 2020-03-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jjjk_symptom_synopsis")
public class JjjkSymptomSynopsis implements Serializable {

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

    @TableField("create_time")
    private Date createTime;

    @TableField("modify_time")
    private Date modifyTime;

    @TableField("remark")
    private String remark;


}
