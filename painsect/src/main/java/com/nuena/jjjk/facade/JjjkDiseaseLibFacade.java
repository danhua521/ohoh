package com.nuena.jjjk.facade;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.nuena.jjjk.entity.JjjkBodypart;
import com.nuena.jjjk.entity.JjjkDeptDiseaseMapping;
import com.nuena.jjjk.entity.JjjkDeptInfo;
import com.nuena.jjjk.entity.JjjkDiseaseLib;
import com.nuena.jjjk.entity.JjjkPartDiseaseMapping;
import com.nuena.jjjk.service.impl.JjjkDeptDiseaseMappingServiceImpl;
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
import com.nuena.jjjk.service.impl.JjjkPartDiseaseMappingServiceImpl;
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
import java.util.Map;
import java.util.stream.Collectors;

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
    @Autowired
    @Qualifier("jjjkDeptDiseaseMappingServiceImpl")
    private JjjkDeptDiseaseMappingServiceImpl jjjkDeptDiseaseMappingService;
    @Autowired
    @Qualifier("jjjkPartDiseaseMappingServiceImpl")
    private JjjkPartDiseaseMappingServiceImpl jjjkPartDiseaseMappingService;

    @Transactional
    public void loadDis(JjjkDeptInfo deptInfo) {
        Map<String, JjjkDiseaseLib> loadedDisMap = getLoadedDisMap();
        List<JjjkDeptDiseaseMapping> saveDeptDiseaseMappingList = Lists.newArrayList();
        List<JjjkDiseaseLib> saveDiseaseLibList = Lists.newArrayList();
        Date now = DateUtil.now();
        getDiseases(deptInfo).forEach(i -> {
            JjjkDiseaseLib loadedDis = loadedDisMap.get(i.getDisId());
            if (loadedDis != null) {
                saveDeptDiseaseMappingList.add(getMapByDeptDis(deptInfo, loadedDis, now));
            } else {
                i.setCreateTime(now);
                saveDiseaseLibList.add(i);
            }
        });

        if (ListUtil.isNotEmpty(saveDiseaseLibList)) {
            jjjkDiseaseLibService.saveBatch(saveDiseaseLibList);
            saveDiseaseLibList.forEach(i -> {
                saveDeptDiseaseMappingList.add(getMapByDeptDis(deptInfo, i, now));
            });
        }
        if (ListUtil.isNotEmpty(saveDeptDiseaseMappingList)) {
            jjjkDeptDiseaseMappingService.saveBatch(saveDeptDiseaseMappingList);
        }
    }

    @Transactional
    public void loadDis(JjjkBodypart bodypart) {
        Map<String, JjjkDiseaseLib> loadedDisMap = getLoadedDisMap();
        List<JjjkPartDiseaseMapping> savePartDiseaseMappingList = Lists.newArrayList();
        List<JjjkDiseaseLib> saveDiseaseLibList = Lists.newArrayList();
        Date now = DateUtil.now();
        getDiseases(bodypart).forEach(i -> {
            JjjkDiseaseLib loadedDis = loadedDisMap.get(i.getDisId());
            if (loadedDis != null) {
                savePartDiseaseMappingList.add(getMapByPartDis(bodypart, loadedDis, now));
            } else {
                i.setCreateTime(now);
                saveDiseaseLibList.add(i);
            }
        });

        if (ListUtil.isNotEmpty(saveDiseaseLibList)) {
            jjjkDiseaseLibService.saveBatch(saveDiseaseLibList);
            saveDiseaseLibList.forEach(i -> {
                savePartDiseaseMappingList.add(getMapByPartDis(bodypart, i, now));
            });
        }
        if (ListUtil.isNotEmpty(savePartDiseaseMappingList)) {
            jjjkPartDiseaseMappingService.saveBatch(savePartDiseaseMappingList);
        }
    }

    /**
     * 获取已下载的疾病 map
     *
     * @return
     */
    private Map<String, JjjkDiseaseLib> getLoadedDisMap() {
        QueryWrapper<JjjkDiseaseLib> diseaseLibQe = new QueryWrapper<>();
        diseaseLibQe.select("id,dis_id,dis_name");
        return list(diseaseLibQe).stream().collect(Collectors.toMap(JjjkDiseaseLib::getDisId, i -> i));
    }

    /**
     * 根据科室，获取网站疾病列表(仅仅包含id、名称、各个模块url)
     *
     * @param deptInfo
     * @return
     */
    private List<JjjkDiseaseLib> getDiseases(JjjkDeptInfo deptInfo) {
        return pageConsult("https://jb.9939.com/jbzz/" + deptInfo.getDeptId() + "_t1/?page=");
    }

    /**
     * 根据部位，获取网站疾病列表(仅仅包含id、名称、各个模块url)
     *
     * @param bodypart
     * @return
     */
    private List<JjjkDiseaseLib> getDiseases(JjjkBodypart bodypart) {
        return pageConsult("https://jb.9939.com/jbzz/" + bodypart.getPartId() + "_t1/?page=");
    }

    /**
     * 页面轮询
     *
     * @param url
     * @return
     */
    private List<JjjkDiseaseLib> pageConsult(String url) {
        List<JjjkDiseaseLib> retList = Lists.newArrayList();
        int page = 0;
        String url_ = null;
        while (page != -1) {
            page++;
            url_ = url + page;
            List<JjjkDiseaseLib> willAddDises = getDiseases(url_);
            if (ListUtil.isNotEmpty(willAddDises)) {
                retList.addAll(willAddDises);
            } else {
                page = -1;
            }
        }
        return retList;
    }

    /**
     * 发送请求
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
                diseaseLib.setSynopsisUrl("https://jb.9939.com/" + disId + "/jianjie/");
                diseaseLib.setEtiologyUrl("https://jb.9939.com/" + disId + "/by/");
                diseaseLib.setPreventUrl("https://jb.9939.com/" + disId + "/yf/");
                diseaseLib.setComplicationUrl("https://jb.9939.com/" + disId + "/bfz/");
                diseaseLib.setSymptomUrl("https://jb.9939.com/" + disId + "/zz/");
                diseaseLib.setExamineUrl("https://jb.9939.com/" + disId + "/lcjc/");
                diseaseLib.setDiscernUrl("https://jb.9939.com/" + disId + "/jb/");
                diseaseLib.setTreatUrl("https://jb.9939.com/" + disId + "/zl/");
                diseaseLib.setNurseUrl("https://jb.9939.com/" + disId + "/yshl/");
                retList.add(diseaseLib);
            });
        } else {
            throw new RuntimeException("请求未获取到数据！");
        }
        return retList;
    }

    /**
     * 由科室和疾病获取映射信息
     *
     * @param deptInfo
     * @param diseaseLib
     * @param now
     * @return
     */
    private JjjkDeptDiseaseMapping getMapByDeptDis(JjjkDeptInfo deptInfo, JjjkDiseaseLib diseaseLib, Date now) {
        JjjkDeptDiseaseMapping jjjkDeptDiseaseMapping = new JjjkDeptDiseaseMapping();
        jjjkDeptDiseaseMapping.setDisId(diseaseLib.getDisId());
        jjjkDeptDiseaseMapping.setDisLibId(diseaseLib.getId());
        jjjkDeptDiseaseMapping.setDisName(diseaseLib.getDisName());
        jjjkDeptDiseaseMapping.setDeptId(deptInfo.getId());
        jjjkDeptDiseaseMapping.setDeptWzId(deptInfo.getDeptId());
        jjjkDeptDiseaseMapping.setDeptName(deptInfo.getDeptName());
        jjjkDeptDiseaseMapping.setCreateTime(now);
        return jjjkDeptDiseaseMapping;
    }

    /**
     * 由部位和疾病获取映射信息
     *
     * @param bodypart
     * @param diseaseLib
     * @param now
     * @return
     */
    private JjjkPartDiseaseMapping getMapByPartDis(JjjkBodypart bodypart, JjjkDiseaseLib diseaseLib, Date now) {
        JjjkPartDiseaseMapping jjjkPartDiseaseMapping = new JjjkPartDiseaseMapping();
        jjjkPartDiseaseMapping.setDisId(diseaseLib.getDisId());
        jjjkPartDiseaseMapping.setDisLibId(diseaseLib.getId());
        jjjkPartDiseaseMapping.setDisName(diseaseLib.getDisName());
        jjjkPartDiseaseMapping.setPartId(bodypart.getId());
        jjjkPartDiseaseMapping.setPartWzId(bodypart.getPartId());
        jjjkPartDiseaseMapping.setPartName(bodypart.getPartName());
        jjjkPartDiseaseMapping.setCreateTime(now);
        return jjjkPartDiseaseMapping;
    }

    /**
     * 下载其他无归属科室的疾病
     */
    @Transactional
    public void loadOtherDis() {
        String url = "https://jb.9939.com/jbzz_t1/?page=";
        List<JjjkDiseaseLib> disList = pageConsult(url);
        Map<String, JjjkDiseaseLib> loadedDisMap = getLoadedDisMap();
        List<JjjkDiseaseLib> saveDiseaseLibList = Lists.newArrayList();
        Date now = DateUtil.now();
        disList.forEach(i -> {
            if (loadedDisMap.get(i.getDisId()) == null) {
                i.setCreateTime(now);
                saveDiseaseLibList.add(i);
            }
        });
        if (ListUtil.isNotEmpty(saveDiseaseLibList)) {
            jjjkDiseaseLibService.saveBatch(saveDiseaseLibList);
        }
    }

}