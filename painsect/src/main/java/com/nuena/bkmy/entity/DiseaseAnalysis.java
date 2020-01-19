package com.nuena.bkmy.entity;

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
 * 百科名医网-疾病解析表
 * </p>
 *
 * @author rgb
 * @since 2020-01-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mybk_disease_analysis")
public class DiseaseAnalysis implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 百科名医网-疾病id
     */
    @TableField("dis_id")
    private String disId;

    /**
     * 百科名医网-疾病名称
     */
    @TableField("dis_name")
    private String disName;

    /**
     * 百科名医网-疾病解析出的内容
     */
    @TableField("dis_anaytxt")
    private String disAnaytxt;

    @TableField("create_time")
    private Date createTime;

    @TableField("remark")
    private String remark;


}
