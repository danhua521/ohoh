package com.nuena.order;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nuena.huazo.entity.MrMrcontent;
import com.nuena.huazo.service.impl.BrRechomeServiceImpl;
import com.nuena.huazo.service.impl.MrMrcontentServiceImpl;
import com.nuena.lantone.service.impl.CasesNumberServiceImpl;
import com.nuena.util.GZIPUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.zip.GZIPOutputStream;

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
    @Autowired
    private MrMrcontentServiceImpl mrMrcontentServiceImpl;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        QueryWrapper<MrMrcontent> mrMrcontentQe = new QueryWrapper<>();
        mrMrcontentQe.last("where rownum<50");
        MrMrcontent mrMrcontent = mrMrcontentServiceImpl.list(mrMrcontentQe).get(20);
        String msg = GZIPUtils.uncompressToString(mrMrcontent.getBljlnr(),"GBK");
        System.out.println(msg);
    }

}