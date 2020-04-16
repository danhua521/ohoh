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
@TableName("MR_MEDICALRECORDS")
public class MrMedicalrecords implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("BLJLID")
    private String bljlid;

    @TableField("BLCJID")
    private String blcjid;

    @TableField("ZZJGDM")
    private String zzjgdm;

    @TableField("BRZYID")
    private String brzyid;

    @TableField("MQZYID")
    private String mqzyid;

    @TableField("BLLBID")
    private String bllbid;

    @TableField("BLMBID")
    private String blmbid;

    @TableField("BCJLSJ")
    private String bcjlsj;

    @TableField("BLJLMC")
    private String bljlmc;

    @TableField("SFZFPB")
    private String sfzfpb;

    @TableField("CJYHDM")
    private String cjyhdm;

    @TableField("CJCZSJ")
    private Date cjczsj;

    @TableField("XGYHDM")
    private String xgyhdm;

    @TableField("XGCZSJ")
    private Date xgczsj;

    @TableField("BLZCBZ")
    private String blzcbz;

    @TableField("BLLBMC")
    private String bllbmc;

    @TableField("XGYHMC")
    private String xgyhmc;

    @TableField("CJCXRQ")
    private Date cjcxrq;

    @TableField("CJLSHM")
    private String cjlshm;


}
