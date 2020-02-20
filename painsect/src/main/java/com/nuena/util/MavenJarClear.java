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
            file.delete();
        }
    }

}