package com.nuena.jjjk.order;

import com.nuena.jjjk.facade.JjjkSymptomLibFacade;
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
@Order(4)
@Component("jjjkSymptomHtmlAnalysis")
public class SymptomHtmlAnalysis implements ApplicationRunner {

    @Value("${jjjk.symptom.html.analysis.finished}")
    private boolean symptom_html_analysis_finished;
    @Autowired
    private JjjkSymptomLibFacade symptomLibFacade;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (symptom_html_analysis_finished) {
            return;
        }
        symptomLibFacade.analysis();
    }

}