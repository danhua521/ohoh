package com.nuena.bkmy.facade;

import com.google.common.collect.Lists;
import com.nuena.bkmy.entity.PreventAnalysis;
import com.nuena.bkmy.entity.PreventRawData;
import com.nuena.bkmy.service.impl.PreventAnalysisServiceImpl;
import com.nuena.bkmy.service.impl.PreventRawDataServiceImpl;
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
 * @time: 2020/1/20 9:59
 */
@Component
public class PreventAnalysisFacade extends PreventAnalysisServiceImpl {

    @Autowired
    @Qualifier("preventRawDataServiceImpl")
    private PreventRawDataServiceImpl preventRawDataService;
    @Autowired
    @Qualifier("preventAnalysisServiceImpl")
    private PreventAnalysisServiceImpl preventAnalysisService;

    /**
     * 解析html
     */
    @Transactional
    public void analysisHtml(List<PreventRawData> preventRawDataList) {
        List<PreventAnalysis> preventAnalysisSaveList = Lists.newArrayList();
        List<PreventRawData> preventRawDataUpdateList = Lists.newArrayList();
        Date now = new Date();
        String txtOfHtml = null;
        for (PreventRawData preventRawData : preventRawDataList) {
            txtOfHtml = analysisEveryHtml(preventRawData.getPvtHtml());
            if (FileUtil.fileWrite("D:\\百科名医\\预防", preventRawData.getPvtName().replaceAll("\\\\", "fanxiegang").replaceAll("/", "xiegang") + "_" + preventRawData.getPvtId() + ".txt", txtOfHtml)) {
                PreventAnalysis preventAnalysis = new PreventAnalysis();
                preventAnalysis.setPvtId(preventRawData.getPvtId());
                preventAnalysis.setPvtName(preventRawData.getPvtName());
                preventAnalysis.setPvtAnaytxt(txtOfHtml);
                preventAnalysis.setCreateTime(now);
                preventAnalysisSaveList.add(preventAnalysis);

                preventRawData.setRemark("1");
                preventRawDataUpdateList.add(preventRawData);
            }
        }
        if (ListUtil.isNotEmpty(preventAnalysisSaveList)) {
            preventAnalysisService.saveBatch(preventAnalysisSaveList);
            preventRawDataService.updateBatchById(preventRawDataUpdateList);
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