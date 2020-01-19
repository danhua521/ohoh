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
 * 百科名医网-症状解析表
 * </p>
 *
 * @author rgb
 * @since 2020-01-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mybk_symptom_analysis")
public class SymptomAnalysis implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 百科名医网-症状id
     */
    @TableField("sym_id")
    private String symId;

    /**
     * 百科名医网-症状名称
     */
    @TableField("sym_name")
    private String symName;

    /**
     * 百科名医网-症状解析出的内容
     */
    @TableField("sym_anaytxt")
    private String symAnaytxt;

    @TableField("create_time")
    private Date createTime;

    @TableField("remark")
    private String remark;


}
