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
import com.nuena.sjjk.service.impl.SjjkDiseaseComplicationServiceImpl;
import com.nuena.sjjk.service.impl.SjjkDiseaseDiscernServiceImpl;
import com.nuena.sjjk.service.impl.SjjkDiseaseDrugServiceImpl;
import com.nuena.sjjk.service.impl.SjjkDiseaseEtiologyServiceImpl;
import com.nuena.sjjk.service.impl.SjjkDiseaseExamineServiceImpl;
import com.nuena.sjjk.service.impl.SjjkDiseaseHealthServiceImpl;
import com.nuena.sjjk.service.impl.SjjkDiseaseLibServiceImpl;
import com.nuena.sjjk.service.impl.SjjkDiseaseMedviceServiceImpl;
import com.nuena.sjjk.service.impl.SjjkDiseaseNurseServiceImpl;
import com.nuena.sjjk.service.impl.SjjkDiseasePreventServiceImpl;
import com.nuena.sjjk.service.impl.SjjkDiseaseSymptomServiceImpl;
import com.nuena.sjjk.service.impl.SjjkDiseaseSynopsisServiceImpl;
import com.nuena.sjjk.service.impl.SjjkDiseaseTreatServiceImpl;
import com.nuena.util.DateUtil;
import com.nuena.util.EnDecodeUtil;
import com.nuena.util.FileUtil;
import com.nuena.util.HttpTool;
import com.nuena.util.JsoupUtil;
import com.nuena.util.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @Qualifier("sjjkDiseaseSynopsisServiceImpl")
    private SjjkDiseaseSynopsisServiceImpl sjjkDiseaseSynopsisService;
    @Autowired
    private SjjkDiseaseEtiologyFacade sjjkDiseaseEtiologyFacade;
    @Autowired
    @Qualifier("sjjkDiseaseEtiologyServiceImpl")
    private SjjkDiseaseEtiologyServiceImpl sjjkDiseaseEtiologyService;
    @Autowired
    private SjjkDiseasePreventFacade sjjkDiseasePreventFacade;
    @Autowired
    @Qualifier("sjjkDiseasePreventServiceImpl")
    private SjjkDiseasePreventServiceImpl sjjkDiseasePreventService;
    @Autowired
    private SjjkDiseaseComplicationFacade sjjkDiseaseComplicationFacade;
    @Autowired
    @Qualifier("sjjkDiseaseComplicationServiceImpl")
    private SjjkDiseaseComplicationServiceImpl sjjkDiseaseComplicationService;
    @Autowired
    private SjjkDiseaseSymptomFacade sjjkDiseaseSymptomFacade;
    @Autowired
    @Qualifier("sjjkDiseaseSymptomServiceImpl")
    private SjjkDiseaseSymptomServiceImpl sjjkDiseaseSymptomService;
    @Autowired
    private SjjkDiseaseExamineFacade sjjkDiseaseExamineFacade;
    @Autowired
    @Qualifier("sjjkDiseaseExamineServiceImpl")
    private SjjkDiseaseExamineServiceImpl sjjkDiseaseExamineService;
    @Autowired
    private SjjkDiseaseDiscernFacade sjjkDiseaseDiscernFacade;
    @Autowired
    @Qualifier("sjjkDiseaseDiscernServiceImpl")
    private SjjkDiseaseDiscernServiceImpl sjjkDiseaseDiscernService;
    @Autowired
    private SjjkDiseaseTreatFacade sjjkDiseaseTreatFacade;
    @Autowired
    @Qualifier("sjjkDiseaseTreatServiceImpl")
    private SjjkDiseaseTreatServiceImpl sjjkDiseaseTreatService;
    @Autowired
    private SjjkDiseaseNurseFacade sjjkDiseaseNurseFacade;
    @Autowired
    @Qualifier("sjjkDiseaseNurseServiceImpl")
    private SjjkDiseaseNurseServiceImpl sjjkDiseaseNurseService;
    @Autowired
    private SjjkDiseaseHealthFacade sjjkDiseaseHealthFacade;
    @Autowired
    @Qualifier("sjjkDiseaseHealthServiceImpl")
    private SjjkDiseaseHealthServiceImpl sjjkDiseaseHealthService;
    @Autowired
    private SjjkDiseaseMedviceFacade sjjkDiseaseMedviceFacade;
    @Autowired
    @Qualifier("sjjkDiseaseMedviceServiceImpl")
    private SjjkDiseaseMedviceServiceImpl sjjkDiseaseMedviceService;
    @Autowired
    private SjjkDiseaseDrugFacade sjjkDiseaseDrugFacade;
    @Autowired
    @Qualifier("sjjkDiseaseDrugServiceImpl")
    private SjjkDiseaseDrugServiceImpl sjjkDiseaseDrugService;

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

    @Transactional
    public void analysis() {
        analysisSynopsis();
        analysisEtiology();
        analysisPrevent();
        analysisComplication();
        analysisSymptom();
        analysisExamine();
        analysisDiscern();
        analysisTreat();
        analysisNurse();
        analysisHealth();
        analysisMedvice();
        analysisDrug();
    }

    /**
     * 解析--简介
     */
    private void analysisSynopsis() {
        List<SjjkDiseaseSynopsis> sjjkDiseaseSynopsisList = sjjkDiseaseSynopsisFacade.list();
        List<SjjkDiseaseLib> sjjkDiseaseLibList = Lists.newArrayList();
        Date now = DateUtil.now();
        sjjkDiseaseSynopsisList.forEach(sjjkDiseaseSynopsis -> {
            Document listLeftElement = Jsoup.parse(EnDecodeUtil.decode(sjjkDiseaseSynopsis.getSynopsisHtml()));
            String anaTxt = JsoupUtil.clean(listLeftElement.outerHtml());
            if (anaTxt.indexOf("医学视频") != -1) {
                anaTxt = anaTxt.substring(0, anaTxt.indexOf("医学视频"));
            }
            if (anaTxt.indexOf("相关文章") != -1) {
                anaTxt = anaTxt.substring(0, anaTxt.indexOf("相关文章"));
            }
            sjjkDiseaseSynopsis.setSynopsisAnaytxt(anaTxt);
            sjjkDiseaseSynopsis.setModifyTime(now);

            String title = listLeftElement.getElementsByClass("disease_box").first().getElementsByClass("disease_title").first().text();
            title = title.substring(0, title.length() - 2);
            SjjkDiseaseLib sjjkDiseaseLib = new SjjkDiseaseLib();
            sjjkDiseaseLib.setId(sjjkDiseaseSynopsis.getDisLibId());
            sjjkDiseaseLib.setDisName(title);
            sjjkDiseaseLib.setIsHtmlsAnay(1);
            sjjkDiseaseLib.setModifyTime(now);
            sjjkDiseaseLibList.add(sjjkDiseaseLib);
        });

        sjjkDiseaseSynopsisService.updateBatchById(sjjkDiseaseSynopsisList);
        diseaseLibService.updateBatchById(sjjkDiseaseLibList);
    }

    /**
     * 解析--病因
     */
    private void analysisEtiology() {
        List<SjjkDiseaseEtiology> sjjkDiseaseEtiologyList = sjjkDiseaseEtiologyFacade.list();
        Date now = DateUtil.now();
        sjjkDiseaseEtiologyList.forEach(sjjkDiseaseEtiology -> {
            Document listLeftElement = Jsoup.parse(EnDecodeUtil.decode(sjjkDiseaseEtiology.getEtiologyHtml()));
            String anaTxt = JsoupUtil.clean(listLeftElement.outerHtml());
            if (anaTxt.indexOf("相关阅读") != -1) {
                anaTxt = anaTxt.substring(0, anaTxt.indexOf("相关阅读"));
            }
            sjjkDiseaseEtiology.setEtiologyAnaytxt(anaTxt);
            sjjkDiseaseEtiology.setModifyTime(now);
        });
        sjjkDiseaseEtiologyService.updateBatchById(sjjkDiseaseEtiologyList);
    }

    /**
     * 解析--预防
     */
    private void analysisPrevent() {
        List<SjjkDiseasePrevent> sjjkDiseasePreventList = sjjkDiseasePreventFacade.list();
        Date now = DateUtil.now();
        sjjkDiseasePreventList.forEach(sjjkDiseasePrevent -> {
            Document listLeftElement = Jsoup.parse(EnDecodeUtil.decode(sjjkDiseasePrevent.getPreventHtml()));
            String anaTxt = JsoupUtil.clean(listLeftElement.outerHtml());
            if (anaTxt.indexOf("相关阅读") != -1) {
                anaTxt = anaTxt.substring(0, anaTxt.indexOf("相关阅读"));
            }
            sjjkDiseasePrevent.setPreventAnaytxt(anaTxt);
            sjjkDiseasePrevent.setModifyTime(now);
        });
        sjjkDiseasePreventService.updateBatchById(sjjkDiseasePreventList);
    }

    /**
     * 解析--并发症
     */
    private void analysisComplication() {
        List<SjjkDiseaseComplication> sjjkDiseaseComplicationList = sjjkDiseaseComplicationFacade.list();
        Date now = DateUtil.now();
        sjjkDiseaseComplicationList.forEach(sjjkDiseaseComplication -> {
            Document listLeftElement = Jsoup.parse(EnDecodeUtil.decode(sjjkDiseaseComplication.getComplicationHtml()));
            String anaTxt = JsoupUtil.clean(listLeftElement.outerHtml());
            if (anaTxt.indexOf("相关阅读") != -1) {
                anaTxt = anaTxt.substring(0, anaTxt.indexOf("相关阅读"));
            }
            sjjkDiseaseComplication.setComplicationAnaytxt(anaTxt);
            sjjkDiseaseComplication.setModifyTime(now);
        });
        sjjkDiseaseComplicationService.updateBatchById(sjjkDiseaseComplicationList);
    }

    /**
     * 解析--症状
     */
    private void analysisSymptom() {
        List<SjjkDiseaseSymptom> sjjkDiseaseSymptomList = sjjkDiseaseSymptomFacade.list();
        Date now = DateUtil.now();
        sjjkDiseaseSymptomList.forEach(sjjkDiseaseSymptom -> {
            Document listLeftElement = Jsoup.parse(EnDecodeUtil.decode(sjjkDiseaseSymptom.getSymptomHtml()));
            String anaTxt = JsoupUtil.clean(listLeftElement.outerHtml());
            if (anaTxt.indexOf("相关阅读") != -1) {
                anaTxt = anaTxt.substring(0, anaTxt.indexOf("相关阅读"));
            }
            sjjkDiseaseSymptom.setSymptomAnaytxt(anaTxt);
            sjjkDiseaseSymptom.setModifyTime(now);
        });
        sjjkDiseaseSymptomService.updateBatchById(sjjkDiseaseSymptomList);
    }

    /**
     * 解析--检查
     */
    private void analysisExamine() {
        List<SjjkDiseaseExamine> sjjkDiseaseExamineList = sjjkDiseaseExamineFacade.list();
        Date now = DateUtil.now();
        sjjkDiseaseExamineList.forEach(sjjkDiseaseExamine -> {
            Document listLeftElement = Jsoup.parse(EnDecodeUtil.decode(sjjkDiseaseExamine.getExamineHtml()));
            String anaTxt = JsoupUtil.clean(listLeftElement.outerHtml());
            if (anaTxt.indexOf("相关阅读") != -1) {
                anaTxt = anaTxt.substring(0, anaTxt.indexOf("相关阅读"));
            }
            sjjkDiseaseExamine.setExamineAnaytxt(anaTxt);
            sjjkDiseaseExamine.setModifyTime(now);
        });
        sjjkDiseaseExamineService.updateBatchById(sjjkDiseaseExamineList);
    }

    /**
     * 解析--诊断鉴别
     */
    private void analysisDiscern() {
        List<SjjkDiseaseDiscern> sjjkDiseaseDiscernList = sjjkDiseaseDiscernFacade.list();
        Date now = DateUtil.now();
        sjjkDiseaseDiscernList.forEach(sjjkDiseaseDiscern -> {
            Document listLeftElement = Jsoup.parse(EnDecodeUtil.decode(sjjkDiseaseDiscern.getDiscernHtml()));
            String anaTxt = JsoupUtil.clean(listLeftElement.outerHtml());
            if (anaTxt.indexOf("相关阅读") != -1) {
                anaTxt = anaTxt.substring(0, anaTxt.indexOf("相关阅读"));
            }
            sjjkDiseaseDiscern.setDiscernAnaytxt(anaTxt);
            sjjkDiseaseDiscern.setModifyTime(now);
        });
        sjjkDiseaseDiscernService.updateBatchById(sjjkDiseaseDiscernList);
    }

    /**
     * 解析--治疗
     */
    private void analysisTreat() {
        List<SjjkDiseaseTreat> sjjkDiseaseTreatList = sjjkDiseaseTreatFacade.list();
        Date now = DateUtil.now();
        sjjkDiseaseTreatList.forEach(sjjkDiseaseTreat -> {
            Document listLeftElement = Jsoup.parse(EnDecodeUtil.decode(sjjkDiseaseTreat.getTreatHtml()));
            String anaTxt = JsoupUtil.clean(listLeftElement.outerHtml());
            if (anaTxt.indexOf("相关阅读") != -1) {
                anaTxt = anaTxt.substring(0, anaTxt.indexOf("相关阅读"));
            }
            sjjkDiseaseTreat.setTreatAnaytxt(anaTxt);
            sjjkDiseaseTreat.setModifyTime(now);
        });
        sjjkDiseaseTreatService.updateBatchById(sjjkDiseaseTreatList);
    }

    /**
     * 解析--护理
     */
    private void analysisNurse() {
        List<SjjkDiseaseNurse> sjjkDiseaseNurseList = sjjkDiseaseNurseFacade.list();
        Date now = DateUtil.now();
        sjjkDiseaseNurseList.forEach(sjjkDiseaseNurse -> {
            Document listLeftElement = Jsoup.parse(EnDecodeUtil.decode(sjjkDiseaseNurse.getNurseHtml()));
            String anaTxt = JsoupUtil.clean(listLeftElement.outerHtml());
            if (anaTxt.indexOf("相关阅读") != -1) {
                anaTxt = anaTxt.substring(0, anaTxt.indexOf("相关阅读"));
            }
            sjjkDiseaseNurse.setNurseAnaytxt(anaTxt);
            sjjkDiseaseNurse.setModifyTime(now);
        });
        sjjkDiseaseNurseService.updateBatchById(sjjkDiseaseNurseList);
    }

    /**
     * 解析--饮食健康
     */
    private void analysisHealth() {
        List<SjjkDiseaseHealth> sjjkDiseaseHealthList = sjjkDiseaseHealthFacade.list();
        Date now = DateUtil.now();
        sjjkDiseaseHealthList.forEach(sjjkDiseaseHealth -> {
            Document listLeftElement = Jsoup.parse(EnDecodeUtil.decode(sjjkDiseaseHealth.getHealthHtml()));
            String anaTxt = JsoupUtil.clean(listLeftElement.outerHtml());
            if (anaTxt.indexOf("相关阅读") != -1) {
                anaTxt = anaTxt.substring(0, anaTxt.indexOf("相关阅读"));
            }
            sjjkDiseaseHealth.setHealthAnaytxt(anaTxt);
            sjjkDiseaseHealth.setModifyTime(now);
        });
        sjjkDiseaseHealthService.updateBatchById(sjjkDiseaseHealthList);
    }

    /**
     * 解析--就诊
     */
    private void analysisMedvice() {
        List<SjjkDiseaseMedvice> sjjkDiseaseMedviceList = sjjkDiseaseMedviceFacade.list();
        Date now = DateUtil.now();
        sjjkDiseaseMedviceList.forEach(sjjkDiseaseMedvice -> {
            Document contentElement = Jsoup.parse(EnDecodeUtil.decode(sjjkDiseaseMedvice.getMedviceHtml()));
            Element contentTextElement = contentElement.getElementById("contentText");
            String anaTxt = JsoupUtil.clean(contentTextElement.outerHtml());
            sjjkDiseaseMedvice.setMedviceAnaytxt(anaTxt);
            sjjkDiseaseMedvice.setModifyTime(now);
        });
        sjjkDiseaseMedviceService.updateBatchById(sjjkDiseaseMedviceList);
    }

    /**
     * 解析--用药
     */
    private void analysisDrug() {
        List<SjjkDiseaseDrug> sjjkDiseaseDrugList = sjjkDiseaseDrugFacade.list();
        Date now = DateUtil.now();
        sjjkDiseaseDrugList.forEach(sjjkDiseaseDrug -> {
            Document fl730Element = Jsoup.parse(EnDecodeUtil.decode(sjjkDiseaseDrug.getDrugHtml()));
            Elements drugListElement = fl730Element.getElementsByClass("drug-list").select("h4");
            String anaTxt = JsoupUtil.clean(drugListElement.outerHtml());
            sjjkDiseaseDrug.setDrugAnaytxt(anaTxt);
            sjjkDiseaseDrug.setModifyTime(now);
        });
        sjjkDiseaseDrugService.updateBatchById(sjjkDiseaseDrugList);
    }

    /**
     * 文件生成
     */
    public void fileGener() {
        QueryWrapper<SjjkDiseaseLib> sjjkDiseaseLibQe = new QueryWrapper<>();
        sjjkDiseaseLibQe.eq("is_htmls_anay", 1);
        sjjkDiseaseLibQe.select("id", "dis_name");
        List<SjjkDiseaseLib> sjjkDiseaseLibList = list(sjjkDiseaseLibQe);

        QueryWrapper<SjjkDiseaseSynopsis> sjjkDiseaseSynopsisQe = new QueryWrapper<>();
        sjjkDiseaseSynopsisQe.select("dis_lib_id", "synopsis_anaytxt");
        Map<Long, String> sjjkDiseaseSynopsisMap = sjjkDiseaseSynopsisFacade.list(sjjkDiseaseSynopsisQe)
                .stream().collect(Collectors.toMap(SjjkDiseaseSynopsis::getDisLibId, SjjkDiseaseSynopsis::getSynopsisAnaytxt));

        QueryWrapper<SjjkDiseaseEtiology> sjjkDiseaseEtiologyQe = new QueryWrapper<>();
        sjjkDiseaseEtiologyQe.select("dis_lib_id", "etiology_anaytxt");
        Map<Long, String> sjjkDiseaseEtiologyMap = sjjkDiseaseEtiologyFacade.list(sjjkDiseaseEtiologyQe)
                .stream().collect(Collectors.toMap(SjjkDiseaseEtiology::getDisLibId, SjjkDiseaseEtiology::getEtiologyAnaytxt));

        QueryWrapper<SjjkDiseasePrevent> sjjkDiseasePreventQe = new QueryWrapper<>();
        sjjkDiseasePreventQe.select("dis_lib_id", "prevent_anaytxt");
        Map<Long, String> sjjkDiseasePreventMap = sjjkDiseasePreventFacade.list(sjjkDiseasePreventQe)
                .stream().collect(Collectors.toMap(SjjkDiseasePrevent::getDisLibId, SjjkDiseasePrevent::getPreventAnaytxt));

        QueryWrapper<SjjkDiseaseComplication> sjjkDiseaseComplicationQe = new QueryWrapper<>();
        sjjkDiseaseComplicationQe.select("dis_lib_id", "complication_anaytxt");
        Map<Long, String> sjjkDiseaseComplicationMap = sjjkDiseaseComplicationFacade.list(sjjkDiseaseComplicationQe)
                .stream().collect(Collectors.toMap(SjjkDiseaseComplication::getDisLibId, SjjkDiseaseComplication::getComplicationAnaytxt));

        QueryWrapper<SjjkDiseaseSymptom> sjjkDiseaseSymptomQe = new QueryWrapper<>();
        sjjkDiseaseSymptomQe.select("dis_lib_id", "symptom_anaytxt");
        Map<Long, String> sjjkDiseaseSymptomMap = sjjkDiseaseSymptomFacade.list(sjjkDiseaseSymptomQe)
                .stream().collect(Collectors.toMap(SjjkDiseaseSymptom::getDisLibId, SjjkDiseaseSymptom::getSymptomAnaytxt));

        QueryWrapper<SjjkDiseaseExamine> sjjkDiseaseExamineQe = new QueryWrapper<>();
        sjjkDiseaseExamineQe.select("dis_lib_id", "examine_anaytxt");
        Map<Long, String> sjjkDiseaseExamineMap = sjjkDiseaseExamineFacade.list(sjjkDiseaseExamineQe)
                .stream().collect(Collectors.toMap(SjjkDiseaseExamine::getDisLibId, SjjkDiseaseExamine::getExamineAnaytxt));

        QueryWrapper<SjjkDiseaseDiscern> sjjkDiseaseDiscernQe = new QueryWrapper<>();
        sjjkDiseaseDiscernQe.select("dis_lib_id", "discern_anaytxt");
        Map<Long, String> sjjkDiseaseDiscernMap = sjjkDiseaseDiscernFacade.list(sjjkDiseaseDiscernQe)
                .stream().collect(Collectors.toMap(SjjkDiseaseDiscern::getDisLibId, SjjkDiseaseDiscern::getDiscernAnaytxt));

        QueryWrapper<SjjkDiseaseTreat> sjjkDiseaseTreatQe = new QueryWrapper<>();
        sjjkDiseaseTreatQe.select("dis_lib_id", "treat_anaytxt");
        Map<Long, String> sjjkDiseaseTreatMap = sjjkDiseaseTreatFacade.list(sjjkDiseaseTreatQe)
                .stream().collect(Collectors.toMap(SjjkDiseaseTreat::getDisLibId, SjjkDiseaseTreat::getTreatAnaytxt));

        QueryWrapper<SjjkDiseaseNurse> sjjkDiseaseNurseQe = new QueryWrapper<>();
        sjjkDiseaseNurseQe.select("dis_lib_id", "nurse_anaytxt");
        Map<Long, String> sjjkDiseaseNurseMap = sjjkDiseaseNurseFacade.list(sjjkDiseaseNurseQe)
                .stream().collect(Collectors.toMap(SjjkDiseaseNurse::getDisLibId, SjjkDiseaseNurse::getNurseAnaytxt));

        QueryWrapper<SjjkDiseaseHealth> sjjkDiseaseHealthQe = new QueryWrapper<>();
        sjjkDiseaseHealthQe.select("dis_lib_id", "health_anaytxt");
        Map<Long, String> sjjkDiseaseHealthMap = sjjkDiseaseHealthFacade.list(sjjkDiseaseHealthQe)
                .stream().collect(Collectors.toMap(SjjkDiseaseHealth::getDisLibId, SjjkDiseaseHealth::getHealthAnaytxt));

        QueryWrapper<SjjkDiseaseMedvice> sjjkDiseaseMedviceQe = new QueryWrapper<>();
        sjjkDiseaseMedviceQe.select("dis_lib_id", "medvice_anaytxt");
        Map<Long, String> sjjkDiseaseMedviceMap = sjjkDiseaseMedviceFacade.list(sjjkDiseaseMedviceQe)
                .stream().collect(Collectors.toMap(SjjkDiseaseMedvice::getDisLibId, SjjkDiseaseMedvice::getMedviceAnaytxt));

        QueryWrapper<SjjkDiseaseDrug> sjjkDiseaseDrugQe = new QueryWrapper<>();
        sjjkDiseaseDrugQe.select("dis_lib_id", "drug_anaytxt");
        Map<Long, String> sjjkDiseaseDrugMap = sjjkDiseaseDrugFacade.list(sjjkDiseaseDrugQe)
                .stream().collect(Collectors.toMap(SjjkDiseaseDrug::getDisLibId, SjjkDiseaseDrug::getDrugAnaytxt));

        sjjkDiseaseLibList.forEach(sjjkDiseaseLib -> {
            String path = "F:\\39健康\\" + sjjkDiseaseLib.getDisName();
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            FileUtil.fileWrite(path, "简介", sjjkDiseaseSynopsisMap.get(sjjkDiseaseLib.getId()));
            FileUtil.fileWrite(path, "病因", sjjkDiseaseEtiologyMap.get(sjjkDiseaseLib.getId()));
            FileUtil.fileWrite(path, "预防", sjjkDiseasePreventMap.get(sjjkDiseaseLib.getId()));
            FileUtil.fileWrite(path, "并发症", sjjkDiseaseComplicationMap.get(sjjkDiseaseLib.getId()));
            FileUtil.fileWrite(path, "症状", sjjkDiseaseSymptomMap.get(sjjkDiseaseLib.getId()));
            FileUtil.fileWrite(path, "检查", sjjkDiseaseExamineMap.get(sjjkDiseaseLib.getId()));
            FileUtil.fileWrite(path, "诊断鉴别", sjjkDiseaseDiscernMap.get(sjjkDiseaseLib.getId()));
            FileUtil.fileWrite(path, "治疗", sjjkDiseaseTreatMap.get(sjjkDiseaseLib.getId()));
            FileUtil.fileWrite(path, "护理", sjjkDiseaseNurseMap.get(sjjkDiseaseLib.getId()));
            FileUtil.fileWrite(path, "饮食保健", sjjkDiseaseHealthMap.get(sjjkDiseaseLib.getId()));
            FileUtil.fileWrite(path, "就诊", sjjkDiseaseMedviceMap.get(sjjkDiseaseLib.getId()));
            FileUtil.fileWrite(path, "用药", sjjkDiseaseDrugMap.get(sjjkDiseaseLib.getId()));
        });
    }

}