package com.nuena.bkmy.order;

import com.nuena.bkmy.entity.DiseaseRawData;
import com.nuena.bkmy.facade.DiseaseAnalysisFacade;
import com.nuena.bkmy.facade.DiseaseRawDataFacade;
import com.nuena.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description:
 * @author: rengb
 * @time: 2020/1/17 17:05
 */
@Order(16)
@Component
public class DiseaseHtmlToTextAnalysis implements ApplicationRunner {

    @Value("${disease.html.analysis.finished}")
    private boolean disease_html_analysis_finished;
    @Autowired
    private DiseaseRawDataFacade diseaseRawDataFacade;
    @Autowired
    private DiseaseAnalysisFacade diseaseAnalysisFacade;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (disease_html_analysis_finished) {
            return;
        }
        startAnalysis();
    }

    /**
     * 开始解析
     */
    private void startAnalysis() {
        List<DiseaseRawData> diseaseRawDataList = null;
        while (ListUtil.isNotEmpty(diseaseRawDataList = diseaseRawDataFacade.getNoAnalysisDiseaseRawData())) {
            diseaseAnalysisFacade.analysisHtml(diseaseRawDataList);
        }
    }

}