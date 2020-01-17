package com.nuena.bkmy.order;

import com.nuena.bkmy.entity.NurseInfo;
import com.nuena.bkmy.facade.NurseInfoFacade;
import com.nuena.bkmy.facade.NurseRawDataFacade;
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
@Order(13)
@Component
public class NurseHtmlInit implements ApplicationRunner {

    @Value("${nurse.html.insect.finished}")
    private boolean nurse_html_insect_finished;
    @Autowired
    private NurseRawDataFacade nurseRawDataFacade;
    @Autowired
    private NurseInfoFacade nurseInfoFacade;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (nurse_html_insect_finished) {
            return;
        }

        List<NurseInfo> nurseInfoList = null;
        while (ListUtil.isNotEmpty(nurseInfoList = nurseInfoFacade.getNullHtmlNur())) {
            nurseInfoList.forEach(i -> {
                try {
                    nurseRawDataFacade.everyNurHtml(i);
                } catch (Exception e) {
                }
            });
        }
    }

}