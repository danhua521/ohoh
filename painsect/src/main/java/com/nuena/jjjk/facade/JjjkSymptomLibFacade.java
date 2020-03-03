package com.nuena.jjjk.facade;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.nuena.jjjk.entity.JjjkDeptInfo;
import com.nuena.jjjk.entity.JjjkDeptSymptomMapping;
import com.nuena.jjjk.entity.JjjkSymptomLib;
import com.nuena.jjjk.service.impl.JjjkDeptSymptomMappingServiceImpl;
import com.nuena.jjjk.service.impl.JjjkSymptomEtiologyServiceImpl;
import com.nuena.jjjk.service.impl.JjjkSymptomExamineServiceImpl;
import com.nuena.jjjk.service.impl.JjjkSymptomLibServiceImpl;
import com.nuena.jjjk.service.impl.JjjkSymptomPreventServiceImpl;
import com.nuena.jjjk.service.impl.JjjkSymptomSynopsisServiceImpl;
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
public class JjjkSymptomLibFacade extends JjjkSymptomLibServiceImpl {

    @Autowired
    private JjjkDeptInfoFacade jjjkDeptInfoFacade;
    @Autowired
    @Qualifier("jjjkSymptomLibServiceImpl")
    private JjjkSymptomLibServiceImpl jjjkSymptomLibService;
    @Autowired
    private JjjkSymptomSynopsisFacade jjjkSymptomSynopsisFacade;
    @Autowired
    @Qualifier("jjjkSymptomSynopsisServiceImpl")
    private JjjkSymptomSynopsisServiceImpl jjjkSymptomSynopsisService;
    @Autowired
    private JjjkSymptomEtiologyFacade jjjkSymptomEtiologyFacade;
    @Autowired
    @Qualifier("jjjkSymptomEtiologyServiceImpl")
    private JjjkSymptomEtiologyServiceImpl jjjkSymptomEtiologyService;
    @Autowired
    private JjjkSymptomPreventFacade jjjkSymptomPreventFacade;
    @Autowired
    @Qualifier("jjjkSymptomPreventServiceImpl")
    private JjjkSymptomPreventServiceImpl jjjkSymptomPreventService;
    @Autowired
    private JjjkSymptomExamineFacade jjjkSymptomExamineFacade;
    @Autowired
    @Qualifier("jjjkSymptomExamineServiceImpl")
    private JjjkSymptomExamineServiceImpl jjjkSymptomExamineService;
    @Autowired
    @Qualifier("jjjkDeptSymptomMappingServiceImpl")
    private JjjkDeptSymptomMappingServiceImpl jjjkDeptSymptomMappingService;

    @Transactional
    public void loadSym(JjjkDeptInfo deptInfo) {
        Map<String, JjjkSymptomLib> loadedSymMap = getLoadedSymMap();
        List<JjjkDeptSymptomMapping> saveDeptSymptomMappingList = Lists.newArrayList();
        List<JjjkSymptomLib> saveSymptomLibList = Lists.newArrayList();
        Date now = DateUtil.now();
        getSymptoms(deptInfo).forEach(i -> {
            JjjkSymptomLib loadedSym = loadedSymMap.get(i.getSymId());
            if (loadedSym != null) {
                saveDeptSymptomMappingList.add(getMapByDeptSym(deptInfo, loadedSym, now));
            } else {
                i.setCreateTime(now);
                saveSymptomLibList.add(i);
            }
        });

        if (ListUtil.isNotEmpty(saveSymptomLibList)) {
            jjjkSymptomLibService.saveBatch(saveSymptomLibList);
            saveSymptomLibList.forEach(i -> {
                saveDeptSymptomMappingList.add(getMapByDeptSym(deptInfo, i, now));
            });
        }
        if (ListUtil.isNotEmpty(saveDeptSymptomMappingList)) {
            jjjkDeptSymptomMappingService.saveBatch(saveDeptSymptomMappingList);
        }
    }

    /**
     * 获取已下载的症状 map
     *
     * @return
     */
    private Map<String, JjjkSymptomLib> getLoadedSymMap() {
        QueryWrapper<JjjkSymptomLib> symptomLibQe = new QueryWrapper<>();
        symptomLibQe.select("id,sym_id,sym_name");
        return list(symptomLibQe).stream().collect(Collectors.toMap(JjjkSymptomLib::getSymId, i -> i));
    }

