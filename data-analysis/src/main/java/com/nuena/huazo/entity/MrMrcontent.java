package com.nuena.huazo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author rgb
 * @since 2020-04-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("MR_MRCONTENT")
public class MrMrcontent implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("BLJLID")
    private String bljlid;

    @TableField("BLJLNR")
    private String bljlnr;

    @TableField("CJCXRQ")
    private Date cjcxrq;

    @TableField("CJLSHM")
    private String cjlshm;

    @TableField("HTMLSJ")
    private String htmlsj;


}
