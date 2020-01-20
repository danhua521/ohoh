package com.nuena.bkmy.facade;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nuena.bkmy.entity.TreatInfo;
import com.nuena.bkmy.entity.TreatRawData;
import com.nuena.bkmy.service.impl.TreatRawDataServiceImpl;
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
 * @time: 2020/1/16 13:46
 */
@Component
public class TreatRawDataFacade extends TreatRawDataServiceImpl {

    @Autowired
    private TreatInfoFacade treatInfoFacade;

    /**
     * 处理单个治疗
     *
     * @param treatInfo
     */
    @Transactional
    public void everyTrtHtml(TreatInfo treatInfo) {
        try {
            Random rd = new Random();
            Thread.sleep(1000 * rd.nextInt(11) + 2000);
        } catch (Exception e) {
        }

        String html = HttpTool.get(treatInfo.getTrtUrl());
        if (StringUtil.isBlank(html)) {
            return;
        }

        TreatRawData treatRawData = new TreatRawData();
        treatRawData.setTrtId(treatInfo.getTrtId());
        treatRawData.setTrtName(treatInfo.getTrtName());
        treatRawData.setTrtUrl(treatInfo.getTrtUrl());
        treatRawData.setTrtHtml(html);
        treatRawData.setCreateTime(new Date());
        save(treatRawData);

        treatInfo.setRemark("1");
        treatInfoFacade.updateById(treatInfo);
    }

    /**
     * 获取所有的未解析html
     *
     * @return
     */
    public List<TreatRawData> getNoAnalysisTreatRawData() {
        QueryWrapper<TreatRawData> treatRawDataQe = new QueryWrapper<>();
        treatRawDataQe.isNull("remark");
        return list(treatRawDataQe);
    }
    
}