package com.nuena.xywy.facade;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.nuena.util.DateUtil;
import com.nuena.util.EnDecodeUtil;
import com.nuena.util.FileUtil;
import com.nuena.util.HttpTool;
import com.nuena.util.JsoupUtil;
import com.nuena.util.StringUtil;
import com.nuena.xywy.entity.XywyDiseaseComplication;
import com.nuena.xywy.entity.XywyDiseaseDiscern;
import com.nuena.xywy.entity.XywyDiseaseEtiology;
import com.nuena.xywy.entity.XywyDiseaseExamine;
import com.nuena.xywy.entity.XywyDiseaseHealth;
import com.nuena.xywy.entity.XywyDiseaseLib;
import com.nuena.xywy.entity.XywyDiseaseNurse;
import com.nuena.xywy.entity.XywyDiseasePrevent;
import com.nuena.xywy.entity.XywyDiseaseSymptom;
import com.nuena.xywy.entity.XywyDiseaseSynopsis;
import com.nuena.xywy.entity.XywyDiseaseTreat;
import com.nuena.xywy.service.impl.XywyDiseaseComplicationServiceImpl;
import com.nuena.xywy.service.impl.XywyDiseaseDiscernServiceImpl;
import com.nuena.xywy.service.impl.XywyDiseaseEtiologyServiceImpl;
import com.nuena.xywy.service.impl.XywyDiseaseExamineServiceImpl;
import com.nuena.xywy.service.impl.XywyDiseaseHealthServiceImpl;
import com.nuena.xywy.service.impl.XywyDiseaseLibServiceImpl;
import com.nuena.xywy.service.impl.XywyDiseaseNurseServiceImpl;
import com.nuena.xywy.service.impl.XywyDiseasePreventServiceImpl;
import com.nuena.xywy.service.impl.XywyDiseaseSymptomServiceImpl;
import com.nuena.xywy.service.impl.XywyDiseaseSynopsisServiceImpl;
import com.nuena.xywy.service.impl.XywyDiseaseTreatServiceImpl;
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
public class XywyDiseaseLibFacade extends XywyDiseaseLibServiceImpl {

    @Autowired
    @Qualifier("xywyDiseaseLibServiceImpl")
    private XywyDiseaseLibServiceImpl xywyDiseaseLibService;
    @Autowired
    private XywyDiseaseSynopsisFacade xywyDiseaseSynopsisFacade;
    @Autowired
    @Qualifier("xywyDiseaseSynopsisServiceImpl")
    private XywyDiseaseSynopsisServiceImpl xywyDiseaseSynopsisService;
    @Autowired
    private XywyDiseaseEtiologyFacade xywyDiseaseEtiologyFacade;
    @Autowired
    @Qualifier("xywyDiseaseEtiologyServiceImpl")
    private XywyDiseaseEtiologyServiceImpl xywyDiseaseEtiologyService;
    @Autowired
    private XywyDiseasePreventFacade xywyDiseasePreventFacade;
    @Autowired
    @Qualifier("xywyDiseasePreventServiceImpl")
    private XywyDiseasePreventServiceImpl xywyDiseasePreventService;
    @Autowired
    private XywyDiseaseComplicationFacade xywyDiseaseComplicationFacade;
    @Autowired
    @Qualifier("xywyDiseaseComplicationServiceImpl")
    private XywyDiseaseComplicationServiceImpl xywyDiseaseComplicationService;
    @Autowired
    private XywyDiseaseSymptomFacade xywyDiseaseSymptomFacade;
    @Autowired
    @Qualifier("xywyDiseaseSymptomServiceImpl")
    private XywyDiseaseSymptomServiceImpl xywyDiseaseSymptomService;
    @Autowired
    private XywyDiseaseExamineFacade xywyDiseaseExamineFacade;
    @Autowired
    @Qualifier("xywyDiseaseExamineServiceImpl")
    private XywyDiseaseExamineServiceImpl xywyDiseaseExamineService;
    @Autowired
    private XywyDiseaseDiscernFacade xywyDiseaseDiscernFacade;
    @Autowired
    @Qualifier("xywyDiseaseDiscernServiceImpl")
    private XywyDiseaseDiscernServiceImpl xywyDiseaseDiscernService;
    @Autowired
    private XywyDiseaseTreatFacade xywyDiseaseTreatFacade;
    @Autowired
    @Qualifier("xywyDiseaseTreatServiceImpl")
    private XywyDiseaseTreatServiceImpl xywyDiseaseTreatService;
    @Autowired
    private XywyDiseaseNurseFacade xywyDiseaseNurseFacade;
    @Autowired
    @Qualifier("xywyDiseaseNurseServiceImpl")
    private XywyDiseaseNurseServiceImpl xywyDiseaseNurseService;
    @Autowired
    private XywyDiseaseHealthFacade xywyDiseaseHealthFacade;
    @Autowired
    @Qualifier("xywyDiseaseHealthServiceImpl")
    private XywyDiseaseHealthServiceImpl xywyDiseaseHealthService;

