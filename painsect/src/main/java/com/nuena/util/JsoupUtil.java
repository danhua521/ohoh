package com.nuena.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

/**
 * @Description:
 * @author: rengb
 * @time: 2020/2/8 19:38
 */
public class JsoupUtil {

    /**
     * 将html解析后保留换行，并清楚多余的空格
     * @param html
     * @return
     */
    public static String clean(String html){
        String text = Jsoup.clean(html, "", Whitelist.none(), new Document.OutputSettings().prettyPrint(false));
        String newText = text.replaceAll("\\s{5,}", "\n");
        String trueContent = newText.replaceFirst("\n", "").trim();
        return trueContent;
    }

}
