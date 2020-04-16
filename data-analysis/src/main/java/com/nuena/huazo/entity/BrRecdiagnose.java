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
@TableName("BR_RECDIAGNOSE")
public class BrRecdiagnose implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 病案首页编号
     */
    @TableId("BASYID")
    private String basyid;

    /**
     * 病案诊断序号
     */
    @TableField("BAZDXH")
    private String bazdxh;

    /**
     * 诊断类别编码
     */
    @TableField("ZDLBDM")
    private String zdlbdm;

    /**
     * 主次诊断判别
     */
    @TableField("ZCZDPB")
    private String zczdpb;

    /**
     * 疾病代码序号
     */
    @TableField("JBDMID")
    private String jbdmid;

    /**
     * 诊断疾病名称
     */
    @TableField("ZDJBMC")
    private String zdjbmc;

    /**
     * ICD码
     */
    @TableField("ICDM")
    private String icdm;

    /**
     * 转归情况编码
     */
    @TableField("ZGQKDM")
    private String zgqkdm;

    /**
     * 入院情况编码
     */
    @TableField("RYQKBM")
    private String ryqkbm;

    /**
     * 病理诊断编号
     */
    @TableField("BLZDBH")
    private String blzdbh;

    /**
     * 诊断符合判别
     */
    @TableField("ZDFHPB")
    private String zdfhpb;

    /**
     * 取数据的修改时间，根据这个时间进行数据同步
     */
    @TableField("CJCXRQ")
    private Date cjcxrq;


}
