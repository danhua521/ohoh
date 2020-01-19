package com.nuena.util;

import com.google.common.collect.Lists;
import com.nuena.bkmy.entity.DeptInfo;
import com.nuena.bkmy.entity.DiseaseInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
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

        String contentHtml = FileUtil.fileRead("C:\\Users\\Administrator\\Desktop\\百科名医网\\test.txt");
        Document contentDoc = Jsoup.parse(contentHtml);

        contentDoc.getElementsByClass("s_directory_flag").forEach(i->{
            i.appendText("@ab98cdef");
        });

        String specialityVersionHtml = contentDoc.getElementById("specialityVersion").html().replaceAll("<br>","@ab98cdef");
        Document specialityVersionDoc = Jsoup.parse(specialityVersionHtml);
        String specialityVersionText = specialityVersionDoc.text().replaceAll("@ab98cdef","\r\n");

        System.out.println(specialityVersionText);

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

        String path="C:\\Users\\Administrator\\Desktop\\百科名医网\\"; String fileName="tt.txt";
        FileWriter fw = new FileWriter(path + "\\" + fileName);
        fw.write(specialityVersionText);
        fw.close();






    }

}