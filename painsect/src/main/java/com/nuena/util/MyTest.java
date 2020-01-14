package com.nuena.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nuena.util.HttpTool;
import com.nuena.util.IpGather;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.FileReader;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author: rengb
 * @time: 2020/1/14 13:47
 */
public class MyTest {

    public static void main(String[] args) throws Exception {
        String msg = FileUtil.fileRead("C:\\Users\\Administrator\\Desktop\\百科名医网\\疾病json1.txt");

        System.out.println(msg);

        Map<String,Object> msgMap = (Map)JSON.parse(msg);
        List<Map<String,Object>> mapList = (List<Map<String,Object>>)msgMap.get("data");
        mapList.forEach(map->{
            System.out.println(map.get("id").toString()+"----"+map.get("name").toString());
        });

        //        String html = HttpTool.get("https://www.baikemy.com/disease/high/flow/list/2","utf-8",
        //                "111.79.45.199",9999);
        //        String html = HttpTool.post("https://www.baikemy.com/disease/detail/1012");
        //        Document doc = Jsoup.parse(html);
        //        System.out.println(html);
        //        System.out.println(doc.getElementsByClass("detail_name").text());


    }
}
