package com.nuena.bkmy.facade;

import com.google.common.collect.Lists;
import com.nuena.bkmy.entity.NurseAnalysis;
import com.nuena.bkmy.entity.NurseRawData;
import com.nuena.bkmy.service.impl.NurseAnalysisServiceImpl;
import com.nuena.bkmy.service.impl.NurseRawDataServiceImpl;
import com.nuena.util.FileUtil;
import com.nuena.util.ListUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @author: rengb
 * @time: 2020/1/20 10:00
 */
@Component
public class NurseAnalysisFacade extends NurseAnalysisServiceImpl {

    @Autowired
    @Qualifier("nurseRawDataServiceImpl")
    private NurseRawDataServiceImpl nurseRawDataService;
    @Autowired
    @Qualifier("nurseAnalysisServiceImpl")
    private NurseAnalysisServiceImpl nurseAnalysisService;

    /**
     * 解析html
     */
    @Transactional
    public void analysisHtml(List<NurseRawData> nurseRawDataList) {
        List<NurseAnalysis> nurseAnalysisSaveList = Lists.newArrayList();
        List<NurseRawData> nurseRawDataUpdateList = Lists.newArrayList();
        Date now = new Date();
        String txtOfHtml = null;
        for (NurseRawData nurseRawData : nurseRawDataList) {
            txtOfHtml = analysisEveryHtml(nurseRawData.getNurHtml());
            if (FileUtil.fileWrite("D:\\百科名医\\护理", nurseRawData.getNurName() + "_" + nurseRawData.getNurId() + ".txt", txtOfHtml)) {
                NurseAnalysis nurseAnalysis = new NurseAnalysis();
                nurseAnalysis.setNurId(nurseRawData.getNurId());
                nurseAnalysis.setNurName(nurseRawData.getNurName());
                nurseAnalysis.setNurAnaytxt(txtOfHtml);
                nurseAnalysis.setCreateTime(now);
                nurseAnalysisSaveList.add(nurseAnalysis);

                nurseRawData.setRemark("1");
                nurseRawDataUpdateList.add(nurseRawData);
            }
        }
        if (ListUtil.isNotEmpty(nurseAnalysisSaveList)) {
            nurseAnalysisService.saveBatch(nurseAnalysisSaveList);
            nurseRawDataService.updateBatchById(nurseRawDataUpdateList);
        }
    }

    /**
     * 解析每一个html
     *
     * @param html
     * @return
     */
    private String analysisEveryHtml(String html) {
        Document contentDoc = Jsoup.parse(html);
        contentDoc.getElementsByClass("s_directory_flag").forEach(i -> {
            i.appendText("@ab98cdef");
        });
        String specialityVersionHtml = contentDoc.getElementById("specialityVersion").html().replaceAll("<br>", "@ab98cdef");
        Document specialityVersionDoc = Jsoup.parse(specialityVersionHtml);
        String specialityVersionText = specialityVersionDoc.text().replaceAll("@ab98cdef", "\r\n");

        return specialityVersionText;
    }

}