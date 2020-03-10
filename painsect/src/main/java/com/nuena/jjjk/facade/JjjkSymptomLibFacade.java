package com.nuena.jjjk.facade;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.nuena.jjjk.entity.JjjkBodypart;
import com.nuena.jjjk.entity.JjjkDeptInfo;
import com.nuena.jjjk.entity.JjjkDeptSymptomMapping;
import com.nuena.jjjk.entity.JjjkPartSymptomMapping;
import com.nuena.jjjk.entity.JjjkSymptomEtiology;
import com.nuena.jjjk.entity.JjjkSymptomExamine;
import com.nuena.jjjk.entity.JjjkSymptomHealth;
import com.nuena.jjjk.entity.JjjkSymptomLib;
import com.nuena.jjjk.entity.JjjkSymptomPrevent;
import com.nuena.jjjk.entity.JjjkSymptomSynopsis;
import com.nuena.jjjk.service.impl.JjjkDeptSymptomMappingServiceImpl;
import com.nuena.jjjk.service.impl.JjjkPartSymptomMappingServiceImpl;
import com.nuena.jjjk.service.impl.JjjkSymptomEtiologyServiceImpl;
import com.nuena.jjjk.service.impl.JjjkSymptomExamineServiceImpl;
import com.nuena.jjjk.service.impl.JjjkSymptomHealthServiceImpl;
import com.nuena.jjjk.service.impl.JjjkSymptomLibServiceImpl;
import com.nuena.jjjk.service.impl.JjjkSymptomPreventServiceImpl;
import com.nuena.jjjk.service.impl.JjjkSymptomSynopsisServiceImpl;
import com.nuena.util.DateUtil;
import com.nuena.util.EnDecodeUtil;
import com.nuena.util.FileUtil;
import com.nuena.util.HttpTool;
import com.nuena.util.JsoupUtil;
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

