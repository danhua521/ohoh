package com.nuena.yxbk;

import com.google.common.collect.Lists;
import com.nuena.util.FileUtil;
import com.nuena.util.JsoupUtil;
import com.nuena.util.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.util.List;

/**
 * @Description:
 * @author: rengb
 * @time: 2020/3/10 0:33
 */
public class YxbkAnalysis {

    public static void main(String[] args) throws Exception {
        YxbkAnalysis yxbkAnalysis = new YxbkAnalysis();
//        yxbkAnalysis.disAnalysis();

        yxbkAnalysis.generDis("F:\\医学网站html文本库\\医学百科网\\疾病\\妇科\\痛经\\痛经");
    }

    private void disAnalysis() {
        File level1FileDec = new File("F:\\医学网站html文本库\\医学百科网\\疾病");
        for (File level2FileDec : level1FileDec.listFiles()) {
            for (File level3FileDec : level2FileDec.listFiles()) {
                File level4File = level3FileDec.listFiles()[0];
                if (level4File.isDirectory()) {
                    System.out.println(level4File.getPath());
                } else {
                    try {
                        generDis(level4File.getPath());
                    } catch (Exception e) {
                        System.out.println("出问题" + level4File.getPath());
                    }
                }
            }
        }
    }

    private void generDis(String filePath) {
        String html = FileUtil.fileRead(filePath);
        Document doc = Jsoup.parse(html);
        Element content1Element = doc.getElementsByClass("content_1").first();
        content1Element.getElementsByClass("texts").forEach(i -> {
            i.text("@start@" + i.text() + "@end@");
        });
        content1Element.getElementsByClass("lTETdf").forEach(i -> {
            i.text("");
        });
        String message = JsoupUtil.clean(
                content1Element.outerHtml()
                        .replaceAll("医学百科网-MedBaike. com", "")
                        .replaceAll("编辑本段", "")
                        .replaceAll("回目录", "")
                        .replaceAll("&nbsp;", "  ")

        );

        List<String> bigList = Lists.newArrayList(message.split("@start@"));

        String newPath = "F:\\医学网站解析结果\\医学百科网" + filePath.substring(20, filePath.lastIndexOf("\\"));
        if (bigList.size() == 1) {
            System.out.println("生成概述：" + filePath);
            FileUtil.fileWrite(newPath, "概述", bigList.get(0));
        } else {
            String revMsg = bigList.remove(0);
            boolean flag = true;
            String[] smallArray = null;
            for (String big : bigList) {
                smallArray = big.split("@end@");
                if (smallArray[0].indexOf("，") != -1
                        || smallArray[0].indexOf("？") != -1
                        || smallArray[0].indexOf(",") != -1
                        || smallArray[0].indexOf("?") != -1) {
                    flag = false;
                }
            }
            if (!flag) {
                System.out.println("生成概述：" + filePath);
                FileUtil.fileWrite(newPath, "概述", message.replaceAll("@start@", "").replaceAll("@end@", ""));
            } else {
                if (StringUtil.isNotBlank(revMsg)) {
                    FileUtil.fileWrite(newPath, "概述", revMsg);
                }
                for (String big : bigList) {
                    smallArray = big.split("@end@");
                    if (smallArray.length == 2) {
                        FileUtil.fileWrite(newPath, smallArray[0], smallArray[1]);
                    }
                }
            }
        }
    }

}