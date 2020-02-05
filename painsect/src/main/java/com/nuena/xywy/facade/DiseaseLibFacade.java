package com.nuena.xywy.facade;

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
    public void initData() {
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

}