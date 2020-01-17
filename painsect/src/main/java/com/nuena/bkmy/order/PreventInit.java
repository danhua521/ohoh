package com.nuena.bkmy.order;

import com.nuena.bkmy.facade.PreventInfoFacade;
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
@Order(10)
@Component
public class PreventInit implements ApplicationRunner {

    @Value("${prevent.entry.insect.finished}")
    private boolean prevent_entry_insect_finished;
    @Autowired
    private PreventInfoFacade preventInfoFacade;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (prevent_entry_insect_finished) {
            return;
        }
        preventInfoFacade.initData();
    }

}