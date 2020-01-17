package com.nuena.bkmy.facade;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.nuena.bkmy.entity.TreatInfo;
import com.nuena.bkmy.service.impl.TreatInfoServiceImpl;
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
 * @time: 2020/1/16 13:45
 */
@Component
public class TreatInfoFacade extends TreatInfoServiceImpl {

    @Autowired
    @Qualifier("treatInfoServiceImpl")
    private TreatInfoServiceImpl treatInfoService;

    /**
     * 初始化，插入治疗信息
     * 1.从治疗首页拉取治疗；2.和数据库现存治疗比对，找出新增的治疗
     */
    @Transactional
    public void initData() {
        List<TreatInfo> treatInfoList = getTrtFromZlsy();
        treatInfoList = filterExistTrt(treatInfoList);
        if (ListUtil.isEmpty(treatInfoList)) {
            return;
        }
        treatInfoService.saveBatch(treatInfoList);
    }

    /**
     * 从治疗的治疗首页中抓取治疗信息
     *
     * @return
     */
    private List<TreatInfo> getTrtFromZlsy() {
        String html = HttpTool.post("https://www.baikemy.com/disease/list/0/0?diseaseContentType=E");
        if (StringUtil.isBlank(html)) {
            return null;
        }

        List<TreatInfo> treatInfoList = Lists.newArrayList();
        Date now = new Date();
        Document doc = Jsoup.parse(html);
        Element typeInfoElement = doc.getElementsByClass("typeInfo").last();
        Elements typeInfoLiElements = typeInfoElement.getElementsByClass("typeInfo_Li");
        typeInfoLiElements.forEach(typeInfoLiElement -> {
            String trtName = typeInfoLiElement.text();
            if (!trtName.equals("更多")) {
                String href = typeInfoLiElement.select("a").attr("href");
                String trtId = href.substring(href.lastIndexOf("/") + 1);
                TreatInfo treatInfo = new TreatInfo();
                treatInfo.setTrtId(trtId);
                treatInfo.setTrtName(trtName);
                treatInfo.setTrtUrl("https://www.baikemy.com" + href);
                treatInfo.setCreateTime(now);
                treatInfoList.add(treatInfo);
            }
        });
        return treatInfoList;
    }

    /**
     * 过滤掉数据库已经存在的治疗
     *
     * @param treatInfoList
     * @return
     */
    private List<TreatInfo> filterExistTrt(List<TreatInfo> treatInfoList) {
        List<TreatInfo> retList = Lists.newArrayList();

        QueryWrapper<TreatInfo> treatInfoQe = new QueryWrapper<>();
        treatInfoQe.select("trt_id");
        List<String> trtIdList = list(treatInfoQe).stream().map(i -> i.getTrtId()).collect(Collectors.toList());

        treatInfoList.forEach(i -> {
            if (!trtIdList.contains(i.getTrtId())) {
                retList.add(i);
            }
        });

        return retList;
    }

    /**
     * 获取未拉取html的治疗
     *
     * @return
     */
    public List<TreatInfo> getNullHtmlTrt() {
        QueryWrapper<TreatInfo> treatInfoQe = new QueryWrapper<>();
        treatInfoQe.isNull("remark");
        treatInfoQe.select("id", "trt_id", "trt_name", "trt_url");
        return list(treatInfoQe);
    }

}