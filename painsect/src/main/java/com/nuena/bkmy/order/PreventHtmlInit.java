package com.nuena.bkmy.order;

import com.nuena.bkmy.entity.PreventInfo;
import com.nuena.bkmy.facade.PreventInfoFacade;
import com.nuena.bkmy.facade.PreventRawDataFacade;
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
@Order(11)
@Component
public class PreventHtmlInit implements ApplicationRunner {

    @Value("${prevent.html.insect.finished}")
    private boolean prevent_html_insect_finished;
    @Autowired
    private PreventRawDataFacade preventRawDataFacade;
    @Autowired
    private PreventInfoFacade preventInfoFacade;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (prevent_html_insect_finished) {
            return;
        }

        List<PreventInfo> preventInfoList = null;
        while (ListUtil.isNotEmpty(preventInfoList = preventInfoFacade.getNullHtmlPvt())) {
            preventInfoList.forEach(i -> {
                try {
                    preventRawDataFacade.everyPvtHtml(i);
                } catch (Exception e) {
                }
            });
        }
    }

}