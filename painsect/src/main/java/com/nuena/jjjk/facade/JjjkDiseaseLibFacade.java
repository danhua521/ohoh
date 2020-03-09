package com.nuena.jjjk.facade;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.nuena.jjjk.entity.JjjkBodypart;
import com.nuena.jjjk.entity.JjjkDeptDiseaseMapping;
import com.nuena.jjjk.entity.JjjkDeptInfo;
import com.nuena.jjjk.entity.JjjkDiseaseComplication;
import com.nuena.jjjk.entity.JjjkDiseaseDiscern;
import com.nuena.jjjk.entity.JjjkDiseaseEtiology;
import com.nuena.jjjk.entity.JjjkDiseaseExamine;
import com.nuena.jjjk.entity.JjjkDiseaseLib;
import com.nuena.jjjk.entity.JjjkDiseaseNurse;
import com.nuena.jjjk.entity.JjjkDiseasePrevent;
import com.nuena.jjjk.entity.JjjkDiseaseSymptom;
import com.nuena.jjjk.entity.JjjkDiseaseSynopsis;
import com.nuena.jjjk.entity.JjjkDiseaseTreat;
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
public class JjjkDiseaseLibFacade extends JjjkDiseaseLibServiceImpl {
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
    private JjjkDeptDiseaseMappingFacade jjjkDeptDiseaseMappingFacade;
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
            Thread.sleep(200);
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

    /**
     * 获取未下载html的疾病列表
     *
     * @return
     */
    public List<JjjkDiseaseLib> getNoLoadHtmlDiseases() {
        QueryWrapper<JjjkDiseaseLib> diseaseLibQe = new QueryWrapper<>();
        diseaseLibQe.eq("is_htmls_load", 0);
        return list(diseaseLibQe);
    }

