package com.nuena.bkmy.facade;

import com.nuena.bkmy.entity.NurseInfo;
import com.nuena.bkmy.entity.NurseRawData;
import com.nuena.bkmy.service.impl.NurseRawDataServiceImpl;
import com.nuena.util.HttpTool;
import com.nuena.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Random;

/**
 * @Description:
 * @author: rengb
 * @time: 2020/1/16 13:42
 */
@Component
public class NurseRawDataFacade extends NurseRawDataServiceImpl {

    @Autowired
    private NurseInfoFacade nurseInfoFacade;

    /**
     * 处理单个护理
     *
     * @param nurseInfo
     */
    @Transactional
    public void everyNurHtml(NurseInfo nurseInfo) {
        try {
            Random rd = new Random();
            Thread.sleep(1000 * rd.nextInt(11) + 2000);
        } catch (Exception e) {
        }

        String html = HttpTool.get(nurseInfo.getNurUrl());
        if (StringUtil.isBlank(html)) {
            return;
        }

        NurseRawData nurseRawData = new NurseRawData();
        nurseRawData.setNurId(nurseInfo.getNurId());
        nurseRawData.setNurName(nurseInfo.getNurName());
        nurseRawData.setNurUrl(nurseInfo.getNurUrl());
        nurseRawData.setNurHtml(html);
        nurseRawData.setCreateTime(new Date());
        save(nurseRawData);

        nurseInfo.setRemark("1");
        nurseInfoFacade.updateById(nurseInfo);
    }

}