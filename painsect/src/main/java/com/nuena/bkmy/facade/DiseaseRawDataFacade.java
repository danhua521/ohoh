package com.nuena.bkmy.facade;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nuena.bkmy.entity.DiseaseInfo;
import com.nuena.bkmy.entity.DiseaseRawData;
import com.nuena.bkmy.service.impl.DiseaseRawDataServiceImpl;
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
 * @time: 2020/1/14 19:50
 */
@Component
public class DiseaseRawDataFacade extends DiseaseRawDataServiceImpl {

    @Autowired
    private DiseaseInfoFacade diseaseInfoFacade;

    /**
     * 处理单个疾病
     *
     * @param diseaseInfo
     */
    @Transactional
    public void everyDisHtml(DiseaseInfo diseaseInfo) {
        try {
            Random rd = new Random();
            Thread.sleep(1000 * rd.nextInt(11) + 2000);
        } catch (Exception e) {
        }

        String html = HttpTool.get(diseaseInfo.getDisUrl());
        if (StringUtil.isBlank(html)) {
            return;
        }

        DiseaseRawData diseaseRawData = new DiseaseRawData();
        diseaseRawData.setDisId(diseaseInfo.getDisId());
        diseaseRawData.setDisName(diseaseInfo.getDisName());
        diseaseRawData.setDisUrl(diseaseInfo.getDisUrl());
        diseaseRawData.setDisHtml(html);
        diseaseRawData.setCreateTime(new Date());
        save(diseaseRawData);

        diseaseInfo.setRemark("1");
        diseaseInfoFacade.updateById(diseaseInfo);
    }

    /**
     * 获取所有的未解析html
     *
     * @return
     */
    public List<DiseaseRawData> getNoAnalysisDiseaseRawData() {
        QueryWrapper<DiseaseRawData> diseaseRawDataQe = new QueryWrapper<>();
        diseaseRawDataQe.isNull("remark");
        return list(diseaseRawDataQe);
    }

}