    /**
     * 下载各模块HTML到本地
     *
     * @param path
     * @param diseaseLibList
     */
    public void loadHtmlToLocal(String path, List<JjjkDiseaseLib> diseaseLibList) {
        File file = new File(path);
        List<String> loadedDisIdList = Lists.newArrayList(file.listFiles()).stream().map(i -> i.getName()).collect(Collectors.toList());
        List<JjjkDiseaseLib> willLoadDisList = null;
        Map<String, String> htmlMap = null;
        while (ListUtil.isNotEmpty(willLoadDisList =
                diseaseLibList.stream().filter(i -> !loadedDisIdList.contains(i.getDisId())).collect(Collectors.toList()))) {
            for (JjjkDiseaseLib willLoadDis : willLoadDisList) {
                System.out.println("疾病：" + willLoadDis.getDisName());
                try {
                    htmlMap = getHtmls(willLoadDis);
                    if (htmlMap != null && htmlMap.size() == 9) {
                        System.out.println("下载各模块html成功---");
                        createLocalFiles(path, willLoadDis.getDisId(), htmlMap);
                        System.out.println("生成文件over---");
                        loadedDisIdList.add(willLoadDis.getDisId());
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
     * @param disId
     * @param content
     */
    private void createLocalFiles(String path, String disId, Map<String, String> content) {
        String path_ = path + disId;
        content.keySet().forEach(key -> {
            FileUtil.fileWrite(path_, key, content.get(key));
        });
    }

    /**
     * 组装包含html 的 map
     *
     * @param diseaseLib
     * @return
     */
    private Map<String, String> getHtmls(JjjkDiseaseLib diseaseLib) {
        Map<String, String> content = Maps.newHashMap();
        String html = loadHtml(diseaseLib.getSynopsisUrl());
        if (StringUtil.isBlank(html)) {
            return null;
        } else {
            content.put("jianjie", html);
        }

        html = loadHtml(diseaseLib.getEtiologyUrl());
        if (StringUtil.isBlank(html)) {
            return null;
        } else {
            content.put("by", html);
        }

        html = loadHtml(diseaseLib.getPreventUrl());
        if (StringUtil.isBlank(html)) {
            return null;
        } else {
            content.put("yf", html);
        }

        html = loadHtml(diseaseLib.getExamineUrl());
        if (StringUtil.isBlank(html)) {
            return null;
        } else {
            content.put("lcjc", html);
        }

        html = loadHtml(diseaseLib.getComplicationUrl());
        if (StringUtil.isBlank(html)) {
            return null;
        } else {
            content.put("bfz", html);
        }

        html = loadHtml(diseaseLib.getSymptomUrl());
        if (StringUtil.isBlank(html)) {
            return null;
        } else {
            content.put("zz", html);
        }

        html = loadHtml(diseaseLib.getDiscernUrl());
        if (StringUtil.isBlank(html)) {
            return null;
        } else {
            content.put("jb", html);
        }

        html = loadHtml(diseaseLib.getTreatUrl());
        if (StringUtil.isBlank(html)) {
            return null;
        } else {
            content.put("zl", html);
        }

        html = loadHtml(diseaseLib.getNurseUrl());
        if (StringUtil.isBlank(html)) {
            return null;
        } else {
            content.put("yshl", html);
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
     * @param diseaseLibList
     */
    @Transactional
    public void loadedHtmlIntoDB(String path, List<JjjkDiseaseLib> diseaseLibList) {
        Date now = DateUtil.now();
        List<JjjkDiseaseSynopsis> jjjkDiseaseSynopsisList = Lists.newArrayList();
        List<JjjkDiseaseEtiology> jjjkDiseaseEtiologyList = Lists.newArrayList();
        List<JjjkDiseasePrevent> jjjkDiseasePreventList = Lists.newArrayList();
        List<JjjkDiseaseExamine> jjjkDiseaseExamineList = Lists.newArrayList();
        List<JjjkDiseaseComplication> jjjkDiseaseComplicationList = Lists.newArrayList();
        List<JjjkDiseaseSymptom> jjjkDiseaseSymptomList = Lists.newArrayList();
        List<JjjkDiseaseDiscern> jjjkDiseaseDiscernList = Lists.newArrayList();
        List<JjjkDiseaseTreat> jjjkDiseaseTreatList = Lists.newArrayList();
        List<JjjkDiseaseNurse> jjjkDiseaseNurseList = Lists.newArrayList();
        diseaseLibList.forEach(diseaseLib -> {
            System.out.println("在进行：" + diseaseLib.getDisName());
            JjjkDiseaseSynopsis jjjkDiseaseSynopsis = new JjjkDiseaseSynopsis();
            jjjkDiseaseSynopsis.setCreateTime(now);
            jjjkDiseaseSynopsis.setModifyTime(now);
            jjjkDiseaseSynopsis.setDisId(diseaseLib.getDisId());
            jjjkDiseaseSynopsis.setDisLibId(diseaseLib.getId());
            jjjkDiseaseSynopsis.setDisName(diseaseLib.getDisName());
            jjjkDiseaseSynopsis.setSynopsisUrl(diseaseLib.getSynopsisUrl());
            jjjkDiseaseSynopsis.setSynopsisHtml(EnDecodeUtil.encode(FileUtil.fileRead(path + diseaseLib.getDisId() + "\\jianjie")));
            jjjkDiseaseSynopsisList.add(jjjkDiseaseSynopsis);

            JjjkDiseaseEtiology jjjkDiseaseEtiology = new JjjkDiseaseEtiology();
            jjjkDiseaseEtiology.setCreateTime(now);
            jjjkDiseaseEtiology.setModifyTime(now);
            jjjkDiseaseEtiology.setDisId(diseaseLib.getDisId());
            jjjkDiseaseEtiology.setDisLibId(diseaseLib.getId());
            jjjkDiseaseEtiology.setDisName(diseaseLib.getDisName());
            jjjkDiseaseEtiology.setEtiologyUrl(diseaseLib.getEtiologyUrl());
            jjjkDiseaseEtiology.setEtiologyHtml(EnDecodeUtil.encode(FileUtil.fileRead(path + diseaseLib.getDisId() + "\\by")));
            jjjkDiseaseEtiologyList.add(jjjkDiseaseEtiology);

            JjjkDiseasePrevent jjjkDiseasePrevent = new JjjkDiseasePrevent();
            jjjkDiseasePrevent.setCreateTime(now);
            jjjkDiseasePrevent.setModifyTime(now);
            jjjkDiseasePrevent.setDisId(diseaseLib.getDisId());
            jjjkDiseasePrevent.setDisLibId(diseaseLib.getId());
            jjjkDiseasePrevent.setDisName(diseaseLib.getDisName());
            jjjkDiseasePrevent.setPreventUrl(diseaseLib.getPreventUrl());
            jjjkDiseasePrevent.setPreventHtml(EnDecodeUtil.encode(FileUtil.fileRead(path + diseaseLib.getDisId() + "\\yf")));
            jjjkDiseasePreventList.add(jjjkDiseasePrevent);

            JjjkDiseaseExamine jjjkDiseaseExamine = new JjjkDiseaseExamine();
            jjjkDiseaseExamine.setCreateTime(now);
            jjjkDiseaseExamine.setModifyTime(now);
            jjjkDiseaseExamine.setDisId(diseaseLib.getDisId());
            jjjkDiseaseExamine.setDisLibId(diseaseLib.getId());
            jjjkDiseaseExamine.setDisName(diseaseLib.getDisName());
            jjjkDiseaseExamine.setExamineUrl(diseaseLib.getExamineUrl());
            jjjkDiseaseExamine.setExamineHtml(EnDecodeUtil.encode(FileUtil.fileRead(path + diseaseLib.getDisId() + "\\lcjc")));
            jjjkDiseaseExamineList.add(jjjkDiseaseExamine);

            JjjkDiseaseComplication jjjkDiseaseComplication = new JjjkDiseaseComplication();
            jjjkDiseaseComplication.setCreateTime(now);
            jjjkDiseaseComplication.setModifyTime(now);
            jjjkDiseaseComplication.setDisId(diseaseLib.getDisId());
            jjjkDiseaseComplication.setDisLibId(diseaseLib.getId());
            jjjkDiseaseComplication.setDisName(diseaseLib.getDisName());
            jjjkDiseaseComplication.setComplicationUrl(diseaseLib.getComplicationUrl());
            jjjkDiseaseComplication.setComplicationHtml(EnDecodeUtil.encode(FileUtil.fileRead(path + diseaseLib.getDisId() + "\\bfz")));
            jjjkDiseaseComplicationList.add(jjjkDiseaseComplication);

            JjjkDiseaseSymptom jjjkDiseaseSymptom = new JjjkDiseaseSymptom();
            jjjkDiseaseSymptom.setCreateTime(now);
            jjjkDiseaseSymptom.setModifyTime(now);
            jjjkDiseaseSymptom.setDisId(diseaseLib.getDisId());
            jjjkDiseaseSymptom.setDisLibId(diseaseLib.getId());
            jjjkDiseaseSymptom.setDisName(diseaseLib.getDisName());
            jjjkDiseaseSymptom.setSymptomUrl(diseaseLib.getSymptomUrl());
            jjjkDiseaseSymptom.setSymptomHtml(EnDecodeUtil.encode(FileUtil.fileRead(path + diseaseLib.getDisId() + "\\zz")));
            jjjkDiseaseSymptomList.add(jjjkDiseaseSymptom);

            JjjkDiseaseDiscern jjjkDiseaseDiscern = new JjjkDiseaseDiscern();
            jjjkDiseaseDiscern.setCreateTime(now);
            jjjkDiseaseDiscern.setModifyTime(now);
            jjjkDiseaseDiscern.setDisId(diseaseLib.getDisId());
            jjjkDiseaseDiscern.setDisLibId(diseaseLib.getId());
            jjjkDiseaseDiscern.setDisName(diseaseLib.getDisName());
            jjjkDiseaseDiscern.setDiscernUrl(diseaseLib.getDiscernUrl());
            jjjkDiseaseDiscern.setDiscernHtml(EnDecodeUtil.encode(FileUtil.fileRead(path + diseaseLib.getDisId() + "\\jb")));
            jjjkDiseaseDiscernList.add(jjjkDiseaseDiscern);

            JjjkDiseaseTreat jjjkDiseaseTreat = new JjjkDiseaseTreat();
            jjjkDiseaseTreat.setCreateTime(now);
            jjjkDiseaseTreat.setModifyTime(now);
            jjjkDiseaseTreat.setDisId(diseaseLib.getDisId());
            jjjkDiseaseTreat.setDisLibId(diseaseLib.getId());
            jjjkDiseaseTreat.setDisName(diseaseLib.getDisName());
            jjjkDiseaseTreat.setTreatUrl(diseaseLib.getTreatUrl());
            jjjkDiseaseTreat.setTreatHtml(EnDecodeUtil.encode(FileUtil.fileRead(path + diseaseLib.getDisId() + "\\zl")));
            jjjkDiseaseTreatList.add(jjjkDiseaseTreat);

            JjjkDiseaseNurse jjjkDiseaseNurse = new JjjkDiseaseNurse();
            jjjkDiseaseNurse.setCreateTime(now);
            jjjkDiseaseNurse.setModifyTime(now);
            jjjkDiseaseNurse.setDisId(diseaseLib.getDisId());
            jjjkDiseaseNurse.setDisLibId(diseaseLib.getId());
            jjjkDiseaseNurse.setDisName(diseaseLib.getDisName());
            jjjkDiseaseNurse.setNurseUrl(diseaseLib.getNurseUrl());
            jjjkDiseaseNurse.setNurseHtml(EnDecodeUtil.encode(FileUtil.fileRead(path + diseaseLib.getDisId() + "\\yshl")));
            jjjkDiseaseNurseList.add(jjjkDiseaseNurse);

            diseaseLib.setIsHtmlsLoad(1);
            diseaseLib.setModifyTime(now);

            if (jjjkDiseaseSynopsisList.size() == 1000) {
                jjjkDiseaseSynopsisService.saveBatch(jjjkDiseaseSynopsisList);
                jjjkDiseaseEtiologyService.saveBatch(jjjkDiseaseEtiologyList);
                jjjkDiseasePreventService.saveBatch(jjjkDiseasePreventList);
                jjjkDiseaseExamineService.saveBatch(jjjkDiseaseExamineList);
                jjjkDiseaseComplicationService.saveBatch(jjjkDiseaseComplicationList);
                jjjkDiseaseSymptomService.saveBatch(jjjkDiseaseSymptomList);
                jjjkDiseaseDiscernService.saveBatch(jjjkDiseaseDiscernList);
                jjjkDiseaseTreatService.saveBatch(jjjkDiseaseTreatList);
                jjjkDiseaseNurseService.saveBatch(jjjkDiseaseNurseList);

                jjjkDiseaseSynopsisList.clear();
                jjjkDiseaseEtiologyList.clear();
                jjjkDiseasePreventList.clear();
                jjjkDiseaseExamineList.clear();
                jjjkDiseaseComplicationList.clear();
                jjjkDiseaseSymptomList.clear();
                jjjkDiseaseDiscernList.clear();
                jjjkDiseaseTreatList.clear();
                jjjkDiseaseNurseList.clear();
            }
        });

        jjjkDiseaseSynopsisService.saveBatch(jjjkDiseaseSynopsisList);
        jjjkDiseaseEtiologyService.saveBatch(jjjkDiseaseEtiologyList);
        jjjkDiseasePreventService.saveBatch(jjjkDiseasePreventList);
        jjjkDiseaseExamineService.saveBatch(jjjkDiseaseExamineList);
        jjjkDiseaseComplicationService.saveBatch(jjjkDiseaseComplicationList);
        jjjkDiseaseSymptomService.saveBatch(jjjkDiseaseSymptomList);
        jjjkDiseaseDiscernService.saveBatch(jjjkDiseaseDiscernList);
        jjjkDiseaseTreatService.saveBatch(jjjkDiseaseTreatList);
        jjjkDiseaseNurseService.saveBatch(jjjkDiseaseNurseList);

        jjjkDiseaseLibService.updateBatchById(diseaseLibList);
    }

    @Transactional
    public void analysis() {
        analysisSynopsis();
        System.gc();
        analysisEtiology();
        System.gc();
        analysisPrevent();
        System.gc();
        analysisComplication();
        System.gc();
        analysisSymptom();
        System.gc();
        analysisExamine();
        System.gc();
        analysisDiscern();
        System.gc();
        analysisTreat();
        System.gc();
        analysisNurse();
        System.gc();
    }

    /**
     * 解析--简介
     */
    private void analysisSynopsis() {
        List<JjjkDiseaseSynopsis> jjjkDiseaseSynopsisUpts = Lists.newArrayList();
        QueryWrapper<JjjkDiseaseSynopsis> jjjkDiseaseSynopsisQe = new QueryWrapper<>();
        jjjkDiseaseSynopsisQe.select("id", "synopsis_html", "dis_lib_id");
        List<JjjkDiseaseSynopsis> jjjkDiseaseSynopsisList = jjjkDiseaseSynopsisFacade.list(jjjkDiseaseSynopsisQe);
        List<JjjkDiseaseLib> jjjkDiseaseLibList = Lists.newArrayList();
        Date now = DateUtil.now();
        jjjkDiseaseSynopsisList.forEach(jjjkDiseaseSynopsis -> {
            Element diseaElement = Jsoup.parse(EnDecodeUtil.decode(jjjkDiseaseSynopsis.getSynopsisHtml())).getElementsByClass("disea").first();
            String anaTxt = JsoupUtil.clean(diseaElement.outerHtml());
            if (anaTxt.indexOf("（温馨提示") != -1) {
                anaTxt = anaTxt.substring(0, anaTxt.indexOf("（温馨提示"));
            }
            JjjkDiseaseSynopsis jjjkDiseaseSynopsisUpt = new JjjkDiseaseSynopsis();
            jjjkDiseaseSynopsisUpt.setId(jjjkDiseaseSynopsis.getId());
            jjjkDiseaseSynopsisUpt.setSynopsisAnaytxt(anaTxt);
            jjjkDiseaseSynopsisUpt.setModifyTime(now);
            jjjkDiseaseSynopsisUpts.add(jjjkDiseaseSynopsisUpt);

            String title = diseaElement.getElementsByClass("bshare").first().select("h2").first().text();
            title = title.substring(0, title.length() - 2);
            JjjkDiseaseLib jjjkDiseaseLib = new JjjkDiseaseLib();
            jjjkDiseaseLib.setId(jjjkDiseaseSynopsis.getDisLibId());
            jjjkDiseaseLib.setDisName(title);
            jjjkDiseaseLib.setIsHtmlsAnay(1);
            jjjkDiseaseLib.setModifyTime(now);
            jjjkDiseaseLibList.add(jjjkDiseaseLib);
        });
        jjjkDiseaseSynopsisList.clear();
        jjjkDiseaseSynopsisService.updateBatchById(jjjkDiseaseSynopsisUpts);
        jjjkDiseaseSynopsisUpts.clear();
        jjjkDiseaseLibService.updateBatchById(jjjkDiseaseLibList);
        jjjkDiseaseLibList.clear();
    }

    /**
     * 解析--病因
     */
    private void analysisEtiology() {
        List<JjjkDiseaseEtiology> jjjkDiseaseEtiologyUpts = Lists.newArrayList();
        QueryWrapper<JjjkDiseaseEtiology> jjjkDiseaseEtiologyQe = new QueryWrapper<>();
        jjjkDiseaseEtiologyQe.select("id", "etiology_html", "dis_lib_id");
        List<JjjkDiseaseEtiology> jjjkDiseaseEtiologyList = jjjkDiseaseEtiologyFacade.list(jjjkDiseaseEtiologyQe);
        Date now = DateUtil.now();
        jjjkDiseaseEtiologyList.forEach(jjjkDiseaseEtiology -> {
            Element bshareElement = Jsoup.parse(EnDecodeUtil.decode(jjjkDiseaseEtiology.getEtiologyHtml()))
                    .getElementsByClass("disea").first()
                    .getElementsByClass("bshare").first();
            String anaTxt = JsoupUtil.clean(bshareElement.outerHtml());
            JjjkDiseaseEtiology jjjkDiseaseEtiologyUpt = new JjjkDiseaseEtiology();
            jjjkDiseaseEtiologyUpt.setId(jjjkDiseaseEtiology.getId());
            jjjkDiseaseEtiologyUpt.setEtiologyAnaytxt(anaTxt);
            jjjkDiseaseEtiologyUpt.setModifyTime(now);
            jjjkDiseaseEtiologyUpts.add(jjjkDiseaseEtiologyUpt);
        });
        jjjkDiseaseEtiologyList.clear();
        jjjkDiseaseEtiologyService.updateBatchById(jjjkDiseaseEtiologyUpts);
        jjjkDiseaseEtiologyUpts.clear();
    }

    /**
     * 解析--预防
     */
    private void analysisPrevent() {
        List<JjjkDiseasePrevent> jjjkDiseasePreventUpts = Lists.newArrayList();
        QueryWrapper<JjjkDiseasePrevent> jjjkDiseasePreventQe = new QueryWrapper<>();
        jjjkDiseasePreventQe.select("id", "prevent_html", "dis_lib_id");
        List<JjjkDiseasePrevent> jjjkDiseasePreventList = jjjkDiseasePreventFacade.list(jjjkDiseasePreventQe);
        Date now = DateUtil.now();
        jjjkDiseasePreventList.forEach(jjjkDiseasePrevent -> {
            Element bshareElement = Jsoup.parse(EnDecodeUtil.decode(jjjkDiseasePrevent.getPreventHtml()))
                    .getElementsByClass("disea").first()
                    .getElementsByClass("bshare").first();
            String anaTxt = JsoupUtil.clean(bshareElement.outerHtml());
            JjjkDiseasePrevent jjjkDiseasePreventUpt = new JjjkDiseasePrevent();
            jjjkDiseasePreventUpt.setId(jjjkDiseasePrevent.getId());
            jjjkDiseasePreventUpt.setPreventAnaytxt(anaTxt);
            jjjkDiseasePreventUpt.setModifyTime(now);
            jjjkDiseasePreventUpts.add(jjjkDiseasePreventUpt);
        });
        jjjkDiseasePreventList.clear();
        jjjkDiseasePreventService.updateBatchById(jjjkDiseasePreventUpts);
        jjjkDiseasePreventUpts.clear();
    }

    /**
     * 解析--并发症
     */
    private void analysisComplication() {
        List<JjjkDiseaseComplication> jjjkDiseaseComplicationUpts = Lists.newArrayList();
        QueryWrapper<JjjkDiseaseComplication> jjjkDiseaseComplicationQe = new QueryWrapper<>();
        jjjkDiseaseComplicationQe.select("id", "complication_html", "dis_lib_id");
        List<JjjkDiseaseComplication> jjjkDiseaseComplicationList = jjjkDiseaseComplicationFacade.list(jjjkDiseaseComplicationQe);
        Date now = DateUtil.now();
        jjjkDiseaseComplicationList.forEach(jjjkDiseaseComplication -> {
            Element bshareElement = Jsoup.parse(EnDecodeUtil.decode(jjjkDiseaseComplication.getComplicationHtml()))
                    .getElementsByClass("disea").first()
                    .getElementsByClass("bshare").first();
            String anaTxt = JsoupUtil.clean(bshareElement.outerHtml());
            if (anaTxt.indexOf("（温馨提示") != -1) {
                anaTxt = anaTxt.substring(0, anaTxt.indexOf("（温馨提示"));
            }
            JjjkDiseaseComplication jjjkDiseaseComplicationUpt = new JjjkDiseaseComplication();
            jjjkDiseaseComplicationUpt.setId(jjjkDiseaseComplication.getId());
            jjjkDiseaseComplicationUpt.setComplicationAnaytxt(anaTxt);
            jjjkDiseaseComplicationUpt.setModifyTime(now);
            jjjkDiseaseComplicationUpts.add(jjjkDiseaseComplicationUpt);
        });
        jjjkDiseaseComplicationList.clear();
        jjjkDiseaseComplicationService.updateBatchById(jjjkDiseaseComplicationUpts);
        jjjkDiseaseComplicationUpts.clear();
    }

    /**
     * 解析--症状
     */
    private void analysisSymptom() {
        List<JjjkDiseaseSymptom> jjjkDiseaseSymptomUpts = Lists.newArrayList();
        QueryWrapper<JjjkDiseaseSymptom> jjjkDiseaseSymptomQe = new QueryWrapper<>();
        jjjkDiseaseSymptomQe.select("id", "symptom_html", "dis_lib_id");
        List<JjjkDiseaseSymptom> jjjkDiseaseSymptomList = jjjkDiseaseSymptomFacade.list(jjjkDiseaseSymptomQe);
        Date now = DateUtil.now();
        jjjkDiseaseSymptomList.forEach(jjjkDiseaseSymptom -> {
            Element bshareElement = Jsoup.parse(EnDecodeUtil.decode(jjjkDiseaseSymptom.getSymptomHtml()))
                    .getElementsByClass("disea").first()
                    .getElementsByClass("bshare").first();
            String anaTxt = JsoupUtil.clean(bshareElement.outerHtml());
            JjjkDiseaseSymptom jjjkDiseaseSymptomUpt = new JjjkDiseaseSymptom();
            jjjkDiseaseSymptomUpt.setId(jjjkDiseaseSymptom.getId());
            jjjkDiseaseSymptomUpt.setSymptomAnaytxt(anaTxt);
            jjjkDiseaseSymptomUpt.setModifyTime(now);
            jjjkDiseaseSymptomUpts.add(jjjkDiseaseSymptomUpt);
        });
        jjjkDiseaseSymptomList.clear();
        jjjkDiseaseSymptomService.updateBatchById(jjjkDiseaseSymptomUpts);
        jjjkDiseaseSymptomUpts.clear();
    }

    /**
     * 解析--检查
     */
    private void analysisExamine() {
        List<JjjkDiseaseExamine> jjjkDiseaseExamineUpts = Lists.newArrayList();
        QueryWrapper<JjjkDiseaseExamine> jjjkDiseaseExamineQe = new QueryWrapper<>();
        jjjkDiseaseExamineQe.select("id", "examine_html", "dis_lib_id");
        List<JjjkDiseaseExamine> jjjkDiseaseExamineList = jjjkDiseaseExamineFacade.list(jjjkDiseaseExamineQe);
        Date now = DateUtil.now();
        jjjkDiseaseExamineList.forEach(jjjkDiseaseExamine -> {
            Element bshareElement = Jsoup.parse(EnDecodeUtil.decode(jjjkDiseaseExamine.getExamineHtml()))
                    .getElementsByClass("disea").first()
                    .getElementsByClass("bshare").first();
            String anaTxt = JsoupUtil.clean(bshareElement.outerHtml());
            if (anaTxt.indexOf("（温馨提示") != -1) {
                anaTxt = anaTxt.substring(0, anaTxt.indexOf("（温馨提示"));
            }
            JjjkDiseaseExamine jjjkDiseaseExamineUpt = new JjjkDiseaseExamine();
            jjjkDiseaseExamineUpt.setId(jjjkDiseaseExamine.getId());
            jjjkDiseaseExamineUpt.setExamineAnaytxt(anaTxt);
            jjjkDiseaseExamineUpt.setModifyTime(now);
            jjjkDiseaseExamineUpts.add(jjjkDiseaseExamineUpt);
        });
        jjjkDiseaseExamineList.clear();
        jjjkDiseaseExamineService.updateBatchById(jjjkDiseaseExamineUpts);
        jjjkDiseaseExamineUpts.clear();
    }

    /**
     * 解析--诊断鉴别
     */
    private void analysisDiscern() {
        List<JjjkDiseaseDiscern> jjjkDiseaseDiscernUpts = Lists.newArrayList();
        QueryWrapper<JjjkDiseaseDiscern> jjjkDiseaseDiscernQe = new QueryWrapper<>();
        jjjkDiseaseDiscernQe.select("id", "discern_html", "dis_lib_id");
        List<JjjkDiseaseDiscern> jjjkDiseaseDiscernList = jjjkDiseaseDiscernFacade.list(jjjkDiseaseDiscernQe);
        Date now = DateUtil.now();
        jjjkDiseaseDiscernList.forEach(jjjkDiseaseDiscern -> {
            Element bshareElement = Jsoup.parse(EnDecodeUtil.decode(jjjkDiseaseDiscern.getDiscernHtml()))
                    .getElementsByClass("disea").first()
                    .getElementsByClass("bshare").first();
            String anaTxt = JsoupUtil.clean(bshareElement.outerHtml());
            if (anaTxt.indexOf("（温馨提示") != -1) {
                anaTxt = anaTxt.substring(0, anaTxt.indexOf("（温馨提示"));
            }
            JjjkDiseaseDiscern jjjkDiseaseDiscernUpt = new JjjkDiseaseDiscern();
            jjjkDiseaseDiscernUpt.setId(jjjkDiseaseDiscern.getId());
            jjjkDiseaseDiscernUpt.setDiscernAnaytxt(anaTxt);
            jjjkDiseaseDiscernUpt.setModifyTime(now);
            jjjkDiseaseDiscernUpts.add(jjjkDiseaseDiscernUpt);
        });
        jjjkDiseaseDiscernList.clear();
        jjjkDiseaseDiscernService.updateBatchById(jjjkDiseaseDiscernUpts);
        jjjkDiseaseDiscernUpts.clear();
    }

    /**
     * 解析--治疗
     */
    private void analysisTreat() {
        List<JjjkDiseaseTreat> jjjkDiseaseTreatUpts = Lists.newArrayList();
        QueryWrapper<JjjkDiseaseTreat> jjjkDiseaseTreatQe = new QueryWrapper<>();
        jjjkDiseaseTreatQe.select("id", "treat_html", "dis_lib_id");
        List<JjjkDiseaseTreat> jjjkDiseaseTreatList = jjjkDiseaseTreatFacade.list(jjjkDiseaseTreatQe);
        Date now = DateUtil.now();
        jjjkDiseaseTreatList.forEach(jjjkDiseaseTreat -> {
            Element bshareElement = Jsoup.parse(EnDecodeUtil.decode(jjjkDiseaseTreat.getTreatHtml()))
                    .getElementsByClass("disea").first()
                    .getElementsByClass("bshare").first();
            String anaTxt = JsoupUtil.clean(bshareElement.outerHtml());
            if (anaTxt.indexOf("（温馨提示") != -1) {
                anaTxt = anaTxt.substring(0, anaTxt.indexOf("（温馨提示"));
            }
            JjjkDiseaseTreat jjjkDiseaseTreatUpt = new JjjkDiseaseTreat();
            jjjkDiseaseTreatUpt.setId(jjjkDiseaseTreat.getId());
            jjjkDiseaseTreatUpt.setTreatAnaytxt(anaTxt);
            jjjkDiseaseTreatUpt.setModifyTime(now);
            jjjkDiseaseTreatUpts.add(jjjkDiseaseTreatUpt);
        });
        jjjkDiseaseTreatList.clear();
        jjjkDiseaseTreatService.updateBatchById(jjjkDiseaseTreatUpts);
        jjjkDiseaseTreatUpts.clear();
    }

    /**
     * 解析--护理
     */
    private void analysisNurse() {
        List<JjjkDiseaseNurse> jjjkDiseaseNurseUpts = Lists.newArrayList();
        QueryWrapper<JjjkDiseaseNurse> jjjkDiseaseNurseQe = new QueryWrapper<>();
        jjjkDiseaseNurseQe.select("id", "nurse_html", "dis_lib_id");
        List<JjjkDiseaseNurse> jjjkDiseaseNurseList = jjjkDiseaseNurseFacade.list(jjjkDiseaseNurseQe);
        Date now = DateUtil.now();
        jjjkDiseaseNurseList.forEach(jjjkDiseaseNurse -> {
            Element bshareElement = Jsoup.parse(EnDecodeUtil.decode(jjjkDiseaseNurse.getNurseHtml()))
                    .getElementsByClass("disea").first()
                    .getElementsByClass("bshare").first();
            String anaTxt = JsoupUtil.clean(bshareElement.outerHtml());
            if (anaTxt.indexOf("（温馨提示") != -1) {
                anaTxt = anaTxt.substring(0, anaTxt.indexOf("（温馨提示"));
            }
            JjjkDiseaseNurse jjjkDiseaseNurseUpt = new JjjkDiseaseNurse();
            jjjkDiseaseNurseUpt.setId(jjjkDiseaseNurse.getId());
            jjjkDiseaseNurseUpt.setNurseAnaytxt(anaTxt);
            jjjkDiseaseNurseUpt.setModifyTime(now);
            jjjkDiseaseNurseUpts.add(jjjkDiseaseNurseUpt);
        });
        jjjkDiseaseNurseList.clear();
        jjjkDiseaseNurseService.updateBatchById(jjjkDiseaseNurseUpts);
        jjjkDiseaseNurseUpts.clear();
    }

    /**
     * 文件生成
     */
    public void fileGener() {
        QueryWrapper<JjjkDiseaseLib> jjjkDiseaseLibQe = new QueryWrapper<>();
        jjjkDiseaseLibQe.eq("is_htmls_anay", 1);
        jjjkDiseaseLibQe.select("id", "dis_name");
        List<JjjkDiseaseLib> jjjkDiseaseLibList = list(jjjkDiseaseLibQe);

        QueryWrapper<JjjkDeptDiseaseMapping> jjjkDeptDiseaseMappingQe = new QueryWrapper<>();
        jjjkDeptDiseaseMappingQe.select("dis_lib_id", "dept_name");
        Map<Long, List<String>> disIdDeptNamesMap = jjjkDeptDiseaseMappingFacade.list(jjjkDeptDiseaseMappingQe)
                .stream().collect(
                        Collectors.groupingBy(
                                JjjkDeptDiseaseMapping::getDisLibId,
                                Collectors.mapping(JjjkDeptDiseaseMapping::getDeptName, Collectors.toList())
                        )
                );

        QueryWrapper<JjjkDiseaseSynopsis> jjjkDiseaseSynopsisQe = new QueryWrapper<>();
        jjjkDiseaseSynopsisQe.select("dis_lib_id", "synopsis_anaytxt");
        Map<Long, String> jjjkDiseaseSynopsisMap = jjjkDiseaseSynopsisFacade.list(jjjkDiseaseSynopsisQe)
                .stream().collect(Collectors.toMap(JjjkDiseaseSynopsis::getDisLibId, JjjkDiseaseSynopsis::getSynopsisAnaytxt));

        QueryWrapper<JjjkDiseaseEtiology> jjjkDiseaseEtiologyQe = new QueryWrapper<>();
        jjjkDiseaseEtiologyQe.select("dis_lib_id", "etiology_anaytxt");
        Map<Long, String> jjjkDiseaseEtiologyMap = jjjkDiseaseEtiologyFacade.list(jjjkDiseaseEtiologyQe)
                .stream().collect(Collectors.toMap(JjjkDiseaseEtiology::getDisLibId, JjjkDiseaseEtiology::getEtiologyAnaytxt));

        QueryWrapper<JjjkDiseasePrevent> jjjkDiseasePreventQe = new QueryWrapper<>();
        jjjkDiseasePreventQe.select("dis_lib_id", "prevent_anaytxt");
        Map<Long, String> jjjkDiseasePreventMap = jjjkDiseasePreventFacade.list(jjjkDiseasePreventQe)
                .stream().collect(Collectors.toMap(JjjkDiseasePrevent::getDisLibId, JjjkDiseasePrevent::getPreventAnaytxt));

        QueryWrapper<JjjkDiseaseComplication> jjjkDiseaseComplicationQe = new QueryWrapper<>();
        jjjkDiseaseComplicationQe.select("dis_lib_id", "complication_anaytxt");
        Map<Long, String> jjjkDiseaseComplicationMap = jjjkDiseaseComplicationFacade.list(jjjkDiseaseComplicationQe)
                .stream().collect(Collectors.toMap(JjjkDiseaseComplication::getDisLibId, JjjkDiseaseComplication::getComplicationAnaytxt));

        QueryWrapper<JjjkDiseaseSymptom> jjjkDiseaseSymptomQe = new QueryWrapper<>();
        jjjkDiseaseSymptomQe.select("dis_lib_id", "symptom_anaytxt");
        Map<Long, String> jjjkDiseaseSymptomMap = jjjkDiseaseSymptomFacade.list(jjjkDiseaseSymptomQe)
                .stream().collect(Collectors.toMap(JjjkDiseaseSymptom::getDisLibId, JjjkDiseaseSymptom::getSymptomAnaytxt));

        QueryWrapper<JjjkDiseaseExamine> jjjkDiseaseExamineQe = new QueryWrapper<>();
        jjjkDiseaseExamineQe.select("dis_lib_id", "examine_anaytxt");
        Map<Long, String> jjjkDiseaseExamineMap = jjjkDiseaseExamineFacade.list(jjjkDiseaseExamineQe)
                .stream().collect(Collectors.toMap(JjjkDiseaseExamine::getDisLibId, JjjkDiseaseExamine::getExamineAnaytxt));

        QueryWrapper<JjjkDiseaseDiscern> jjjkDiseaseDiscernQe = new QueryWrapper<>();
        jjjkDiseaseDiscernQe.select("dis_lib_id", "discern_anaytxt");
        Map<Long, String> jjjkDiseaseDiscernMap = jjjkDiseaseDiscernFacade.list(jjjkDiseaseDiscernQe)
                .stream().collect(Collectors.toMap(JjjkDiseaseDiscern::getDisLibId, JjjkDiseaseDiscern::getDiscernAnaytxt));

        QueryWrapper<JjjkDiseaseTreat> jjjkDiseaseTreatQe = new QueryWrapper<>();
        jjjkDiseaseTreatQe.select("dis_lib_id", "treat_anaytxt");
        Map<Long, String> jjjkDiseaseTreatMap = jjjkDiseaseTreatFacade.list(jjjkDiseaseTreatQe)
                .stream().collect(Collectors.toMap(JjjkDiseaseTreat::getDisLibId, JjjkDiseaseTreat::getTreatAnaytxt));

        QueryWrapper<JjjkDiseaseNurse> jjjkDiseaseNurseQe = new QueryWrapper<>();
        jjjkDiseaseNurseQe.select("dis_lib_id", "nurse_anaytxt");
        Map<Long, String> jjjkDiseaseNurseMap = jjjkDiseaseNurseFacade.list(jjjkDiseaseNurseQe)
                .stream().collect(Collectors.toMap(JjjkDiseaseNurse::getDisLibId, JjjkDiseaseNurse::getNurseAnaytxt));

        jjjkDiseaseLibList.forEach(jjjkDiseaseLib -> {
            List<String> deptNames = disIdDeptNamesMap.get(jjjkDiseaseLib.getId());
            if (ListUtil.isEmpty(deptNames)) {
                deptNames = Lists.newArrayList();
                deptNames.add("无归属");
            }
            deptNames.forEach(deptName -> {
                String path = "F:\\久久健康网\\疾病\\" + deptName + "\\" + jjjkDiseaseLib.getDisName();
                File file = new File(path);
                if (!file.exists()) {
                    file.mkdirs();
                }
                FileUtil.fileWrite(path, "简介", jjjkDiseaseSynopsisMap.get(jjjkDiseaseLib.getId()));
                FileUtil.fileWrite(path, "病因", jjjkDiseaseEtiologyMap.get(jjjkDiseaseLib.getId()));
                FileUtil.fileWrite(path, "预防", jjjkDiseasePreventMap.get(jjjkDiseaseLib.getId()));
                FileUtil.fileWrite(path, "并发症", jjjkDiseaseComplicationMap.get(jjjkDiseaseLib.getId()));
                FileUtil.fileWrite(path, "症状", jjjkDiseaseSymptomMap.get(jjjkDiseaseLib.getId()));
                FileUtil.fileWrite(path, "检查", jjjkDiseaseExamineMap.get(jjjkDiseaseLib.getId()));
                FileUtil.fileWrite(path, "诊断鉴别", jjjkDiseaseDiscernMap.get(jjjkDiseaseLib.getId()));
                FileUtil.fileWrite(path, "治疗", jjjkDiseaseTreatMap.get(jjjkDiseaseLib.getId()));
                FileUtil.fileWrite(path, "护理", jjjkDiseaseNurseMap.get(jjjkDiseaseLib.getId()));
            });
        });
    }

}