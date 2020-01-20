package com.nuena.bkmy.facade;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nuena.bkmy.entity.SymptomInfo;
import com.nuena.bkmy.entity.SymptomRawData;
import com.nuena.bkmy.service.impl.SymptomRawDataServiceImpl;
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
 * @time: 2020/1/15 15:11
 */
@Component
public class SymptomRawDataFacade extends SymptomRawDataServiceImpl {

    @Autowired
    private SymptomInfoFacade symptomInfoFacade;

    /**
     * 处理单个症状
     *
     * @param symptomInfo
     */
    @Transactional
    public void everySymHtml(SymptomInfo symptomInfo) {
        try {
            Random rd = new Random();
            Thread.sleep(1000 * rd.nextInt(11) + 2000);
        } catch (Exception e) {
        }

        String html = HttpTool.get(symptomInfo.getSymUrl());
        if (StringUtil.isBlank(html)) {
            return;
        }

        SymptomRawData symptomRawData = new SymptomRawData();
        symptomRawData.setSymId(symptomInfo.getSymId());
        symptomRawData.setSymName(symptomInfo.getSymName());
        symptomRawData.setSymUrl(symptomInfo.getSymUrl());
        symptomRawData.setSymHtml(html);
        symptomRawData.setCreateTime(new Date());
        save(symptomRawData);

        symptomInfo.setRemark("1");
        symptomInfoFacade.updateById(symptomInfo);
    }

    /**
     * 获取所有的未解析html
     *
     * @return
     */
    public List<SymptomRawData> getNoAnalysisSymptomRawData() {
        QueryWrapper<SymptomRawData> symptomRawDataQe = new QueryWrapper<>();
        symptomRawDataQe.isNull("remark");
        return list(symptomRawDataQe);
    }

}