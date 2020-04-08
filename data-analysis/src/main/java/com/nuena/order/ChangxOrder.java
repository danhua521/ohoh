package com.nuena.order;

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

//    @Autowired
//    private CasesNumberServiceImpl casesNumberService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        System.out.println(casesNumberService.list().size());
    }


}