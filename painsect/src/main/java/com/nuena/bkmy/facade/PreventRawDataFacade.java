package com.nuena.bkmy.facade;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nuena.bkmy.entity.PreventInfo;
import com.nuena.bkmy.entity.PreventRawData;
import com.nuena.bkmy.service.impl.PreventRawDataServiceImpl;
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
 * @time: 2020/1/16 13:44
 */
@Component
public class PreventRawDataFacade extends PreventRawDataServiceImpl {

    @Autowired
    private PreventInfoFacade preventInfoFacade;

    /**
     * 处理单个预防
     *
     * @param preventInfo
     */
    @Transactional
    public void everyPvtHtml(PreventInfo preventInfo) {
        try {
            Random rd = new Random();
            Thread.sleep(1000 * rd.nextInt(11) + 2000);
        } catch (Exception e) {
        }

        String html = HttpTool.get(preventInfo.getPvtUrl());
        if (StringUtil.isBlank(html)) {
            return;
        }

        PreventRawData preventRawData = new PreventRawData();
        preventRawData.setPvtId(preventInfo.getPvtId());
        preventRawData.setPvtName(preventInfo.getPvtName());
        preventRawData.setPvtUrl(preventInfo.getPvtUrl());
        preventRawData.setPvtHtml(html);
        preventRawData.setCreateTime(new Date());
        save(preventRawData);

        preventInfo.setRemark("1");
        preventInfoFacade.updateById(preventInfo);
    }

    /**
     * 获取所有的未解析html
     *
     * @return
     */
    public List<PreventRawData> getNoAnalysisPreventRawData() {
        QueryWrapper<PreventRawData> preventRawDataQe = new QueryWrapper<>();
        preventRawDataQe.isNull("remark");
        return list(preventRawDataQe);
    }

}