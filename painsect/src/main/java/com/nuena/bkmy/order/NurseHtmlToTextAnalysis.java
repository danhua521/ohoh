package com.nuena.bkmy.order;

import com.nuena.bkmy.entity.NurseRawData;
import com.nuena.bkmy.facade.NurseAnalysisFacade;
import com.nuena.bkmy.facade.NurseRawDataFacade;
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
@Order(22)
@Component
public class NurseHtmlToTextAnalysis implements ApplicationRunner {

    @Value("${nurse.html.analysis.finished}")
    private boolean nurse_html_analysis_finished;
    @Autowired
    private NurseRawDataFacade nurseRawDataFacade;
    @Autowired
    private NurseAnalysisFacade nurseAnalysisFacade;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (nurse_html_analysis_finished) {
            return;
        }
        startAnalysis();
    }

    /**
     * 开始解析
     */
    private void startAnalysis() {
        List<NurseRawData> nurseRawDataList = null;
        while (ListUtil.isNotEmpty(nurseRawDataList = nurseRawDataFacade.getNoAnalysisNurseRawData())) {
            nurseAnalysisFacade.analysisHtml(nurseRawDataList);
        }
    }

}