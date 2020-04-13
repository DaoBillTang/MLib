package com.dtb.core.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.Field;

/**
 * @author: dtb
 * @email: wendaoyuqin1@gmail.com
 * @createTime: 19-3-25下午5:28
 * 对于 一个 普通的 po 进行的操作
 */
@SuppressWarnings("all")
public class BeanUtils {

    /**
     * @param origin      数据源,拥有数据的 内容
     * @param destination 目标数据,需要被 赋值的数据
     * @param <T>         数据类型
     */
    public static <T> boolean mergeObjectWithoutNull(T destination, T origin) {
        if (origin == null || destination == null) {
            return false;
        }
        if (!origin.getClass().equals(destination.getClass())) {
            return false;
        }

        Field[] fields = origin.getClass().getDeclaredFields();

        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(origin);
                if (null != value) {
                    field.set(destination, value);
                }
                field.setAccessible(false);
            } catch (Exception e) {
            }
        }
        return true;
    }

    /**
     * @param origin      数据源,拥有数据的 内容
     * @param destination 目标数据,需要被 赋值的数据
     * @param <T>         数据类型
     */
    public static <T> boolean mergeObject(T destination, T origin) {
        if (origin == null || destination == null) {
            return false;
        }
        if (!origin.getClass().equals(destination.getClass())) {
            return false;
        }
        Field[] fields = origin.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(origin);
                field.set(destination, value);
                field.setAccessible(false);
            } catch (Exception e) {
            }
        }
        return true;
    }

    public static <T> boolean putObjectInPre(T origin, Context context, String dictName) {
        if (origin == null) {
            return false;
        }
        PreTools pre = new PreTools(context, dictName);
        return putObjectInPre(origin, pre);
    }

    public static <T> boolean putObjectInPre(T origin, PreTools pre) {
        if (origin == null) {
            return false;
        }
        Field[] fields = origin.getClass().getDeclaredFields();
        SharedPreferences.Editor editor = pre.editor();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                pre.put(editor, field.getName(), field.get(origin));
                field.setAccessible(false);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        editor.apply();
        return true;
    }

    public static <T> Object getObjectPre(T origin, Context context, String dictName) {
        if (origin == null) {
            return null;
        }
        PreTools pre = new PreTools(context, dictName);
        return getObjectPre(origin, pre);
    }

    public static <T> Object getObjectPre(T origin, PreTools pre) {
        if (origin == null) {
            return null;
        }
        Field[] fields = origin.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Class<?> t = field.getType();
                String name = field.getName();
                if (t == boolean.class || t == Boolean.class) {
                    field.set(origin, pre.getBoolean(name, false));
                } else if (t == String.class) {
                    field.set(origin, pre.getString(name));
                } else if (t == int.class || t == Integer.class) {
                    field.set(origin, pre.getInt(name));
                }
                field.setAccessible(false);
            } catch (Exception e) {
            }
        }
        return origin;
    }
}
