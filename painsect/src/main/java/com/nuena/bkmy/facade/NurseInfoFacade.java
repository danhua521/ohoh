package com.nuena.bkmy.facade;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.nuena.bkmy.entity.NurseInfo;
import com.nuena.bkmy.service.impl.NurseInfoServiceImpl;
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
 * @time: 2020/1/16 13:42
 */
@Component
public class NurseInfoFacade extends NurseInfoServiceImpl {

    @Autowired
    @Qualifier("nurseInfoServiceImpl")
    private NurseInfoServiceImpl nurseInfoService;

    /**
     * 初始化，插入护理信息
     * 1.从护理首页拉取护理；2.和数据库现存护理比对，找出新增的护理
     */
    @Transactional
    public void initData() {
        List<NurseInfo> nurseInfoList = getNurFromHlsy();
        nurseInfoList = filterExistNur(nurseInfoList);
        if (ListUtil.isEmpty(nurseInfoList)) {
            return;
        }
        nurseInfoService.saveBatch(nurseInfoList);
    }

    /**
     * 从护理的护理首页中抓取护理信息
     *
     * @return
     */
    private List<NurseInfo> getNurFromHlsy() {
        String html = HttpTool.post("https://www.baikemy.com/disease/list/0/0?diseaseContentType=N");
        if (StringUtil.isBlank(html)) {
            return null;
        }

        List<NurseInfo> nurseInfoList = Lists.newArrayList();
        Date now = new Date();
        Document doc = Jsoup.parse(html);
        Element typeInfoElement = doc.getElementsByClass("typeInfo").last();
        Elements typeInfoLiElements = typeInfoElement.getElementsByClass("typeInfo_Li");
        typeInfoLiElements.forEach(typeInfoLiElement -> {
            String nurName = typeInfoLiElement.text();
            if (!nurName.equals("更多")) {
                String href = typeInfoLiElement.select("a").attr("href");
                String nurId = href.substring(href.lastIndexOf("/") + 1);
                NurseInfo nurseInfo = new NurseInfo();
                nurseInfo.setNurId(nurId);
                nurseInfo.setNurName(nurName);
                nurseInfo.setNurUrl("https://www.baikemy.com" + href);
                nurseInfo.setCreateTime(now);
                nurseInfoList.add(nurseInfo);
            }
        });
        return nurseInfoList;
    }

    /**
     * 过滤掉数据库已经存在的护理
     *
     * @param nurseInfoList
     * @return
     */
    private List<NurseInfo> filterExistNur(List<NurseInfo> nurseInfoList) {
        List<NurseInfo> retList = Lists.newArrayList();

        QueryWrapper<NurseInfo> nurseInfoQe = new QueryWrapper<>();
        nurseInfoQe.select("nur_id");
        List<String> nurIdList = list(nurseInfoQe).stream().map(i -> i.getNurId()).collect(Collectors.toList());

        nurseInfoList.forEach(i -> {
            if (!nurIdList.contains(i.getNurId())) {
                retList.add(i);
            }
        });

        return retList;
    }

    /**
     * 获取未拉取html的护理
     *
     * @return
     */
    public List<NurseInfo> getNullHtmlNur() {
        QueryWrapper<NurseInfo> nurseInfoQe = new QueryWrapper<>();
        nurseInfoQe.isNull("remark");
        nurseInfoQe.select("id", "nur_id", "nur_name", "nur_url");
        return list(nurseInfoQe);
    }

}