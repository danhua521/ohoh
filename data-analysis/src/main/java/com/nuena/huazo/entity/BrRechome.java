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
@TableName("BR_RECHOME")
public class BrRechome implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 病案首页编号
     */
    @TableId("BASYID")
    private String basyid;

    /**
     * 病人住院序号
     */
    @TableField("BRZYID")
    private String brzyid;

    /**
     * 组织机构id
     */
    @TableField("ZZJGID")
    private String zzjgid;

    /**
     * 医疗机构名称
     */
    @TableField("ZZJGMC")
    private String zzjgmc;

    /**
     * 医疗机构代码
     */
    @TableField("ZZJGBH")
    private String zzjgbh;

    /**
     * 医疗付费方式
     */
    @TableField("YLFKLB")
    private String ylfklb;

    /**
     * 健康卡号
     */
    @TableField("BRJKKH")
    private String brjkkh;

    /**
     * 住院次数
     */
    @TableField("BRZYCS")
    private String brzycs;

    /**
     * 病案号
     */
    @TableField("BRBABH")
    private String brbabh;

    /**
     * 姓名
     */
    @TableField("BRBAXM")
    private String brbaxm;

    /**
     * 性别
     */
    @TableField("BRBAXB")
    private String brbaxb;

    /**
     * 出生日期
     */
    @TableField("BRCSRQ")
    private Date brcsrq;

    /**
     * 年龄
     */
    @TableField("BRDQNL")
    private String brdqnl;

    /**
     * 年龄单位
     */
    @TableField("BRNLDW")
    private String brnldw;

    /**
     * 国籍
     */
    @TableField("BRBAGJ")
    private String brbagj;

    /**
     * 新生儿出生月数
     */
    @TableField("YENLYS")
    private String yenlys;

    /**
     * 新生儿出生天数
     */
    @TableField("YENLTS")
    private String yenlts;

    /**
     * 新生儿出生体重
     */
    @TableField("YECSTZ")
    private String yecstz;

    /**
     * 新生儿入院体重
     */
    @TableField("YERYTZ")
    private String yerytz;

    /**
     * 出生地
     */
    @TableField("BRCSDZ")
    private String brcsdz;

    /**
     * 籍贯
     */
    @TableField("BRBAJG")
    private String brbajg;

    /**
     * 民族
     */
    @TableField("BRBAMZ")
    private String brbamz;

    /**
     * 身份证号
     */
    @TableField("BRSFZH")
    private String brsfzh;

    /**
     * 职业
     */
    @TableField("BRBASF")
    private String brbasf;

    /**
     * 婚姻
     */
    @TableField("BRHYZK")
    private String brhyzk;

    /**
     * 现住址
     */
    @TableField("BRLXDZ")
    private String brlxdz;

    /**
     * 现住址电话
     */
    @TableField("BRLXDH")
    private String brlxdh;

    /**
     * 现住址邮编
     */
    @TableField("LXDZYB")
    private String lxdzyb;

    /**
     * 户口地址
     */
    @TableField("BRHKDZ")
    private String brhkdz;

    /**
     * 户口地址邮编
     */
    @TableField("HKDZYB")
    private String hkdzyb;

    /**
     * 工作单位
     */
    @TableField("GZDWMC")
    private String gzdwmc;

    /**
     * 工作单位电话
     */
    @TableField("GZDWDH")
    private String gzdwdh;

    /**
     * 工作单位邮编
     */
    @TableField("GZDWYB")
    private String gzdwyb;

    /**
     * 联系人姓名
     */
    @TableField("LXRYXM")
    private String lxryxm;

    /**
     * 联系人关系
     */
    @TableField("LXRYGX")
    private String lxrygx;

    /**
     * 联系人地址
     */
    @TableField("LXRYDZ")
    private String lxrydz;

    /**
     * 联系人电话
     */
    @TableField("LXRYDH")
    private String lxrydh;

    /**
     * 入院途径
     */
    @TableField("RYLYDM")
    private String rylydm;

    /**
     * 入院时间
     */
    @TableField("BRRYRQ")
    private Date brryrq;

    /**
     * 入院科别编码
     */
    @TableField("RYKSID")
    private String ryksid;

    /**
     * 入院科别
     */
    @TableField("RYKSMC")
    private String ryksmc;

    /**
     * 入院病房编码
     */
    @TableField("RYBQID")
    private String rybqid;

    /**
     * 入院病房
     */
    @TableField("RYBQMC")
    private String rybqmc;

    /**
     * 入院床位序号
     */
    @TableField("RYCWID")
    private String rycwid;

    /**
     * 入院床位号码
     */
    @TableField("RYCWHM")
    private String rycwhm;

    /**
     * 转科科别
     */
    @TableField("BRZKKB")
    private String brzkkb;

    /**
     * 出院时间
     */
    @TableField("BRCYRQ")
    private Date brcyrq;

    /**
     * 出院科别编码
     */
    @TableField("CYKSID")
    private String cyksid;

    /**
     * 出院科别
     */
    @TableField("CYKSMC")
    private String cyksmc;

    /**
     * 出院病房编码
     */
    @TableField("CYBQID")
    private String cybqid;

    /**
     * 出院病房
     */
    @TableField("CYBQMC")
    private String cybqmc;

    /**
     * 出院床位序号
     */
    @TableField("CYCWID")
    private String cycwid;

    /**
     * 出院床位号码
     */
    @TableField("CYCWHM")
    private String cycwhm;

    /**
     * 实际住院天数
     */
    @TableField("SJZYTS")
    private String sjzyts;

    /**
     * 门急诊诊断
     */
    @TableField("BRMZZD")
    private String brmzzd;

    /**
     * 门急诊诊断编码
     */
    @TableField("MZZDDM")
    private String mzzddm;

    /**
     * 损伤中毒因素
     */
    @TableField("SSZDYSMC")
    private String sszdysmc;

    /**
     * 损伤中毒因素编码
     */
    @TableField("SSZDYSBM")
    private String sszdysbm;

    /**
     * 病理诊断
     */
    @TableField("BLZDMC")
    private String blzdmc;

    /**
     * 病理诊断编码
     */
    @TableField("BLZDBM")
    private String blzdbm;

    /**
     * 病理诊断编号
     */
    @TableField("BLZDBH")
    private String blzdbh;

    /**
     * 药物过敏
     */
    @TableField("YWYWGM")
    private String ywywgm;

    /**
     * 过敏药物
     */
    @TableField("BRGMYW")
    private String brgmyw;

    /**
     * 死亡患者尸检
     */
    @TableField("BRSFSJ")
    private String brsfsj;

    /**
     * 血型
     */
    @TableField("BRBAXX")
    private String brbaxx;

    /**
     * Rh
     */
    @TableField("BRBARH")
    private String brbarh;

    /**
     * 科主任id
     */
    @TableField("KZRID")
    private String kzrid;

    /**
     * 科主任
     */
    @TableField("KZR")
    private String kzr;

    /**
     * 主任医师id
     */
    @TableField("ZRYSID")
    private String zrysid;

    /**
     * 主任医师
     */
    @TableField("ZRYS")
    private String zrys;

    /**
     * 主治医师id
     */
    @TableField("ZZYSID")
    private String zzysid;

    /**
     * 主治医师
     */
    @TableField("ZZYS")
    private String zzys;

    /**
     * 住院医师id
     */
    @TableField("ZYYSID")
    private String zyysid;

    /**
     * 住院医师
     */
    @TableField("ZYYS")
    private String zyys;

    /**
     * 责任护士id
     */
    @TableField("ZRHSID")
    private String zrhsid;

    /**
     * 责任护士
     */
    @TableField("ZRHS")
    private String zrhs;

    /**
     * 进修医师id
     */
    @TableField("JXYSID")
    private String jxysid;

    /**
     * 进修医师
     */
    @TableField("JXYS")
    private String jxys;

    /**
     * 实习医师id
     */
    @TableField("SXYSID")
    private String sxysid;

    /**
     * 实习医师
     */
    @TableField("SXYS")
    private String sxys;

    /**
     * 编码员id
     */
    @TableField("BMYID")
    private String bmyid;

    /**
     * 编码员
     */
    @TableField("BMY")
    private String bmy;

    /**
     * 病案质量
     */
    @TableField("BRBAZL")
    private String brbazl;

    /**
     * 质控医师
     */
    @TableField("BAZKYS")
    private String bazkys;

    /**
     * 质控护士
     */
    @TableField("BAZKHS")
    private String bazkhs;

    /**
     * 质控日期
     */
    @TableField("BAZKRQ")
    private Date bazkrq;

    /**
     * 离院方式
     */
    @TableField("BRLYFS")
    private String brlyfs;

    /**
     * 接收机构名称
     */
    @TableField("ZYJGMC")
    private String zyjgmc;

    /**
     * 31天内再住院计划
     */
    @TableField("SSYZZY")
    private String ssyzzy;

    /**
     * 再住院目的
     */
    @TableField("SSYZZYMD")
    private String ssyzzymd;

    /**
     * 颅脑损伤患者昏迷前天数
     */
    @TableField("RYQHMTS")
    private String ryqhmts;

    /**
     * 颅脑损伤患者昏迷前小时
     */
    @TableField("RYQHMXS")
    private String ryqhmxs;

    /**
     * 颅脑损伤患者昏迷前分钟
     */
    @TableField("RYQHMFZ")
    private String ryqhmfz;

    /**
     * 颅脑损伤患者昏迷后天数
     */
    @TableField("RYHHMTS")
    private String ryhhmts;

    /**
     * 颅脑损伤患者昏迷后小时
     */
    @TableField("RYHHMXS")
    private String ryhhmxs;

    /**
     * 颅脑损伤患者昏迷后分钟
     */
    @TableField("RYHHMFZ")
    private String ryhhmfz;

    /**
     * 总费用
     */
    @TableField("ZFY")
    private String zfy;

    /**
     * 自付金额
     */
    @TableField("ZFJE")
    private String zfje;

    /**
     * 一般医疗服务费
     */
    @TableField("YBYLFWF")
    private String ybylfwf;

    /**
     * 一般治疗服务费
     */
    @TableField("YBZLCZF")
    private String ybzlczf;

    /**
     * 护理费
     */
    @TableField("HLF")
    private String hlf;

    /**
     * 其他费用
     */
    @TableField("QTFY")
    private String qtfy;

    /**
     * 病理诊断费
     */
    @TableField("BLZDF")
    private String blzdf;

    /**
     * 实验室诊断费
     */
    @TableField("SYSZDF")
    private String syszdf;

    /**
     * 影像学诊断费
     */
    @TableField("YXXZDF")
    private String yxxzdf;

    /**
     * 临床诊断项目费
     */
    @TableField("LCZDXMF")
    private String lczdxmf;

    /**
     * 非手术治疗项目费
     */
    @TableField("FSSZLXMF")
    private String fsszlxmf;

    /**
     * 临床物理治疗费
     */
    @TableField("LCWLZLF")
    private String lcwlzlf;

    /**
     * 手术治疗费
     */
    @TableField("SSZLF")
    private String sszlf;

    /**
     * 麻醉费
     */
    @TableField("MZF")
    private String mzf;

    /**
     * 手术费
     */
    @TableField("SSF")
    private String ssf;

    /**
     * 康复类
     */
    @TableField("KFF")
    private String kff;

    /**
     * 中医治疗费
     */
    @TableField("ZYZLF")
    private String zyzlf;

    /**
     * 西药费
     */
    @TableField("XYF")
    private String xyf;

    /**
     * 抗菌药物费用
     */
    @TableField("KJYWF")
    private String kjywf;

    /**
     * 中成药费
     */
    @TableField("ZCYF")
    private String zcyf;

    /**
     * 中草药费
     */
    @TableField("CYF")
    private String cyf;

    /**
     * 血费
     */
    @TableField("XF")
    private String xf;

    /**
     * 白蛋白类制品费
     */
    @TableField("BDBLZPF")
    private String bdblzpf;

    /**
     * 球蛋白类制品费
     */
    @TableField("QDBLZPF")
    private String qdblzpf;

    /**
     * 凝血因子类制品费
     */
    @TableField("NXYZLZPF")
    private String nxyzlzpf;

    /**
     * 细胞因子类制品费
     */
    @TableField("XBYZLZPF")
    private String xbyzlzpf;

    /**
     * 检查用一次性医用材料费
     */
    @TableField("JCYYCXYYCLF")
    private String jcyycxyyclf;

    /**
     * 治疗用一次性医用材料费
     */
    @TableField("ZLYYCXYYCLF")
    private String zlyycxyyclf;

    /**
     * 手术用一次性医用材料费
     */
    @TableField("SSYYCXYYCLF")
    private String ssyycxyyclf;

    /**
     * 其他类其他费
     */
    @TableField("QTF")
    private String qtf;

    /**
     * 单病种管理
     */
    @TableField("DBZGL")
    private String dbzgl;

    /**
     * 临床路径管理
     */
    @TableField("SSLCLJGL")
    private String sslcljgl;

    /**
     * 门诊与住院
     */
    @TableField("MZZYFH")
    private String mzzyfh;

    /**
     * 入院与出院
     */
    @TableField("RYCYFH")
    private String rycyfh;

    /**
     * 术前与术后
     */
    @TableField("SQSHFH")
    private String sqshfh;

    /**
     * 临床与病理
     */
    @TableField("LCBLFH")
    private String lcblfh;

    /**
     * 放射与病理
     */
    @TableField("FSBLFH")
    private String fsblfh;

    /**
     * 病人抢救次数
     */
    @TableField("BRQJCS")
    private String brqjcs;

    /**
     * 病人抢救成功次数
     */
    @TableField("QJCGCS")
    private String qjcgcs;

    /**
     * 是否为自动出院
     */
    @TableField("ZDCYPB")
    private String zdcypb;

    /**
     * 转归情况
     */
    @TableField("CYQKDM")
    private String cyqkdm;

    /**
     * 取数据的修改时间，根据这个时间进行数据同步
     */
    @TableField("CJCXRQ")
    private Date cjcxrq;


}
