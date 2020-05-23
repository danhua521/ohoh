package com.nuena.order;

import com.google.common.collect.Lists;
import com.nuena.lantone.entity.MedicalRecordContent;
import com.nuena.util.ListUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description: xml数据分析
 * @author: rengb
 * @time: 2020/1/14 15:45
 */
@Order(1)
@Component
@Slf4j
public class XmlDataAnalysisOrder implements ApplicationRunner {

    @Autowired
    private TaiZhouXmlDataAnalysisFacade taiZhouXmlDataAnalysisFacade;
    @Autowired
    private ChangxXmlDataAnalysisFacade changxXmlDataAnalysisFacade;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        changxRyjlXmlDataAnalysis();
    }

    private void taiZhouXmlDataAnalysis() throws Exception {
        log.error("----------分析开始---------------------");
        List<String> failRecTitles = Lists.newArrayList();//执行失败的模块
        taiZhouXmlDataAnalysisFacade.init();
        long[] modeIds = { 1, 2, 3, 4, 5, 7, 8, 9, 10, 11, 17, 18, 19, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 34, 35 };
        String nodePath = "//emr_xml_root/TermList";
        List<String> recTitles = null;
        for (long modelId : modeIds) {
            recTitles = taiZhouXmlDataAnalysisFacade.getRecTitles(modelId);
            recTitles.forEach(recTitle -> {
                try {
                    taiZhouXmlDataAnalysisFacade.analysisByRecTitle(modelId, recTitle, nodePath);
                } catch (Exception e) {
                    log.error("[" + modelId + "-" + recTitle + "]执行失败--" + e.getMessage(), e);
                    failRecTitles.add("[" + modelId + "-" + recTitle + "]");
                }
            });
        }
        log.error("----------分析结束---------------------");
        failRecTitles.forEach(i -> {
            log.error("执行失败的模块：" + i);
        });
    }

    private void taizhouCreateKeysDataExcel() {
        log.error("----------生成keys的excel开始---------------------");
        taiZhouXmlDataAnalysisFacade.createKeysDataExcel();
        log.error("----------生成keys的excel结束---------------------");
    }

    private void taizhouGetModeMappingInfo() {
        log.error("----------生成模板映射json文件开始---------------------");
        taiZhouXmlDataAnalysisFacade.getModeMappingInfo();
        log.error("----------生成模板映射json文件结束---------------------");
    }

    private void taizhouEncrypData() throws Exception {
        log.error("----------数据加密开始---------------------");
        taiZhouXmlDataAnalysisFacade.init();
        List<MedicalRecordContent> medicalRecordContentList = null;
        while (ListUtil.isNotEmpty(medicalRecordContentList = taiZhouXmlDataAnalysisFacade.getNoEncrypData())) {
            try {
                taiZhouXmlDataAnalysisFacade.encrypData(medicalRecordContentList);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        log.error("----------数据加密结束---------------------");
    }

    //数据库模板生成表中入院记录模块keys需要拼上性别
    private void changxXmlDataAnalysis() throws Exception {
        log.error("----------分析开始---------------------");
        List<String> failRecTitles = Lists.newArrayList();//执行失败的模块
        changxXmlDataAnalysisFacade.init();
        long[] modeIds = { 2, 3, 4, 5, 10, 11, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 35 };
        String nodePath = "//DocObjContent/Region";
        List<String> recTitles = null;
        for (long modelId : modeIds) {
            recTitles = changxXmlDataAnalysisFacade.getRecTitles(modelId);
            recTitles.forEach(recTitle -> {
                try {
                    changxXmlDataAnalysisFacade.analysisByRecTitle(modelId, recTitle, nodePath, "");
                } catch (Exception e) {
                    log.error("[" + modelId + "-" + recTitle + "]执行失败--" + e.getMessage(), e);
                    failRecTitles.add("[" + modelId + "-" + recTitle + "]");
                }
            });
        }
        log.error("----------分析结束---------------------");
        failRecTitles.forEach(i -> {
            log.error("执行失败的模块：" + i);
        });
    }

    //长兴入院记录分男女跑模板
    private void changxRyjlXmlDataAnalysis() throws Exception {
        log.error("----------入院记录分析开始---------------------");
        List<String> failRecTitles = Lists.newArrayList();//执行失败的模块
        changxXmlDataAnalysisFacade.init();
        long[] modeIds = { 1 };
        String nodePath = "//DocObjContent/Region";
        List<String> recTitles = null;
        for (long modelId : modeIds) {
            recTitles = changxXmlDataAnalysisFacade.getRecTitles(modelId);
            recTitles.forEach(recTitle -> {
                try {
                    changxXmlDataAnalysisFacade.analysisByRecTitle(modelId, recTitle, nodePath, "女");
                    changxXmlDataAnalysisFacade.analysisByRecTitle(modelId, recTitle, nodePath, "男");
                } catch (Exception e) {
                    log.error("[" + modelId + "-" + recTitle + "]执行失败--" + e.getMessage(), e);
                    failRecTitles.add("[" + modelId + "-" + recTitle + "]");
                }
            });
        }
        log.error("----------分析结束---------------------");
        failRecTitles.forEach(i -> {
            log.error("执行失败的模块：" + i);
        });
    }

    private void changxEncrypData() throws Exception {
        log.error("----------数据加密开始---------------------");
        changxXmlDataAnalysisFacade.init();
        List<MedicalRecordContent> medicalRecordContentList = null;
        while (ListUtil.isNotEmpty(medicalRecordContentList = changxXmlDataAnalysisFacade.getNoEncrypData())) {
            try {
                changxXmlDataAnalysisFacade.encrypData(medicalRecordContentList);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        log.error("----------数据加密结束---------------------");
    }

}