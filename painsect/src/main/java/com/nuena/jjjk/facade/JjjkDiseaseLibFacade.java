package com.nuena.jjjk.facade;

import com.google.common.collect.Lists;
import com.nuena.jjjk.entity.JjjkDeptInfo;
import com.nuena.jjjk.entity.JjjkDiseaseLib;
import com.nuena.jjjk.service.impl.JjjkDiseaseComplicationServiceImpl;
import com.nuena.jjjk.service.impl.JjjkDiseaseDiscernServiceImpl;
import com.nuena.jjjk.service.impl.JjjkDiseaseEtiologyServiceImpl;
import com.nuena.jjjk.service.impl.JjjkDiseaseExamineServiceImpl;
import com.nuena.jjjk.service.impl.JjjkDiseaseLibServiceImpl;
import com.nuena.jjjk.service.impl.JjjkDiseaseNurseServiceImpl;
import com.nuena.jjjk.service.impl.JjjkDiseasePreventServiceImpl;
import com.nuena.jjjk.service.impl.JjjkDiseaseSymptomServiceImpl;
import com.nuena.jjjk.service.impl.JjjkDiseaseSynopsisServiceImpl;
import com.nuena.jjjk.service.impl.JjjkDiseaseTreatServiceImpl;
import com.nuena.util.DateUtil;
import com.nuena.util.HttpTool;
import com.nuena.util.ListUtil;
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
 * @time: 2020/2/28 11:05
 */
@Component
public class JjjkDiseaseLibFacade extends JjjkDiseaseLibServiceImpl {

    @Autowired
    private JjjkDeptInfoFacade jjjkDeptInfoFacade;
    @Autowired
    @Qualifier("jjjkDiseaseLibServiceImpl")
    private JjjkDiseaseLibServiceImpl jjjkDiseaseLibService;
    @Autowired
    private JjjkDiseaseSynopsisFacade jjjkDiseaseSynopsisFacade;
    @Autowired
    @Qualifier("jjjkDiseaseSynopsisServiceImpl")
    private JjjkDiseaseSynopsisServiceImpl jjjkDiseaseSynopsisService;
    @Autowired
    private JjjkDiseaseEtiologyFacade jjjkDiseaseEtiologyFacade;
    @Autowired
    @Qualifier("jjjkDiseaseEtiologyServiceImpl")
    private JjjkDiseaseEtiologyServiceImpl jjjkDiseaseEtiologyService;
    @Autowired
    private JjjkDiseasePreventFacade jjjkDiseasePreventFacade;
    @Autowired
    @Qualifier("jjjkDiseasePreventServiceImpl")
    private JjjkDiseasePreventServiceImpl jjjkDiseasePreventService;
    @Autowired
    private JjjkDiseaseComplicationFacade jjjkDiseaseComplicationFacade;
    @Autowired
    @Qualifier("jjjkDiseaseComplicationServiceImpl")
    private JjjkDiseaseComplicationServiceImpl jjjkDiseaseComplicationService;
    @Autowired
    private JjjkDiseaseSymptomFacade jjjkDiseaseSymptomFacade;
    @Autowired
    @Qualifier("jjjkDiseaseSymptomServiceImpl")
    private JjjkDiseaseSymptomServiceImpl jjjkDiseaseSymptomService;
    @Autowired
    private JjjkDiseaseExamineFacade jjjkDiseaseExamineFacade;
    @Autowired
    @Qualifier("jjjkDiseaseExamineServiceImpl")
    private JjjkDiseaseExamineServiceImpl jjjkDiseaseExamineService;
    @Autowired
    private JjjkDiseaseDiscernFacade jjjkDiseaseDiscernFacade;
    @Autowired
    @Qualifier("jjjkDiseaseDiscernServiceImpl")
    private JjjkDiseaseDiscernServiceImpl jjjkDiseaseDiscernService;
    @Autowired
    private JjjkDiseaseTreatFacade jjjkDiseaseTreatFacade;
    @Autowired
    @Qualifier("jjjkDiseaseTreatServiceImpl")
    private JjjkDiseaseTreatServiceImpl jjjkDiseaseTreatService;
    @Autowired
    private JjjkDiseaseNurseFacade jjjkDiseaseNurseFacade;
    @Autowired
    @Qualifier("jjjkDiseaseNurseServiceImpl")
    private JjjkDiseaseNurseServiceImpl jjjkDiseaseNurseService;

