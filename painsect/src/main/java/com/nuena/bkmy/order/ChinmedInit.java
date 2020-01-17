package com.nuena.bkmy.order;

import com.nuena.bkmy.facade.ChinmedInfoFacade;
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
@Order(14)
@Component
public class ChinmedInit implements ApplicationRunner {

    @Value("${chinmed.entry.insect.finished}")
    private boolean chinmed_entry_insect_finished;
    @Autowired
    private ChinmedInfoFacade chinmedInfoFacade;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (chinmed_entry_insect_finished) {
            return;
        }
        chinmedInfoFacade.initData();
    }

}