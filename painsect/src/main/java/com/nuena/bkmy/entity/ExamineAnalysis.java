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
 * 百科名医网-检查解析表
 * </p>
 *
 * @author rgb
 * @since 2020-01-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mybk_examine_analysis")
public class ExamineAnalysis implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 百科名医网-检查id
     */
    @TableField("exa_id")
    private String exaId;

    /**
     * 百科名医网-检查名称
     */
    @TableField("exa_name")
    private String exaName;

    /**
     * 百科名医网-检查解析出的内容
     */
    @TableField("exa_anaytxt")
    private String exaAnaytxt;

    @TableField("create_time")
    private Date createTime;

    @TableField("remark")
    private String remark;


}
