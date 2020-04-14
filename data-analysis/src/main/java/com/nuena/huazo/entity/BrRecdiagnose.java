package com.nuena.huazo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 病案诊断信息
 * </p>
 *
 * @author rgb
 * @since 2020-04-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("BR_RECDIAGNOSE")
public class BrRecdiagnose implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 病案首页编号BASYID
     */
    @TableField("BASYID")
    private String basyid;

    /**
     * 病案诊断序号BAZDXH
     */
    @TableField("BAZDXH")
    private String bazdxh;

    /**
     * 诊断类别编码ZDLBDM
     */
    @TableField("ZDLBDM")
    private String zdlbdm;

    /**
     * 主次诊断判别ZCZDPB
     */
    @TableField("ZCZDPB")
    private String zczdpb;

    /**
     * 疾病代码序号JBDMID
     */
    @TableField("JBDMID")
    private String jbdmid;

    /**
     * 诊断疾病名称ZDJBMC
     */
    @TableField("ZDJBMC")
    private String zdjbmc;

    /**
     * ICD码
     */
    @TableField("ICDM")
    private String icdm;

    /**
     * 转归情况
     */
    @TableField("ZGQKDM")
    private String zgqkdm;

    /**
     * 入院情况
     */
    @TableField("RYQKBM")
    private String ryqkbm;

    /**
     * 病理诊断编号
     */
    @TableField("BLZDBH")
    private String blzdbh;

    /**
     * 诊断符合情况
     */
    @TableField("ZDFHPB")
    private String zdfhpb;

    @TableField("CJCXRQ")
    private Date cjcxrq;


}
