package com.lu.framework.helper;

import com.lu.framework.annotation.Controller;
import com.lu.framework.annotation.Service;
import com.lu.framework.util.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * @author lupeng
 * 2021/3/4
 */
public class ClassHelper {
    private static final Set<Class<?>> CLASS_SET;

    static {
        String p = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(p);
    }

    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    /**
     * 获取所有service类
     *
     * @return
     */
    public static Set<Class<?>> getClassServiceSet() {
        Set<Class<?>> classes = new HashSet<>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(Service.class)) {
                classes.add(cls);
            }
        }
        return classes;

    }

    /**
     * 获取所有service类
     *
     * @return
     */
    public static Set<Class<?>> getClassControllerSet() {
        Set<Class<?>> classes = new HashSet<>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(Controller.class)) {
                classes.add(cls);
            }
        }
        return classes;
    }

    /**
     * 获取基础包里面的所有bean
     *
     * @return
     */
    public static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> beanClass = new HashSet<>();
        beanClass.addAll(getClassServiceSet());
        beanClass.addAll(getClassControllerSet());
        return beanClass;
    }

    /**
     * 获取基础包底下某父类的所有子类或某接口的所有实现类
     *
     * @param superClass
     * @return
     */
    public static Set<Class<?>> getClassSetBySuper(Class<?> superClass) {
        Set<Class<?>> classes = new HashSet<>();
        for (Class<?> cls : CLASS_SET) {
            if (superClass.isAssignableFrom(cls) && !superClass.equals(cls)) {
                classes.add(cls);
            }
        }
        return classes;
    }

    /**
     * 获取基础包名下带有某注解的所有类
     * @param annotationClass
     * @return
     */
    public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass) {
        Set<Class<?>> classes = new HashSet<>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(annotationClass)) {
                classes.add(cls);
            }
        }
        return classes;
    }
}
