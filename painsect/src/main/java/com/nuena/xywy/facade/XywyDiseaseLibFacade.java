package com.nuena.xywy.facade;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.nuena.util.DateUtil;
import com.nuena.util.EnDecodeUtil;
import com.nuena.util.HttpTool;
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
import com.nuena.xywy.service.impl.XywyDiseaseLibServiceImpl;
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
public class XywyDiseaseLibFacade extends XywyDiseaseLibServiceImpl {

    @Autowired
    @Qualifier("xywyDiseaseLibServiceImpl")
    private XywyDiseaseLibServiceImpl xywyDiseaseLibService;
    @Autowired
    private XywyDiseaseSynopsisFacade xywyDiseaseSynopsisFacade;
    @Autowired
    private XywyDiseaseEtiologyFacade xywyDiseaseEtiologyFacade;
    @Autowired
    private XywyDiseasePreventFacade xywyDiseasePreventFacade;
    @Autowired
    private XywyDiseaseComplicationFacade xywyDiseaseComplicationFacade;
    @Autowired
    private XywyDiseaseSymptomFacade xywyDiseaseSymptomFacade;
    @Autowired
    private XywyDiseaseExamineFacade xywyDiseaseExamineFacade;
    @Autowired
    private XywyDiseaseDiscernFacade xywyDiseaseDiscernFacade;
    @Autowired
    private XywyDiseaseTreatFacade xywyDiseaseTreatFacade;
    @Autowired
    private XywyDiseaseNurseFacade xywyDiseaseNurseFacade;
    @Autowired
    private XywyDiseaseHealthFacade xywyDiseaseHealthFacade;

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

}