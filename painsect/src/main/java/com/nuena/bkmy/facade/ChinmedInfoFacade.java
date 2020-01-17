package com.nuena.bkmy.facade;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.nuena.bkmy.entity.ChinmedInfo;
import com.nuena.bkmy.service.impl.ChinmedInfoServiceImpl;
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
 * @time: 2020/1/17 16:00
 */
@Component
public class ChinmedInfoFacade extends ChinmedInfoServiceImpl {

    @Autowired
    @Qualifier("chinmedInfoServiceImpl")
    private ChinmedInfoServiceImpl chinmedInfoService;

    /**
     * 初始化，插入中医信息
     * 1.从中医首页拉取中医；2.和数据库现存中医比对，找出新增的中医
     */
    @Transactional
    public void initData() {
        List<ChinmedInfo> chinmedInfoList = getChdFromZysy();
        chinmedInfoList = filterExistChd(chinmedInfoList);
        if (ListUtil.isEmpty(chinmedInfoList)) {
            return;
        }
        chinmedInfoService.saveBatch(chinmedInfoList);
    }

    /**
     * 从中医的中医首页中抓取中医信息
     *
     * @return
     */
    private List<ChinmedInfo> getChdFromZysy() {
        String html = HttpTool.post("https://www.baikemy.com/disease/list/0/0?diseaseContentType=I");
        if (StringUtil.isBlank(html)) {
            return null;
        }

        List<ChinmedInfo> chinmedInfoList = Lists.newArrayList();
        Date now = new Date();
        Document doc = Jsoup.parse(html);
        Element typeInfoElement = doc.getElementsByClass("typeInfo").last();
        Elements typeInfoLiElements = typeInfoElement.getElementsByClass("typeInfo_Li");
        typeInfoLiElements.forEach(typeInfoLiElement -> {
            String chdName = typeInfoLiElement.text();
            if (!chdName.equals("更多")) {
                String href = typeInfoLiElement.select("a").attr("href");
                String chdId = href.substring(href.lastIndexOf("/") + 1);
                ChinmedInfo chinmedInfo = new ChinmedInfo();
                chinmedInfo.setChdId(chdId);
                chinmedInfo.setChdName(chdName);
                chinmedInfo.setChdUrl("https://www.baikemy.com" + href);
                chinmedInfo.setCreateTime(now);
                chinmedInfoList.add(chinmedInfo);
            }
        });
        return chinmedInfoList;
    }

    /**
     * 过滤掉数据库已经存在的中医
     *
     * @param chinmedInfoList
     * @return
     */
    private List<ChinmedInfo> filterExistChd(List<ChinmedInfo> chinmedInfoList) {
        List<ChinmedInfo> retList = Lists.newArrayList();

        QueryWrapper<ChinmedInfo> chinmedInfoQe = new QueryWrapper<>();
        chinmedInfoQe.select("chd_id");
        List<String> chdIdList = list(chinmedInfoQe).stream().map(i -> i.getChdId()).collect(Collectors.toList());

        chinmedInfoList.forEach(i -> {
            if (!chdIdList.contains(i.getChdId())) {
                retList.add(i);
            }
        });

        return retList;
    }

    /**
     * 获取未拉取html的中医
     *
     * @return
     */
    public List<ChinmedInfo> getNullHtmlChd() {
        QueryWrapper<ChinmedInfo> chinmedInfoQe = new QueryWrapper<>();
        chinmedInfoQe.isNull("remark");
        chinmedInfoQe.select("id", "chd_id", "chd_name", "chd_url");
        return list(chinmedInfoQe);
    }

}