package com.nuena.bkmy.facade;

import com.google.common.collect.Lists;
import com.nuena.bkmy.entity.SymptomAnalysis;
import com.nuena.bkmy.entity.SymptomRawData;
import com.nuena.bkmy.service.impl.SymptomAnalysisServiceImpl;
import com.nuena.bkmy.service.impl.SymptomRawDataServiceImpl;
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
 * @time: 2020/1/20 9:57
 */
@Component
public class SymptomAnalysisFacade extends SymptomAnalysisServiceImpl {

    @Autowired
    @Qualifier("symptomRawDataServiceImpl")
    private SymptomRawDataServiceImpl symptomRawDataService;
    @Autowired
    @Qualifier("symptomAnalysisServiceImpl")
    private SymptomAnalysisServiceImpl symptomAnalysisService;

    /**
     * 解析html
     */
    @Transactional
    public void analysisHtml(List<SymptomRawData> symptomRawDataList) {
        List<SymptomAnalysis> symptomAnalysisSaveList = Lists.newArrayList();
        List<SymptomRawData> symptomRawDataUpdateList = Lists.newArrayList();
        Date now = new Date();
        String txtOfHtml = null;
        for (SymptomRawData symptomRawData : symptomRawDataList) {
            txtOfHtml = analysisEveryHtml(symptomRawData.getSymHtml());
            if (FileUtil.fileWrite("D:\\百科名医\\症状", symptomRawData.getSymName() + "_" + symptomRawData.getSymId() + ".txt", txtOfHtml)) {
                SymptomAnalysis symptomAnalysis = new SymptomAnalysis();
                symptomAnalysis.setSymId(symptomRawData.getSymId());
                symptomAnalysis.setSymName(symptomRawData.getSymName());
                symptomAnalysis.setSymAnaytxt(txtOfHtml);
                symptomAnalysis.setCreateTime(now);
                symptomAnalysisSaveList.add(symptomAnalysis);

                symptomRawData.setRemark("1");
                symptomRawDataUpdateList.add(symptomRawData);
            }
        }
        if (ListUtil.isNotEmpty(symptomAnalysisSaveList)) {
            symptomAnalysisService.saveBatch(symptomAnalysisSaveList);
            symptomRawDataService.updateBatchById(symptomRawDataUpdateList);
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