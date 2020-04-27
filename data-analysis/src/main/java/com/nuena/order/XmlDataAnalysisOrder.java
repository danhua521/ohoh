package com.nuena.order;

import com.google.common.collect.Lists;
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
        taiZhouXmlDataAnalysisFacade.init();
        long modelId = 1l;
        String nodePath = "//emr_xml_root/TermList";
        List<String> recTitles = taiZhouXmlDataAnalysisFacade.getRecTitles(modelId);
        List<String> failRecTitles = Lists.newArrayList();//执行失败的recTitle
        recTitles.forEach(recTitle -> {
            try {
                taiZhouXmlDataAnalysisFacade.analysisByRecTitle(modelId, recTitle, nodePath);
            } catch (Exception e) {
                log.info(e.getMessage(), e);
                failRecTitles.add(recTitle);
            }
        });

        log.info("----------执行失败---------------------");
        log.info("执行失败的recTitle：");
        failRecTitles.forEach(i -> {
            log.info("----------" + i);
        });
    }

}