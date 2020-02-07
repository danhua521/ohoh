package com.nuena.sjjk.order;

import com.nuena.sjjk.entity.SjjkDiseaseLib;
import com.nuena.sjjk.facade.SjjkDiseaseLibFacade;
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
@Order(2)
@Component("sjjkDiseaseHtmlInit")
public class DiseaseHtmlInit implements ApplicationRunner {

    @Value("${sjjk.disease.html.insect.finished}")
    private boolean disease_html_insect_finished;
    @Autowired
    private SjjkDiseaseLibFacade diseaseLibFacade;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (disease_html_insect_finished) {
            return;
        }

        List<SjjkDiseaseLib> diseaseLibList = null;
        while (ListUtil.isNotEmpty(diseaseLibList = diseaseLibFacade.getNoLoadHtmlDiseases())) {
            diseaseLibList.forEach(i -> {
                try {
                    diseaseLibFacade.loadHtml(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

}