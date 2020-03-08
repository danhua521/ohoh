package com.nuena.jjjk.order;

import com.nuena.jjjk.entity.JjjkDiseaseLib;
import com.nuena.jjjk.facade.JjjkDiseaseLibFacade;
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
@Component("jjjkDiseaseHtmlInit")
public class DiseaseHtmlInit implements ApplicationRunner {

    @Value("${jjjk.disease.html.insect.finished}")
    private boolean disease_html_insect_finished;
    @Autowired
    private JjjkDiseaseLibFacade diseaseLibFacade;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (disease_html_insect_finished) {
            return;
        }

        List<JjjkDiseaseLib> diseaseLibList = diseaseLibFacade.getNoLoadHtmlDiseases();
        String path = "D:\\jjjk_jb\\nr\\";
        diseaseLibFacade.loadHtmlToLocal(path, diseaseLibList);
        diseaseLibFacade.loadedHtmlIntoDB(path, diseaseLibList);
    }

}