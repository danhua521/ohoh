package com.nuena.util;

import java.lang.reflect.Field;

/**
 * @Description:
 * @author: rengb
 * @time: 2020/4/20 16:18
 */
public class ObjectUtil {

    /**
     * 将对象object中私有属性为字符串"NULL"或者"null"的字段值改为null
     *
     * @param object
     * @param <T>
     * @return
     */
    public static <T> T attributeNullHandle(Object object) {
        if (object == null) {
            return null;
        }
        try {
            Field[] fields = object.getClass().getDeclaredFields();
            String fieldObjectValue = null;
            for (Field field : fields) {
                field.setAccessible(true);
                Object fieldObject = field.get(object);
                if (fieldObject != null && fieldObject.getClass().getName().equals("java.lang.String")) {
                    fieldObjectValue = fieldObject.toString();
                    if (fieldObjectValue.equals("NULL") || fieldObjectValue.equals("null")) {
                        field.set(object, null);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) object;
    }

}