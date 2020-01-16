package com.nuena.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Description: List 工具类
 * @author: gaodm
 * @date: 2017/12/28 15:36
 * @version: V1.0
 */
public class ListUtil {
    /**
     * list的第一行
     */
    public static final int FIRST = 0;

    private ListUtil() {

    }

    /**
     * 创建List对象
     *
     * @param <E> 泛型，
     * @return
     */
    public static <E> ArrayList<E> newArrayList() {
        return new ArrayList<>();
    }

    /**
     * 判断List是否为空
     *
     * @param list
     * @return
     */
    public static boolean isEmpty(List list) {
        if (null == list) {
            return Boolean.TRUE;
        }
        if (list.isEmpty()) {
            return Boolean.TRUE;
        }
        if (list.size() < 1) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 判断List是否为非空
     *
     * @param list
     * @return
     */
    public static boolean isNotEmpty(List list) {
        return !isEmpty(list);
    }

    /**
     * 获取List集合中第一个对象，前提是自己先判断这个list不会为空
     *
     * @param list
     * @param <E>
     * @return
     */
    public static <E> E firstEntity(List<E> list) {
        if (isEmpty(list)) {
            return null;
        }
        return list.get(FIRST);
    }

    public static <E> ArrayList<E> arrayToList(E[] strArray) {
        ArrayList<E> arrayList = new ArrayList<>(strArray.length);
        Collections.addAll(arrayList, strArray);
        return arrayList;
    }

    public static void main(String[] args) throws Exception {
        String[] i ={"A","B"};
        List<String> o = arrayToList(i);
        System.out.println("输入参数:"+ i);
        System.out.println("输出参数:"+ o);
    }
}
