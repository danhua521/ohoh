package com.nuena.xywy.order;

import com.nuena.xywy.facade.DiseaseLibFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class DiseaseIdInit implements ApplicationRunner {

    @Value("${xywy.disease.id.insect.finished}")
    private boolean disease_id_insect_finished;
    @Autowired
    private DiseaseLibFacade diseaseLibFacade;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (disease_id_insect_finished) {
            return;
        }
        diseaseLibFacade.initData();
    }

}