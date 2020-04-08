package com.nuena.util;

import com.google.common.collect.Lists;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author: rengb
 * @time: 2020/1/14 13:47
 */
public class MyTest {

    public static void main(String[] args) throws Exception {
//fun5();
//        File level1FileDec = new File("F:\\医学网站文本库\\医学百科网\\疾病");
//        for (File level2FileDec : level1FileDec.listFiles()) {
//            for (File level3FileDec : level2FileDec.listFiles()) {
//                String level4FilePath = level3FileDec.listFiles()[0].getPath();
//                level4FilePath = level4FilePath.substring(16, level4FilePath.lastIndexOf("\\"));
//                System.out.println(level4FilePath);
//            }
//        }
//        List<String> list = Lists.newArrayList();
//        int page_=0;
//        int cou = 672;
//        for (int page=1;page<=cou;page++){
//            if (page==1){
//                page_=cou;
//            }
//            if (page==cou){
//                page_=1;
//            }
//            list.addAll(fun5(page,page_));
//        }
//
//        System.out.println(list.stream().distinct().count());

//        th("C:\\Users\\RGB\\Desktop\\调试\\001.txt");

    }

    public static void th(String filePath){
        String content = FileUtil.fileRead(filePath);
        System.out.println(content);
        FileUtil.fileWrite("C:\\Users\\RGB\\Desktop\\调试","ttb.java",content);
    }



//
//    public static List<String> fun5(int page,int page_) throws ClientProtocolException, IOException {
//        List<String> list = Lists.newArrayList();
//        String url = "http://cmkd.hnadl.cn/Search.html?t=Disease";
//        CloseableHttpClient httpclient = HttpClients.createDefault();
//        HttpPost httppost =new HttpPost(url);
//
//        //httppost.addHeader("Cookie","JSESSIONID=fnwebidwn==");
//        httppost.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36");
//        httppost.addHeader("Cookie","ASP.NET_SessionId=c3lhg0m2sfkywb553mo1vez0");
//        httppost.addHeader("Content-Type","application/x-www-form-urlencoded");
////        httppost.addHeader("","");
////        httppost.addHeader("","");
////        httppost.addHeader("","");
//
//
//        //在集合中放入我们表单中的name --- value 键值对
//        List<BasicNameValuePair> pair =new ArrayList<BasicNameValuePair>();
//        pair.add(new BasicNameValuePair("__EVENTTARGET", "ctl00$cphCenter$ctlPager2"));
//        pair.add(new BasicNameValuePair("__EVENTARGUMENT", page+""));
//        pair.add(new BasicNameValuePair("__LASTFOCUS", ""));
//        pair.add(new BasicNameValuePair("__VIEWSTATE", "/wEPDwUJMTgwNTc5MDA0DxYEHgJTRWUeB1Nob3dFeHBlFgJmD2QWAgIDD2QWBAIBDxYCHgRUZXh0BQZndXNldCBkAgUPZBYUAgEPEGQQFQQM5oyJ55a+55eF5ZCNDOaMieiLseaWh+WQjQnmjInliKvlkI0J5oyJSUNE5Y+3FQQHc3RyTmFtZQlzdHJOYW1lX0UJc3RyTmFtZV9BBnN0cklDRBQrAwRnZ2dnZGQCBg9kFgJmDxAPFgYeDURhdGFUZXh0RmllbGQFD3N0ckNhdGVnb3J5TmFtZR4ORGF0YVZhbHVlRmllbGQFDWludENhdGVnb3J5SUQeC18hRGF0YUJvdW5kZ2QQFRsJ55qu6IKk56eRDOiAgeW5tOeXheenkQblhL/np5EJ57K+56We56eRBumqqOenkQzogp3og4blpJbnp5EG5aaH56eRCeihgOa2suenkQzmma7pgJrlpJbnp5EJ5raI5YyW56eRDOWGheWIhuazjOenkQznpZ7nu4/lpJbnp5EP5b+D6KGA566h5YaF56eRDOaEn+afk+WGheenkQzms4zlsL/lpJbnp5EG55y856eRCeiDuOWkluenkQnlkbzlkLjnp5EJ6aOO5rm/56eRCeiCv+eYpOenkQnogr7lhoXnp5EG5Lqn56eRDOelnue7j+WGheenkQnku6PosKLnp5EJ6ICz6by75ZaJCeWPo+iFlOenkQbng6fkvKQVGwExATIBMwE0ATUBNgE3ATgBOQIxMAIxMQIxMgIxMwIxNAIxNQIxNgIxNwIxOAIxOQIyMAIyMQIyMgIyMwIyNAIyNQIyNgIyNxQrAxtnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dkZAIHDw8WAh8CZWRkAggPFgIfAgUENjcxNWQCCQ8WAh8CBQE1ZAIKDxYCHwIFAzY3MmQCCw8QZGQWAWZkAgwPDxYGHgtSZWNvcmRjb3VudAK7NB4IUGFnZVNpemUCCh4QQ3VycmVudFBhZ2VJbmRleAIFZGQCEw8WAh8CBQEwZAIUDxBkZBYBZmQYAQUeX19Db250cm9sc1JlcXVpcmVQb3N0QmFja0tleV9fFh0FHWN0bDAwJGNwaENlbnRlciRjYlR3aWNlU2VhcmNoBShjdGwwMCRjcGhDZW50ZXIkdWNDYXRlZ29yeSRjYmxDYXRlZ29yeSQwBShjdGwwMCRjcGhDZW50ZXIkdWNDYXRlZ29yeSRjYmxDYXRlZ29yeSQxBShjdGwwMCRjcGhDZW50ZXIkdWNDYXRlZ29yeSRjYmxDYXRlZ29yeSQyBShjdGwwMCRjcGhDZW50ZXIkdWNDYXRlZ29yeSRjYmxDYXRlZ29yeSQzBShjdGwwMCRjcGhDZW50ZXIkdWNDYXRlZ29yeSRjYmxDYXRlZ29yeSQ0BShjdGwwMCRjcGhDZW50ZXIkdWNDYXRlZ29yeSRjYmxDYXRlZ29yeSQ1BShjdGwwMCRjcGhDZW50ZXIkdWNDYXRlZ29yeSRjYmxDYXRlZ29yeSQ2BShjdGwwMCRjcGhDZW50ZXIkdWNDYXRlZ29yeSRjYmxDYXRlZ29yeSQ3BShjdGwwMCRjcGhDZW50ZXIkdWNDYXRlZ29yeSRjYmxDYXRlZ29yeSQ4BShjdGwwMCRjcGhDZW50ZXIkdWNDYXRlZ29yeSRjYmxDYXRlZ29yeSQ5BSljdGwwMCRjcGhDZW50ZXIkdWNDYXRlZ29yeSRjYmxDYXRlZ29yeSQxMAUpY3RsMDAkY3BoQ2VudGVyJHVjQ2F0ZWdvcnkkY2JsQ2F0ZWdvcnkkMTEFKWN0bDAwJGNwaENlbnRlciR1Y0NhdGVnb3J5JGNibENhdGVnb3J5JDEyBSljdGwwMCRjcGhDZW50ZXIkdWNDYXRlZ29yeSRjYmxDYXRlZ29yeSQxMwUpY3RsMDAkY3BoQ2VudGVyJHVjQ2F0ZWdvcnkkY2JsQ2F0ZWdvcnkkMTQFKWN0bDAwJGNwaENlbnRlciR1Y0NhdGVnb3J5JGNibENhdGVnb3J5JDE1BSljdGwwMCRjcGhDZW50ZXIkdWNDYXRlZ29yeSRjYmxDYXRlZ29yeSQxNgUpY3RsMDAkY3BoQ2VudGVyJHVjQ2F0ZWdvcnkkY2JsQ2F0ZWdvcnkkMTcFKWN0bDAwJGNwaENlbnRlciR1Y0NhdGVnb3J5JGNibENhdGVnb3J5JDE4BSljdGwwMCRjcGhDZW50ZXIkdWNDYXRlZ29yeSRjYmxDYXRlZ29yeSQxOQUpY3RsMDAkY3BoQ2VudGVyJHVjQ2F0ZWdvcnkkY2JsQ2F0ZWdvcnkkMjAFKWN0bDAwJGNwaENlbnRlciR1Y0NhdGVnb3J5JGNibENhdGVnb3J5JDIxBSljdGwwMCRjcGhDZW50ZXIkdWNDYXRlZ29yeSRjYmxDYXRlZ29yeSQyMgUpY3RsMDAkY3BoQ2VudGVyJHVjQ2F0ZWdvcnkkY2JsQ2F0ZWdvcnkkMjMFKWN0bDAwJGNwaENlbnRlciR1Y0NhdGVnb3J5JGNibENhdGVnb3J5JDI0BSljdGwwMCRjcGhDZW50ZXIkdWNDYXRlZ29yeSRjYmxDYXRlZ29yeSQyNQUpY3RsMDAkY3BoQ2VudGVyJHVjQ2F0ZWdvcnkkY2JsQ2F0ZWdvcnkkMjYFKWN0bDAwJGNwaENlbnRlciR1Y0NhdGVnb3J5JGNibENhdGVnb3J5JDI2ws4Uf6jh48YGupz86LnyzBf+1jw="));
//        pair.add(new BasicNameValuePair("__EVENTVALIDATION", "/wEWLQKd+6cYAuvK55oFAri/mp4JAoS/mp4JAsidsLcPAs+Z3q0OAs6Z3q0OAoOu3swHAs3f7N0PAteVkoUNApKIr6MLApKIw/4DApKI19kMApKI67QFApKI37YIApKI85EBApKIh+0JApKIm8gCApKIz3wCkojj1wkCm8aTswQCmsaTswQCmcaTswQCmMaTswQCn8aTswQCnsaTswQCncaTswQCnMaTswQCo8aTswQCosaTswQCm8anjg0Cmsanjg0Cmcanjg0CmManjg0Cn8anjg0Cnsanjg0Cncanjg0Cq67A2gQCpMGqtwgCpcGqtwgCpsGqtwgCq66s/wsCpMHGkgcCpcHGkgcCpsHGkge0AMjv1D34onpK2s1nGtkCdb7aIw=="));
//        pair.add(new BasicNameValuePair("ctl00$cphCenter$ddlField", "strName"));
//        pair.add(new BasicNameValuePair("ctl00$cphCenter$ddlFuzzy", "2"));
//        pair.add(new BasicNameValuePair("ctl00$cphCenter$txtSearchKey", ""));
//        pair.add(new BasicNameValuePair("ctl00$cphCenter$ddlPageSize1", "10"));
//        pair.add(new BasicNameValuePair("ctl00$cphCenter$ctlPager_input", page_+""));
//        pair.add(new BasicNameValuePair("ctl00$cphCenter$ddlPageSize2", "10"));
//        pair.add(new BasicNameValuePair("ctl00$cphCenter$ctlPager2_input", page_+""));
//
//
//        //httppost 中放入我们的经过url编码的表单参数
//        httppost.setEntity(new UrlEncodedFormEntity(pair));
//
//        HttpResponse response=httpclient.execute(httppost);
//
//        Utf8ResponseHandler utf8ResponseHandler = new Utf8ResponseHandler();
//        String retMsg = utf8ResponseHandler.handleResponse(response);
//        FileUtil.fileWrite("D:\\html",page+"",retMsg);
//        httpclient.close();
//
//        Document doc = Jsoup.parse(retMsg);
//        doc.getElementsByClass("text5").forEach(i->{
//            System.out.println(i.text());
//            list.add(i.text());
//        });
//        return list;
//    }


}