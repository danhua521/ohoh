package com.nuena.jjjk.order;

import com.nuena.jjjk.entity.JjjkSymptomLib;
import com.nuena.jjjk.facade.JjjkSymptomLibFacade;
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
 * @time: 2020/1/14 15:45
 */
@Order(3)
@Component("jjjkSymptomHtmlInit")
public class SymptomHtmlInit implements ApplicationRunner {

    @Value("${jjjk.symptom.html.insect.finished}")
    private boolean symptom_html_insect_finished;
    @Autowired
    private JjjkSymptomLibFacade symptomLibFacade;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (symptom_html_insect_finished) {
            return;
        }

        List<JjjkSymptomLib> symptomLibList = symptomLibFacade.getNoLoadHtmlSymptoms();
        String path = "D:\\jjjk\\";
        symptomLibFacade.loadHtmlToLocal(path, symptomLibList);
        symptomLibFacade.loadedHtmlIntoDB(path, symptomLibList);
    }

}