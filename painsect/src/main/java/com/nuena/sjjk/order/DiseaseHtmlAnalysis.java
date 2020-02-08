package com.nuena.sjjk.order;

import com.nuena.sjjk.facade.SjjkDiseaseLibFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @author: rengb
 * @time: 2020/1/14 15:45
 */
@Order(3)
@Component("sjjkDiseaseHtmlAnalysis")
public class DiseaseHtmlAnalysis implements ApplicationRunner {

    @Value("${sjjk.disease.html.analysis.finished}")
    private boolean disease_html_analysis_finished;
    @Autowired
    private SjjkDiseaseLibFacade diseaseLibFacade;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (disease_html_analysis_finished) {
            return;
        }
        diseaseLibFacade.analysis();
    }

}