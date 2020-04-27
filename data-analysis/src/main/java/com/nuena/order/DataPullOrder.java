package com.nuena.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Description: 数据拉取
 * @author: rengb
 * @time: 2020/1/14 15:45
 */
@Order(1)
@Component
@Slf4j
public class DataPullOrder implements ApplicationRunner {

//    @Autowired
//    private ChangxDataPullFacade changxDataPullFacade;
//    @Autowired
//    private TaiZhouDataPullFacade taiZhouDataPullFacade;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        changxDataPullFacade.dataTrans();
//        taiZhouDataPullFacade.dataTrans();
    }

}