import java.io.File;
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
    @Autowired
    @Qualifier("jjjkPartSymptomMappingServiceImpl")
    private JjjkPartSymptomMappingServiceImpl jjjkPartSymptomMappingService;
    @Autowired
    private JjjkPartSymptomMappingFacade jjjkPartSymptomMappingFacade;
    @Autowired
    @Qualifier("jjjkSymptomHealthServiceImpl")
    private JjjkSymptomHealthServiceImpl jjjkSymptomHealthService;
    @Autowired
    private JjjkSymptomHealthFacade jjjkSymptomHealthFacade;

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

    @Transactional
    public void loadSym(JjjkBodypart bodypart) {
        Map<String, JjjkSymptomLib> loadedSymMap = getLoadedSymMap();
        List<JjjkPartSymptomMapping> savePartSymptomMappingList = Lists.newArrayList();
        List<JjjkSymptomLib> saveSymptomLibList = Lists.newArrayList();
        Date now = DateUtil.now();
        getSymptoms(bodypart).forEach(i -> {
            JjjkSymptomLib loadedSym = loadedSymMap.get(i.getSymId());
            if (loadedSym != null) {
                savePartSymptomMappingList.add(getMapByPartSym(bodypart, loadedSym, now));
            } else {
                i.setCreateTime(now);
                saveSymptomLibList.add(i);
            }
        });

        if (ListUtil.isNotEmpty(saveSymptomLibList)) {
            jjjkSymptomLibService.saveBatch(saveSymptomLibList);
            saveSymptomLibList.forEach(i -> {
                savePartSymptomMappingList.add(getMapByPartSym(bodypart, i, now));
            });
        }
        if (ListUtil.isNotEmpty(savePartSymptomMappingList)) {
            jjjkPartSymptomMappingService.saveBatch(savePartSymptomMappingList);
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
     * 根据部位，获取网站症状列表(仅仅包含id、名称、各个模块url)
     *
     * @param bodypart
     * @return
     */
    private List<JjjkSymptomLib> getSymptoms(JjjkBodypart bodypart) {
        return pageConsult("https://jb.9939.com/jbzz/" + bodypart.getPartId() + "_t2/?page=");
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
     * 由部位和症状获取映射信息
     *
     * @param bodypart
     * @param symptomLib
     * @param now
     * @return
     */
    private JjjkPartSymptomMapping getMapByPartSym(JjjkBodypart bodypart, JjjkSymptomLib symptomLib, Date now) {
        JjjkPartSymptomMapping jjjkPartSymptomMapping = new JjjkPartSymptomMapping();
        jjjkPartSymptomMapping.setSymId(symptomLib.getSymId());
        jjjkPartSymptomMapping.setSymLibId(symptomLib.getId());
        jjjkPartSymptomMapping.setSymName(symptomLib.getSymName());
        jjjkPartSymptomMapping.setPartId(bodypart.getId());
        jjjkPartSymptomMapping.setPartWzId(bodypart.getPartId());
        jjjkPartSymptomMapping.setPartName(bodypart.getPartName());
        jjjkPartSymptomMapping.setCreateTime(now);
        return jjjkPartSymptomMapping;
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

    /**
     * 获取未下载html的症状列表
     *
     * @return
     */
    public List<JjjkSymptomLib> getNoLoadHtmlSymptoms() {
        QueryWrapper<JjjkSymptomLib> symptomLibQe = new QueryWrapper<>();
        symptomLibQe.eq("is_htmls_load", 0);
        return list(symptomLibQe);
    }

    /**
     * 下载各模块HTML到本地
     *
     * @param path
     * @param symptomLibList
     */
    public void loadHtmlToLocal(String path, List<JjjkSymptomLib> symptomLibList) {
        File file = new File(path);
        List<String> loadedSymIdList = Lists.newArrayList(file.listFiles()).stream().map(i -> i.getName()).collect(Collectors.toList());
        List<JjjkSymptomLib> willLoadSymList = null;
        Map<String, String> htmlMap = null;
        while (ListUtil.isNotEmpty(willLoadSymList =
                symptomLibList.stream().filter(i -> !loadedSymIdList.contains(i.getSymId())).collect(Collectors.toList()))) {
            for (JjjkSymptomLib willLoadSym : willLoadSymList) {
                System.out.println("症状：" + willLoadSym.getSymName());
                try {
                    htmlMap = getHtmls(willLoadSym);
                    if (htmlMap != null && htmlMap.size() == 5) {
                        System.out.println("下载各模块html成功---");
                        createLocalFiles(path, willLoadSym.getSymId(), htmlMap);
                        System.out.println("生成文件over---");
                        loadedSymIdList.add(willLoadSym.getSymId());
                    } else {
                        System.out.println("下载各模块html失败---");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 生成本地html文件
     *
     * @param path
     * @param symId
     * @param content
     */
    private void createLocalFiles(String path, String symId, Map<String, String> content) {
        String path_ = path + symId;
        content.keySet().forEach(key -> {
            FileUtil.fileWrite(path_, key, content.get(key));
        });
    }

    /**
     * 组装包含html 的 map
     *
     * @param symptomLib
     * @return
     */
    private Map<String, String> getHtmls(JjjkSymptomLib symptomLib) {
        Map<String, String> content = Maps.newHashMap();
        String html = loadHtml(symptomLib.getSynopsisUrl());
        if (StringUtil.isBlank(html)) {
            return null;
        } else {
            content.put("synopsis", html);
        }

        html = loadHtml(symptomLib.getEtiologyUrl());
        if (StringUtil.isBlank(html)) {
            return null;
        } else {
            content.put("etiology", html);
        }

        html = loadHtml(symptomLib.getPreventUrl());
        if (StringUtil.isBlank(html)) {
            return null;
        } else {
            content.put("prevent", html);
        }

        html = loadHtml(symptomLib.getExamineUrl());
        if (StringUtil.isBlank(html)) {
            return null;
        } else {
            content.put("examine", html);
        }

        html = loadHtml(symptomLib.getHealthUrl());
        if (StringUtil.isBlank(html)) {
            return null;
        } else {
            content.put("health", html);
        }
        return content;
    }

    /**
     * 拉取html
     *
     * @param url
     * @return
     */
    private String loadHtml(String url) {
        try {
            Thread.sleep(200);
        } catch (Exception e) {
        }
        return HttpTool.get(url);
    }

    /**
     * 将下载的各模块html插入到数据库
     *
     * @param path
     * @param symptomLibList
     */
    @Transactional
    public void loadedHtmlIntoDB(String path, List<JjjkSymptomLib> symptomLibList) {
        Date now = DateUtil.now();
        List<JjjkSymptomSynopsis> jjjkSymptomSynopsisList = Lists.newArrayList();
        List<JjjkSymptomEtiology> jjjkSymptomEtiologyList = Lists.newArrayList();
        List<JjjkSymptomPrevent> jjjkSymptomPreventList = Lists.newArrayList();
        List<JjjkSymptomExamine> jjjkSymptomExamineList = Lists.newArrayList();
        List<JjjkSymptomHealth> jjjkSymptomHealthList = Lists.newArrayList();
        symptomLibList.forEach(symptomLib -> {
            System.out.println("在进行：" + symptomLib.getSymName());
            JjjkSymptomSynopsis jjjkSymptomSynopsis = new JjjkSymptomSynopsis();
            jjjkSymptomSynopsis.setCreateTime(now);
            jjjkSymptomSynopsis.setModifyTime(now);
            jjjkSymptomSynopsis.setSymId(symptomLib.getSymId());
            jjjkSymptomSynopsis.setSymLibId(symptomLib.getId());
            jjjkSymptomSynopsis.setSymName(symptomLib.getSymName());
            jjjkSymptomSynopsis.setSynopsisUrl(symptomLib.getSynopsisUrl());
            jjjkSymptomSynopsis.setSynopsisHtml(EnDecodeUtil.encode(FileUtil.fileRead(path + symptomLib.getSymId() + "\\synopsis")));
            jjjkSymptomSynopsisList.add(jjjkSymptomSynopsis);

            JjjkSymptomEtiology jjjkSymptomEtiology = new JjjkSymptomEtiology();
            jjjkSymptomEtiology.setCreateTime(now);
            jjjkSymptomEtiology.setModifyTime(now);
            jjjkSymptomEtiology.setSymId(symptomLib.getSymId());
            jjjkSymptomEtiology.setSymLibId(symptomLib.getId());
            jjjkSymptomEtiology.setSymName(symptomLib.getSymName());
            jjjkSymptomEtiology.setEtiologyUrl(symptomLib.getEtiologyUrl());
            jjjkSymptomEtiology.setEtiologyHtml(EnDecodeUtil.encode(FileUtil.fileRead(path + symptomLib.getSymId() + "\\etiology")));
            jjjkSymptomEtiologyList.add(jjjkSymptomEtiology);

            JjjkSymptomPrevent jjjkSymptomPrevent = new JjjkSymptomPrevent();
            jjjkSymptomPrevent.setCreateTime(now);
            jjjkSymptomPrevent.setModifyTime(now);
            jjjkSymptomPrevent.setSymId(symptomLib.getSymId());
            jjjkSymptomPrevent.setSymLibId(symptomLib.getId());
            jjjkSymptomPrevent.setSymName(symptomLib.getSymName());
            jjjkSymptomPrevent.setPreventUrl(symptomLib.getPreventUrl());
            jjjkSymptomPrevent.setPreventHtml(EnDecodeUtil.encode(FileUtil.fileRead(path + symptomLib.getSymId() + "\\prevent")));
            jjjkSymptomPreventList.add(jjjkSymptomPrevent);

            JjjkSymptomExamine jjjkSymptomExamine = new JjjkSymptomExamine();
            jjjkSymptomExamine.setCreateTime(now);
            jjjkSymptomExamine.setModifyTime(now);
            jjjkSymptomExamine.setSymId(symptomLib.getSymId());
            jjjkSymptomExamine.setSymLibId(symptomLib.getId());
            jjjkSymptomExamine.setSymName(symptomLib.getSymName());
            jjjkSymptomExamine.setExamineUrl(symptomLib.getExamineUrl());
            jjjkSymptomExamine.setExamineHtml(EnDecodeUtil.encode(FileUtil.fileRead(path + symptomLib.getSymId() + "\\examine")));
            jjjkSymptomExamineList.add(jjjkSymptomExamine);

            JjjkSymptomHealth jjjkSymptomHealth = new JjjkSymptomHealth();
            jjjkSymptomHealth.setCreateTime(now);
            jjjkSymptomHealth.setModifyTime(now);
            jjjkSymptomHealth.setSymId(symptomLib.getSymId());
            jjjkSymptomHealth.setSymLibId(symptomLib.getId());
            jjjkSymptomHealth.setSymName(symptomLib.getSymName());
            jjjkSymptomHealth.setHealthUrl(symptomLib.getHealthUrl());
            jjjkSymptomHealth.setHealthHtml(EnDecodeUtil.encode(FileUtil.fileRead(path + symptomLib.getSymId() + "\\health")));
            jjjkSymptomHealthList.add(jjjkSymptomHealth);

            symptomLib.setIsHtmlsLoad(1);
            symptomLib.setModifyTime(now);

            if (jjjkSymptomSynopsisList.size() == 1000) {
                jjjkSymptomSynopsisService.saveBatch(jjjkSymptomSynopsisList);
                jjjkSymptomEtiologyService.saveBatch(jjjkSymptomEtiologyList);
                jjjkSymptomPreventService.saveBatch(jjjkSymptomPreventList);
                jjjkSymptomExamineService.saveBatch(jjjkSymptomExamineList);
                jjjkSymptomHealthService.saveBatch(jjjkSymptomHealthList);
                jjjkSymptomSynopsisList.clear();
                jjjkSymptomEtiologyList.clear();
                jjjkSymptomPreventList.clear();
                jjjkSymptomExamineList.clear();
                jjjkSymptomHealthList.clear();
            }
        });

        jjjkSymptomSynopsisService.saveBatch(jjjkSymptomSynopsisList);
        jjjkSymptomEtiologyService.saveBatch(jjjkSymptomEtiologyList);
        jjjkSymptomPreventService.saveBatch(jjjkSymptomPreventList);
        jjjkSymptomExamineService.saveBatch(jjjkSymptomExamineList);
        jjjkSymptomHealthService.saveBatch(jjjkSymptomHealthList);

        jjjkSymptomLibService.updateBatchById(symptomLibList);
    }

    @Transactional
    public void analysis() {
        analysisSynopsis();
        System.gc();
        analysisEtiology();
        System.gc();
        analysisPrevent();
        System.gc();
        analysisExamine();
        System.gc();
        analysisHealth();
        System.gc();
    }

    /**
     * 解析--简介
     */
    private void analysisSynopsis() {
        List<JjjkSymptomSynopsis> jjjkSymptomSynopsisUpts = Lists.newArrayList();
        QueryWrapper<JjjkSymptomSynopsis> jjjkSymptomSynopsisQe = new QueryWrapper<>();
        jjjkSymptomSynopsisQe.select("id", "synopsis_html");
        List<JjjkSymptomSynopsis> jjjkSymptomSynopsisList = jjjkSymptomSynopsisFacade.list(jjjkSymptomSynopsisQe);
        List<JjjkSymptomLib> jjjkSymptomLibList = Lists.newArrayList();
        Date now = DateUtil.now();
        jjjkSymptomSynopsisList.forEach(jjjkSymptomSynopsis -> {
            Element art1Element = Jsoup.parse(EnDecodeUtil.decode(jjjkSymptomSynopsis.getSynopsisHtml())).getElementsByClass("art_l").first();
            String anaTxt = JsoupUtil.clean(art1Element.outerHtml());
            if (anaTxt.indexOf("（温馨提示") != -1) {
                anaTxt = anaTxt.substring(0, anaTxt.indexOf("（温馨提示"));
            }
            JjjkSymptomSynopsis jjjkSymptomSynopsisUpt = new JjjkSymptomSynopsis();
            jjjkSymptomSynopsisUpt.setId(jjjkSymptomSynopsis.getId());
            jjjkSymptomSynopsisUpt.setSynopsisAnaytxt(anaTxt);
            jjjkSymptomSynopsisUpt.setModifyTime(now);
            jjjkSymptomSynopsisUpts.add(jjjkSymptomSynopsisUpt);

            String title = art1Element.getElementsByClass("bshare").first().select("h2").first().text();
            title = title.substring(0, title.length() - 2);
            JjjkSymptomLib jjjkSymptomLib = new JjjkSymptomLib();
            jjjkSymptomLib.setId(jjjkSymptomSynopsis.getSymLibId());
            jjjkSymptomLib.setSymName(title);
            jjjkSymptomLib.setIsHtmlsAnay(1);
            jjjkSymptomLib.setModifyTime(now);
            jjjkSymptomLibList.add(jjjkSymptomLib);
        });
        jjjkSymptomSynopsisList.clear();
        jjjkSymptomSynopsisService.updateBatchById(jjjkSymptomSynopsisUpts);
        jjjkSymptomSynopsisUpts.clear();
        jjjkSymptomLibService.updateBatchById(jjjkSymptomLibList);
        jjjkSymptomLibList.clear();
    }

    /**
     * 解析--病因
     */
    private void analysisEtiology() {
        List<JjjkSymptomEtiology> jjjkSymptomEtiologyUpts = Lists.newArrayList();
        QueryWrapper<JjjkSymptomEtiology> jjjkSymptomEtiologyQe = new QueryWrapper<>();
        jjjkSymptomEtiologyQe.select("id", "etiology_html");
        List<JjjkSymptomEtiology> jjjkSymptomEtiologyList = jjjkSymptomEtiologyFacade.list(jjjkSymptomEtiologyQe);
        Date now = DateUtil.now();
        jjjkSymptomEtiologyList.forEach(jjjkSymptomEtiology -> {
            Element bshareElement = Jsoup.parse(EnDecodeUtil.decode(jjjkSymptomEtiology.getEtiologyHtml()))
                    .getElementsByClass("art_l").first()
                    .getElementsByClass("bshare").first();
            String anaTxt = JsoupUtil.clean(bshareElement.outerHtml());
            JjjkSymptomEtiology jjjkSymptomEtiologyUpt = new JjjkSymptomEtiology();
            jjjkSymptomEtiologyUpt.setId(jjjkSymptomEtiology.getId());
            jjjkSymptomEtiologyUpt.setEtiologyAnaytxt(anaTxt);
            jjjkSymptomEtiologyUpt.setModifyTime(now);
            jjjkSymptomEtiologyUpts.add(jjjkSymptomEtiologyUpt);
        });
        jjjkSymptomEtiologyList.clear();
        jjjkSymptomEtiologyService.updateBatchById(jjjkSymptomEtiologyUpts);
        jjjkSymptomEtiologyUpts.clear();
    }

    /**
     * 解析--预防
     */
    private void analysisPrevent() {
        List<JjjkSymptomPrevent> jjjkSymptomPreventUpts = Lists.newArrayList();
        QueryWrapper<JjjkSymptomPrevent> jjjkSymptomPreventQe = new QueryWrapper<>();
        jjjkSymptomPreventQe.select("id", "prevent_html");
        List<JjjkSymptomPrevent> jjjkSymptomPreventList = jjjkSymptomPreventFacade.list(jjjkSymptomPreventQe);
        Date now = DateUtil.now();
        jjjkSymptomPreventList.forEach(jjjkSymptomPrevent -> {
            Element bshareElement = Jsoup.parse(EnDecodeUtil.decode(jjjkSymptomPrevent.getPreventHtml()))
                    .getElementsByClass("art_l").first()
                    .getElementsByClass("bshare").first();
            String anaTxt = JsoupUtil.clean(bshareElement.outerHtml());
            JjjkSymptomPrevent jjjkSymptomPreventUpt = new JjjkSymptomPrevent();
            jjjkSymptomPreventUpt.setId(jjjkSymptomPrevent.getId());
            jjjkSymptomPreventUpt.setPreventAnaytxt(anaTxt);
            jjjkSymptomPreventUpt.setModifyTime(now);
            jjjkSymptomPreventUpts.add(jjjkSymptomPreventUpt);
        });
        jjjkSymptomPreventList.clear();
        jjjkSymptomPreventService.updateBatchById(jjjkSymptomPreventUpts);
        jjjkSymptomPreventUpts.clear();
    }

    /**
     * 解析--检查
     */
    private void analysisExamine() {
        List<JjjkSymptomExamine> jjjkSymptomExamineUpts = Lists.newArrayList();
        QueryWrapper<JjjkSymptomExamine> jjjkSymptomExamineQe = new QueryWrapper<>();
        jjjkSymptomExamineQe.select("id", "examine_html");
        List<JjjkSymptomExamine> jjjkSymptomExamineList = jjjkSymptomExamineFacade.list(jjjkSymptomExamineQe);
        Date now = DateUtil.now();
        jjjkSymptomExamineList.forEach(jjjkSymptomExamine -> {
            Element bshareElement = Jsoup.parse(EnDecodeUtil.decode(jjjkSymptomExamine.getExamineHtml()))
                    .getElementsByClass("art_l").first()
                    .getElementsByClass("bshare").first();
            String anaTxt = JsoupUtil.clean(bshareElement.outerHtml());
            if (anaTxt.indexOf("（温馨提示") != -1) {
                anaTxt = anaTxt.substring(0, anaTxt.indexOf("（温馨提示"));
            }
            JjjkSymptomExamine jjjkSymptomExamineUpt = new JjjkSymptomExamine();
            jjjkSymptomExamineUpt.setId(jjjkSymptomExamine.getId());
            jjjkSymptomExamineUpt.setExamineAnaytxt(anaTxt);
            jjjkSymptomExamineUpt.setModifyTime(now);
            jjjkSymptomExamineUpts.add(jjjkSymptomExamineUpt);
        });
        jjjkSymptomExamineList.clear();
        jjjkSymptomExamineService.updateBatchById(jjjkSymptomExamineUpts);
        jjjkSymptomExamineUpts.clear();
    }

    /**
     * 解析--食疗
     */
    private void analysisHealth() {
        List<JjjkSymptomHealth> jjjkSymptomHealthUpts = Lists.newArrayList();
        QueryWrapper<JjjkSymptomHealth> jjjkSymptomHealthQe = new QueryWrapper<>();
        jjjkSymptomHealthQe.select("id", "health_html");
        List<JjjkSymptomHealth> jjjkSymptomHealthList = jjjkSymptomHealthFacade.list(jjjkSymptomHealthQe);
        Date now = DateUtil.now();
        jjjkSymptomHealthList.forEach(jjjkSymptomHealth -> {
            Element bshareElement = Jsoup.parse(EnDecodeUtil.decode(jjjkSymptomHealth.getHealthHtml()))
                    .getElementsByClass("art_l").first()
                    .getElementsByClass("bshare").first();
            String anaTxt = JsoupUtil.clean(bshareElement.outerHtml());
            JjjkSymptomHealth jjjkSymptomHealthUpt = new JjjkSymptomHealth();
            jjjkSymptomHealthUpt.setId(jjjkSymptomHealth.getId());
            jjjkSymptomHealthUpt.setHealthAnaytxt(anaTxt);
            jjjkSymptomHealthUpt.setModifyTime(now);
            jjjkSymptomHealthUpts.add(jjjkSymptomHealthUpt);
        });
        jjjkSymptomHealthList.clear();
        jjjkSymptomHealthService.updateBatchById(jjjkSymptomHealthUpts);
        jjjkSymptomHealthUpts.clear();
    }

    /**
     * 文件生成
     */
    public void fileGener() {
        QueryWrapper<JjjkSymptomLib> jjjkSymptomLibQe = new QueryWrapper<>();
        jjjkSymptomLibQe.eq("is_htmls_anay", 1);
        jjjkSymptomLibQe.select("id", "sym_name");
        List<JjjkSymptomLib> jjjkSymptomLibList = list(jjjkSymptomLibQe);

        QueryWrapper<JjjkPartSymptomMapping> jjjkPartSymptomMappingQe = new QueryWrapper<>();
        jjjkPartSymptomMappingQe.select("part_name", "sym_lib_id");
        Map<Long, List<String>> symIdPartNamesMap = jjjkPartSymptomMappingFacade.list(jjjkPartSymptomMappingQe)
                .stream().collect(
                        Collectors.groupingBy(
                                JjjkPartSymptomMapping::getSymLibId,
                                Collectors.mapping(JjjkPartSymptomMapping::getPartName, Collectors.toList())
                        )
                );

        QueryWrapper<JjjkSymptomSynopsis> jjjkSymptomSynopsisQe = new QueryWrapper<>();
        jjjkSymptomSynopsisQe.select("sym_lib_id", "synopsis_anaytxt");
        Map<Long, String> jjjkSymptomSynopsisMap = jjjkSymptomSynopsisFacade.list(jjjkSymptomSynopsisQe)
                .stream().collect(Collectors.toMap(JjjkSymptomSynopsis::getSymLibId, JjjkSymptomSynopsis::getSynopsisAnaytxt));

        QueryWrapper<JjjkSymptomEtiology> jjjkSymptomEtiologyQe = new QueryWrapper<>();
        jjjkSymptomEtiologyQe.select("sym_lib_id", "etiology_anaytxt");
        Map<Long, String> jjjkSymptomEtiologyMap = jjjkSymptomEtiologyFacade.list(jjjkSymptomEtiologyQe)
                .stream().collect(Collectors.toMap(JjjkSymptomEtiology::getSymLibId, JjjkSymptomEtiology::getEtiologyAnaytxt));

        QueryWrapper<JjjkSymptomPrevent> jjjkSymptomPreventQe = new QueryWrapper<>();
        jjjkSymptomPreventQe.select("sym_lib_id", "prevent_anaytxt");
        Map<Long, String> jjjkSymptomPreventMap = jjjkSymptomPreventFacade.list(jjjkSymptomPreventQe)
                .stream().collect(Collectors.toMap(JjjkSymptomPrevent::getSymLibId, JjjkSymptomPrevent::getPreventAnaytxt));

        QueryWrapper<JjjkSymptomExamine> jjjkSymptomExamineQe = new QueryWrapper<>();
        jjjkSymptomExamineQe.select("sym_lib_id", "examine_anaytxt");
        Map<Long, String> jjjkSymptomExamineMap = jjjkSymptomExamineFacade.list(jjjkSymptomExamineQe)
                .stream().collect(Collectors.toMap(JjjkSymptomExamine::getSymLibId, JjjkSymptomExamine::getExamineAnaytxt));

        QueryWrapper<JjjkSymptomHealth> jjjkSymptomHealthQe = new QueryWrapper<>();
        jjjkSymptomHealthQe.select("sym_lib_id", "health_anaytxt");
        Map<Long, String> jjjkSymptomHealthMap = jjjkSymptomHealthFacade.list(jjjkSymptomHealthQe)
                .stream().collect(Collectors.toMap(JjjkSymptomHealth::getSymLibId, JjjkSymptomHealth::getHealthAnaytxt));

        jjjkSymptomLibList.forEach(jjjkSymptomLib -> {
            List<String> partNames = symIdPartNamesMap.get(jjjkSymptomLib.getId());
            if (ListUtil.isEmpty(partNames)) {
                partNames = Lists.newArrayList();
                partNames.add("无归属");
            }
            partNames.forEach(partName -> {
                String path = "F:\\久久健康网\\症状\\" + partName + "\\"
                        + jjjkSymptomLib.getSymName().replaceAll("\\\\", "zxg").replaceAll("/", "fxg");
                File file = new File(path);
                if (!file.exists()) {
                    file.mkdirs();
                }
                FileUtil.fileWrite(path, "简介", jjjkSymptomSynopsisMap.get(jjjkSymptomLib.getId()));
                FileUtil.fileWrite(path, "病因", jjjkSymptomEtiologyMap.get(jjjkSymptomLib.getId()));
                FileUtil.fileWrite(path, "预防", jjjkSymptomPreventMap.get(jjjkSymptomLib.getId()));
                FileUtil.fileWrite(path, "检查", jjjkSymptomExamineMap.get(jjjkSymptomLib.getId()));
                FileUtil.fileWrite(path, "食疗", jjjkSymptomHealthMap.get(jjjkSymptomLib.getId()));
            });
        });
    }

}