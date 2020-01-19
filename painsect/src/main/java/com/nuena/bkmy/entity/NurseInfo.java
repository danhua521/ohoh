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
 * 百科名医网-护理信息表
 * </p>
 *
 * @author rgb
 * @since 2020-01-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mybk_nurse_info")
public class NurseInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 百科名医网-护理id
     */
    @TableField("nur_id")
    private String nurId;

    /**
     * 百科名医网-护理名称
     */
    @TableField("nur_name")
    private String nurName;

    /**
     * 百科名医网-护理url
     */
    @TableField("nur_url")
    private String nurUrl;

    @TableField("create_time")
    private Date createTime;

    @TableField("remark")
    private String remark;


}