    @Transactional
    public void initDisIdData() {
        List<XywyDiseaseLib> diseaseLibList = Lists.newArrayList();
        String zimus = "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z";
        for (String zimu : zimus.split(",")) {
            diseaseLibList.addAll(getDiseases(zimu));
        }

        Date now = DateUtil.now();
        diseaseLibList.forEach(i -> {
            i.setCreateTime(now);
        });

        xywyDiseaseLibService.saveBatch(diseaseLibList);
    }

    /**
     * 根据拼音首字母，获取疾病列表(仅仅包含id和各个模块url)
     *
     * @param zimu
     * @return
     */
    private List<XywyDiseaseLib> getDiseases(String zimu) {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }
        List<XywyDiseaseLib> retList = Lists.newArrayList();
        String html = HttpTool.get("http://jib.xywy.com/html/" + zimu + ".html");
        if (StringUtil.isBlank(html)) {
            return retList;
        }

        Document doc = Jsoup.parse(html);
        Element jblistConEarElement = doc.getElementsByClass("jblist-con-ear").first();
        if (jblistConEarElement == null) {
            return retList;
        }

        Elements aElements = jblistConEarElement.getElementsByClass("ks-zm-list").select("a");
        aElements.forEach(i -> {
            XywyDiseaseLib diseaseLib = new XywyDiseaseLib();
            String href = i.attr("href");
            String disId = href.substring(8, href.indexOf(".htm"));
            diseaseLib.setDisId(disId);
            diseaseLib.setSynopsisUrl("http://jib.xywy.com/il_sii/gaishu/" + disId + ".htm");
            diseaseLib.setEtiologyUrl("http://jib.xywy.com/il_sii/cause/" + disId + ".htm");
            diseaseLib.setPreventUrl("http://jib.xywy.com/il_sii/prevent/" + disId + ".htm");
            diseaseLib.setComplicationUrl("http://jib.xywy.com/il_sii/neopathy/" + disId + ".htm");
            diseaseLib.setSymptomUrl("http://jib.xywy.com/il_sii/symptom/" + disId + ".htm");
            diseaseLib.setExamineUrl("http://jib.xywy.com/il_sii/inspect/" + disId + ".htm");
            diseaseLib.setDiscernUrl("http://jib.xywy.com/il_sii/diagnosis/" + disId + ".htm");
            diseaseLib.setTreatUrl("http://jib.xywy.com/il_sii/treat/" + disId + ".htm");
            diseaseLib.setNurseUrl("http://jib.xywy.com/il_sii/nursing/" + disId + ".htm");
            diseaseLib.setHealthUrl("http://jib.xywy.com/il_sii/food/" + disId + ".htm");
            retList.add(diseaseLib);
        });
        return retList;
    }

    /**
     * 获取未下载html的疾病列表
     *
     * @return
     */
    public List<XywyDiseaseLib> getNoLoadHtmlDiseases() {
        QueryWrapper<XywyDiseaseLib> diseaseLibQe = new QueryWrapper<>();
        diseaseLibQe.eq("is_htmls_load", 0);
        return list(diseaseLibQe);
    }

    /**
     * 下载疾病的各个模块html
     *
     * @param diseaseLib
     */
    @Transactional
    public void loadHtml(XywyDiseaseLib diseaseLib) {
        String synopsisHtml, etiologyHtml, preventHtml, complicationHtml, symptomHtml, examineHtml,
                discernHtml, treatHtml, nurseHtml, healthHtml;
        synopsisHtml = loadHtml(diseaseLib.getSynopsisUrl());
        if (StringUtil.isBlank(synopsisHtml)) {
            return;
        }
        etiologyHtml = loadHtml(diseaseLib.getEtiologyUrl());
        if (StringUtil.isBlank(etiologyHtml)) {
            return;
        }
        preventHtml = loadHtml(diseaseLib.getPreventUrl());
        if (StringUtil.isBlank(preventHtml)) {
            return;
        }
        complicationHtml = loadHtml(diseaseLib.getComplicationUrl());
        if (StringUtil.isBlank(complicationHtml)) {
            return;
        }
        symptomHtml = loadHtml(diseaseLib.getSymptomUrl());
        if (StringUtil.isBlank(symptomHtml)) {
            return;
        }
        examineHtml = loadHtml(diseaseLib.getExamineUrl());
        if (StringUtil.isBlank(examineHtml)) {
            return;
        }
        discernHtml = loadHtml(diseaseLib.getDiscernUrl());
        if (StringUtil.isBlank(discernHtml)) {
            return;
        }
        treatHtml = loadHtml(diseaseLib.getTreatUrl());
        if (StringUtil.isBlank(treatHtml)) {
            return;
        }
        nurseHtml = loadHtml(diseaseLib.getNurseUrl());
        if (StringUtil.isBlank(nurseHtml)) {
            return;
        }
        healthHtml = loadHtml(diseaseLib.getHealthUrl());
        if (StringUtil.isBlank(healthHtml)) {
            return;
        }

        Date now = DateUtil.now();
        XywyDiseaseSynopsis xywyDiseaseSynopsis = new XywyDiseaseSynopsis();
        xywyDiseaseSynopsis.setDisLibId(diseaseLib.getId());
        xywyDiseaseSynopsis.setDisId(diseaseLib.getDisId());
        xywyDiseaseSynopsis.setDisName(diseaseLib.getDisName());
        xywyDiseaseSynopsis.setSynopsisUrl(diseaseLib.getSynopsisUrl());
        xywyDiseaseSynopsis.setSynopsisHtml(synopsisHtml);
        xywyDiseaseSynopsis.setCreateTime(now);
        xywyDiseaseSynopsis.setModifyTime(now);
        xywyDiseaseSynopsisFacade.save(xywyDiseaseSynopsis);

        XywyDiseaseEtiology xywyDiseaseEtiology = new XywyDiseaseEtiology();
        xywyDiseaseEtiology.setDisLibId(diseaseLib.getId());
        xywyDiseaseEtiology.setDisId(diseaseLib.getDisId());
        xywyDiseaseEtiology.setDisName(diseaseLib.getDisName());
        xywyDiseaseEtiology.setEtiologyUrl(diseaseLib.getEtiologyUrl());
        xywyDiseaseEtiology.setEtiologyHtml(etiologyHtml);
        xywyDiseaseEtiology.setCreateTime(now);
        xywyDiseaseEtiology.setModifyTime(now);
        xywyDiseaseEtiologyFacade.save(xywyDiseaseEtiology);

        XywyDiseasePrevent xywyDiseasePrevent = new XywyDiseasePrevent();
        xywyDiseasePrevent.setDisLibId(diseaseLib.getId());
        xywyDiseasePrevent.setDisId(diseaseLib.getDisId());
        xywyDiseasePrevent.setDisName(diseaseLib.getDisName());
        xywyDiseasePrevent.setPreventUrl(diseaseLib.getPreventUrl());
        xywyDiseasePrevent.setPreventHtml(preventHtml);
        xywyDiseasePrevent.setCreateTime(now);
        xywyDiseasePrevent.setModifyTime(now);
        xywyDiseasePreventFacade.save(xywyDiseasePrevent);

        XywyDiseaseComplication xywyDiseaseComplication = new XywyDiseaseComplication();
        xywyDiseaseComplication.setDisLibId(diseaseLib.getId());
        xywyDiseaseComplication.setDisId(diseaseLib.getDisId());
        xywyDiseaseComplication.setDisName(diseaseLib.getDisName());
        xywyDiseaseComplication.setComplicationUrl(diseaseLib.getComplicationUrl());
        xywyDiseaseComplication.setComplicationHtml(complicationHtml);
        xywyDiseaseComplication.setCreateTime(now);
        xywyDiseaseComplication.setModifyTime(now);
        xywyDiseaseComplicationFacade.save(xywyDiseaseComplication);

        XywyDiseaseSymptom xywyDiseaseSymptom = new XywyDiseaseSymptom();
        xywyDiseaseSymptom.setDisLibId(diseaseLib.getId());
        xywyDiseaseSymptom.setDisId(diseaseLib.getDisId());
        xywyDiseaseSymptom.setDisName(diseaseLib.getDisName());
        xywyDiseaseSymptom.setSymptomUrl(diseaseLib.getSymptomUrl());
        xywyDiseaseSymptom.setSymptomHtml(symptomHtml);
        xywyDiseaseSymptom.setCreateTime(now);
        xywyDiseaseSymptom.setModifyTime(now);
        xywyDiseaseSymptomFacade.save(xywyDiseaseSymptom);

        XywyDiseaseExamine xywyDiseaseExamine = new XywyDiseaseExamine();
        xywyDiseaseExamine.setDisLibId(diseaseLib.getId());
        xywyDiseaseExamine.setDisId(diseaseLib.getDisId());
        xywyDiseaseExamine.setDisName(diseaseLib.getDisName());
        xywyDiseaseExamine.setExamineUrl(diseaseLib.getExamineUrl());
        xywyDiseaseExamine.setExamineHtml(examineHtml);
        xywyDiseaseExamine.setCreateTime(now);
        xywyDiseaseExamine.setModifyTime(now);
        xywyDiseaseExamineFacade.save(xywyDiseaseExamine);

        XywyDiseaseDiscern xywyDiseaseDiscern = new XywyDiseaseDiscern();
        xywyDiseaseDiscern.setDisLibId(diseaseLib.getId());
        xywyDiseaseDiscern.setDisId(diseaseLib.getDisId());
        xywyDiseaseDiscern.setDisName(diseaseLib.getDisName());
        xywyDiseaseDiscern.setDiscernUrl(diseaseLib.getDiscernUrl());
        xywyDiseaseDiscern.setDiscernHtml(discernHtml);
        xywyDiseaseDiscern.setCreateTime(now);
        xywyDiseaseDiscern.setModifyTime(now);
        xywyDiseaseDiscernFacade.save(xywyDiseaseDiscern);

        XywyDiseaseTreat xywyDiseaseTreat = new XywyDiseaseTreat();
        xywyDiseaseTreat.setDisLibId(diseaseLib.getId());
        xywyDiseaseTreat.setDisId(diseaseLib.getDisId());
        xywyDiseaseTreat.setDisName(diseaseLib.getDisName());
        xywyDiseaseTreat.setTreatUrl(diseaseLib.getTreatUrl());
        xywyDiseaseTreat.setTreatHtml(treatHtml);
        xywyDiseaseTreat.setCreateTime(now);
        xywyDiseaseTreat.setModifyTime(now);
        xywyDiseaseTreatFacade.save(xywyDiseaseTreat);

        XywyDiseaseNurse xywyDiseaseNurse = new XywyDiseaseNurse();
        xywyDiseaseNurse.setDisLibId(diseaseLib.getId());
        xywyDiseaseNurse.setDisId(diseaseLib.getDisId());
        xywyDiseaseNurse.setDisName(diseaseLib.getDisName());
        xywyDiseaseNurse.setNurseUrl(diseaseLib.getNurseUrl());
        xywyDiseaseNurse.setNurseHtml(nurseHtml);
        xywyDiseaseNurse.setCreateTime(now);
        xywyDiseaseNurse.setModifyTime(now);
        xywyDiseaseNurseFacade.save(xywyDiseaseNurse);

        XywyDiseaseHealth xywyDiseaseHealth = new XywyDiseaseHealth();
        xywyDiseaseHealth.setDisLibId(diseaseLib.getId());
        xywyDiseaseHealth.setDisId(diseaseLib.getDisId());
        xywyDiseaseHealth.setDisName(diseaseLib.getDisName());
        xywyDiseaseHealth.setHealthUrl(diseaseLib.getHealthUrl());
        xywyDiseaseHealth.setHealthHtml(healthHtml);
        xywyDiseaseHealth.setCreateTime(now);
        xywyDiseaseHealth.setModifyTime(now);
        xywyDiseaseHealthFacade.save(xywyDiseaseHealth);

        diseaseLib.setIsHtmlsLoad(1);
        diseaseLib.setModifyTime(now);
        updateById(diseaseLib);
    }

    /**
     * 下载html
     *
     * @param url
     * @return
     */
    private String loadHtml(String url) {
        String ret = null;
        try {
            Thread.sleep(500);
        } catch (Exception e) {
        }
        String html = HttpTool.post(url);
        if (StringUtil.isNotBlank(html)) {
            Document doc = Jsoup.parse(html);
            Element jibArticlElement = doc.getElementsByClass("jib-articl").first();
            if (jibArticlElement != null) {
                ret = EnDecodeUtil.encode(jibArticlElement.outerHtml());
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
    }

    /**
     * 解析--简介
     */
    private void analysisSynopsis() {
        List<XywyDiseaseSynopsis> xywyDiseaseSynopsisList = xywyDiseaseSynopsisFacade.list();
        List<XywyDiseaseLib> xywyDiseaseLibList = Lists.newArrayList();
        Date now = DateUtil.now();
        xywyDiseaseSynopsisList.forEach(xywyDiseaseSynopsis -> {
            Document jibArticlElement = Jsoup.parse(EnDecodeUtil.decode(xywyDiseaseSynopsis.getSynopsisHtml()));
            String anaTxt = JsoupUtil.clean(jibArticlElement.outerHtml());
            xywyDiseaseSynopsis.setSynopsisAnaytxt(anaTxt);
            xywyDiseaseSynopsis.setModifyTime(now);

            String title = jibArticlElement.getElementsByClass("jib-articl-tit").first().text();
            title = title.substring(0, title.length() - 5);
            XywyDiseaseLib xywyDiseaseLib = new XywyDiseaseLib();
            xywyDiseaseLib.setId(xywyDiseaseSynopsis.getDisLibId());
            xywyDiseaseLib.setDisName(title);
            xywyDiseaseLib.setIsHtmlsAnay(1);
            xywyDiseaseLib.setModifyTime(now);
            xywyDiseaseLibList.add(xywyDiseaseLib);
        });

        xywyDiseaseSynopsisService.updateBatchById(xywyDiseaseSynopsisList);
        xywyDiseaseLibService.updateBatchById(xywyDiseaseLibList);
    }

    /**
     * 解析--病因
     */
    private void analysisEtiology() {
        List<XywyDiseaseEtiology> xywyDiseaseEtiologyList = xywyDiseaseEtiologyFacade.list();
        Date now = DateUtil.now();
        xywyDiseaseEtiologyList.forEach(xywyDiseaseEtiology -> {
            Document jibArticlElement = Jsoup.parse(EnDecodeUtil.decode(xywyDiseaseEtiology.getEtiologyHtml()));
            String anaTxt = JsoupUtil.clean(jibArticlElement.outerHtml());
            xywyDiseaseEtiology.setEtiologyAnaytxt(anaTxt);
            xywyDiseaseEtiology.setModifyTime(now);
        });
        xywyDiseaseEtiologyService.updateBatchById(xywyDiseaseEtiologyList);
    }

    /**
     * 解析--预防
     */
    private void analysisPrevent() {
        List<XywyDiseasePrevent> xywyDiseasePreventList = xywyDiseasePreventFacade.list();
        Date now = DateUtil.now();
        xywyDiseasePreventList.forEach(xywyDiseasePrevent -> {
            Document jibArticlElement = Jsoup.parse(EnDecodeUtil.decode(xywyDiseasePrevent.getPreventHtml()));
            String anaTxt = JsoupUtil.clean(jibArticlElement.outerHtml());
            xywyDiseasePrevent.setPreventAnaytxt(anaTxt);
            xywyDiseasePrevent.setModifyTime(now);
        });
        xywyDiseasePreventService.updateBatchById(xywyDiseasePreventList);
    }

    /**
     * 解析--并发症
     */
    private void analysisComplication() {
        List<XywyDiseaseComplication> xywyDiseaseComplicationList = xywyDiseaseComplicationFacade.list();
        Date now = DateUtil.now();
        xywyDiseaseComplicationList.forEach(xywyDiseaseComplication -> {
            Document jibArticlElement = Jsoup.parse(EnDecodeUtil.decode(xywyDiseaseComplication.getComplicationHtml()));
            String anaTxt = JsoupUtil.clean(jibArticlElement.outerHtml());
            xywyDiseaseComplication.setComplicationAnaytxt(anaTxt);
            xywyDiseaseComplication.setModifyTime(now);
        });
        xywyDiseaseComplicationService.updateBatchById(xywyDiseaseComplicationList);
    }

    /**
     * 解析--症状
     */
    private void analysisSymptom() {
        List<XywyDiseaseSymptom> xywyDiseaseSymptomList = xywyDiseaseSymptomFacade.list();
        Date now = DateUtil.now();
        xywyDiseaseSymptomList.forEach(xywyDiseaseSymptom -> {
            Document jibArticlElement = Jsoup.parse(EnDecodeUtil.decode(xywyDiseaseSymptom.getSymptomHtml()));
            String anaTxt = JsoupUtil.clean(jibArticlElement.outerHtml());
            xywyDiseaseSymptom.setSymptomAnaytxt(anaTxt);
            xywyDiseaseSymptom.setModifyTime(now);
        });
        xywyDiseaseSymptomService.updateBatchById(xywyDiseaseSymptomList);
    }

    /**
     * 解析--检查
     */
    private void analysisExamine() {
        List<XywyDiseaseExamine> xywyDiseaseExamineList = xywyDiseaseExamineFacade.list();
        Date now = DateUtil.now();
        xywyDiseaseExamineList.forEach(xywyDiseaseExamine -> {
            Document jibArticlElement = Jsoup.parse(EnDecodeUtil.decode(xywyDiseaseExamine.getExamineHtml()));
            String anaTxt = JsoupUtil.clean(jibArticlElement.outerHtml());
            xywyDiseaseExamine.setExamineAnaytxt(anaTxt);
            xywyDiseaseExamine.setModifyTime(now);
        });
        xywyDiseaseExamineService.updateBatchById(xywyDiseaseExamineList);
    }

    /**
     * 解析--诊断鉴别
     */
    private void analysisDiscern() {
        List<XywyDiseaseDiscern> xywyDiseaseDiscernList = xywyDiseaseDiscernFacade.list();
        Date now = DateUtil.now();
        xywyDiseaseDiscernList.forEach(xywyDiseaseDiscern -> {
            Document jibArticlElement = Jsoup.parse(EnDecodeUtil.decode(xywyDiseaseDiscern.getDiscernHtml()));
            String anaTxt = JsoupUtil.clean(jibArticlElement.outerHtml());
            xywyDiseaseDiscern.setDiscernAnaytxt(anaTxt);
            xywyDiseaseDiscern.setModifyTime(now);
        });
        xywyDiseaseDiscernService.updateBatchById(xywyDiseaseDiscernList);
    }

    /**
     * 解析--治疗
     */
    private void analysisTreat() {
        List<XywyDiseaseTreat> xywyDiseaseTreatList = xywyDiseaseTreatFacade.list();
        Date now = DateUtil.now();
        xywyDiseaseTreatList.forEach(xywyDiseaseTreat -> {
            Document jibArticlElement = Jsoup.parse(EnDecodeUtil.decode(xywyDiseaseTreat.getTreatHtml()));
            String anaTxt = JsoupUtil.clean(jibArticlElement.outerHtml());
            xywyDiseaseTreat.setTreatAnaytxt(anaTxt);
            xywyDiseaseTreat.setModifyTime(now);
        });
        xywyDiseaseTreatService.updateBatchById(xywyDiseaseTreatList);
    }

    /**
     * 解析--护理
     */
    private void analysisNurse() {
        List<XywyDiseaseNurse> xywyDiseaseNurseList = xywyDiseaseNurseFacade.list();
        Date now = DateUtil.now();
        xywyDiseaseNurseList.forEach(xywyDiseaseNurse -> {
            Document jibArticlElement = Jsoup.parse(EnDecodeUtil.decode(xywyDiseaseNurse.getNurseHtml()));
            String anaTxt = JsoupUtil.clean(jibArticlElement.outerHtml());
            xywyDiseaseNurse.setNurseAnaytxt(anaTxt);
            xywyDiseaseNurse.setModifyTime(now);
        });
        xywyDiseaseNurseService.updateBatchById(xywyDiseaseNurseList);
    }

    /**
     * 解析--饮食健康
     */
    private void analysisHealth() {
        List<XywyDiseaseHealth> xywyDiseaseHealthList = xywyDiseaseHealthFacade.list();
        Date now = DateUtil.now();
        xywyDiseaseHealthList.forEach(xywyDiseaseHealth -> {
            Document jibArticlElement = Jsoup.parse(EnDecodeUtil.decode(xywyDiseaseHealth.getHealthHtml()));
            String anaTxt = JsoupUtil.clean(jibArticlElement.outerHtml());
            xywyDiseaseHealth.setHealthAnaytxt(anaTxt);
            xywyDiseaseHealth.setModifyTime(now);
        });
        xywyDiseaseHealthService.updateBatchById(xywyDiseaseHealthList);
    }

    /**
     * 文件生成
     */
    public void fileGener() {
        QueryWrapper<XywyDiseaseLib> xywyDiseaseLibQe = new QueryWrapper<>();
        xywyDiseaseLibQe.eq("is_htmls_anay", 1);
        xywyDiseaseLibQe.select("id", "dis_name");
        List<XywyDiseaseLib> xywyDiseaseLibList = list(xywyDiseaseLibQe);

        QueryWrapper<XywyDiseaseSynopsis> xywyDiseaseSynopsisQe = new QueryWrapper<>();
        xywyDiseaseSynopsisQe.select("dis_lib_id", "synopsis_anaytxt");
        Map<Long, String> xywyDiseaseSynopsisMap = xywyDiseaseSynopsisFacade.list(xywyDiseaseSynopsisQe)
                .stream().collect(Collectors.toMap(XywyDiseaseSynopsis::getDisLibId, XywyDiseaseSynopsis::getSynopsisAnaytxt));

        QueryWrapper<XywyDiseaseEtiology> xywyDiseaseEtiologyQe = new QueryWrapper<>();
        xywyDiseaseEtiologyQe.select("dis_lib_id", "etiology_anaytxt");
        Map<Long, String> xywyDiseaseEtiologyMap = xywyDiseaseEtiologyFacade.list(xywyDiseaseEtiologyQe)
                .stream().collect(Collectors.toMap(XywyDiseaseEtiology::getDisLibId, XywyDiseaseEtiology::getEtiologyAnaytxt));

        QueryWrapper<XywyDiseasePrevent> xywyDiseasePreventQe = new QueryWrapper<>();
        xywyDiseasePreventQe.select("dis_lib_id", "prevent_anaytxt");
        Map<Long, String> xywyDiseasePreventMap = xywyDiseasePreventFacade.list(xywyDiseasePreventQe)
                .stream().collect(Collectors.toMap(XywyDiseasePrevent::getDisLibId, XywyDiseasePrevent::getPreventAnaytxt));

        QueryWrapper<XywyDiseaseComplication> xywyDiseaseComplicationQe = new QueryWrapper<>();
        xywyDiseaseComplicationQe.select("dis_lib_id", "complication_anaytxt");
        Map<Long, String> xywyDiseaseComplicationMap = xywyDiseaseComplicationFacade.list(xywyDiseaseComplicationQe)
                .stream().collect(Collectors.toMap(XywyDiseaseComplication::getDisLibId, XywyDiseaseComplication::getComplicationAnaytxt));

        QueryWrapper<XywyDiseaseSymptom> xywyDiseaseSymptomQe = new QueryWrapper<>();
        xywyDiseaseSymptomQe.select("dis_lib_id", "symptom_anaytxt");
        Map<Long, String> xywyDiseaseSymptomMap = xywyDiseaseSymptomFacade.list(xywyDiseaseSymptomQe)
                .stream().collect(Collectors.toMap(XywyDiseaseSymptom::getDisLibId, XywyDiseaseSymptom::getSymptomAnaytxt));

        QueryWrapper<XywyDiseaseExamine> xywyDiseaseExamineQe = new QueryWrapper<>();
        xywyDiseaseExamineQe.select("dis_lib_id", "examine_anaytxt");
        Map<Long, String> xywyDiseaseExamineMap = xywyDiseaseExamineFacade.list(xywyDiseaseExamineQe)
                .stream().collect(Collectors.toMap(XywyDiseaseExamine::getDisLibId, XywyDiseaseExamine::getExamineAnaytxt));

        QueryWrapper<XywyDiseaseDiscern> xywyDiseaseDiscernQe = new QueryWrapper<>();
        xywyDiseaseDiscernQe.select("dis_lib_id", "discern_anaytxt");
        Map<Long, String> xywyDiseaseDiscernMap = xywyDiseaseDiscernFacade.list(xywyDiseaseDiscernQe)
                .stream().collect(Collectors.toMap(XywyDiseaseDiscern::getDisLibId, XywyDiseaseDiscern::getDiscernAnaytxt));

        QueryWrapper<XywyDiseaseTreat> xywyDiseaseTreatQe = new QueryWrapper<>();
        xywyDiseaseTreatQe.select("dis_lib_id", "treat_anaytxt");
        Map<Long, String> xywyDiseaseTreatMap = xywyDiseaseTreatFacade.list(xywyDiseaseTreatQe)
                .stream().collect(Collectors.toMap(XywyDiseaseTreat::getDisLibId, XywyDiseaseTreat::getTreatAnaytxt));

        QueryWrapper<XywyDiseaseNurse> xywyDiseaseNurseQe = new QueryWrapper<>();
        xywyDiseaseNurseQe.select("dis_lib_id", "nurse_anaytxt");
        Map<Long, String> xywyDiseaseNurseMap = xywyDiseaseNurseFacade.list(xywyDiseaseNurseQe)
                .stream().collect(Collectors.toMap(XywyDiseaseNurse::getDisLibId, XywyDiseaseNurse::getNurseAnaytxt));

        QueryWrapper<XywyDiseaseHealth> xywyDiseaseHealthQe = new QueryWrapper<>();
        xywyDiseaseHealthQe.select("dis_lib_id", "health_anaytxt");
        Map<Long, String> xywyDiseaseHealthMap = xywyDiseaseHealthFacade.list(xywyDiseaseHealthQe)
                .stream().collect(Collectors.toMap(XywyDiseaseHealth::getDisLibId, XywyDiseaseHealth::getHealthAnaytxt));

        xywyDiseaseLibList.forEach(xywyDiseaseLib -> {
            String path = "F:\\寻医问药\\" + xywyDiseaseLib.getDisName();
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            FileUtil.fileWrite(path, "简介", xywyDiseaseSynopsisMap.get(xywyDiseaseLib.getId()));
            FileUtil.fileWrite(path, "病因", xywyDiseaseEtiologyMap.get(xywyDiseaseLib.getId()));
            FileUtil.fileWrite(path, "预防", xywyDiseasePreventMap.get(xywyDiseaseLib.getId()));
            FileUtil.fileWrite(path, "并发症", xywyDiseaseComplicationMap.get(xywyDiseaseLib.getId()));
            FileUtil.fileWrite(path, "症状", xywyDiseaseSymptomMap.get(xywyDiseaseLib.getId()));
            FileUtil.fileWrite(path, "检查", xywyDiseaseExamineMap.get(xywyDiseaseLib.getId()));
            FileUtil.fileWrite(path, "诊断鉴别", xywyDiseaseDiscernMap.get(xywyDiseaseLib.getId()));
            FileUtil.fileWrite(path, "治疗", xywyDiseaseTreatMap.get(xywyDiseaseLib.getId()));
            FileUtil.fileWrite(path, "护理", xywyDiseaseNurseMap.get(xywyDiseaseLib.getId()));
            FileUtil.fileWrite(path, "饮食保健", xywyDiseaseHealthMap.get(xywyDiseaseLib.getId()));
        });
    }

}