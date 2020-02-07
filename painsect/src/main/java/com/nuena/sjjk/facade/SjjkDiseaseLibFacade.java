package com.nuena.sjjk.facade;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.nuena.sjjk.entity.SjjkDiseaseLib;
import com.nuena.sjjk.service.impl.SjjkDiseaseLibServiceImpl;
import com.nuena.util.DateUtil;
import com.nuena.util.EnDecodeUtil;
import com.nuena.util.HttpTool;
import com.nuena.util.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @author: rengb
 * @time: 2020/2/6 23:58
 */
@Component
public class SjjkDiseaseLibFacade extends SjjkDiseaseLibServiceImpl {

    @Autowired
    @Qualifier("sjjkDiseaseLibServiceImpl")
    private SjjkDiseaseLibServiceImpl diseaseLibService;

    @Transactional
    public void initDisIdData() {
        List<SjjkDiseaseLib> diseaseLibList = Lists.newArrayList();
        for (int i = 1; i <= 134; i++) {
            diseaseLibList.addAll(getDiseases(i));
        }

        Date now = DateUtil.now();
        diseaseLibList.forEach(i -> {
            i.setCreateTime(now);
        });

        diseaseLibService.saveBatch(diseaseLibList);
    }

    /**
     * 根据页码，获取疾病列表(包含id(其实是首字母) 名称 各个模块url)
     *
     * @param yeshu
     * @return
     */
    private List<SjjkDiseaseLib> getDiseases(int yeshu) {
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
        }
        List<SjjkDiseaseLib> retList = Lists.newArrayList();
        String html = HttpTool.get("http://jbk.39.net/bw/t1_p" + yeshu + "/");
        if (StringUtil.isBlank(html)) {
            return retList;
        }

        Document doc = Jsoup.parse(html);
        Element resultContentElement = doc.getElementsByClass("result_content").first();
        if (resultContentElement == null) {
            return retList;
        }

        Elements aElements = resultContentElement.getElementsByClass("result_item_top_l").select("a");
        aElements.forEach(i -> {
            SjjkDiseaseLib diseaseLib = new SjjkDiseaseLib();
            String href = i.attr("href");
            String disSzmsx = href.substring(18, href.length() - 1);
            diseaseLib.setDisId(disSzmsx);
            diseaseLib.setDisName(i.text());
            diseaseLib.setSynopsisUrl("http://jbk.39.net/" + disSzmsx + "/jbzs/");
            diseaseLib.setEtiologyUrl("http://jbk.39.net/" + disSzmsx + "/blby/");
            diseaseLib.setPreventUrl("http://jbk.39.net/" + disSzmsx + "/yfhl/");
            diseaseLib.setComplicationUrl("http://jbk.39.net/" + disSzmsx + "/bfbz/");
            diseaseLib.setSymptomUrl("http://jbk.39.net/" + disSzmsx + "/zztz/");
            diseaseLib.setExamineUrl("http://jbk.39.net/" + disSzmsx + "/jcjb/");
            diseaseLib.setDiscernUrl("http://jbk.39.net/" + disSzmsx + "/jb/");
            diseaseLib.setTreatUrl("http://jbk.39.net/" + disSzmsx + "/yyzl/");
            diseaseLib.setNurseUrl("http://jbk.39.net/" + disSzmsx + "/hl/");
            diseaseLib.setHealthUrl("http://jbk.39.net/" + disSzmsx + "/ysbj/");
            diseaseLib.setMedviceUrl("http://jbk.39.net/" + disSzmsx + "/jzzn/");
            diseaseLib.setDrugUrl("http://jbk.39.net/" + disSzmsx + "/cyyp/");
            diseaseLib.setAskanswerUrl("http://jbk.39.net/" + disSzmsx + "/zjzx/");
            retList.add(diseaseLib);
        });
        return retList;
    }

    /**
     * 获取未下载html的疾病列表
     *
     * @return
     */
    public List<SjjkDiseaseLib> getNoLoadHtmlDiseases() {
        QueryWrapper<SjjkDiseaseLib> diseaseLibQe = new QueryWrapper<>();
        diseaseLibQe.eq("is_htmls_load", 0);
        return list(diseaseLibQe);
    }

    /**
     * 下载疾病的各个模块html
     *
     * @param diseaseLib
     */
    @Transactional
    public void loadHtml(SjjkDiseaseLib diseaseLib) {
        String synopsisHtml = loadHtml(diseaseLib.getSynopsisUrl(), "list_left");
        String etiologyHtml = loadHtml(diseaseLib.getEtiologyUrl(), "list_left");
        String preventHtml = loadHtml(diseaseLib.getPreventUrl(), "list_left");
        String complicationHtml = loadHtml(diseaseLib.getComplicationUrl(), "list_left");
        String symptomHtml = loadHtml(diseaseLib.getSymptomUrl(), "list_left");
        String examineHtml = loadHtml(diseaseLib.getExamineUrl(), "list_left");
        String discernHtml = loadHtml(diseaseLib.getDiscernUrl(), "list_left");
        String treatHtml = loadHtml(diseaseLib.getTreatUrl(), "list_left");
        String nurseHtml = loadHtml(diseaseLib.getNurseUrl(), "list_left");
        String healthHtml = loadHtml(diseaseLib.getHealthUrl(), "list_left");
        String medviceHtml = loadHtml(diseaseLib.getMedviceUrl(), "content");
        String drugHtml = loadHtml(diseaseLib.getDrugUrl(), "fl730");

        if (StringUtil.isNotBlank(synopsisHtml)
                && StringUtil.isNotBlank(etiologyHtml)
                && StringUtil.isNotBlank(preventHtml)
                && StringUtil.isNotBlank(complicationHtml)
                && StringUtil.isNotBlank(symptomHtml)
                && StringUtil.isNotBlank(examineHtml)
                && StringUtil.isNotBlank(discernHtml)
                && StringUtil.isNotBlank(treatHtml)
                && StringUtil.isNotBlank(nurseHtml)
                && StringUtil.isNotBlank(healthHtml)
                && StringUtil.isNotBlank(drugHtml)) {
            diseaseLib.setSynopsisHtml(synopsisHtml);
            diseaseLib.setEtiologyHtml(etiologyHtml);
            diseaseLib.setPreventHtml(preventHtml);
            diseaseLib.setComplicationHtml(complicationHtml);
            diseaseLib.setSymptomHtml(symptomHtml);
            diseaseLib.setExamineHtml(examineHtml);
            diseaseLib.setDiscernHtml(discernHtml);
            diseaseLib.setTreatHtml(treatHtml);
            diseaseLib.setNurseHtml(nurseHtml);
            diseaseLib.setHealthHtml(healthHtml);
            diseaseLib.setMedviceHtml(medviceHtml);
            diseaseLib.setDrugHtml(drugHtml);
            diseaseLib.setIsHtmlsLoad(1);
            updateById(diseaseLib);
        }
    }

    /**
     * 下载html
     *
     * @param url
     * @param modelName
     * @return
     */
    private String loadHtml(String url, String modelName) {
        String ret = null;
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
        }
        String html = HttpTool.post(url);
        if (StringUtil.isNotBlank(html)) {
            Document doc = Jsoup.parse(html);
            Element modelNameElement = doc.getElementsByClass("modelName").first();
            if (modelNameElement != null) {
                ret = EnDecodeUtil.encode(modelNameElement.outerHtml());
            }
        }
        return ret;
    }
    
}