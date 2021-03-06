package com.nuena.bkmy.order;

import com.nuena.bkmy.facade.SymptomInfoFacade;
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
@Order(6)
@Component
public class SymptomInit implements ApplicationRunner {

    @Value("${symptom.entry.insect.finished}")
    private boolean symptom_entry_insect_finished;
    @Autowired
    private SymptomInfoFacade symptomInfoFacade;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (symptom_entry_insect_finished) {
            return;
        }
        symptomInfoFacade.initData();
    }

}