package com.nuena.myzx.order;

import com.nuena.myzx.facade.MyzxDiseaseLibFacade;
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
@Component("myzxDiseaseHtmlAnalysis")
public class DiseaseHtmlAnalysis implements ApplicationRunner {

    @Value("${myzx.disease.html.analysis.finished}")
    private boolean disease_html_analysis_finished;
    @Autowired
    private MyzxDiseaseLibFacade diseaseLibFacade;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (disease_html_analysis_finished) {
            return;
        }
        diseaseLibFacade.analysis();
    }

}