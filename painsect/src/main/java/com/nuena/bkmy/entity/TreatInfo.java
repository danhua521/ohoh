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
 * 百科名医网-治疗信息表
 * </p>
 *
 * @author rgb
 * @since 2020-01-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mybk_treat_info")
public class TreatInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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

    @TableField("create_time")
    private Date createTime;

    @TableField("remark")
    private String remark;


}
