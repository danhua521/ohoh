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
 * 病人手术信息
 * </p>
 *
 * @author rgb
 * @since 2020-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("BR_RECOPERATION")
public class BrRecoperation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 病案首页编号BASYID
     */
    @TableId("BASYID")
    private String basyid;

    /**
     * 病人手术序号BRSSXH
     */
    @TableField("BRSSXH")
    private String brssxh;

    /**
     * 病人手术日期BRSSRQ
     */
    @TableField("BRSSRQ")
    private Date brssrq;

    /**
     * 术前住院天数SQZYTS
     */
    @TableField("SQZYTS")
    private String sqzyts;

    /**
     * 病人手术组号BRSSZH
     */
    @TableField("BRSSZH")
    private String brsszh;

    /**
     * 手术代码编码SSDMID
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
     * 手术级别ID
     */
    @TableField("SSJBID")
    private String ssjbid;

    /**
     * 手术医生编码SSYSID
     */
    @TableField("SSYSID")
    private String ssysid;

    /**
     * 手术医生名称
     */
    @TableField("SSYSMC")
    private String ssysmc;

    /**
     * 一助护士编码YZHSID
     */
    @TableField("YZHSID")
    private String yzhsid;

    /**
     * 一助护士名称
     */
    @TableField("YZHSMC")
    private String yzhsmc;

    /**
     * 二助护士编码EZHSID
     */
    @TableField("EZHSID")
    private String ezhsid;

    /**
     * 二助护士名称
     */
    @TableField("EZHSMC")
    private String ezhsmc;

    /**
     * 三助护士编码
     */
    @TableField("SZHSID")
    private String szhsid;

    /**
     * 三助护士名称
     */
    @TableField("SZHSMC")
    private String szhsmc;

    /**
     * 麻醉方法代码MZFFDM
     */
    @TableField("MZFFDM")
    private String mzffdm;

    /**
     * 麻醉方法名称
     */
    @TableField("MZFFMC")
    private String mzffmc;

    /**
     * 麻醉医生编码MZYSID
     */
    @TableField("MZYSID")
    private String mzysid;

    /**
     * 麻醉医生名称
     */
    @TableField("MZYSMC")
    private String mzysmc;

    /**
     * 麻醉医生编码2MZYSID2
     */
    @TableField("MZYSID2")
    private String mzysid2;

    /**
     * 麻醉医生姓名
     */
    @TableField("MZYSMC2")
    private String mzysmc2;

    /**
     * 切口等级编码QKDJDM
     */
    @TableField("QKDJDM")
    private String qkdjdm;

    /**
     * 愈合等级编码YHDJDM
     */
    @TableField("YHDJDM")
    private String yhdjdm;

    /**
     * 手术符合判别SSFHPB
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
