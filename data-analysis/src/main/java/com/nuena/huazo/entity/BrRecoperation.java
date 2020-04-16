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
@TableName("BR_RECOPERATION")
public class BrRecoperation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 病案首页编号
     */
    @TableId("BASYID")
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
     * 手术代码编码
     */
    @TableField("SSDMID")
    private String ssdmid;

    /**
     * 手术医生编码
     */
    @TableField("SSYSID")
    private String ssysid;

    /**
     * 一助护士编码
     */
    @TableField("YZHSID")
    private String yzhsid;

    /**
     * 二助护士编码
     */
    @TableField("EZHSID")
    private String ezhsid;

    /**
     * 麻醉方法代码
     */
    @TableField("MZFFDM")
    private String mzffdm;

    /**
     * 麻醉医生编码
     */
    @TableField("MZYSID")
    private String mzysid;

    /**
     * 切口等级编码
     */
    @TableField("QKDJDM")
    private String qkdjdm;

    /**
     * 愈合等级编码
     */
    @TableField("YHDJDM")
    private String yhdjdm;

    /**
     * 手术符合判别
     */
    @TableField("SSFHPB")
    private String ssfhpb;

    /**
     * 麻醉医生编码2
     */
    @TableField("MZYSID2")
    private String mzysid2;

    /**
     * 病人手术名称
     */
    @TableField("BRSSMC")
    private String brssmc;

    /**
     * 手术级别
     */
    @TableField("SSJBID")
    private String ssjbid;

    /**
     * 麻醉方法名称
     */
    @TableField("MZFFMC")
    private String mzffmc;

    /**
     * 拟手术名称
     */
    @TableField("NSSMC")
    private String nssmc;

    /**
     * 三助护士编码
     */
    @TableField("SZHSID")
    private String szhsid;

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

    /**
     * 取数据的修改时间，根据这个时间进行数据同步
     */
    @TableField("CJCXRQ")
    private Date cjcxrq;


}
