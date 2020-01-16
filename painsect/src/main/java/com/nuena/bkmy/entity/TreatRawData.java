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
 * 百科名医网-治疗html表
 * </p>
 *
 * @author rgb
 * @since 2020-01-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mybk_treat_raw_data")
public class TreatRawData implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 百科名医网-治疗id
     */
    @TableField("trt_id")
    private String trtId;

    /**
     * 百科名医网-治疗名称
     */
    @TableField("trt_name")
    private String trtName;

    /**
     * 百科名医网-治疗url
     */
    @TableField("trt_url")
    private String trtUrl;

    /**
     * 百科名医网-治疗html
     */
    @TableField("trt_html")
    private String trtHtml;

    @TableField("create_time")
    private Date createTime;

    @TableField("remark")
    private String remark;


}