    /**
     * 根据科室，获取网站症状列表(仅仅包含id、名称、各个模块url)
     *
     * @param deptInfo
     * @return
     */
    private List<JjjkSymptomLib> getSymptoms(JjjkDeptInfo deptInfo) {
        return pageConsult("https://jb.9939.com/jbzz/" + deptInfo.getDeptId() + "_t2/?page=");
    }

    /**
     * 页面轮询
     *
     * @param url
     * @return
     */
    private List<JjjkSymptomLib> pageConsult(String url) {
        List<JjjkSymptomLib> retList = Lists.newArrayList();
        int page = 0;
        String url_ = null;
        while (page != -1) {
            page++;
            url_ = url + page;
            List<JjjkSymptomLib> willAddSymes = getSymptoms(url_);
            if (ListUtil.isNotEmpty(willAddSymes)) {
                retList.addAll(willAddSymes);
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
    private List<JjjkSymptomLib> getSymptoms(String url) {
        try {
            Thread.sleep(500);
        } catch (Exception e) {
        }
        List<JjjkSymptomLib> retList = Lists.newArrayList();
        String html = HttpTool.get(url);
        if (StringUtil.isNotBlank(html)) {
            Document doc = Jsoup.parse(html);
            Element sboxElement = doc.getElementsByClass("sbox").first();
            if (sboxElement == null) {
                throw new RuntimeException("请求未获取到数据！");
            }

            Elements aElements = sboxElement.getElementsByClass("doc_anwer").select("h3").select("a");
            aElements.forEach(i -> {
                JjjkSymptomLib symptomLib = new JjjkSymptomLib();
                String href = i.attr("href");
                String symId = href.substring(32, href.lastIndexOf("/"));
                symptomLib.setSymId(symId);
                symptomLib.setSymName(i.text());
                symptomLib.setSynopsisUrl("https://jb.9939.com/zhengzhuang/" + symId + "/jianjie/");
                symptomLib.setEtiologyUrl("https://jb.9939.com/zhengzhuang/" + symId + "/zzqy/");
                symptomLib.setPreventUrl("https://jb.9939.com/zhengzhuang/" + symId + "/yufang/");
                symptomLib.setExamineUrl("https://jb.9939.com/zhengzhuang/" + symId + "/jiancha/");
                symptomLib.setHealthUrl("https://jb.9939.com/zhengzhuang/" + symId + "/shiliao/");
                retList.add(symptomLib);
            });
        } else {
            throw new RuntimeException("请求未获取到数据！");
        }
        return retList;
    }

    /**
     * 由科室和症状获取映射信息
     *
     * @param deptInfo
     * @param symptomLib
     * @param now
     * @return
     */
    private JjjkDeptSymptomMapping getMapByDeptSym(JjjkDeptInfo deptInfo, JjjkSymptomLib symptomLib, Date now) {
        JjjkDeptSymptomMapping jjjkDeptSymptomMapping = new JjjkDeptSymptomMapping();
        jjjkDeptSymptomMapping.setSymId(symptomLib.getSymId());
        jjjkDeptSymptomMapping.setSymLibId(symptomLib.getId());
        jjjkDeptSymptomMapping.setSymName(symptomLib.getSymName());
        jjjkDeptSymptomMapping.setDeptId(deptInfo.getId());
        jjjkDeptSymptomMapping.setDeptWzId(deptInfo.getDeptId());
        jjjkDeptSymptomMapping.setDeptName(deptInfo.getDeptName());
        jjjkDeptSymptomMapping.setCreateTime(now);
        return jjjkDeptSymptomMapping;
    }

    /**
     * 下载其他无归属科室的症状
     */
    @Transactional
    public void loadOtherSym() {
        String url = "https://jb.9939.com/jbzz_t2/?page=";
        List<JjjkSymptomLib> symList = pageConsult(url);
        Map<String, JjjkSymptomLib> loadedSymMap = getLoadedSymMap();
        List<JjjkSymptomLib> saveSymptomLibList = Lists.newArrayList();
        Date now = DateUtil.now();
        symList.forEach(i -> {
            if (loadedSymMap.get(i.getSymId()) == null) {
                i.setCreateTime(now);
                saveSymptomLibList.add(i);
            }
        });
        if (ListUtil.isNotEmpty(saveSymptomLibList)) {
            jjjkSymptomLibService.saveBatch(saveSymptomLibList);
        }
    }

}