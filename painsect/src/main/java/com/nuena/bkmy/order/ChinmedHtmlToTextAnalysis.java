package com.nuena.bkmy.order;

import com.nuena.bkmy.entity.ChinmedRawData;
import com.nuena.bkmy.facade.ChinmedAnalysisFacade;
import com.nuena.bkmy.facade.ChinmedRawDataFacade;
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
@Order(20)
@Component
public class ChinmedHtmlToTextAnalysis implements ApplicationRunner {

    @Value("${chinmed.html.analysis.finished}")
    private boolean chinmed_html_analysis_finished;
    @Autowired
    private ChinmedRawDataFacade chinmedRawDataFacade;
    @Autowired
    private ChinmedAnalysisFacade chinmedAnalysisFacade;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (chinmed_html_analysis_finished) {
            return;
        }
        startAnalysis();
    }

    /**
     * 开始解析
     */
    private void startAnalysis() {
        List<ChinmedRawData> chinmedRawDataList = null;
        while (ListUtil.isNotEmpty(chinmedRawDataList = chinmedRawDataFacade.getNoAnalysisChinmedRawData())) {
            chinmedAnalysisFacade.analysisHtml(chinmedRawDataList);
        }
    }

}