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
 * @since 2020-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("BR_INPATIENTINFO")
public class BrInpatientinfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 病人唯一主键ID
     */
    @TableId("BRZYID")
    private String brzyid;

    @TableField("ZZJGDM")
    private String zzjgdm;

    @TableField("BRDAXM")
    private String brdaxm;

    @TableField("BRJZHM")
    private String brjzhm;

    @TableField("ZYBQID")
    private String zybqid;

    @TableField("ZYBQMC")
    private String zybqmc;

    @TableField("ZYKSID")
    private String zyksid;

    @TableField("ZYKSMC")
    private String zyksmc;

    @TableField("ZYCWID")
    private String zycwid;

    @TableField("ZYCWHM")
    private String zycwhm;

    @TableField("BRLBID")
    private String brlbid;

    @TableField("BRXZID")
    private String brxzid;

    @TableField("BRRYRQ")
    private Date brryrq;

    @TableField("BRCYRQ")
    private Date brcyrq;

    @TableField("BRDABH")
    private String brdabh;

    @TableField("BRDAXB")
    private String brdaxb;

    @TableField("BRCSRQ")
    private Date brcsrq;

    @TableField("JBDMID")
    private String jbdmid;

    @TableField("JBMSXX")
    private String jbmsxx;

    @TableField("ZZYSID")
    private String zzysid;

    @TableField("ZZYSXM")
    private String zzysxm;

    @TableField("CJCXRQ")
    private Date cjcxrq;

    @TableField("CJLSHM")
    private String cjlshm;


}
