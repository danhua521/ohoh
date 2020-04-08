package com.nuena.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @Description: java GZIP压缩与解压缩
 * @author: rengb
 * @time: 2020/4/8 22:38
 */
public class GZIPUtils {
    public static final String GZIP_ENCODE_UTF_8 = "UTF-8";
    public static final String GZIP_ENCODE_ISO_8859_1 = "ISO-8859-1";

    /**
     * 将字符串根据某种编码压缩成数组
     *
     * @param str
     * @param encoding
     * @return
     */
    public static byte[] compress(String str, String encoding) {
        if (StringUtil.isBlank(str)) {
            return null;
        }
        byte[] ret = null;
        ByteArrayOutputStream out = null;
        GZIPOutputStream gzip = null;
        try {
            out = new ByteArrayOutputStream();
            gzip = new GZIPOutputStream(out);
            gzip.write(str.getBytes(encoding));
            ret = out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (gzip != null) {
                    gzip.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
            }
        }
        return ret;
    }

    /**
     * 将字符串根据默认编码压缩成数组
     *
     * @param str
     * @return
     * @throws IOException
     */
    public static byte[] compress(String str) throws IOException {
        return compress(str, GZIP_ENCODE_UTF_8);
    }

    /**
     * 将压缩的数组解压成正常的数组
     *
     * @param bytes
     * @return
     */
    public static byte[] uncompress(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        byte[] ret = null;
        ByteArrayInputStream in = null;
        ByteArrayOutputStream out = null;
        GZIPInputStream ungzip = null;
        try {
            in = new ByteArrayInputStream(bytes);
            out = new ByteArrayOutputStream();
            ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            ret = out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ungzip != null) {
                    ungzip.close();
                }
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
            }
        }
        return ret;
    }

    /**
     * 将压缩的数组根据某种编码解压成正常的字符串
     *
     * @param bytes
     * @param encoding
     * @return
     */
    public static String uncompressToString(byte[] bytes, String encoding) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        String ret = null;
        ByteArrayOutputStream out = null;
        ByteArrayInputStream in = null;
        GZIPInputStream ungzip = null;
        try {
            out = new ByteArrayOutputStream();
            in = new ByteArrayInputStream(bytes);
            ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            ret = out.toString(encoding);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ungzip != null) {
                    ungzip.close();
                }
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
            }
        }
        return ret;
    }

    /**
     * 将压缩的数组根据默认编码解压成正常的字符串
     *
     * @param bytes
     * @return
     */
    public static String uncompressToString(byte[] bytes) {
        return uncompressToString(bytes, GZIP_ENCODE_UTF_8);
    }

    /**
     * 将压缩的字符流根据某种编码解压成正常的字符串
     *
     * @param in
     * @param encoding
     * @return
     */
    public static String uncompressToString(InputStream in, String encoding) {
        String ret = null;
        ByteArrayOutputStream out = null;
        GZIPInputStream ungzip = null;
        try {
            if (in == null || in.available() == 0) {
                return null;
            }
            out = new ByteArrayOutputStream();
            ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            ret = out.toString(encoding);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ungzip != null) {
                    ungzip.close();
                }
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
            }
        }
        return ret;
    }

    /**
     * 将压缩的字符流根据默认编码解压成正常的字符串
     *
     * @param in
     * @return
     */
    public static String uncompressToString(InputStream in) {
        return uncompressToString(in, GZIP_ENCODE_UTF_8);
    }

    public static void main(String[] args) throws IOException {
        String s = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        System.out.println("字符串长度：" + s.length());
        System.out.println("压缩后：：" + compress(s).length);
        System.out.println("解压后：" + uncompress(compress(s)).length);
        System.out.println("解压字符串后：：" + uncompressToString(compress(s)).length());
    }

}