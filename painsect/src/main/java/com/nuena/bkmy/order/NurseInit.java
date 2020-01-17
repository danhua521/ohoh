package com.nuena.bkmy.order;

import com.nuena.bkmy.facade.NurseInfoFacade;
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
@Order(12)
@Component
public class NurseInit implements ApplicationRunner {

    @Value("${nurse.entry.insect.finished}")
    private boolean nurse_entry_insect_finished;
    @Autowired
    private NurseInfoFacade nurseInfoFacade;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (nurse_entry_insect_finished) {
            return;
        }
        nurseInfoFacade.initData();
    }

}