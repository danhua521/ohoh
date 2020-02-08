package com.nuena.util;

import com.google.common.collect.Lists;
import com.nuena.bkmy.entity.DeptInfo;
import com.nuena.bkmy.entity.DiseaseInfo;
import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Description:
 * @author: rengb
 * @time: 2020/1/14 13:47
 */
public class MyTest {

    public static void main(String[] args) throws Exception {

        //        String contentHtml = FileUtil.fileRead("C:\\Users\\Administrator\\Desktop\\百科名医网\\test.txt");
        //        Document contentDoc = Jsoup.parse(contentHtml);
        //
        //        contentDoc.getElementsByClass("s_directory_flag").forEach(i->{
        //            i.appendText("@ab98cdef");
        //        });
        //
        //        String specialityVersionHtml = contentDoc.getElementById("specialityVersion").html().replaceAll("<br>","@ab98cdef");
        //        Document specialityVersionDoc = Jsoup.parse(specialityVersionHtml);
        //        String specialityVersionText = specialityVersionDoc.text().replaceAll("@ab98cdef","\r\n");
        //
        //        System.out.println(specialityVersionText);

        //        List<String> items =
        //                Arrays.asList("apple", "apple", "banana",
        //                        "apple", "orange", "banana", "papaya");
        //
        //        Map<String, Long> result =
        //                items.stream().collect(
        //                        Collectors.groupingBy(
        //                                Function.identity(), Collectors.counting()
        //                        )
        //                );

        //        System.out.println(result);

        //        String msg = FileUtil.fileRead("C:\\Users\\Administrator\\Desktop\\百科名医网\\test.txt");
        //        String[] arry = null;
        //        for(String a : msg.split(",")){
        //            arry = a.split("=");
        //            System.out.println("insert into test values('" + arry[0].trim()+"',"+arry[1]+");");
        //        }

        //
        //        html2 = html2.replaceAll("<br>","@ab98cdef");
        //
        //        Document doc2 = Jsoup.parse(html2);
        //
        //        System.out.println(doc2.text().replaceAll("@ab98cdef","\r\n"));

        //        String path="C:/Users/Administrator/Desktop/百科名医网/"; String fileName="尺骨干下1\\/3骨折合并下尺关节脱位切开复位内固定术.txt";
        //        FileWriter fw = new FileWriter(path + "\\" + fileName);
        //        fw.write("123");
        //        fw.close();

        //        String msg = "鼻型结外NK\\T细胞/淋巴瘤_51393838158593.txt";

//        System.out.println(parsePdf("C:\\Users\\Administrator\\Desktop\\tt9\\西氏内科学(中文第22版上册).pdf"));
        //        File file = new File("");




//        System.out.println(contentHtml);
//        System.out.println(contentHtml);
//        String msg = EnDecodeUtil.encode(contentHtml);
//        System.out.println(msg);
//        System.out.println(EnDecodeUtil.decode(msg));
//        Document contentDoc = Jsoup.parse(contentHtml);
//        Element jblistConEarElement = contentDoc.getElementsByClass("jblist-con-ear").first();
//        Elements aElements = jblistConEarElement.getElementsByClass("ks-zm-list").select("a");
//        System.out.println(aElements.size());
//        aElements.forEach(i->{
//            String href = i.attr("href");
//            System.out.println(href.substring(8,href.indexOf(".htm")));
//        });
//        String zimus = "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z";
//        System.out.println(zimus.split(",").length);
//        String nritong = contentDoc.getElementsByClass("jib-articl").first().text();
//        System.out.println(nritong);
//        FileUtil.fileWrite("F:\\迅雷下载","dte",nritong);

//        System.out.println("任"+"\r\n"+"国宾");

        String contentHtml = HttpTool.get("http://jbk.39.net/wcda/cyyp/");
        Document contentDoc = Jsoup.parse(contentHtml);


        Element fl730Element = contentDoc.getElementsByClass("fl730").first();

        Elements drugListElement = fl730Element.getElementsByClass("drug-list").select("h4");
        String anaTxt = JsoupUtil.clean(drugListElement.outerHtml());
        System.out.println(anaTxt);

//        String title = listLeftElement.getElementsByClass("disease_box").first().getElementsByClass("disease_title").first().text();
//        title = title.substring(0,title.length()-2);
//        System.out.println(title);
//        String anaTxt = JsoupUtil.clean(listLeftElement.outerHtml());
//        if (anaTxt.indexOf("相关阅读")!=-1){
//            anaTxt = anaTxt.substring(0,anaTxt.indexOf("相关阅读"));
//        }
//
//
//        System.out.println(anaTxt);
//        parse();
//        Element diseaseBoxElement = listLeftElement.getElementsByClass("disease_box").first();
//        String disName = diseaseBoxElement.getElementsByClass("disease_title").first().text();
//        String jii = diseaseBoxElement.getElementsByClass("introduction").first().text();
//
//        System.out.println(disName);
//        System.out.println(jii);

    }

