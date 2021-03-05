package com.lu.framework.helper;

import com.lu.framework.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author lupeng
 * 2021/3/4
 * BeanHelper 在类加载时就会创建一个Bean容器 BEAN_MAP, 然后获取到应用中所有bean的Class对象, 再通过反射创建bean实例, 储存到 BEAN_MAP 中.
 */
public class BeanHelper {

    public static final Map<Class<?>, Object> BEAN_MAP = new HashMap<>();

    static {
        Set<Class<?>> classes = ClassHelper.getBeanClassSet();
        for (Class<?> cls : classes) {
            Object o = ReflectionUtil.newInstance(cls);
            BEAN_MAP.put(cls,o);
        }
    }

    /**
     * 获取 Bean 容器
     */
    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    /**
     * 获取 Bean 实例
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> cls) {
        if (!BEAN_MAP.containsKey(cls)) {
            throw new RuntimeException("can not get bean by class: " + cls);
        }
        return (T) BEAN_MAP.get(cls);
    }

    /**
     * 设置 Bean 实例
     */
    public static void setBean(Class<?> cls, Object obj) {
        BEAN_MAP.put(cls, obj);
    }
}
