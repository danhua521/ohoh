package com.nuena.order;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class TaiZhouOrder implements ApplicationRunner {

    @Autowired
    private TaiZhouFacade taiZhouFacade;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        taiZhouFacade.dataTrans();//拉住院信息、文书记录、文书内容
        taiZhouFacade.dataTransYiZhu();//拉医嘱
        taiZhouFacade.dataTransBasy();//拉病案首页
        taiZhouFacade.dataTransBasyshzd();//拉病案首页的手术、病案首页的诊断
    }

}