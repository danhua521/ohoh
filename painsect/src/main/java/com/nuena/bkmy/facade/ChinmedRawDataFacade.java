package com.nuena.bkmy.facade;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nuena.bkmy.entity.ChinmedInfo;
import com.nuena.bkmy.entity.ChinmedRawData;
import com.nuena.bkmy.service.impl.ChinmedRawDataServiceImpl;
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
 * @time: 2020/1/17 16:00
 */
@Component
public class ChinmedRawDataFacade extends ChinmedRawDataServiceImpl {

    @Autowired
    private ChinmedInfoFacade chinmedInfoFacade;

    /**
     * 处理单个中医
     *
     * @param chinmedInfo
     */
    @Transactional
    public void everyChdHtml(ChinmedInfo chinmedInfo) {
        try {
            Random rd = new Random();
            Thread.sleep(1000 * rd.nextInt(11) + 2000);
        } catch (Exception e) {
        }

        String html = HttpTool.get(chinmedInfo.getChdUrl());
        if (StringUtil.isBlank(html)) {
            return;
        }

        ChinmedRawData chinmedRawData = new ChinmedRawData();
        chinmedRawData.setChdId(chinmedInfo.getChdId());
        chinmedRawData.setChdName(chinmedInfo.getChdName());
        chinmedRawData.setChdUrl(chinmedInfo.getChdUrl());
        chinmedRawData.setChdHtml(html);
        chinmedRawData.setCreateTime(new Date());
        save(chinmedRawData);

        chinmedInfo.setRemark("1");
        chinmedInfoFacade.updateById(chinmedInfo);
    }

    /**
     * 获取所有的未解析html
     *
     * @return
     */
    public List<ChinmedRawData> getNoAnalysisChinmedRawData() {
        QueryWrapper<ChinmedRawData> chinmedRawDataQe = new QueryWrapper<>();
        chinmedRawDataQe.isNull("remark");
        return list(chinmedRawDataQe);
    }

}