    public static void parse(){
        String htmlStr = "<table id=kbtable >"
                + "<tr> "
                + "<td width=123>"
                + "<div id=12>这里是要获取的数据1</div>"
                + "<div id=13>这里是要获取的数据2</div>"
                + "</td>"
                + "<td width=123>"
                + "<div id=12>这里是要获取的数据3</div>"
                + "<div id=13>这里是要获取的数据4</div>"
                + "</td>	"
                + "</tr>"
                + "</table>";
        Document doc = Jsoup.parse(htmlStr);
        // 根据id获取table
        Element table = doc.getElementById("kbtable");
        // 使用选择器选择该table内所有的<tr> <tr/>
        Elements trs = table.select("tr");
        //遍历该表格内的所有的<tr> <tr/>
        for (int i = 0; i < trs.size(); ++i) {
            // 获取一个tr
            Element tr = trs.get(i);
            // 获取该行的所有td节点
            Elements tds = tr.select("td");
            // 选择某一个td节点
            for (int j = 0; j < tds.size(); ++j) {
                Element td = tds.get(j);
                // 获取td节点的所有div
                Elements divs = td.select("div");
                // 选择一个div
                for (int k = 0; k < divs.size(); k++) {
                    Element div = divs.get(k);
                    //获取文本信息
                    String text = div.text();
                    //输出到控制台
                    System.out.println(text);
                }
            }
        }
    }


    public static String getFileType(String path) {
        try {
            File file = new File(path);
            Tika tika = new Tika();
            String fileType = tika.detect(file);
            if (fileType != null && fileType.contains("/")) {
                fileType = fileType.substring(fileType.indexOf("/") + 1);
            }
            return fileType;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getContext(String path) {

        try {

            File file = new File(path);

            Tika tika = new Tika();

            String filecontent = tika.parseToString(file);

            System.out.println("Extracted Content: " + filecontent);

            return filecontent;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return "";
    }

    public static String parsePdf(String path) {
        try {
            BodyContentHandler handler = new BodyContentHandler();

            Metadata metadata = new Metadata();

            FileInputStream inputstream = new FileInputStream(new File(path));

            ParseContext pcontext = new ParseContext();
            PDFParser pdfparser = new PDFParser();
            pdfparser.parse(inputstream, handler, metadata, pcontext);
                        System.out.println("Contents of the PDF :" + handler.toString());
            System.out.println("Metadata of the PDF:");
            String[] metadataNames = metadata.names();
            for (String name : metadataNames) {
                System.out.println(name + " : " + metadata.get(name));
            }
        } catch (Exception e) {
        }
        return "";
    }

//    public static String parsePdf(String path) {
//
//        try {
//
//            BodyContentHandler handler = new BodyContentHandler();
//
//            Metadata metadata = new Metadata();
//
//            FileInputStream inputstream = new FileInputStream(new File(path));
//
//            ParseContext pcontext = new ParseContext();
//
//
//            //parsing the document using PDF parser
//
//            PDFParser pdfparser = new PDFParser();
//
//            pdfparser.parse(inputstream, handler, metadata, pcontext);
//
//            //getting the content of the document
//
//            System.out.println("Contents of the PDF :" + handler.toString());
//
//            // 元数据提取
//
//            System.out.println("Metadata of the PDF:");
//
//            String[] metadataNames = metadata.names();
//
//            for (String name : metadataNames) {
//
//                System.out.println(name + " : " + metadata.get(name));
//
//            }
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//        }
//
//        return "";
//
//    }

}