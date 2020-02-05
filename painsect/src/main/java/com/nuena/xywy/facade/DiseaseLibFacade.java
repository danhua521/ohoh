package com.nuena.xywy.facade;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.nuena.util.DateUtil;
import com.nuena.util.HttpTool;
import com.nuena.util.StringUtil;
import com.nuena.xywy.entity.DiseaseLib;
import com.nuena.xywy.service.impl.DiseaseLibServiceImpl;
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
import java.util.Random;

/**
 * @Description:
 * @author: rengb
 * @time: 2020/2/5 22:56
 */
@Component
public class DiseaseLibFacade extends DiseaseLibServiceImpl {

    @Autowired
    @Qualifier("diseaseLibServiceImpl")
    private DiseaseLibServiceImpl diseaseLibService;

    @Transactional
    public void initDisIdData() {
        List<DiseaseLib> diseaseLibList = Lists.newArrayList();
        String zimus = "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z";
        for (String zimu : zimus.split(",")) {
            diseaseLibList.addAll(getDiseases(zimu));
        }

        Date now = DateUtil.now();
        diseaseLibList.forEach(i -> {
            i.setCreateTime(now);
        });

        diseaseLibService.saveBatch(diseaseLibList);
    }

    /**
     * 根据拼音首字母，获取疾病列表(仅仅包含id和各个模块url)
     *
     * @param zimu
     * @return
     */
    private List<DiseaseLib> getDiseases(String zimu) {
        try {
            Random rd = new Random();
            Thread.sleep(1000 * rd.nextInt(11) + 2000);
        } catch (Exception e) {
        }
        List<DiseaseLib> retList = Lists.newArrayList();
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
            DiseaseLib diseaseLib = new DiseaseLib();
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
    public List<DiseaseLib> getNoLoadHtmlDiseases() {
        QueryWrapper<DiseaseLib> diseaseLibQe = new QueryWrapper<>();
        diseaseLibQe.eq("is_htmls_load", 0);
        return list(diseaseLibQe);
    }

    /**
     * 下载疾病的各个模块html
     *
     * @param diseaseLib
     */
    @Transactional
    public void loadHtml(DiseaseLib diseaseLib) {
        String synopsisHtml = loadHtml(diseaseLib.getSynopsisUrl());
        String etiologyHtml = loadHtml(diseaseLib.getEtiologyUrl());
        String preventHtml = loadHtml(diseaseLib.getPreventUrl());
        String complicationHtml = loadHtml(diseaseLib.getComplicationUrl());
        String symptomHtml = loadHtml(diseaseLib.getSymptomUrl());
        String examineHtml = loadHtml(diseaseLib.getExamineUrl());
        String discernHtml = loadHtml(diseaseLib.getDiscernUrl());
        String treatHtml = loadHtml(diseaseLib.getTreatUrl());
        String nurseHtml = loadHtml(diseaseLib.getNurseUrl());
        String healthHtml = loadHtml(diseaseLib.getHealthUrl());

        if (StringUtil.isNotBlank(synopsisHtml)
                && StringUtil.isNotBlank(etiologyHtml)
                && StringUtil.isNotBlank(preventHtml)
                && StringUtil.isNotBlank(complicationHtml)
                && StringUtil.isNotBlank(symptomHtml)
                && StringUtil.isNotBlank(examineHtml)
                && StringUtil.isNotBlank(discernHtml)
                && StringUtil.isNotBlank(treatHtml)
                && StringUtil.isNotBlank(nurseHtml)
                && StringUtil.isNotBlank(healthHtml)) {
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
            diseaseLib.setIsHtmlsLoad(1);
            updateById(diseaseLib);
        }
    }

    /**
     * 下载html
     *
     * @param url
     * @return
     */
    private String loadHtml(String url) {
        try {
            Random rd = new Random();
            Thread.sleep(1000 * rd.nextInt(11) + 2000);
        } catch (Exception e) {
        }
        return HttpTool.post(url);
    }

}