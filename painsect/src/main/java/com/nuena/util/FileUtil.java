package com.nuena.util;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * @Description:
 * @author: rengb
 * @time: 2020/1/14 16:01
 */
public class FileUtil {

    /**
     * 读取文件，返回字符串
     *
     * @param path
     * @return
     */
    public static String fileRead(String path) {
        String ret = null;
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(path);
            br = new BufferedReader(fr);
            String line = null;
            StringBuffer sbf = new StringBuffer();
            while ((line = br.readLine()) != null) {
                sbf.append(line);
            }
            ret = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ret;
    }


}