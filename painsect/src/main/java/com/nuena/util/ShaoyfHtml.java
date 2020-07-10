package com.nuena.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author: rengb
 * @time: 2020/7/10 15:07
 */
public class ShaoyfHtml {

    public static void main(String[] args) {
        ShaoyfHtml shaoyfHtml = new ShaoyfHtml();
        String html = FileUtil.fileRead("C:\\Users\\RGB\\Desktop\\邵逸夫html解析\\1469942_38(50129).html");
        System.out.println(GsonUtil.toJson(shaoyfHtml.getPageMap(html)));
    }

    public Map<String, Object> getPageMap(String html) {
        Map<String, Object> ret = null;
        try {
            ret = new HashMap<>();
            Document doc = Jsoup.parse(html);
            ret.put("病案首页", getOtherPageMap(doc));
            ret.put("手术信息", getOperMaps(doc));
            ret.put("诊断信息", getDiagMaps(doc));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    private Map<String, String> getOtherPageMap(Document doc) {
        Elements tableElements = doc.getElementsByTag("table");
        Map<String, String> otherPageMap = new HashMap<>();
        for (int i = 0; i < tableElements.size(); i++) {
            if (i == 13 || i == 20 || i == 4 || i == 14 || i == 15 || i == 12) {
                continue;
            }
            tableElements.get(i).getElementsByAttributeValue("token", "term").forEach(spanElement -> {
                otherPageMap.put(
                        spanElement.attr("Comment"),
                        spanElement.ownText().replaceAll(" ", "").replaceAll("&nbsp;", "")
                );
            });
        }
        otherPageMap.put("联系人关系", otherPageMap.get("关系"));
        otherPageMap.remove("关系");

        tableElements.get(4).getElementsByAttributeValue("token", "term").forEach(spanElement -> {
            String comment = spanElement.attr("Comment");
            String ownTex = spanElement.ownText().replaceAll(" ", "").replaceAll("&nbsp;", "");
            if (comment.equals("新生儿年龄")) {
                comment = comment + spanElement.nextElementSibling().ownText();
            }
            otherPageMap.put(comment, ownTex);
        });

        tableElements.get(12).getElementsByAttributeValue("token", "term").forEach(spanElement -> {
            String comment = spanElement.attr("Comment");
            String ownTex = spanElement.ownText().replaceAll(" ", "").replaceAll("&nbsp;", "");
            if (comment.equals("ICD10")) {
                otherPageMap.put("门急诊诊断编码", ownTex);
            } else {
                otherPageMap.put("门急诊诊断", ownTex);
            }
        });

        tableElements.get(14).getElementsByAttributeValue("token", "term").forEach(spanElement -> {
            String comment = spanElement.attr("Comment");
            String ownTex = spanElement.ownText().replaceAll(" ", "").replaceAll("&nbsp;", "");
            if (comment.equals("ICD10")) {
                otherPageMap.put("损伤中毒因素编码", ownTex);
            } else {
                otherPageMap.put("损伤中毒因素", ownTex);
            }
        });

        tableElements.get(15).getElementsByAttributeValue("token", "term").forEach(spanElement -> {
            String comment = spanElement.attr("Comment");
            String ownTex = spanElement.ownText().replaceAll(" ", "").replaceAll("&nbsp;", "");
            if (comment.equals("ICD10")) {
                otherPageMap.put("病理诊断编码", ownTex);
            } else if (comment.equals("病理号")) {
                otherPageMap.put("病理诊断编号", ownTex);
            } else {
                otherPageMap.put("病理诊断", ownTex);
            }
        });
        return otherPageMap;
    }

    private List<Map<String, String>> getDiagMaps(Document doc) {
        Element tableElement = doc.getElementsByClass("tr1").get(1);
        Elements trElements = tableElement.getElementsByTag("tr");
        List<String> spanTexts = new ArrayList<>();
        for (int i = 1; i < trElements.size() - 1; i++) {
            trElements.get(i).getElementsByAttributeValue("token", "term").forEach(spanElement -> {
                spanTexts.add(spanElement.ownText());
            });
        }
        List<Map<String, String>> diagMaps = new ArrayList<>();
        Map<String, String> diagMap = null;
        String spanText = null;
        for (int i = 0; i < spanTexts.size(); i++) {
            spanText = spanTexts.get(i).replaceAll(" ", "").replaceAll("&nbsp;", "");
            if (i % 3 == 0) {
                if (!spanText.equals("")) {
                    diagMap = new HashMap<>();
                    diagMap.put("诊断名称", spanText);
                } else {
                    diagMap = null;
                }
            }
            if (diagMap == null) {
                continue;
            }
            if (i % 3 == 1) {
                diagMap.put("诊断编码", spanText);
            }
            if (i % 3 == 2) {
                diagMap.put("入院情况", spanText);
                diagMaps.add(diagMap);
            }
        }
        return diagMaps;
    }

    private List<Map<String, String>> getOperMaps(Document doc) {
        Element tableElement = doc.getElementsByClass("tr1").get(2);
        Elements trElements = tableElement.getElementsByTag("tr");
        List<Map<String, String>> operMaps = new ArrayList<>();
        for (int i = 2; i < trElements.size(); i++) {
            Map<String, String> operMap = new HashMap<>();
            trElements.get(i).getElementsByAttributeValue("token", "term").forEach(spanElement -> {
                operMap.put(
                        spanElement.attr("Comment"),
                        spanElement.ownText().replaceAll(" ", "").replaceAll("&nbsp;", "")
                );
            });
            if (!operMap.get("手术名称").equals("")) {
                operMaps.add(operMap);
            }
        }
        return operMaps;
    }

}