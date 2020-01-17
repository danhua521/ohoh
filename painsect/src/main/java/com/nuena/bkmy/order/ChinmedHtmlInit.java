package com.nuena.bkmy.order;

import com.nuena.bkmy.entity.ChinmedInfo;
import com.nuena.bkmy.facade.ChinmedInfoFacade;
import com.nuena.bkmy.facade.ChinmedRawDataFacade;
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
@Order(15)
@Component
public class ChinmedHtmlInit implements ApplicationRunner {

    @Value("${chinmed.html.insect.finished}")
    private boolean chinmed_html_insect_finished;
    @Autowired
    private ChinmedRawDataFacade chinmedRawDataFacade;
    @Autowired
    private ChinmedInfoFacade chinmedInfoFacade;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (chinmed_html_insect_finished) {
            return;
        }

        List<ChinmedInfo> chinmedInfoList = null;
        while (ListUtil.isNotEmpty(chinmedInfoList = chinmedInfoFacade.getNullHtmlChd())) {
            chinmedInfoList.forEach(i -> {
                try {
                    chinmedRawDataFacade.everyChdHtml(i);
                } catch (Exception e) {
                }
            });
        }
    }

}