package com.nuena.bkmy.order;

import com.nuena.bkmy.facade.ExamineInfoFacade;
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
@Component
public class ExamineInit implements ApplicationRunner {

    @Value("${examine.entry.insect.finished}")
    private boolean examine_entry_insect_finished;
    @Autowired
    private ExamineInfoFacade examineInfoFacade;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (examine_entry_insect_finished) {
            return;
        }
        examineInfoFacade.initData();
    }

}