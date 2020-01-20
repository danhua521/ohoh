package com.nuena.bkmy.facade;

import com.google.common.collect.Lists;
import com.nuena.bkmy.entity.DiseaseAnalysis;
import com.nuena.bkmy.entity.DiseaseRawData;
import com.nuena.bkmy.service.impl.DiseaseAnalysisServiceImpl;
import com.nuena.bkmy.service.impl.DiseaseRawDataServiceImpl;
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
 * @time: 2020/1/19 10:54
 */
@Component
public class DiseaseAnalysisFacade extends DiseaseAnalysisServiceImpl {

    @Autowired
    @Qualifier("diseaseRawDataServiceImpl")
    private DiseaseRawDataServiceImpl diseaseRawDataService;
    @Autowired
    @Qualifier("diseaseAnalysisServiceImpl")
    private DiseaseAnalysisServiceImpl diseaseAnalysisService;

    /**
     * 解析html
     */
    @Transactional
    public void analysisHtml(List<DiseaseRawData> diseaseRawDataList) {
        List<DiseaseAnalysis> diseaseAnalysisSaveList = Lists.newArrayList();
        List<DiseaseRawData> diseaseRawDataUpdateList = Lists.newArrayList();
        Date now = new Date();
        String txtOfHtml = null;
        for (DiseaseRawData diseaseRawData : diseaseRawDataList) {
            txtOfHtml = analysisEveryHtml(diseaseRawData.getDisHtml());
            if (FileUtil.fileWrite("D:\\百科名医\\疾病", diseaseRawData.getDisName().replaceAll("\\\\", "fanxiegang").replaceAll("/", "xiegang") + "_" + diseaseRawData.getDisId() + ".txt", txtOfHtml)) {
                DiseaseAnalysis diseaseAnalysis = new DiseaseAnalysis();
                diseaseAnalysis.setDisId(diseaseRawData.getDisId());
                diseaseAnalysis.setDisName(diseaseRawData.getDisName());
                diseaseAnalysis.setDisAnaytxt(txtOfHtml);
                diseaseAnalysis.setCreateTime(now);
                diseaseAnalysisSaveList.add(diseaseAnalysis);

                diseaseRawData.setRemark("1");
                diseaseRawDataUpdateList.add(diseaseRawData);
            }
        }
        if (ListUtil.isNotEmpty(diseaseAnalysisSaveList)) {
            diseaseAnalysisService.saveBatch(diseaseAnalysisSaveList);
            diseaseRawDataService.updateBatchById(diseaseRawDataUpdateList);
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