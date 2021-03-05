package com.lu.framework.helper;

import com.lu.framework.annotation.Autowired;
import com.lu.framework.util.ReflectionUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

/**
 * @author lupeng
 * 2021/3/4
 * <p>
 * 最后就是实现 IOC 了, 我们需要做的就是遍历Bean容器中的所有bean, 为所有带 @Autowired 注解的属性注入实例. 这个实例从Bean容器中获取.
 */
public class IocHelper {
    static {
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (MapUtils.isNotEmpty(beanMap)) {
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
                Class<?> beanClass = beanEntry.getKey();
                Object beanInt = beanEntry.getValue();
                Field[] beanField = beanClass.getDeclaredFields();
                for (Field field : beanField) {
                    if (field.isAnnotationPresent(Autowired.class)) {
                        Class<?> beanFieldClass = field.getType();
                        Set<Class<?>> classSetBySuper = ClassHelper.getClassSetBySuper(beanFieldClass);
                        if (CollectionUtils.isNotEmpty(classSetBySuper)) {
                            //获取第一个实现类
                            beanFieldClass = classSetBySuper.iterator().next();
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            if (beanFieldInstance != null) {
                                ReflectionUtil.setField(beanInt, field, beanFieldInstance);
                            }
                        }

                    }
                }
            }
        }
    }
}
