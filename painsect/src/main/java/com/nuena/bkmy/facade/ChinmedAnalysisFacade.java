package com.nuena.bkmy.facade;

import com.google.common.collect.Lists;
import com.nuena.bkmy.entity.ChinmedAnalysis;
import com.nuena.bkmy.entity.ChinmedRawData;
import com.nuena.bkmy.service.impl.ChinmedAnalysisServiceImpl;
import com.nuena.bkmy.service.impl.ChinmedRawDataServiceImpl;
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
public class ChinmedAnalysisFacade extends ChinmedAnalysisServiceImpl {

    @Autowired
    @Qualifier("chinmedRawDataServiceImpl")
    private ChinmedRawDataServiceImpl chinmedRawDataService;
    @Autowired
    @Qualifier("chinmedAnalysisServiceImpl")
    private ChinmedAnalysisServiceImpl chinmedAnalysisService;

    /**
     * 解析html
     */
    @Transactional
    public void analysisHtml(List<ChinmedRawData> chinmedRawDataList) {
        List<ChinmedAnalysis> chinmedAnalysisSaveList = Lists.newArrayList();
        List<ChinmedRawData> chinmedRawDataUpdateList = Lists.newArrayList();
        Date now = new Date();
        String txtOfHtml = null;
        for (ChinmedRawData chinmedRawData : chinmedRawDataList) {
            txtOfHtml = analysisEveryHtml(chinmedRawData.getChdHtml());
            if (FileUtil.fileWrite("D:\\百科名医\\中医", chinmedRawData.getChdName() + "_" + chinmedRawData.getChdId() + ".txt", txtOfHtml)) {
                ChinmedAnalysis chinmedAnalysis = new ChinmedAnalysis();
                chinmedAnalysis.setChdId(chinmedRawData.getChdId());
                chinmedAnalysis.setChdName(chinmedRawData.getChdName());
                chinmedAnalysis.setChdAnaytxt(txtOfHtml);
                chinmedAnalysis.setCreateTime(now);
                chinmedAnalysisSaveList.add(chinmedAnalysis);

                chinmedRawData.setRemark("1");
                chinmedRawDataUpdateList.add(chinmedRawData);
            }
        }
        if (ListUtil.isNotEmpty(chinmedAnalysisSaveList)) {
            chinmedAnalysisService.saveBatch(chinmedAnalysisSaveList);
            chinmedRawDataService.updateBatchById(chinmedRawDataUpdateList);
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