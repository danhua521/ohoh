package com.nuena.bkmy.facade;

import com.google.common.collect.Lists;
import com.nuena.bkmy.entity.ExamineAnalysis;
import com.nuena.bkmy.entity.ExamineRawData;
import com.nuena.bkmy.service.impl.ExamineAnalysisServiceImpl;
import com.nuena.bkmy.service.impl.ExamineRawDataServiceImpl;
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
public class ExamineAnalysisFacade extends ExamineAnalysisServiceImpl {

    @Autowired
    @Qualifier("examineRawDataServiceImpl")
    private ExamineRawDataServiceImpl examineRawDataService;
    @Autowired
    @Qualifier("examineAnalysisServiceImpl")
    private ExamineAnalysisServiceImpl examineAnalysisService;

    /**
     * 解析html
     */
    @Transactional
    public void analysisHtml(List<ExamineRawData> examineRawDataList) {
        List<ExamineAnalysis> examineAnalysisSaveList = Lists.newArrayList();
        List<ExamineRawData> examineRawDataUpdateList = Lists.newArrayList();
        Date now = new Date();
        String txtOfHtml = null;
        for (ExamineRawData examineRawData : examineRawDataList) {
            txtOfHtml = analysisEveryHtml(examineRawData.getExaHtml());
            if (FileUtil.fileWrite("D:\\百科名医\\检查", examineRawData.getExaName().replaceAll("\\\\", "fanxiegang").replaceAll("/", "xiegang") + "_" + examineRawData.getExaId() + ".txt", txtOfHtml)) {
                ExamineAnalysis examineAnalysis = new ExamineAnalysis();
                examineAnalysis.setExaId(examineRawData.getExaId());
                examineAnalysis.setExaName(examineRawData.getExaName());
                examineAnalysis.setExaAnaytxt(txtOfHtml);
                examineAnalysis.setCreateTime(now);
                examineAnalysisSaveList.add(examineAnalysis);

                examineRawData.setRemark("1");
                examineRawDataUpdateList.add(examineRawData);
            }
        }
        if (ListUtil.isNotEmpty(examineAnalysisSaveList)) {
            examineAnalysisService.saveBatch(examineAnalysisSaveList);
            examineRawDataService.updateBatchById(examineRawDataUpdateList);
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