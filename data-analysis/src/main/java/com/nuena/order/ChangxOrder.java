package com.nuena.order;

import com.nuena.huazo.service.impl.BrRechomeServiceImpl;
import com.nuena.lantone.service.impl.CasesNumberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @author: rengb
 * @time: 2020/1/14 15:45
 */
@Order(1)
@Component
public class ChangxOrder implements ApplicationRunner {

    @Autowired
    private CasesNumberServiceImpl casesNumberService;
    @Autowired
    private BrRechomeServiceImpl brRechomeService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(casesNumberService.list().size());
        System.out.println(brRechomeService.list().size());
    }

}