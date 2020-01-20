package com.nuena.bkmy.order;

import com.nuena.bkmy.entity.TreatRawData;
import com.nuena.bkmy.facade.TreatAnalysisFacade;
import com.nuena.bkmy.facade.TreatRawDataFacade;
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
@Order(19)
@Component
public class TreatHtmlToTextAnalysis implements ApplicationRunner {

    @Value("${treat.html.analysis.finished}")
    private boolean treat_html_analysis_finished;
    @Autowired
    private TreatRawDataFacade treatRawDataFacade;
    @Autowired
    private TreatAnalysisFacade treatAnalysisFacade;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (treat_html_analysis_finished) {
            return;
        }
        startAnalysis();
    }

    /**
     * 开始解析
     */
    private void startAnalysis() {
        List<TreatRawData> treatRawDataList = null;
        while (ListUtil.isNotEmpty(treatRawDataList = treatRawDataFacade.getNoAnalysisTreatRawData())) {
            treatAnalysisFacade.analysisHtml(treatRawDataList);
        }
    }

}