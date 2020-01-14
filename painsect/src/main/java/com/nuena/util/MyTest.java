package com.nuena.util;

import com.google.common.collect.Lists;
import com.nuena.bkmy.entity.DiseaseInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @author: rengb
 * @time: 2020/1/14 13:47
 */
public class MyTest {

    public static void main(String[] args) throws Exception {

        //        String html = HttpTool.get("https://www.baikemy.com/disease/list/0/0?diseaseContentType=A", "utf-8",
        //                "49.77.210.81", 9999);
        ////        String html = HttpTool.post("https://www.baikemy.com/disease/detail/1012");
        //        Document doc = Jsoup.parse(html);
        //        System.out.println(html);
        //        System.out.println(doc.getElementsByClass("detail_name").text());

        jbymsjpq();

    }

    /**
     * 疾病页面疾病数据爬取
     */
    public static void jbymsjpq() {
        Date now = new Date();
        List<DiseaseInfo> diseaseInfoSaveList = Lists.newArrayList();
        String html = FileUtil.fileRead("C:\\Users\\Administrator\\Desktop\\百科名医网\\疾病页面.txt");
        Document doc = Jsoup.parse(html);
        Element typeInfoElement = doc.getElementsByClass("typeInfo").last();
        Elements typeInfoLiElements = typeInfoElement.getElementsByClass("typeInfo_Li");
        typeInfoLiElements.forEach(typeInfoLiElement -> {
            String disName = typeInfoLiElement.text();
            if (!disName.equals("更多")) {
                String href = typeInfoLiElement.select("a").attr("href");
                String disId = href.substring(16);
                System.out.println(disId+"-------"+disName);
                DiseaseInfo diseaseInfo = new DiseaseInfo();
                diseaseInfo.setDisId(disId);
                diseaseInfo.setDisName(disName);
                diseaseInfo.setCreateTime(now);
                diseaseInfoSaveList.add(diseaseInfo);
            }
        });
        System.out.println(diseaseInfoSaveList.size());
    }


}
