package com.nuena.bkmy.order;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nuena.bkmy.entity.SymptomInfo;
import com.nuena.bkmy.entity.SymptomRawData;
import com.nuena.bkmy.facade.SymptomInfoFacade;
import com.nuena.bkmy.facade.SymptomRawDataFacade;
import com.nuena.util.HttpTool;
import com.nuena.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @Description:
 * @author: rengb
 * @time: 2020/1/14 15:45
 */
@Order(7)
@Component
public class SymptomHtmlInit implements ApplicationRunner {

    @Value("${symptom.html.insect.finished}")
    private boolean symptom_html_insect_finished;
    @Autowired
    private SymptomRawDataFacade symptomRawDataFacade;
    @Autowired
    private SymptomInfoFacade symptomInfoFacade;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (symptom_html_insect_finished) {
            return;
        }
        initData();
    }

    /**
     * 初始化，插入症状html信息
     */
    private void initData() {
        QueryWrapper<SymptomInfo> symptomInfoQe = new QueryWrapper<>();
        symptomInfoQe.isNull("remark");
        List<SymptomInfo> symptomInfoList = symptomInfoFacade.list(symptomInfoQe);
        symptomInfoList.forEach(i -> {
            everySymHtml(i);
        });
    }

    private void everySymHtml(SymptomInfo symptomInfo) {
        try {
            Random rd = new Random();
            Thread.sleep(1000 * rd.nextInt(10) + 2000);
            String html = HttpTool.get(symptomInfo.getSymUrl());
            if (StringUtil.isBlank(html)) {
                return;
            }

            SymptomRawData symptomRawData = new SymptomRawData();
            symptomRawData.setSymId(symptomInfo.getSymId());
            symptomRawData.setSymName(symptomInfo.getSymName());
            symptomRawData.setSymUrl(symptomInfo.getSymUrl());
            symptomRawData.setSymHtml(html);
            symptomRawData.setCreateTime(new Date());
            symptomRawDataFacade.save(symptomRawData);

            symptomInfo.setRemark("1");
            symptomInfoFacade.updateById(symptomInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}