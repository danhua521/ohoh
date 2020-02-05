package com.nuena.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 * @Description:
 * @author: rengb
 * @time: 2020/1/14 11:10
 */
public class HttpTool {

    /**
     * GET 请求
     *
     * @param url 地址
     * @return
     */
    public static String get(String url) {
        return ask(url, "GET", null, null, null);
    }

    /**
     * GET 请求
     *
     * @param url          地址
     * @param backCharCode 返回数据的编码
     * @return
     */
    public static String get(String url, String backCharCode) {
        return ask(url, "GET", backCharCode, null, null);
    }

    /**
     * GET 请求
     *
     * @param url       地址
     * @param proxyIp   代理地址ip
     * @param proxyPort 代理地址端口
     * @return
     */
    public static String get(String url, String proxyIp, Integer proxyPort) {
        return ask(url, "GET", null, proxyIp, proxyPort);
    }

    /**
     * GET 请求
     *
     * @param url          地址
     * @param backCharCode 返回数据的编码
     * @param proxyIp      代理地址ip
     * @param proxyPort    代理地址端口
     * @return
     */
    public static String get(String url, String backCharCode, String proxyIp, Integer proxyPort) {
        return ask(url, "GET", backCharCode, proxyIp, proxyPort);
    }

    /**
     * POST 请求
     *
     * @param url 地址
     * @return
     */
    public static String post(String url) {
        return ask(url, "POST", null, null, null);
    }

    /**
     * POST 请求
     *
     * @param url          地址
     * @param backCharCode 返回数据的编码
     * @return
     */
    public static String post(String url, String backCharCode) {
        return ask(url, "POST", backCharCode, null, null);
    }

    /**
     * POST 请求
     *
     * @param url       地址
     * @param proxyIp   代理地址ip
     * @param proxyPort 代理地址端口
     * @return
     */
    public static String post(String url, String proxyIp, Integer proxyPort) {
        return ask(url, "POST", null, proxyIp, proxyPort);
    }

    /**
     * POST 请求
     *
     * @param url          地址
     * @param backCharCode 返回数据的编码
     * @param proxyIp      代理地址ip
     * @param proxyPort    代理地址端口
     * @return
     */
    public static String post(String url, String backCharCode, String proxyIp, Integer proxyPort) {
        return ask(url, "POST", backCharCode, proxyIp, proxyPort);
    }

    /**
     * 发送请求
     *
     * @param objects
     * @return
     */
    public static String ask(Object... objects) {
        if (objects == null || objects.length == 0 || objects.length > 5) {
            return null;
        }
        String url = (String) objects[0];
        String askType = "GET", backCharCode = null, proxyIp = null;
        Integer proxyPort = null;
        if (objects.length > 1) {
            askType = (String) objects[1];
        }
        if (objects.length > 2) {
            backCharCode = (String) objects[2];
        }
        if (objects.length > 3) {
            proxyIp = (String) objects[3];
        }
        if (objects.length > 4) {
            proxyPort = (Integer) objects[4];
        }
        return ask(url, askType, backCharCode, proxyIp, proxyPort);
    }

    /**
     * 发送请求
     *
     * @param url          地址
     * @param askType      请求方式，GET或者POST
     * @param backCharCode 返回数据的编码
     * @param proxyIp      代理地址ip
     * @param proxyPort    代理地址端口
     * @return 返回数据
     */
    public static String ask(String url, String askType, String backCharCode, String proxyIp, Integer proxyPort) {
        if (StringUtil.isBlank(url) || StringUtil.isBlank(askType)) {
            return null;
        }
        String ret = null;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = null;
        try {
            RequestConfig.Builder builder = RequestConfig.custom()
                    .setConnectTimeout(10000)//设置连接超时时间,单位毫秒
                    .setSocketTimeout(10000);//设置读取超时时间,单位毫秒
            RequestConfig requestConfig = null;
            if (StringUtil.isNotBlank(proxyIp) && proxyPort != null) {
                HttpHost httpHost = new HttpHost(proxyIp, proxyPort);
                requestConfig = builder.setProxy(httpHost)//设置代理
                        .build();
            } else {
                requestConfig = builder.build();
            }

            if (askType.equals("GET")) {
                HttpGet httpGet = new HttpGet(url);
                if (requestConfig != null) {
                    httpGet.setConfig(requestConfig);
                }
                httpGet.addHeader("x-forwarded-for", "183.166.110.166");
                response = httpClient.execute(httpGet);
            } else if (askType.equals("POST")) {
                HttpPost httpPost = new HttpPost(url);
                if (requestConfig != null) {
                    httpPost.setConfig(requestConfig);
                }
                httpPost.addHeader("x-forwarded-for", "183.166.110.166");
                response = httpClient.execute(httpPost);
            }
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                if (StringUtil.isNotBlank(backCharCode)) {
                    ret = EntityUtils.toString(responseEntity, backCharCode);
                } else {
                    ret = EntityUtils.toString(responseEntity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ret;
    }


}
