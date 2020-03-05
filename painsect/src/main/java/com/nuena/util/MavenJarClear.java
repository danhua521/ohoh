package com.nuena.util;

import java.io.File;

/**
 * @Description: 本地maven仓库中_remote.repositories清理
 * @author: rengb
 * @time: 2020/2/20 10:05
 */
public class MavenJarClear {

    /**
     * 开始清理
     *
     * @param file
     */
    public static void startClear(File file) {
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            for (File i : file.listFiles()) {
                startClear(i);
            }

        }
        if (file.getName().equals("_remote.repositories")) {
            System.out.println("_remote.repositories文件："+file.getName());
            file.delete();
        }
        if (file.getName().indexOf("lastUpdated") != -1) {
            System.out.println("lastUpdated文件："+file.getName());
            file.delete();
        }
    }

    public static void main(String[] args) {
        MavenJarClear.startClear(new File("F:\\work\\maven_repo\\repository"));
    }

}