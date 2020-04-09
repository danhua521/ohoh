package com.nuena.order;

import com.nuena.huazo.entity.MrMrcontent;
import com.nuena.util.ListUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description:
 * @author: rengb
 * @time: 2020/1/14 15:45
 */
@Order(1)
@Component
@Slf4j
public class ChangxOrder implements ApplicationRunner {

    @Autowired
    private ChangxFacade changxFacade;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<MrMrcontent> noJmData = null;
        while (ListUtil.isNotEmpty(noJmData = changxFacade.getNoJmData())) {
            try {
                Thread.sleep(1000);
                changxFacade.noJmDataToJm(noJmData);
                noJmData.clear();
                System.gc();
            } catch (Exception e) {
                e.printStackTrace();
                noJmData.clear();
                System.gc();
            }
        }
    }

}