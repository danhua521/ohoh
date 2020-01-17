package com.nuena.bkmy.facade;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.nuena.bkmy.entity.PreventInfo;
import com.nuena.bkmy.service.impl.PreventInfoServiceImpl;
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
 * @time: 2020/1/16 13:43
 */
@Component
public class PreventInfoFacade extends PreventInfoServiceImpl {

    @Autowired
    @Qualifier("preventInfoServiceImpl")
    private PreventInfoServiceImpl preventInfoService;

    /**
     * 初始化，插入预防信息
     * 1.从预防首页拉取预防；2.和数据库现存预防比对，找出新增的预防
     */
    @Transactional
    public void initData() {
        List<PreventInfo> preventInfoList = getPvtFromYfsy();
        preventInfoList = filterExistPvt(preventInfoList);
        if (ListUtil.isEmpty(preventInfoList)) {
            return;
        }
        preventInfoService.saveBatch(preventInfoList);
    }

    /**
     * 从预防的预防首页中抓取预防信息
     *
     * @return
     */
    private List<PreventInfo> getPvtFromYfsy() {
        String html = HttpTool.post("https://www.baikemy.com/disease/list/0/0?diseaseContentType=K");
        if (StringUtil.isBlank(html)) {
            return null;
        }

        List<PreventInfo> preventInfoList = Lists.newArrayList();
        Date now = new Date();
        Document doc = Jsoup.parse(html);
        Element typeInfoElement = doc.getElementsByClass("typeInfo").last();
        Elements typeInfoLiElements = typeInfoElement.getElementsByClass("typeInfo_Li");
        typeInfoLiElements.forEach(typeInfoLiElement -> {
            String pvtName = typeInfoLiElement.text();
            if (!pvtName.equals("更多")) {
                String href = typeInfoLiElement.select("a").attr("href");
                String pvtId = href.substring(href.lastIndexOf("/") + 1);
                PreventInfo preventInfo = new PreventInfo();
                preventInfo.setPvtId(pvtId);
                preventInfo.setPvtName(pvtName);
                preventInfo.setPvtUrl("https://www.baikemy.com" + href);
                preventInfo.setCreateTime(now);
                preventInfoList.add(preventInfo);
            }
        });
        return preventInfoList;
    }

    /**
     * 过滤掉数据库已经存在的预防
     *
     * @param preventInfoList
     * @return
     */
    private List<PreventInfo> filterExistPvt(List<PreventInfo> preventInfoList) {
        List<PreventInfo> retList = Lists.newArrayList();

        QueryWrapper<PreventInfo> preventInfoQe = new QueryWrapper<>();
        preventInfoQe.select("pvt_id");
        List<String> pvtIdList = list(preventInfoQe).stream().map(i -> i.getPvtId()).collect(Collectors.toList());

        preventInfoList.forEach(i -> {
            if (!pvtIdList.contains(i.getPvtId())) {
                retList.add(i);
            }
        });

        return retList;
    }

    /**
     * 获取未拉取html的预防
     *
     * @return
     */
    public List<PreventInfo> getNullHtmlPvt() {
        QueryWrapper<PreventInfo> preventInfoQe = new QueryWrapper<>();
        preventInfoQe.isNull("remark");
        preventInfoQe.select("id", "pvt_id", "pvt_name", "pvt_url");
        return list(preventInfoQe);
    }

}