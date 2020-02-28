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
 * 久久健康网—疾病-简介
 * </p>
 *
 * @author rgb
 * @since 2020-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jjjk_disease_synopsis")
public class JjjkDiseaseSynopsis implements Serializable {

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
