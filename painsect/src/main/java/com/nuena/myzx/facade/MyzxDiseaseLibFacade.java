package com.nuena.myzx.facade;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.nuena.myzx.entity.MyzxDiseaseLib;
import com.nuena.myzx.service.impl.MyzxDiseaseLibServiceImpl;
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

import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @author: rengb
 * @time: 2020/2/10 21:56
 */
@Component
public class MyzxDiseaseLibFacade extends MyzxDiseaseLibServiceImpl {

    @Autowired
    @Qualifier("myzxDiseaseLibServiceImpl")
    private MyzxDiseaseLibServiceImpl myzxDiseaseLibService;

    @Transactional
    public void initDisData() {
        List<MyzxDiseaseLib> diseaseLibList = Lists.newArrayList();
        for (int i = 1; i <= 100; i++) {
            diseaseLibList.addAll(getDiseases(i));
        }

        Date now = DateUtil.now();
        diseaseLibList.forEach(i -> {
            i.setCreateTime(now);
        });

        myzxDiseaseLibService.saveBatch(diseaseLibList);
    }

    /**
     * 根据页码，获取疾病列表(包含id(其实是首字母) 名称 疾病url)
     *
     * @param yeshu
     * @return
     */
    private List<MyzxDiseaseLib> getDiseases(int yeshu) {
        try {
            Thread.sleep(500);
        } catch (Exception e) {
        }
        List<MyzxDiseaseLib> retList = Lists.newArrayList();
        String html = HttpTool.get("http://www.myzx.cn/jibing/list-p" + yeshu + ".html");
        if (StringUtil.isBlank(html)) {
            return retList;
        }

        Document doc = Jsoup.parse(html);
        Elements aElements = doc.getElementsByClass("h3").select("a");
        aElements.forEach(i -> {
            MyzxDiseaseLib diseaseLib = new MyzxDiseaseLib();
            String href = i.attr("href");
            String disSzmsx = href.substring(8, href.length() - 5);
            diseaseLib.setDisId(disSzmsx);
            diseaseLib.setDisName(i.text());
            diseaseLib.setDisUrl("http://www.myzx.cn/jibing/" + disSzmsx + ".html");
            retList.add(diseaseLib);
        });
        return retList;
    }

    /**
     * 获取未下载html的疾病列表
     *
     * @return
     */
    public List<MyzxDiseaseLib> getNoLoadHtmlDiseases() {
        QueryWrapper<MyzxDiseaseLib> diseaseLibQe = new QueryWrapper<>();
        diseaseLibQe.eq("is_htmls_load", 0);
        return list(diseaseLibQe);
    }

    /**
     * 下载疾病的html
     *
     * @param diseaseLib
     */
    @Transactional
    public void loadHtml(MyzxDiseaseLib diseaseLib) {
        try {
            Thread.sleep(500);
        } catch (Exception e) {
        }
        String html = HttpTool.post(diseaseLib.getDisUrl());
        Document doc = Jsoup.parse(html);
        Element mediaListElement = doc.getElementsByClass("media_list").first();
        diseaseLib.setDisHtml(EnDecodeUtil.encode(mediaListElement.outerHtml()));
        diseaseLib.setIsHtmlsLoad(1);
        diseaseLib.setModifyTime(DateUtil.now());
        updateById(diseaseLib);
    }

    @Transactional
    public void analysis() {
        QueryWrapper<MyzxDiseaseLib> myzxDiseaseLibQe = new QueryWrapper<>();
        myzxDiseaseLibQe.eq("is_htmls_load", 1);
        List<MyzxDiseaseLib> myzxDiseaseLibList = list(myzxDiseaseLibQe);
        List<MyzxDiseaseLib> myzxDiseaseLibUptList = Lists.newArrayList();
        myzxDiseaseLibList.forEach(myzxDiseaseLib -> {
            myzxDiseaseLibUptList.add(analyDis(myzxDiseaseLib));
        });
        if (ListUtil.isNotEmpty(myzxDiseaseLibUptList)) {
            myzxDiseaseLibService.updateBatchById(myzxDiseaseLibUptList);
        }
    }

    /**
     * 解析单个疾病html
     *
     * @param myzxDiseaseLib
     * @return
     */
    private MyzxDiseaseLib analyDis(MyzxDiseaseLib myzxDiseaseLib) {
        MyzxDiseaseLib ret = new MyzxDiseaseLib();
        String mediaListHtml = EnDecodeUtil.decode(myzxDiseaseLib.getDisHtml());
        mediaListHtml = mediaListHtml.replaceAll("<br>", "@ab98cdef");

        Element mediaListElement = Jsoup.parse(mediaListHtml);
        Element cardListElement = mediaListElement.getElementsByClass("card_list").first();
        String cardListTxt = JsoupUtil.clean(cardListElement.outerHtml());
        cardListTxt = cardListTxt
                .replace("展开全部疾病", "")
                .replace("展开全部症状", "")
                .replace("全部收起", "")
                .replaceAll("@ab98cdef", "\r\n");

        StringBuffer sbf = new StringBuffer();
        String title, content, bigContent;
        Elements desListElements = mediaListElement.getElementsByClass("des_list");
        for (Element desListElement : desListElements) {
            title = desListElement.selectFirst("dt").text();
            content = JsoupUtil.clean(desListElement.selectFirst("dd").outerHtml())
                    .replaceAll("@ab98cdef", "\r\n")
                    .replaceAll("&nbsp;", "");
            bigContent = JsoupUtil.clean(desListElement.outerHtml())
                    .replaceAll("@ab98cdef", "\r\n")
                    .replaceAll("&nbsp;", "");
            if (title.equals("疾病介绍")) {
                content = content + "\r\n" + cardListTxt;
                bigContent = bigContent + "\r\n" + cardListTxt;
                ret.setSynopsis(content);
            } else if (title.equals("病因")) {
                ret.setEtiology(content);
            } else if (title.equals("症状")) {
                ret.setSymptom(content);
            } else if (title.equals("检查")) {
                ret.setExamine(content);
            } else if (title.equals("鉴别")) {
                ret.setDiscern(content);
            } else if (title.equals("并发症")) {
                ret.setComplication(content);
            } else if (title.equals("预防")) {
                ret.setPrevent(content);
            } else if (title.equals("治疗")) {
                ret.setTreat(content);
            }
            sbf.append(bigContent + "\r\n");
        }
        ret.setId(myzxDiseaseLib.getId());
        ret.setDisAnayContent(sbf.toString());
        ret.setIsHtmlsAnay(1);
        ret.setModifyTime(DateUtil.now());
        return ret;
    }

    /**
     * 文件生成
     */
    public void fileGener() {
        QueryWrapper<MyzxDiseaseLib> myzxDiseaseLibQe = new QueryWrapper<>();
        myzxDiseaseLibQe.eq("is_htmls_anay", 1);
        List<MyzxDiseaseLib> myzxDiseaseLibList = list(myzxDiseaseLibQe);
        String path = "F:\\名医在线\\";
        myzxDiseaseLibList.forEach(myzxDiseaseLib -> {
            FileUtil.fileWrite(path, myzxDiseaseLib.getDisName(), myzxDiseaseLib.getDisAnayContent());
        });
    }

}
