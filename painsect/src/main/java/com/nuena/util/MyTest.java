package com.nuena.util;

import com.google.common.collect.Lists;
import com.nuena.bkmy.entity.DeptInfo;
import com.nuena.bkmy.entity.DiseaseInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @Description:
 * @author: rengb
 * @time: 2020/1/14 13:47
 */
public class MyTest {

    public static void main(String[] args) throws Exception {

//                String html = HttpTool.post("https://www.baikemy.com/disease/high/flow/list/86","utf-8");
//        System.out.println(html);
//        //        String html = HttpTool.post("https://www.baikemy.com/disease/detail/1012");
//                Document doc = Jsoup.parse(html);
//       Elements ets = doc.getElementsByClass("index_top_left_content").first().select("a");
//       ets.forEach(i->{
//           String href = i.attr("href");
//           href= href.substring(14,href.indexOf("?")-2);
//           System.out.println(i.attr("href"));
//           System.out.println(href);
//
//       });
//        Random rd = new Random();
//        System.out.println(rd.nextInt(10));
//        jbymsjpq();
//        System.out.println(html);
//        List<String> deptInfoList = Lists.newArrayList();
//        deptInfoList.add("rgb");
//        deptInfoList.add("yyk");
//        deptInfoList.add(0,"cs");
//
//        System.out.println(deptInfoList);
        String ss = "/disease/detail/850";
        System.out.println(ss.lastIndexOf("/"));
        System.out.println(ss.substring(ss.lastIndexOf("/")+1));

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
