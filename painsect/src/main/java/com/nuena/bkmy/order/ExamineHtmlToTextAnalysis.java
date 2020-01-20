package com.nuena.bkmy.order;

import com.nuena.bkmy.entity.ExamineRawData;
import com.nuena.bkmy.facade.ExamineAnalysisFacade;
import com.nuena.bkmy.facade.ExamineRawDataFacade;
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
@Order(18)
@Component
public class ExamineHtmlToTextAnalysis implements ApplicationRunner {

    @Value("${examine.html.analysis.finished}")
    private boolean examine_html_analysis_finished;
    @Autowired
    private ExamineRawDataFacade examineRawDataFacade;
    @Autowired
    private ExamineAnalysisFacade examineAnalysisFacade;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (examine_html_analysis_finished) {
            return;
        }
        startAnalysis();
    }

    /**
     * 开始解析
     */
    private void startAnalysis() {
        List<ExamineRawData> examineRawDataList = null;
        while (ListUtil.isNotEmpty(examineRawDataList = examineRawDataFacade.getNoAnalysisExamineRawData())) {
            examineAnalysisFacade.analysisHtml(examineRawDataList);
        }
    }

}