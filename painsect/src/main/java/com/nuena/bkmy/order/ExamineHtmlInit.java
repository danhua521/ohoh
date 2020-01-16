package com.nuena.bkmy.order;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nuena.bkmy.entity.ExamineInfo;
import com.nuena.bkmy.entity.ExamineRawData;
import com.nuena.bkmy.facade.ExamineInfoFacade;
import com.nuena.bkmy.facade.ExamineRawDataFacade;
import com.nuena.util.HttpTool;
import com.nuena.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Random;

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
        initData();
    }

    /**
     * 初始化，插入检查html信息
     */
    private void initData() {
        QueryWrapper<ExamineInfo> examineInfoQe = new QueryWrapper<>();
        examineInfoQe.isNull("remark");
        List<ExamineInfo> examineInfoList = examineInfoFacade.list(examineInfoQe);
        examineInfoList.forEach(i -> {
            everyExaHtml(i);
        });
    }

    private void everyExaHtml(ExamineInfo examineInfo) {
        try {
            Random rd = new Random();
            Thread.sleep(1000 * rd.nextInt(10) + 2000);
            String html = HttpTool.get(examineInfo.getExaUrl());
            if (StringUtil.isBlank(html)) {
                return;
            }

            ExamineRawData examineRawData = new ExamineRawData();
            examineRawData.setExaId(examineInfo.getExaId());
            examineRawData.setExaName(examineInfo.getExaName());
            examineRawData.setExaUrl(examineInfo.getExaUrl());
            examineRawData.setExaHtml(html);
            examineRawData.setCreateTime(new Date());
            examineRawDataFacade.save(examineRawData);

            examineInfo.setRemark("1");
            examineInfoFacade.updateById(examineInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}