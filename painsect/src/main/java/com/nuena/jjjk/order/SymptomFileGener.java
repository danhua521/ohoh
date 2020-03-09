package com.nuena.jjjk.order;

import com.nuena.jjjk.facade.JjjkSymptomLibFacade;
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
@Order(5)
@Component("jjjkSymptomFileGener")
public class SymptomFileGener implements ApplicationRunner {

    @Value("${jjjk.symptom.file.gener.finished}")
    private boolean symptom_file_gener_finished;
    @Autowired
    private JjjkSymptomLibFacade symptomLibFacade;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (symptom_file_gener_finished) {
            return;
        }
        symptomLibFacade.fileGener();
    }

}