package com.nuena.bkmy.order;

import com.nuena.bkmy.entity.PreventRawData;
import com.nuena.bkmy.facade.PreventAnalysisFacade;
import com.nuena.bkmy.facade.PreventRawDataFacade;
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
@Order(21)
@Component
public class PreventHtmlToTextAnalysis implements ApplicationRunner {

    @Value("${prevent.html.analysis.finished}")
    private boolean prevent_html_analysis_finished;
    @Autowired
    private PreventRawDataFacade preventRawDataFacade;
    @Autowired
    private PreventAnalysisFacade preventAnalysisFacade;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (prevent_html_analysis_finished) {
            return;
        }
        startAnalysis();
    }

    /**
     * 开始解析
     */
    private void startAnalysis() {
        List<PreventRawData> preventRawDataList = null;
        while (ListUtil.isNotEmpty(preventRawDataList = preventRawDataFacade.getNoAnalysisPreventRawData())) {
            preventAnalysisFacade.analysisHtml(preventRawDataList);
        }
    }

}