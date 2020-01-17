package com.nuena.bkmy.order;

import com.nuena.bkmy.entity.ExamineInfo;
import com.nuena.bkmy.facade.ExamineInfoFacade;
import com.nuena.bkmy.facade.ExamineRawDataFacade;
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
@Order(5)
@Component
public class ExamineHtmlInit implements ApplicationRunner {

    @Value("${examine.html.insect.finished}")
    private boolean examine_html_insect_finished;
    @Autowired
    private ExamineRawDataFacade examineRawDataFacade;
    @Autowired
    private ExamineInfoFacade examineInfoFacade;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (examine_html_insect_finished) {
            return;
        }

        List<ExamineInfo> examineInfoList = null;
        while (ListUtil.isNotEmpty(examineInfoList = examineInfoFacade.getNullHtmlExa())) {
            examineInfoList.forEach(i -> {
                try {
                    examineRawDataFacade.everyExaHtml(i);
                } catch (Exception e) {
                }
            });
        }
    }

}