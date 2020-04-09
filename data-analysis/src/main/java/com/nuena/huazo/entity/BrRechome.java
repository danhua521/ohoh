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
 * @since 2020-04-09
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
     * 组织机构名称
     */
    @TableField("ZZJGMC")
    private String zzjgmc;

    /**
     * 组织机构编号
     */
    @TableField("ZZJGBH")
    private String zzjgbh;

    /**
     * 医疗付款类别
     */
    @TableField("YLFKLB")
    private String ylfklb;

    /**
     * 病人健康卡号
     */
    @TableField("BRJKKH")
    private String brjkkh;

    /**
     * 住院次数
     */
    @TableField("BRZYCS")
    private String brzycs;

    /**
     * 病人病案编号
     */
    @TableField("BRBABH")
    private String brbabh;

    /**
     * 病人病案姓名
     */
    @TableField("BRBAXM")
    private String brbaxm;

    /**
     * 病人病案性别
     */
    @TableField("BRBAXB")
    private String brbaxb;

    /**
     * 病人出生日期
     */
    @TableField("BRCSRQ")
    private Date brcsrq;

    /**
     * 病人年龄
     */
    @TableField("BRDQNL")
    private String brdqnl;

    /**
     * 年龄单位
     */
    @TableField("BRNLDW")
    private String brnldw;

    /**
     * 病人国籍
     */
    @TableField("BRBAGJ")
    private String brbagj;

    /**
     * 婴儿年龄月数
     */
    @TableField("YENLYS")
    private String yenlys;

    /**
     * 婴儿年龄天数
     */
    @TableField("YENLTS")
    private String yenlts;

    /**
     * 婴儿出生体重
     */
    @TableField("YECSTZ")
    private String yecstz;

    /**
     * 婴儿入院体重
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
     * 病人身份证号
     */
    @TableField("BRSFZH")
    private String brsfzh;

    /**
     * 病人病案身份
     */
    @TableField("BRBASF")
    private String brbasf;

    /**
     * 病人婚姻状况
     */
    @TableField("BRHYZK")
    private String brhyzk;

    /**
     * 病人联系地址
     */
    @TableField("BRLXDZ")
    private String brlxdz;

    /**
     * 病人联系电话
     */
    @TableField("BRLXDH")
    private String brlxdh;

    /**
     * 联系地址邮编
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
     * 工作单位名称
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
     * 1.急诊 2.门诊 3.其他医疗机构转入 9.其他
     */
    @TableField("RYLYDM")
    private String rylydm;

    /**
     * 病人入院日期
     */
    @TableField("BRRYRQ")
    private Date brryrq;

    /**
     * 入院科室编码
     */
    @TableField("RYKSID")
    private String ryksid;

    /**
     * 入院科室名称
     */
    @TableField("RYKSMC")
    private String ryksmc;

    /**
     * 入院病区编码
     */
    @TableField("RYBQID")
    private String rybqid;

    /**
     * 入院病区名称
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
     * 病人转科科别
     */
    @TableField("BRZKKB")
    private String brzkkb;

    /**
     * 病人出院日期
     */
    @TableField("BRCYRQ")
    private Date brcyrq;

    /**
     * 出院科室编码
     */
    @TableField("CYKSID")
    private String cyksid;

    /**
     * 出院科室名称
     */
    @TableField("CYKSMC")
    private String cyksmc;

    /**
     * 出院病区编码
     */
    @TableField("CYBQID")
    private String cybqid;

    /**
     * 出院病区名称
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
     * 病人门诊诊断
     */
    @TableField("BRMZZD")
    private String brmzzd;

    /**
     * 门诊诊断编码
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
     * 有无药物过敏
     */
    @TableField("YWYWGM")
    private String ywywgm;

    /**
     * 病人过敏药物
     */
    @TableField("BRGMYW")
    private String brgmyw;

    /**
     * 病人是否尸检
     */
    @TableField("BRSFSJ")
    private String brsfsj;

    /**
     * 病人病案血型
     */
    @TableField("BRBAXX")
    private String brbaxx;

    /**
     * 病人病案RH型
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
     * 病人病案质量
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
     * 1.医嘱离院 2.医嘱转院 3.医嘱转社区卫生服务机构/乡镇卫生院 4.非医嘱离院 5.死亡 6.其他
     */
    @TableField("BRLYFS")
    private String brlyfs;

    /**
     * 接收机构名称
     */
    @TableField("ZYJGMC")
    private String zyjgmc;

    /**
     * 是否有出院31天内再住院计划
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
     * 单病种管理 1.是 2.否
     */
    @TableField("DBZGL")
    private String dbzgl;

    /**
     * 临床路径管理 1.完成 2.变异 3.退出 4.未入
     */
    @TableField("SSLCLJGL")
    private String sslcljgl;

    /**
     * 门诊与出院符合 0.未做 1.符合 2.不符合 3.不确定
     */
    @TableField("MZZYFH")
    private String mzzyfh;

    /**
     * 入院与出院符合 0.未做 1.符合 2.不符合 3.不确定
     */
    @TableField("RYCYFH")
    private String rycyfh;

    /**
     * 术前与术后符合 0.未做 1.符合 2.不符合 3.不确定
     */
    @TableField("SQSHFH")
    private String sqshfh;

    /**
     * 临床与病理符合 0.未做 1.符合 2.不符合 3.不确定
     */
    @TableField("LCBLFH")
    private String lcblfh;

    /**
     * 放射与病理符合0.未做 1.符合 2.不符合 3.不确定
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
     * 是否为自动出院 1.是 2.否
     */
    @TableField("ZDCYPB")
    private String zdcypb;

    /**
     * 转归情况 1.治愈 2.好转 3.未愈 4.死亡 5.其他
     */
    @TableField("CYQKDM")
    private String cyqkdm;

    /**
     * 取数据的修改时间，根据这个时间进行数据同步
     */
    @TableField("CJCXRQ")
    private Date cjcxrq;


}
