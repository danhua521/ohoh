package com.nuena.sjjk.entity;

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
 * 39健康网—疾病-预防
 * </p>
 *
 * @author rgb
 * @since 2020-02-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sjjk_disease_prevent")
public class SjjkDiseasePrevent implements Serializable {

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

    @TableField("create_time")
    private Date createTime;

    @TableField("modify_time")
    private Date modifyTime;

    @TableField("remark")
    private String remark;


}
