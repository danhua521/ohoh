package com.nuena.bkmy.order;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nuena.bkmy.entity.DiseaseInfo;
import com.nuena.bkmy.entity.DiseaseRawData;
import com.nuena.bkmy.facade.DiseaseInfoFacade;
import com.nuena.bkmy.facade.DiseaseRawDataFacade;
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
@Order(2)
@Component
public class DiseaseHtmlInit implements ApplicationRunner {

    @Value("${disease.html.insect.finished}")
    private boolean disease_html_insect_finished;
    @Autowired
    private DiseaseRawDataFacade diseaseRawDataFacade;
    @Autowired
    private DiseaseInfoFacade diseaseInfoFacade;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (disease_html_insect_finished) {
            return;
        }
        initData();
    }

    /**
     * 初始化，插入疾病html信息
     */
    private void initData() {
        QueryWrapper<DiseaseInfo> diseaseInfoQe = new QueryWrapper<>();
        diseaseInfoQe.isNull("remark");
        List<DiseaseInfo> diseaseInfoList = diseaseInfoFacade.list(diseaseInfoQe);
        diseaseInfoList.forEach(i->{
            everyDisHtml(i);
        });
    }

    private void everyDisHtml(DiseaseInfo diseaseInfo) {
        try {
            Random rd = new Random();
            Thread.sleep(1000*rd.nextInt(11)+2000);
            String html = HttpTool.get(diseaseInfo.getDisUrl());
            if (StringUtil.isBlank(html)) {
                return;
            }

            DiseaseRawData diseaseRawData = new DiseaseRawData();
            diseaseRawData.setDisId(diseaseInfo.getDisId());
            diseaseRawData.setDisName(diseaseInfo.getDisName());
            diseaseRawData.setDisUrl(diseaseInfo.getDisUrl());
            diseaseRawData.setDisHtml(html);
            diseaseRawData.setCreateTime(new Date());
            diseaseRawDataFacade.save(diseaseRawData);

            diseaseInfo.setRemark("1");
            diseaseInfoFacade.updateById(diseaseInfo);
        } catch (Exception e) {
        }
    }


}
