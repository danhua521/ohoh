package com.nuena.order;

import com.google.common.collect.Lists;
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

    @Override
    public void run(ApplicationArguments args) throws Exception {
        taiZhouXmlDataAnalysis();
    }

    private void taiZhouXmlDataAnalysis() throws Exception {
        log.error("----------分析开始---------------------");
        taiZhouXmlDataAnalysisFacade.init();
        long[] modeIds = { 1, 2, 3, 4, 5, 7, 8, 9, 10, 11, 17, 18, 19, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 34, 35 };
        String nodePath = "//emr_xml_root/TermList";
        for (long modelId : modeIds) {
            List<String> recTitles = taiZhouXmlDataAnalysisFacade.getRecTitles(modelId);
            List<String> failRecTitles = Lists.newArrayList();//执行失败的recTitle
            recTitles.forEach(recTitle -> {
                try {
                    taiZhouXmlDataAnalysisFacade.analysisByRecTitle(modelId, recTitle, nodePath);
                } catch (Exception e) {
                    log.error(recTitle + "执行失败--" + e.getMessage(), e);
                    failRecTitles.add(recTitle);
                }
            });

            if (ListUtil.isNotEmpty(failRecTitles)) {
                log.error("执行失败的recTitle：");
                failRecTitles.forEach(i -> {
                    log.info("----------" + i);
                });
            }
        }
        log.error("----------分析结束---------------------");
    }

}