package com.nuena.lantone.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.sql.Blob;
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
 * @since 2020-04-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("qc_brecord")
public class Brecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("bljlid")
    private String bljlid;

    @TableField("bljlnr")
    private Blob bljlnr;

    @TableField("cjcxrq")
    private Date cjcxrq;

    @TableField("cjlshm")
    private String cjlshm;

    @TableField("personid")
    private String personid;

    @TableField("xmlnr")
    private String xmlnr;


}
