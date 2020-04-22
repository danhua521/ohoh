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
 * 病人医嘱
 * </p>
 *
 * @author rgb
 * @since 2020-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("BR_DOCTADVICE")
public class BrDoctadvice implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 病人医嘱ID
     */
    @TableField("BRYZID")
    private String bryzid;

    /**
     * 病人ID
     */
    @TableField("BRZYID")
    private String brzyid;

    /**
     * 医生开单判别
     */
    @TableField("YSKDPB")
    private String yskdpb;

    /**
     * 医嘱频率判别
     */
    @TableField("YZPLPB")
    private String yzplpb;

    /**
     * 父类医嘱ID
     */
    @TableField("FLYZID")
    private String flyzid;

    /**
     * 医嘱类型判别
     */
    @TableField("YZLXPB")
    private String yzlxpb;

    /**
     * 一次使用数量
     */
    @TableField("YCSYSL")
    private String ycsysl;

    /**
     * 一次用量单位
     */
    @TableField("YCYLDW")
    private String ycyldw;

    /**
     * 医嘱单次剂量
     */
    @TableField("YZDCJL")
    private String yzdcjl;

    /**
     * 单次剂量单位
     */
    @TableField("DCJLDW")
    private String dcjldw;

    /**
     * 给药方式ID
     */
    @TableField("GYFSID")
    private String gyfsid;

    /**
     * 医嘱处理类型
     */
    @TableField("YZCLLX")
    private String yzcllx;

    /**
     * 医嘱开始时间
     */
    @TableField("YZKSSJ")
    private Date yzkssj;

    /**
     * 医嘱项目名称
     */
    @TableField("YZXMMC")
    private String yzxmmc;

    /**
     * 医嘱状态判别
     */
    @TableField("YZZTPB")
    private String yzztpb;

    /**
     * 医嘱结束时间
     */
    @TableField("YZJSSJ")
    private Date yzjssj;

    /**
     * 医嘱同组序号
     */
    @TableField("YZTZXH")
    private String yztzxh;

    /**
     * 医嘱处方类型
     */
    @TableField("YZCFLX")
    private String yzcflx;

    /**
     * 医嘱领药类型
     */
    @TableField("YZLYLX")
    private String yzlylx;

    /**
     * 医生嘱托
     */
    @TableField("YSZTSM")
    private String ysztsm;

    /**
     * 医院id
     */
    @TableField("ZZJGDM")
    private String zzjgdm;

    /**
     * 开单医生ID
     */
    @TableField("KDYSID")
    private String kdysid;

    /**
     * 开单医生姓名
     */
    @TableField("KDYSMC")
    private String kdysmc;

    @TableField("CJCXRQ")
    private Date cjcxrq;


}
