package com.nuena.bkmy.order;

import com.nuena.bkmy.entity.SymptomInfo;
import com.nuena.bkmy.facade.SymptomInfoFacade;
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
 * @time: 2020/1/14 15:45
 */
@Order(7)
@Component
public class SymptomHtmlInit implements ApplicationRunner {

    @Value("${symptom.html.insect.finished}")
    private boolean symptom_html_insect_finished;
    @Autowired
    private SymptomRawDataFacade symptomRawDataFacade;
    @Autowired
    private SymptomInfoFacade symptomInfoFacade;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (symptom_html_insect_finished) {
            return;
        }

        List<SymptomInfo> symptomInfoList = null;
        while (ListUtil.isNotEmpty(symptomInfoList = symptomInfoFacade.getNullHtmlSym())) {
            symptomInfoList.forEach(i -> {
                try {
                    symptomRawDataFacade.everySymHtml(i);
                } catch (Exception e) {
                }
            });
        }
    }

}