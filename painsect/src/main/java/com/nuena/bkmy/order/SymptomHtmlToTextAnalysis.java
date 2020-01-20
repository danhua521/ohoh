package com.nuena.bkmy.order;

import com.nuena.bkmy.entity.SymptomRawData;
import com.nuena.bkmy.facade.SymptomAnalysisFacade;
import com.nuena.bkmy.facade.SymptomRawDataFacade;
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
@Order(17)
@Component
public class SymptomHtmlToTextAnalysis implements ApplicationRunner {

    @Value("${symptom.html.analysis.finished}")
    private boolean symptom_html_analysis_finished;
    @Autowired
    private SymptomRawDataFacade symptomRawDataFacade;
    @Autowired
    private SymptomAnalysisFacade symptomAnalysisFacade;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (symptom_html_analysis_finished) {
            return;
        }
        startAnalysis();
    }

    /**
     * 开始解析
     */
    private void startAnalysis() {
        List<SymptomRawData> symptomRawDataList = null;
        while (ListUtil.isNotEmpty(symptomRawDataList = symptomRawDataFacade.getNoAnalysisSymptomRawData())) {
            symptomAnalysisFacade.analysisHtml(symptomRawDataList);
        }
    }

}