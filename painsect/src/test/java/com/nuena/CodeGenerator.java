package com.nuena;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @author: rengb
 * @time: 2019/11/5 17:39
 */
public class CodeGenerator {

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String classPath = CodeGenerator.class.getResource("").getPath();
        final String projectPath = classPath.substring(0, classPath.indexOf("target") - 1);
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("rgb");
        gc.setOpen(false);
        gc.setFileOverride(true);
        gc.setDateType(DateType.ONLY_DATE);
        gc.setServiceName("%sService");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://223.93.170.82:23506/painsect?useUnicode=true&useSSL=false&characterEncoding=utf8");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("diagbot@20180822");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.nuena.myzx");
        pc.setEntity("entity");
        pc.setMapper("mapper");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称================================模块名（自己设置）
                return projectPath + "/src/main/resources/mapper/myzx/"
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        mpg.setTemplate(new TemplateConfig().setXml(null));

        // 策略配置**（个性化定制）**
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setEntityTableFieldAnnotationEnable(true);
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setRestControllerStyle(true);
        strategy.setTablePrefix("");
        strategy.setInclude(
//                "mybk_disease_raw_data",
//                "mybk_disease_info",
//                "mybk_dept_info",
//                "mybk_symptom_info",
//                "mybk_symptom_raw_data",
//                "mybk_examine_info",
//                "mybk_examine_raw_data",
//                "mybk_prevent_info",
//                "mybk_prevent_raw_data",
//                "mybk_nurse_info",
//                "mybk_nurse_raw_data",
//                "mybk_treat_info",
//                "mybk_treat_raw_data",
//                "mybk_chinmed_info",
//                "mybk_chinmed_raw_data",
//                "mybk_disease_analysis",
//                "mybk_symptom_analysis",
//                "mybk_examine_analysis",
//                "mybk_treat_analysis",
//                "mybk_chinmed_analysis",
//                "mybk_prevent_analysis",
//                "mybk_nurse_analysis"
//                "xywy_disease_lib"
//                "xywy_disease_lib",
//                "xywy_disease_lib",
//                "xywy_disease_synopsis",
//                "xywy_disease_etiology",
//                "xywy_disease_prevent",
//                "xywy_disease_complication",
//                "xywy_disease_symptom",
//                "xywy_disease_examine",
//                "xywy_disease_discern",
//                "xywy_disease_treat",
//                "xywy_disease_nurse",
//                "xywy_disease_health"
                "myzx_disease_lib"
        );
        mpg.setStrategy(strategy);

        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

}