    @Transactional
    public void loadDis(JjjkDeptInfo deptInfo) {
        List<JjjkDiseaseLib> diseaseLibList = getDiseases(deptInfo);
        if (ListUtil.isNotEmpty(diseaseLibList)) {
            Date now = DateUtil.now();
            diseaseLibList.forEach(i -> {
                i.setDeptId(deptInfo.getId());
                i.setDeptWzId(deptInfo.getDeptId());
                i.setCreateTime(now);
            });
            jjjkDiseaseLibService.saveBatch(diseaseLibList);
        }
    }


    /**
     * 根据科室，获取疾病列表(仅仅包含id、名称、各个模块url)
     *
     * @param deptInfo
     * @return
     */
    private List<JjjkDiseaseLib> getDiseases(JjjkDeptInfo deptInfo) {
        List<JjjkDiseaseLib> retList = Lists.newArrayList();
        int page = 0;
        String url = null;
        while (page != -1) {
            page++;
            url = "https://jb.9939.com/jbzz/" + deptInfo.getDeptId() + "_t1/?page=" + page;
            List<JjjkDiseaseLib> willAddDises = getDiseases(url);
            if (ListUtil.isNotEmpty(willAddDises)) {
                retList.addAll(willAddDises);
            } else {
                page = -1;
            }
        }
        return retList;
    }

    /**
     * 发送请求，获取疾病列表(仅仅包含id、名称、各个模块url)
     *
     * @param url
     * @return
     */
    private List<JjjkDiseaseLib> getDiseases(String url) {
        try {
            Thread.sleep(500);
        } catch (Exception e) {
        }
        List<JjjkDiseaseLib> retList = Lists.newArrayList();
        String html = HttpTool.get(url);
        if (StringUtil.isNotBlank(html)) {
            Document doc = Jsoup.parse(html);
            Element sboxElement = doc.getElementsByClass("sbox").first();
            if (sboxElement == null) {
                throw new RuntimeException("请求未获取到数据！");
            }

            Elements aElements = sboxElement.getElementsByClass("doc_anwer").select("h3").select("a");
            aElements.forEach(i -> {
                JjjkDiseaseLib diseaseLib = new JjjkDiseaseLib();
                String href = i.attr("href");
                String disId = href.substring(20, href.lastIndexOf("/"));
                diseaseLib.setDisId(disId);
                diseaseLib.setDisName(i.text());
                diseaseLib.setSynopsisUrl("https://jb.9939.com/" + disId + "jianjie/");
                diseaseLib.setEtiologyUrl("https://jb.9939.com/" + disId + "by/");
                diseaseLib.setPreventUrl("https://jb.9939.com/" + disId + "yf/");
                diseaseLib.setComplicationUrl("https://jb.9939.com/" + disId + "bfz/");
                diseaseLib.setSymptomUrl("https://jb.9939.com/" + disId + "zz/");
                diseaseLib.setExamineUrl("https://jb.9939.com/" + disId + "lcjc/");
                diseaseLib.setDiscernUrl("https://jb.9939.com/" + disId + "jb/");
                diseaseLib.setTreatUrl("https://jb.9939.com/" + disId + "zl/");
                diseaseLib.setNurseUrl("https://jb.9939.com/" + disId + "yshl/");
                retList.add(diseaseLib);
            });
        } else {
            throw new RuntimeException("请求未获取到数据！");
        }
        return retList;
    }

}