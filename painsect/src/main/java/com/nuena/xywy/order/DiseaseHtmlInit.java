package com.nuena.xywy.order;

import com.nuena.util.ListUtil;
import com.nuena.xywy.entity.XywyDiseaseLib;
import com.nuena.xywy.facade.XywyDiseaseLibFacade;
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
@Order(2)
@Component("xywyDiseaseHtmlInit")
public class DiseaseHtmlInit implements ApplicationRunner {

    @Value("${xywy.disease.html.insect.finished}")
    private boolean disease_html_insect_finished;
    @Autowired
    private XywyDiseaseLibFacade xywyDiseaseLibFacade;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (disease_html_insect_finished) {
            return;
        }

        List<XywyDiseaseLib> diseaseLibList = null;
        while (ListUtil.isNotEmpty(diseaseLibList = xywyDiseaseLibFacade.getNoLoadHtmlDiseases())) {
            diseaseLibList.forEach(i -> {
                try {
                    xywyDiseaseLibFacade.loadHtml(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

}