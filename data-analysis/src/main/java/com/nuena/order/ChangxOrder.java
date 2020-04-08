package com.nuena.order;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.netflix.discovery.converters.Auto;
import com.nuena.orglib.entity.CasesNumber;
import com.nuena.orglib.mapper.CasesNumberMapper;
import com.nuena.orglib.service.impl.CasesNumberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

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

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(casesNumberService.list().size());
    }


}