package com.nuena.util;

import com.google.common.collect.Lists;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

/**
 * @Description:
 * @author: rengb
 * @time: 2020/1/14 14:39
 */
public class IpGather {

    /**
     * 可用ip
     * 49.77.210.81,9999
     * 61.240.222.27,3128
     *
     * @throws Exception
     */
    public static void yundaili()throws Exception{
        FileReader fr = new FileReader("D:\\ceshi.txt");
        BufferedReader br = new BufferedReader(fr);
        List<String> list = Lists.newArrayList();
        List<String> ipList = Lists.newArrayList();
        String msg = null;
        while ((msg = br.readLine())!=null){
            list.add(msg);
        }
        br.close();
        fr.close();
        list.forEach(i->{
            String[] arry = i.split(",");
            String html = HttpTool.post("https://www.baikemy.com/disease/detail/1012",
                    arry[0],Integer.parseInt(arry[1]));
            if (StringUtil.isNotBlank(html)){
                ipList.add(i);
            }
        });
        ipList.forEach(i->{
            System.out.println(i);
        });

    }


}
