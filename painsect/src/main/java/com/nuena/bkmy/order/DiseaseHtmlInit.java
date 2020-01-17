package com.nuena.bkmy.order;

import com.nuena.bkmy.entity.DiseaseInfo;
import com.nuena.bkmy.facade.DiseaseInfoFacade;
import com.nuena.bkmy.facade.DiseaseRawDataFacade;
import com.nuena.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@Order(3)
@Component
public class DiseaseHtmlInit implements ApplicationRunner {

    @Value("${disease.html.insect.finished}")
    private boolean disease_html_insect_finished;
    @Autowired
    private DiseaseInfoFacade diseaseInfoFacade;
    @Autowired
    private DiseaseRawDataFacade diseaseRawDataFacade;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (disease_html_insect_finished) {
            return;
        }

        List<DiseaseInfo> diseaseInfoList = null;
        while (ListUtil.isNotEmpty(diseaseInfoList = diseaseInfoFacade.getNullHtmlDis())) {
            diseaseInfoList.forEach(i -> {
                try {
                    diseaseRawDataFacade.everyDisHtml(i);
                } catch (Exception e) {
                }
            });
        }
    }

}