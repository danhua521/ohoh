package com.nuena.yxbk;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.nuena.util.FileUtil;
import com.nuena.util.HttpTool;
import com.nuena.util.ListUtil;
import com.nuena.util.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description:
 * @author: rengb
 * @time: 2020/3/8 22:22
 */
public class YxbkLoad {

    public static void main(String[] args) {
        YxbkLoad yxbkLoad = new YxbkLoad();
        String url = "http://www.medbaike.com/category-view-1.html";
        String path = "F:\\医学百科网\\症状";
        String otherId = "category-view-1";
        String otherName = "无归属";
//        yxbkLoad.loadAll(url, path, otherId, otherName);

        url = "http://www.medbaike.com/category-view-2.html";
        path = "F:\\医学百科网\\症状";
        otherId = "category-view-2";
//        yxbkLoad.loadAll(url, path, otherId, otherName);
        System.out.println(yxbkLoad.getExcus(path).stream().distinct().count());
    }

    private void loadAll(String url, String path, String otherId, String otherName) {
        List<String> idList = Lists.newArrayList();
        Map<String, String> idNameMap = Maps.newHashMap();
        List<String> loadedIdList = Lists.newArrayList();
        List<String> willIdList = null;
        String html = HttpTool.get(url);
        Document doc = Jsoup.parse(html);
        doc.getElementsByClass("i6-ff").select("a").forEach(i -> {
            String href = i.attr("href");
            String id = href.substring(0, href.indexOf(".html"));
            idList.add(id);
            idNameMap.put(id, i.text());
        });

        List<String> excus = Lists.newArrayList();
        while (ListUtil.isNotEmpty(willIdList = idList.stream().filter(i -> !loadedIdList.contains(i)).collect(Collectors.toList()))) {
            willIdList.forEach(i -> {
                try {
                    System.out.println("开始下载：" + idNameMap.get(i));
                    pageConsult(i, idNameMap.get(i), path, excus);
                    loadedIdList.add(i);
                    System.out.println("结束下载：" + idNameMap.get(i));
                } catch (Exception e) {
                    System.out.println("失败下载：" + idNameMap.get(i));
                    e.printStackTrace();
                }
            });
        }

        List<String> excus1 = getExcus(path);
        boolean flag = false;
        while (!flag) {
            try {
                pageConsult(otherId, otherName, path, excus1);
                flag = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    private void pageConsult(String id, String name, String path, List<String> excus) {
        int page = 0;
        String url_ = null;
        while (page != -1) {
            page++;
            url_ = "http://www.medbaike.com/" + id + "-" + page + ".html";
            if (!getSml(url_, name, path, excus)) {
                page = -1;
            }
        }
    }

    private boolean getSml(String url, String name, String path, List<String> excus) {
        boolean flag = false;
        try {
            Thread.sleep(100);
        } catch (Exception e) {
        }
        String html = HttpTool.get(url);
        if (StringUtil.isNotBlank(html)) {
            Document doc = Jsoup.parse(html);
            if (doc.getElementsByClass("w-710").first() == null) {
                throw new RuntimeException("请求未获取到数据！");
            }
            Elements aElements = doc.getElementsByClass("col-dl").select("dt").select("a");
            String smlName, smlUrl, smlHtml;
            for (Element i : aElements) {
                smlName = i.text();
                smlName = smlName.replaceAll("\\\\", "zxg").replaceAll("/", "fxg");
                if (!excus.contains(smlName)) {
                    smlUrl = "http://www.medbaike.com/" + i.attr("href");
                    smlHtml = HttpTool.get(smlUrl);
                    System.out.println("生成文件：" + path + "\\" + name + "\\" + smlName + "\\" + smlName);
                    if (!FileUtil.fileWrite(path + "\\" + name + "\\" + smlName, smlName, smlHtml)) {
                        throw new RuntimeException("文件生成失败：" + path + "\\" + name + "\\" + smlName + "\\" + smlName);
                    }
                }
                flag = true;
            }
        } else {
            throw new RuntimeException("请求未获取到数据！");
        }
        return flag;
    }

    private List<String> getExcus(String path) {
        List<String> retList = Lists.newArrayList();
        if (path.indexOf("无归属") == -1) {
            File file = new File(path);
            if (file.isDirectory()) {
                for (File i : file.listFiles()) {
                    retList.addAll(getExcus(i.getPath()));
                }
            } else {
                retList.add(file.getName());
            }
        }
        return retList;
    }

}