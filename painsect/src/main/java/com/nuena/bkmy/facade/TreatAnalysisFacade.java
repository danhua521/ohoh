package com.nuena.bkmy.facade;

import com.google.common.collect.Lists;
import com.nuena.bkmy.entity.TreatAnalysis;
import com.nuena.bkmy.entity.TreatRawData;
import com.nuena.bkmy.service.impl.TreatAnalysisServiceImpl;
import com.nuena.bkmy.service.impl.TreatRawDataServiceImpl;
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
 * @time: 2020/1/20 9:58
 */
@Component
public class TreatAnalysisFacade extends TreatAnalysisServiceImpl {

    @Autowired
    @Qualifier("treatRawDataServiceImpl")
    private TreatRawDataServiceImpl treatRawDataService;
    @Autowired
    @Qualifier("treatAnalysisServiceImpl")
    private TreatAnalysisServiceImpl treatAnalysisService;

    /**
     * 解析html
     */
    @Transactional
    public void analysisHtml(List<TreatRawData> treatRawDataList) {
        List<TreatAnalysis> treatAnalysisSaveList = Lists.newArrayList();
        List<TreatRawData> treatRawDataUpdateList = Lists.newArrayList();
        Date now = new Date();
        String txtOfHtml = null;
        for (TreatRawData treatRawData : treatRawDataList) {
            txtOfHtml = analysisEveryHtml(treatRawData.getTrtHtml());
            if (FileUtil.fileWrite("D:\\百科名医\\治疗", treatRawData.getTrtName() + "_" + treatRawData.getTrtId() + ".txt", txtOfHtml)) {
                TreatAnalysis treatAnalysis = new TreatAnalysis();
                treatAnalysis.setTrtId(treatRawData.getTrtId());
                treatAnalysis.setTrtName(treatRawData.getTrtName());
                treatAnalysis.setTrtAnaytxt(txtOfHtml);
                treatAnalysis.setCreateTime(now);
                treatAnalysisSaveList.add(treatAnalysis);

                treatRawData.setRemark("1");
                treatRawDataUpdateList.add(treatRawData);
            }
        }
        if (ListUtil.isNotEmpty(treatAnalysisSaveList)) {
            treatAnalysisService.saveBatch(treatAnalysisSaveList);
            treatRawDataService.updateBatchById(treatRawDataUpdateList);
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