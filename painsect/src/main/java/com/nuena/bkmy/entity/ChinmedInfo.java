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
 * 百科名医网-中医信息表
 * </p>
 *
 * @author rgb
 * @since 2020-01-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mybk_chinmed_info")
public class ChinmedInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 百科名医网-中医id
     */
    @TableField("chd_id")
    private String chdId;

    /**
     * 百科名医网-中医名称
     */
    @TableField("chd_name")
    private String chdName;

    /**
     * 百科名医网-中医url
     */
    @TableField("chd_url")
    private String chdUrl;

    @TableField("create_time")
    private Date createTime;

    @TableField("remark")
    private String remark;


}
