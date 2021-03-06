package com.nuena.bkmy.facade;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.nuena.bkmy.entity.DiseaseInfo;
import com.nuena.bkmy.service.impl.DiseaseInfoServiceImpl;
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
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @Description:
 * @author: rengb
 * @time: 2020/1/14 15:48
 */
@Component
public class DiseaseInfoFacade extends DiseaseInfoServiceImpl {

    @Autowired
    @Qualifier("diseaseInfoServiceImpl")
    private DiseaseInfoServiceImpl diseaseInfoService;

    /**
     * 初始化，插入疾病信息
     * 1.从首页拉取疾病；2.从疾病首页拉取疾病；3.前2部分合并去重；4.和数据库现存疾病比对，找出新增的疾病
     */
    @Transactional
    public void initData() {
        List<DiseaseInfo> diseaseInfoList = Lists.newArrayList();
        diseaseInfoList.addAll(getDisFromJbsy());

        List<String> disIdList = diseaseInfoList.stream().map(i -> i.getDisId()).collect(Collectors.toList());
        for (DiseaseInfo i : getDisFromJsonTxt()) {
            if (!disIdList.contains(i.getDisId())) {
                diseaseInfoList.add(i);
            }
        }

        diseaseInfoList = filterExistDis(diseaseInfoList.stream().distinct().collect(Collectors.toList()));
        if (ListUtil.isEmpty(diseaseInfoList)) {
            return;
        }
        Date now = new Date();
        diseaseInfoList.forEach(i -> {
            i.setCreateTime(now);
        });
        diseaseInfoService.saveBatch(diseaseInfoList);
    }

    /**
     * 从网站首页中抓取疾病信息
     *
     * @return
     */
    private List<DiseaseInfo> getDisFromJsonTxt() {
        Random rd = new Random();
        try {
            Thread.sleep(1000 * rd.nextInt(4) + 2000);
        } catch (Exception e) {
        }

        List<DiseaseInfo> diseaseInfoList = Lists.newArrayList();
        String syHtml = HttpTool.post("https://www.baikemy.com/");
        Document doc = Jsoup.parse(syHtml);
        Elements ets = doc.getElementsByClass("index_top_left_content").first().select("a");
        ets.forEach(et -> {
            try {
                Thread.sleep(1000 * rd.nextInt(4) + 2000);
            } catch (Exception e) {
            }
            String href = et.attr("href");
            String deptId = href.substring(14, href.indexOf("?") - 2);
            String jsonContent = HttpTool.post("https://www.baikemy.com/disease/high/flow/list/" + deptId, "utf-8");
            if (StringUtil.isNotBlank(jsonContent)) {
                Map<String, Object> msgMap = (Map) JSON.parse(jsonContent);
                List<Map<String, Object>> mapList = (List<Map<String, Object>>) msgMap.get("data");
                mapList.forEach(map -> {
                    DiseaseInfo diseaseInfo = new DiseaseInfo();
                    diseaseInfo.setDisId(map.get("id").toString());
                    diseaseInfo.setDisName(map.get("name").toString());
                    diseaseInfo.setDisUrl("https://www.baikemy.com/disease/detail/" + diseaseInfo.getDisId());
                    diseaseInfoList.add(diseaseInfo);
                });
            }
        });
        return diseaseInfoList;
    }

    /**
     * 从疾病的疾病首页中抓取疾病信息
     *
     * @return
     */
    private List<DiseaseInfo> getDisFromJbsy() {
        List<DiseaseInfo> diseaseInfoList = Lists.newArrayList();
        String html = HttpTool.post("https://www.baikemy.com/disease/list/0/0?diseaseContentType=A");
        if (StringUtil.isBlank(html)) {
            return diseaseInfoList;
        }

        Document doc = Jsoup.parse(html);
        Element typeInfoElement = doc.getElementsByClass("typeInfo").last();
        Elements typeInfoLiElements = typeInfoElement.getElementsByClass("typeInfo_Li");
        typeInfoLiElements.forEach(typeInfoLiElement -> {
            String disName = typeInfoLiElement.text();
            if (!disName.equals("更多")) {
                String href = typeInfoLiElement.select("a").attr("href");
                String disId = href.substring(href.lastIndexOf("/") + 1);
                DiseaseInfo diseaseInfo = new DiseaseInfo();
                diseaseInfo.setDisId(disId);
                diseaseInfo.setDisName(disName);
                diseaseInfo.setDisUrl("https://www.baikemy.com" + href);
                diseaseInfoList.add(diseaseInfo);
            }
        });
        return diseaseInfoList;
    }

    /**
     * 过滤掉数据库已经存在的疾病
     *
     * @param diseaseInfoList
     * @return
     */
    private List<DiseaseInfo> filterExistDis(List<DiseaseInfo> diseaseInfoList) {
        List<DiseaseInfo> retList = Lists.newArrayList();

        QueryWrapper<DiseaseInfo> diseaseInfoQe = new QueryWrapper<>();
        diseaseInfoQe.select("dis_id");
        List<String> disIdList = list(diseaseInfoQe).stream().map(i -> i.getDisId()).collect(Collectors.toList());

        diseaseInfoList.forEach(i -> {
            if (!disIdList.contains(i.getDisId())) {
                retList.add(i);
            }
        });

        return retList;
    }

    /**
     * 获取未拉取html的疾病
     *
     * @return
     */
    public List<DiseaseInfo> getNullHtmlDis() {
        QueryWrapper<DiseaseInfo> diseaseInfoQe = new QueryWrapper<>();
        diseaseInfoQe.isNull("remark");
        diseaseInfoQe.select("id", "dis_id", "dis_name", "dis_url");
        return list(diseaseInfoQe);
    }

}