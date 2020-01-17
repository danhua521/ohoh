package com.nuena.bkmy.facade;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.nuena.bkmy.entity.ExamineInfo;
import com.nuena.bkmy.service.impl.ExamineInfoServiceImpl;
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
 * @time: 2020/1/15 15:54
 */
@Component
public class ExamineInfoFacade extends ExamineInfoServiceImpl {

    @Autowired
    @Qualifier("examineInfoServiceImpl")
    private ExamineInfoServiceImpl examineInfoService;

    /**
     * 初始化，插入检查信息
     * 1.从检查首页拉取检查；2.和数据库现存检查比对，找出新增的检查
     */
    @Transactional
    public void initData() {
        List<ExamineInfo> examineInfoList = getExaFromJcsy();
        examineInfoList = filterExistExa(examineInfoList);
        if (ListUtil.isEmpty(examineInfoList)) {
            return;
        }
        examineInfoService.saveBatch(examineInfoList);
    }

    /**
     * 从检查的检查首页中抓取检查信息
     *
     * @return
     */
    private List<ExamineInfo> getExaFromJcsy() {
        String html = HttpTool.post("https://www.baikemy.com/disease/list/0/0?diseaseContentType=C");
        if (StringUtil.isBlank(html)) {
            return null;
        }

        List<ExamineInfo> examineInfoList = Lists.newArrayList();
        Date now = new Date();
        Document doc = Jsoup.parse(html);
        Element typeInfoElement = doc.getElementsByClass("typeInfo").last();
        Elements typeInfoLiElements = typeInfoElement.getElementsByClass("typeInfo_Li");
        typeInfoLiElements.forEach(typeInfoLiElement -> {
            String exaName = typeInfoLiElement.text();
            if (!exaName.equals("更多")) {
                String href = typeInfoLiElement.select("a").attr("href");
                String exaId = href.substring(href.lastIndexOf("/") + 1);
                ExamineInfo examineInfo = new ExamineInfo();
                examineInfo.setExaId(exaId);
                examineInfo.setExaName(exaName);
                examineInfo.setExaUrl("https://www.baikemy.com" + href);
                examineInfo.setCreateTime(now);
                examineInfoList.add(examineInfo);
            }
        });
        return examineInfoList;
    }

    /**
     * 过滤掉数据库已经存在的检查
     *
     * @param examineInfoList
     * @return
     */
    private List<ExamineInfo> filterExistExa(List<ExamineInfo> examineInfoList) {
        List<ExamineInfo> retList = Lists.newArrayList();

        QueryWrapper<ExamineInfo> examineInfoQe = new QueryWrapper<>();
        examineInfoQe.select("exa_id");
        List<String> exaIdList = list(examineInfoQe).stream().map(i -> i.getExaId()).collect(Collectors.toList());

        examineInfoList.forEach(i -> {
            if (!exaIdList.contains(i.getExaId())) {
                retList.add(i);
            }
        });

        return retList;
    }

    /**
     * 获取未拉取html的检查
     *
     * @return
     */
    public List<ExamineInfo> getNullHtmlExa() {
        QueryWrapper<ExamineInfo> examineInfoQe = new QueryWrapper<>();
        examineInfoQe.isNull("remark");
        examineInfoQe.select("id", "exa_id", "exa_name", "exa_url");
        return list(examineInfoQe);
    }

}