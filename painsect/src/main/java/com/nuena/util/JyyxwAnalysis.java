package com.nuena.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;
import java.util.Map;

/**
 * @Description: http://www.labmed.cn/
 * @author: rengb
 * @time: 2020/4/24 14:16
 */
public class JyyxwAnalysis {

    /**
     * 通过http://www.labmed.cn/search/index.php?keyword=&m=content&c=search&a=mysearch&catid=9&dosubmit=1可以
     * 拉到所有的  检验项目临床意义
     *
     * @param args
     */
    public static void main(String[] args) {
        String html = FileUtil.fileRead("C:\\Users\\RGB\\Desktop\\调试\\tt3.txt");
        Document doc = Jsoup.parse(html);
        List<Map<String, String>> list = Lists.newArrayList();
        doc.getElementsByClass("show").forEach(element -> {
            list.add(createMap(JsoupUtil.clean(element.getElementsByClass("con").first().outerHtml())));
        });
        String[] headerNames = { "项目名称", "英文缩写", "所属类别", "参考范围", "临床意义", "影响因素", "相关仪器试剂" };
        String[] dataMapKeys = { "项目名称", "英文缩写", "所属类别", "参考范围", "临床意义", "影响因素", "相关仪器试剂" };
        ExcelUtil.createExcel("C:\\Users\\RGB\\Desktop\\调试", "检验项目临床意义数据", "检验项目临床意义数据", headerNames, dataMapKeys, list);
    }

    private static Map<String, String> createMap(String content) {
        int t1 = content.indexOf("项目名称：");
        int t2 = content.indexOf("英文缩写：");
        int t3 = content.indexOf("所属类别：");
        int t4 = content.indexOf("参考范围：");
        int t5 = content.indexOf("临床意义：");
        int t6 = content.indexOf("影响因素：");
        int t7 = content.indexOf("相关仪器试剂：");

        Map<String, String> map = Maps.newHashMap();
        if (t1 + 5 == t2) {
            map.put("项目名称", "");
        } else {
            map.put("项目名称", content.substring(t1 + 5, t2).trim());
        }

        if (t2 + 5 == t3) {
            map.put("英文缩写", "");
        } else {
            map.put("英文缩写", content.substring(t2 + 5, t3).trim());
        }

        if (t3 + 5 == t4) {
            map.put("所属类别", "");
        } else {
            map.put("所属类别", content.substring(t3 + 5, t4).trim());
        }

        if (t4 + 5 == t5) {
            map.put("参考范围", "");
        } else {
            map.put("参考范围", content.substring(t4 + 5, t5).trim());
        }

        if (t5 + 5 == t6) {
            map.put("临床意义", "");
        } else {
            map.put("临床意义", content.substring(t5 + 5, t6).trim());
        }

        if (t6 + 5 == t7) {
            map.put("影响因素", "");
        } else {
            map.put("影响因素", content.substring(t6 + 5, t7).trim());
        }

        if (t7 + 7 > content.length() - 1) {
            map.put("相关仪器试剂", "");
        } else {
            map.put("相关仪器试剂", content.substring(t7 + 7).trim());
        }

        return map;
    }

}