package com.nuena.bkmy.facade;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.nuena.bkmy.entity.SymptomInfo;
import com.nuena.bkmy.service.impl.SymptomInfoServiceImpl;
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
import java.util.stream.Collectors;

/**
 * @Description:
 * @author: rengb
 * @time: 2020/1/15 14:49
 */
@Component
public class SymptomInfoFacade extends SymptomInfoServiceImpl {

    @Autowired
    @Qualifier("symptomInfoServiceImpl")
    private SymptomInfoServiceImpl symptomInfoService;

    /**
     * 初始化，插入症状信息
     * 1.从症状首页拉取症状；2.和数据库现存症状比对，找出新增的症状
     */
    @Transactional
    public void initData() {
        List<SymptomInfo> symptomInfoList = getSymFromZzsy();
        symptomInfoList = filterExistSym(symptomInfoList);
        if (ListUtil.isEmpty(symptomInfoList)) {
            return;
        }
        symptomInfoService.saveBatch(symptomInfoList);
    }

    /**
     * 从症状的症状首页中抓取症状信息
     *
     * @return
     */
    private List<SymptomInfo> getSymFromZzsy() {
        String html = HttpTool.post("https://www.baikemy.com/disease/list/0/0?diseaseContentType=B");
        if (StringUtil.isBlank(html)) {
            return null;
        }

        List<SymptomInfo> symptomInfoList = Lists.newArrayList();
        Date now = new Date();
        Document doc = Jsoup.parse(html);
        Element typeInfoElement = doc.getElementsByClass("typeInfo").last();
        Elements typeInfoLiElements = typeInfoElement.getElementsByClass("typeInfo_Li");
        typeInfoLiElements.forEach(typeInfoLiElement -> {
            String symName = typeInfoLiElement.text();
            if (!symName.equals("更多")) {
                String href = typeInfoLiElement.select("a").attr("href");
                String symId = href.substring(href.lastIndexOf("/") + 1);
                SymptomInfo symptomInfo = new SymptomInfo();
                symptomInfo.setSymId(symId);
                symptomInfo.setSymName(symName);
                symptomInfo.setSymUrl("https://www.baikemy.com" + href);
                symptomInfo.setCreateTime(now);
                symptomInfoList.add(symptomInfo);
            }
        });
        return symptomInfoList;
    }

    /**
     * 过滤掉数据库已经存在的症状
     *
     * @param symptomInfoList
     * @return
     */
    private List<SymptomInfo> filterExistSym(List<SymptomInfo> symptomInfoList) {
        List<SymptomInfo> retList = Lists.newArrayList();

        QueryWrapper<SymptomInfo> symptomInfoQe = new QueryWrapper<>();
        symptomInfoQe.select("sym_id");
        List<String> symIdList = list(symptomInfoQe).stream().map(i -> i.getSymId()).collect(Collectors.toList());

        symptomInfoList.forEach(i -> {
            if (!symIdList.contains(i.getSymId())) {
                retList.add(i);
            }
        });

        return retList;
    }

    /**
     * 获取未拉取html的症状
     *
     * @return
     */
    public List<SymptomInfo> getNullHtmlSym() {
        QueryWrapper<SymptomInfo> symptomInfoQe = new QueryWrapper<>();
        symptomInfoQe.isNull("remark");
        symptomInfoQe.select("id", "sym_id", "sym_name", "sym_url");
        return list(symptomInfoQe);
    }

}