package com.nuena.bkmy.order;

import com.nuena.bkmy.entity.TreatInfo;
import com.nuena.bkmy.facade.TreatInfoFacade;
import com.nuena.bkmy.facade.TreatRawDataFacade;
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
@Order(9)
@Component
public class TreatHtmlInit implements ApplicationRunner {

    @Value("${treat.html.insect.finished}")
    private boolean treat_html_insect_finished;
    @Autowired
    private TreatRawDataFacade treatRawDataFacade;
    @Autowired
    private TreatInfoFacade treatInfoFacade;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (treat_html_insect_finished) {
            return;
        }

        List<TreatInfo> treatInfoList = null;
        while (ListUtil.isNotEmpty(treatInfoList = treatInfoFacade.getNullHtmlTrt())) {
            treatInfoList.forEach(i -> {
                try {
                    treatRawDataFacade.everyTrtHtml(i);
                } catch (Exception e) {
                }
            });
        }
    }

}