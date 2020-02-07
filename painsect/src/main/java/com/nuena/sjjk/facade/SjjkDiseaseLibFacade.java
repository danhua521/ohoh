package com.nuena.sjjk.facade;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.nuena.sjjk.entity.SjjkDiseaseComplication;
import com.nuena.sjjk.entity.SjjkDiseaseDiscern;
import com.nuena.sjjk.entity.SjjkDiseaseDrug;
import com.nuena.sjjk.entity.SjjkDiseaseEtiology;
import com.nuena.sjjk.entity.SjjkDiseaseExamine;
import com.nuena.sjjk.entity.SjjkDiseaseHealth;
import com.nuena.sjjk.entity.SjjkDiseaseLib;
import com.nuena.sjjk.entity.SjjkDiseaseMedvice;
import com.nuena.sjjk.entity.SjjkDiseaseNurse;
import com.nuena.sjjk.entity.SjjkDiseasePrevent;
import com.nuena.sjjk.entity.SjjkDiseaseSymptom;
import com.nuena.sjjk.entity.SjjkDiseaseSynopsis;
import com.nuena.sjjk.entity.SjjkDiseaseTreat;
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
    @Autowired
    private SjjkDiseaseSynopsisFacade sjjkDiseaseSynopsisFacade;
    @Autowired
    private SjjkDiseaseEtiologyFacade sjjkDiseaseEtiologyFacade;
    @Autowired
    private SjjkDiseasePreventFacade sjjkDiseasePreventFacade;
    @Autowired
    private SjjkDiseaseComplicationFacade sjjkDiseaseComplicationFacade;
    @Autowired
    private SjjkDiseaseSymptomFacade sjjkDiseaseSymptomFacade;
    @Autowired
    private SjjkDiseaseExamineFacade sjjkDiseaseExamineFacade;
    @Autowired
    private SjjkDiseaseDiscernFacade sjjkDiseaseDiscernFacade;
    @Autowired
    private SjjkDiseaseTreatFacade sjjkDiseaseTreatFacade;
    @Autowired
    private SjjkDiseaseNurseFacade sjjkDiseaseNurseFacade;
    @Autowired
    private SjjkDiseaseHealthFacade sjjkDiseaseHealthFacade;
    @Autowired
    private SjjkDiseaseMedviceFacade sjjkDiseaseMedviceFacade;
    @Autowired
    private SjjkDiseaseDrugFacade sjjkDiseaseDrugFacade;

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
        String synopsisHtml, etiologyHtml, preventHtml, complicationHtml, symptomHtml, examineHtml,
                discernHtml, treatHtml, nurseHtml, healthHtml, medviceHtml, drugHtml;
        synopsisHtml = loadHtml(diseaseLib.getSynopsisUrl(), "list_left");
        if (StringUtil.isBlank(synopsisHtml)) {
            return;
        }
        etiologyHtml = loadHtml(diseaseLib.getEtiologyUrl(), "list_left");
        if (StringUtil.isBlank(etiologyHtml)) {
            return;
        }
        preventHtml = loadHtml(diseaseLib.getPreventUrl(), "list_left");
        if (StringUtil.isBlank(preventHtml)) {
            return;
        }
        complicationHtml = loadHtml(diseaseLib.getComplicationUrl(), "list_left");
        if (StringUtil.isBlank(complicationHtml)) {
            return;
        }
        symptomHtml = loadHtml(diseaseLib.getSymptomUrl(), "list_left");
        if (StringUtil.isBlank(symptomHtml)) {
            return;
        }
        examineHtml = loadHtml(diseaseLib.getExamineUrl(), "list_left");
        if (StringUtil.isBlank(examineHtml)) {
            return;
        }
        discernHtml = loadHtml(diseaseLib.getDiscernUrl(), "list_left");
        if (StringUtil.isBlank(discernHtml)) {
            return;
        }
        treatHtml = loadHtml(diseaseLib.getTreatUrl(), "list_left");
        if (StringUtil.isBlank(treatHtml)) {
            return;
        }
        nurseHtml = loadHtml(diseaseLib.getNurseUrl(), "list_left");
        if (StringUtil.isBlank(nurseHtml)) {
            return;
        }
        healthHtml = loadHtml(diseaseLib.getHealthUrl(), "list_left");
        if (StringUtil.isBlank(healthHtml)) {
            return;
        }
        medviceHtml = loadHtml(diseaseLib.getMedviceUrl(), "content");
        if (StringUtil.isBlank(medviceHtml)) {
            return;
        }
        drugHtml = loadHtml(diseaseLib.getDrugUrl(), "fl730");
        if (StringUtil.isBlank(drugHtml)) {
            return;
        }

        Date now = DateUtil.now();
        SjjkDiseaseSynopsis sjjkDiseaseSynopsis = new SjjkDiseaseSynopsis();
        sjjkDiseaseSynopsis.setDisLibId(diseaseLib.getId());
        sjjkDiseaseSynopsis.setDisId(diseaseLib.getDisId());
        sjjkDiseaseSynopsis.setDisName(diseaseLib.getDisName());
        sjjkDiseaseSynopsis.setSynopsisUrl(diseaseLib.getSynopsisUrl());
        sjjkDiseaseSynopsis.setSynopsisHtml(synopsisHtml);
        sjjkDiseaseSynopsis.setCreateTime(now);
        sjjkDiseaseSynopsis.setModifyTime(now);
        sjjkDiseaseSynopsisFacade.save(sjjkDiseaseSynopsis);

        SjjkDiseaseEtiology sjjkDiseaseEtiology = new SjjkDiseaseEtiology();
        sjjkDiseaseEtiology.setDisLibId(diseaseLib.getId());
        sjjkDiseaseEtiology.setDisId(diseaseLib.getDisId());
        sjjkDiseaseEtiology.setDisName(diseaseLib.getDisName());
        sjjkDiseaseEtiology.setEtiologyUrl(diseaseLib.getEtiologyUrl());
        sjjkDiseaseEtiology.setEtiologyHtml(etiologyHtml);
        sjjkDiseaseEtiology.setCreateTime(now);
        sjjkDiseaseEtiology.setModifyTime(now);
        sjjkDiseaseEtiologyFacade.save(sjjkDiseaseEtiology);

        SjjkDiseasePrevent sjjkDiseasePrevent = new SjjkDiseasePrevent();
        sjjkDiseasePrevent.setDisLibId(diseaseLib.getId());
        sjjkDiseasePrevent.setDisId(diseaseLib.getDisId());
        sjjkDiseasePrevent.setDisName(diseaseLib.getDisName());
        sjjkDiseasePrevent.setPreventUrl(diseaseLib.getPreventUrl());
        sjjkDiseasePrevent.setPreventHtml(preventHtml);
        sjjkDiseasePrevent.setCreateTime(now);
        sjjkDiseasePrevent.setModifyTime(now);
        sjjkDiseasePreventFacade.save(sjjkDiseasePrevent);

        SjjkDiseaseComplication sjjkDiseaseComplication = new SjjkDiseaseComplication();
        sjjkDiseaseComplication.setDisLibId(diseaseLib.getId());
        sjjkDiseaseComplication.setDisId(diseaseLib.getDisId());
        sjjkDiseaseComplication.setDisName(diseaseLib.getDisName());
        sjjkDiseaseComplication.setComplicationUrl(diseaseLib.getComplicationUrl());
        sjjkDiseaseComplication.setComplicationHtml(complicationHtml);
        sjjkDiseaseComplication.setCreateTime(now);
        sjjkDiseaseComplication.setModifyTime(now);
        sjjkDiseaseComplicationFacade.save(sjjkDiseaseComplication);

        SjjkDiseaseSymptom sjjkDiseaseSymptom = new SjjkDiseaseSymptom();
        sjjkDiseaseSymptom.setDisLibId(diseaseLib.getId());
        sjjkDiseaseSymptom.setDisId(diseaseLib.getDisId());
        sjjkDiseaseSymptom.setDisName(diseaseLib.getDisName());
        sjjkDiseaseSymptom.setSymptomUrl(diseaseLib.getSymptomUrl());
        sjjkDiseaseSymptom.setSymptomHtml(symptomHtml);
        sjjkDiseaseSymptom.setCreateTime(now);
        sjjkDiseaseSymptom.setModifyTime(now);
        sjjkDiseaseSymptomFacade.save(sjjkDiseaseSymptom);

        SjjkDiseaseExamine sjjkDiseaseExamine = new SjjkDiseaseExamine();
        sjjkDiseaseExamine.setDisLibId(diseaseLib.getId());
        sjjkDiseaseExamine.setDisId(diseaseLib.getDisId());
        sjjkDiseaseExamine.setDisName(diseaseLib.getDisName());
        sjjkDiseaseExamine.setExamineUrl(diseaseLib.getExamineUrl());
        sjjkDiseaseExamine.setExamineHtml(examineHtml);
        sjjkDiseaseExamine.setCreateTime(now);
        sjjkDiseaseExamine.setModifyTime(now);
        sjjkDiseaseExamineFacade.save(sjjkDiseaseExamine);

        SjjkDiseaseDiscern sjjkDiseaseDiscern = new SjjkDiseaseDiscern();
        sjjkDiseaseDiscern.setDisLibId(diseaseLib.getId());
        sjjkDiseaseDiscern.setDisId(diseaseLib.getDisId());
        sjjkDiseaseDiscern.setDisName(diseaseLib.getDisName());
        sjjkDiseaseDiscern.setDiscernUrl(diseaseLib.getDiscernUrl());
        sjjkDiseaseDiscern.setDiscernHtml(discernHtml);
        sjjkDiseaseDiscern.setCreateTime(now);
        sjjkDiseaseDiscern.setModifyTime(now);
        sjjkDiseaseDiscernFacade.save(sjjkDiseaseDiscern);

        SjjkDiseaseTreat sjjkDiseaseTreat = new SjjkDiseaseTreat();
        sjjkDiseaseTreat.setDisLibId(diseaseLib.getId());
        sjjkDiseaseTreat.setDisId(diseaseLib.getDisId());
        sjjkDiseaseTreat.setDisName(diseaseLib.getDisName());
        sjjkDiseaseTreat.setTreatUrl(diseaseLib.getTreatUrl());
        sjjkDiseaseTreat.setTreatHtml(treatHtml);
        sjjkDiseaseTreat.setCreateTime(now);
        sjjkDiseaseTreat.setModifyTime(now);
        sjjkDiseaseTreatFacade.save(sjjkDiseaseTreat);

        SjjkDiseaseNurse sjjkDiseaseNurse = new SjjkDiseaseNurse();
        sjjkDiseaseNurse.setDisLibId(diseaseLib.getId());
        sjjkDiseaseNurse.setDisId(diseaseLib.getDisId());
        sjjkDiseaseNurse.setDisName(diseaseLib.getDisName());
        sjjkDiseaseNurse.setNurseUrl(diseaseLib.getNurseUrl());
        sjjkDiseaseNurse.setNurseHtml(nurseHtml);
        sjjkDiseaseNurse.setCreateTime(now);
        sjjkDiseaseNurse.setModifyTime(now);
        sjjkDiseaseNurseFacade.save(sjjkDiseaseNurse);

        SjjkDiseaseHealth sjjkDiseaseHealth = new SjjkDiseaseHealth();
        sjjkDiseaseHealth.setDisLibId(diseaseLib.getId());
        sjjkDiseaseHealth.setDisId(diseaseLib.getDisId());
        sjjkDiseaseHealth.setDisName(diseaseLib.getDisName());
        sjjkDiseaseHealth.setHealthUrl(diseaseLib.getHealthUrl());
        sjjkDiseaseHealth.setHealthHtml(healthHtml);
        sjjkDiseaseHealth.setCreateTime(now);
        sjjkDiseaseHealth.setModifyTime(now);
        sjjkDiseaseHealthFacade.save(sjjkDiseaseHealth);

        SjjkDiseaseMedvice sjjkDiseaseMedvice = new SjjkDiseaseMedvice();
        sjjkDiseaseMedvice.setDisLibId(diseaseLib.getId());
        sjjkDiseaseMedvice.setDisId(diseaseLib.getDisId());
        sjjkDiseaseMedvice.setDisName(diseaseLib.getDisName());
        sjjkDiseaseMedvice.setMedviceUrl(diseaseLib.getMedviceUrl());
        sjjkDiseaseMedvice.setMedviceHtml(medviceHtml);
        sjjkDiseaseMedvice.setCreateTime(now);
        sjjkDiseaseMedvice.setModifyTime(now);
        sjjkDiseaseMedviceFacade.save(sjjkDiseaseMedvice);

        SjjkDiseaseDrug sjjkDiseaseDrug = new SjjkDiseaseDrug();
        sjjkDiseaseDrug.setDisLibId(diseaseLib.getId());
        sjjkDiseaseDrug.setDisId(diseaseLib.getDisId());
        sjjkDiseaseDrug.setDisName(diseaseLib.getDisName());
        sjjkDiseaseDrug.setDrugUrl(diseaseLib.getDrugUrl());
        sjjkDiseaseDrug.setDrugHtml(drugHtml);
        sjjkDiseaseDrug.setCreateTime(now);
        sjjkDiseaseDrug.setModifyTime(now);
        sjjkDiseaseDrugFacade.save(sjjkDiseaseDrug);

        diseaseLib.setIsHtmlsLoad(1);
        diseaseLib.setModifyTime(now);
        updateById(diseaseLib);
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
            Thread.sleep(1000);
        } catch (Exception e) {
        }
        String html = HttpTool.post(url);
        if (StringUtil.isNotBlank(html)) {
            Document doc = Jsoup.parse(html);
            Element modelNameElement = doc.getElementsByClass(modelName).first();
            if (modelNameElement != null) {
                ret = EnDecodeUtil.encode(modelNameElement.outerHtml());
            }
        }
        return ret;
    }

}