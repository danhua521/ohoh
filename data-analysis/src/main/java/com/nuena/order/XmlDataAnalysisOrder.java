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
        changxXmlDataAnalysis();
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

    private void changxXmlDataAnalysis() throws Exception {
        log.error("----------分析开始---------------------");
        List<String> failRecTitles = Lists.newArrayList();//执行失败的模块
        changxXmlDataAnalysisFacade.init();
//        long[] modeIds = { 1, 2, 3, 4, 5, 7, 10, 11, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 32, 35, 53, 54 };
        long[] modeIds = { 1,5};
        List<String> extitle = Lists.newArrayList(
                "24小时入出院记录",
                "入院记录（不含系统回顾Padua）",
                "入院记录（专用）",
                "入院记录（产科含系统回顾）",
                "入院记录（产科）-长兴",
                "入院记录（儿科）",
                "入院记录（含系统回顾Caprini）",
                "入院记录（含系统回顾Padua）",
                "入院记录（妇科含系统回顾）",
                "入院记录（妇科）-长兴",
                "入院记录（心内不含系统回顾）",
                "入院记录（心内含系统回顾）",
                "入院记录（新生儿）",
                "入院记录（神经内科）",
                "入院记录（神经外科）-长兴",
                "入院记录（耳鼻咽喉含系统回顾）",
                "（日间）入出院记录"
        );


        String nodePath = "//DocObjContent/Region";
        List<String> recTitles = null;
        for (long modelId : modeIds) {
            recTitles = changxXmlDataAnalysisFacade.getRecTitles(modelId);
            recTitles.forEach(recTitle -> {
                try {
                    if (!extitle.contains(recTitle)){
                        changxXmlDataAnalysisFacade.analysisByRecTitle(modelId, recTitle, nodePath);
                    }
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

}