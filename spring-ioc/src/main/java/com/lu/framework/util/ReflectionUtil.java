package com.lu.framework.util;

import com.lu.framework.helper.ClassHelper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author lupeng
 * 2021/3/4
 */
public final class ReflectionUtil {

    /**
     * 创建实例
     * @param cls
     * @return
     */
    public static Object newInstance(Class<?> cls) {
        Object object = null;
        try {
            object = cls.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    /**
     * 创建实例（根据类名）
     * @param className
     * @return
     */
    public static Object newInstance (String className) {
        return newInstance(ClassUtil.loadClass(className));
    }

    /**
     * 执行方法
     * @param obj
     * @param method
     * @param args
     * @return
     */
    public static Object invokeMethod (Object obj, Method method, Object ... args) {
        Object result = null;
        try {
            result = method.invoke(obj, args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 设置成员变量的值
     * @param obj
     * @param field
     * @param value
     */
    public static void setField (Object obj, Field field, Object value) {
        try {
            field.setAccessible(true);
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
