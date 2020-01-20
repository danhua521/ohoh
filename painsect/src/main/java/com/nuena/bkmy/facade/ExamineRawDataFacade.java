package com.nuena.bkmy.facade;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nuena.bkmy.entity.ExamineInfo;
import com.nuena.bkmy.entity.ExamineRawData;
import com.nuena.bkmy.service.impl.ExamineRawDataServiceImpl;
import com.nuena.util.HttpTool;
import com.nuena.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @Description:
 * @author: rengb
 * @time: 2020/1/15 15:54
 */
@Component
public class ExamineRawDataFacade extends ExamineRawDataServiceImpl {

    @Autowired
    private ExamineInfoFacade examineInfoFacade;

    /**
     * 处理单个检查
     *
     * @param examineInfo
     */
    @Transactional
    public void everyExaHtml(ExamineInfo examineInfo) {
        try {
            Random rd = new Random();
            Thread.sleep(1000 * rd.nextInt(11) + 2000);
        } catch (Exception e) {
        }

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
        save(examineRawData);

        examineInfo.setRemark("1");
        examineInfoFacade.updateById(examineInfo);
    }

    /**
     * 获取所有的未解析html
     *
     * @return
     */
    public List<ExamineRawData> getNoAnalysisExamineRawData() {
        QueryWrapper<ExamineRawData> examineRawDataQe = new QueryWrapper<>();
        examineRawDataQe.isNull("remark");
        return list(examineRawDataQe);
    }

}