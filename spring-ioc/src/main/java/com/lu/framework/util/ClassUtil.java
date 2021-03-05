package com.lu.framework.util;

import com.sun.org.apache.bcel.internal.generic.RET;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * 类加载
 *
 * @author lupeng
 * 2021/3/3
 */
public class ClassUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtil.class);

    /**
     * 获取类加载器
     *
     * @return
     */
    public static ClassLoader getClassloader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 加载类
     *
     * @param className
     * @param isInitialized
     * @return
     */
    public static Class<?> loadClass(String className, boolean isInitialized) {
        Class<?> c = null;
        try {
            c = Class.forName(className, isInitialized, getClassloader());
        } catch (Exception e) {
            LOGGER.error("error:" + e);
        }
        return c;
    }

    /**
     * 加载类（默认将初始化类）
     */
    public static Class<?> loadClass(String className) {
        return loadClass(className, true);
    }

    public static Set<Class<?>> getClassSet (String packageName) {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        try {
            Enumeration<URL> enumeration = getClassloader().getResources(packageName);
            while (enumeration.hasMoreElements()){
                URL url = enumeration.nextElement();
                String protocol = url.getProtocol();
                if (protocol.equals("file")) {

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classSet;
    }
}
