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
 * 病案首页手术
 * </p>
 *
 * @author rgb
 * @since 2020-04-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("BR_RECOPERATION")
public class BrRecoperation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 病案首页编号
     */
    @TableField("BASYID")
    private String basyid;

    /**
     * 病人手术序号
     */
    @TableField("BRSSXH")
    private String brssxh;

    /**
     * 病人手术日期
     */
    @TableField("BRSSRQ")
    private Date brssrq;

    /**
     * 术前住院天数
     */
    @TableField("SQZYTS")
    private String sqzyts;

    /**
     * 病人手术组号
     */
    @TableField("BRSSZH")
    private String brsszh;

    /**
     * 手术代码id
     */
    @TableField("SSDMID")
    private String ssdmid;

    /**
     * 病人手术名称
     */
    @TableField("BRSSMC")
    private String brssmc;

    /**
     * 拟手术名称
     */
    @TableField("NSSMC")
    private String nssmc;

    /**
     * 手术级别
     */
    @TableField("SSJBID")
    private String ssjbid;

    /**
     * 手术医生id
     */
    @TableField("SSYSID")
    private String ssysid;

    /**
     * 手术医生姓名
     */
    @TableField("SSYSMC")
    private String ssysmc;

    /**
     * 手术一助id
     */
    @TableField("YZHSID")
    private String yzhsid;

    /**
     * 手术一助姓名
     */
    @TableField("YZHSMC")
    private String yzhsmc;

    /**
     * 手术二助id
     */
    @TableField("EZHSID")
    private String ezhsid;

    /**
     * 手术二助姓名
     */
    @TableField("EZHSMC")
    private String ezhsmc;

    /**
     * 手术三助id
     */
    @TableField("SZHSID")
    private String szhsid;

    /**
     * 手术三助姓名
     */
    @TableField("SZHSMC")
    private String szhsmc;

    /**
     * 麻醉方法代码
     */
    @TableField("MZFFDM")
    private String mzffdm;

    /**
     * 麻醉方法名称
     */
    @TableField("MZFFMC")
    private String mzffmc;

    /**
     * 麻醉医生id
     */
    @TableField("MZYSID")
    private String mzysid;

    /**
     * 麻醉医生姓名
     */
    @TableField("MZYSMC")
    private String mzysmc;

    /**
     * 麻醉医生id2
     */
    @TableField("MZYSID2")
    private String mzysid2;

    /**
     * 麻醉医生姓名
     */
    @TableField("MZYSMC2")
    private String mzysmc2;

    /**
     * 切口等级
     */
    @TableField("QKDJDM")
    private String qkdjdm;

    /**
     * 愈合等级
     */
    @TableField("YHDJDM")
    private String yhdjdm;

    /**
     * 手术符合判别
     */
    @TableField("SSFHPB")
    private String ssfhpb;

    /**
     * 开始时间
     */
    @TableField("SSKSSJ")
    private Date sskssj;

    /**
     * 结束时间
     */
    @TableField("SSJSSJ")
    private Date ssjssj;

    /**
     * ASA分级
     */
    @TableField("ASAFJ")
    private String asafj;

    /**
     * 风险等级
     */
    @TableField("SSFXDJ")
    private String ssfxdj;

    /**
     * 输血量
     */
    @TableField("SSSHUXL")
    private String ssshuxl;

    /**
     * 失血量
     */
    @TableField("SSSHIXL")
    private String ssshixl;

    @TableField("CJCXRQ")
    private Date cjcxrq;


}
