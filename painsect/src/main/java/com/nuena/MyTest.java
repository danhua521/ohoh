package com.nuena;

import com.nuena.util.HttpTool;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * @Description:
 * @author: rengb
 * @time: 2020/1/14 13:47
 */
public class MyTest {

    public static void main(String[] args){
        String html = HttpTool.post("https://www.baikemy.com/disease/detail/30687104574721",
                "49.77.209.204",9999);
        Document doc = Jsoup.parse(html);
        System.out.println(doc.getElementsByClass("detail_name").text());
    }
}
