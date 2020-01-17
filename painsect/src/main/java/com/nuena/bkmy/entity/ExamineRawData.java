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
 * 百科名医网-检查html表
 * </p>
 *
 * @author rgb
 * @since 2020-01-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mybk_examine_raw_data")
public class ExamineRawData implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

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
     * 百科名医网-检查url
     */
    @TableField("exa_url")
    private String exaUrl;

    /**
     * 百科名医网-检查html
     */
    @TableField("exa_html")
    private String exaHtml;

    @TableField("create_time")
    private Date createTime;

    @TableField("remark")
    private String remark;


}
