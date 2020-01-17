package com.nuena.bkmy.order;

import com.nuena.bkmy.facade.TreatInfoFacade;
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
@Order(8)
@Component
public class TreatInit implements ApplicationRunner {

    @Value("${treat.entry.insect.finished}")
    private boolean treat_entry_insect_finished;
    @Autowired
    private TreatInfoFacade treatInfoFacade;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (treat_entry_insect_finished) {
            return;
        }
        treatInfoFacade.initData();
    }

}