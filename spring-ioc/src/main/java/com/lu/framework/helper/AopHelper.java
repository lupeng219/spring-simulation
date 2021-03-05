package com.lu.framework.helper;

import com.lu.framework.annotation.Aspect;
import com.lu.framework.proxy.Proxy;
import com.lu.framework.proxy.ProxyFactory;
import com.lu.framework.util.ClassUtil;

import java.util.*;

/**
 * @author lupeng
 * 2021/3/5
 * 框架中所有Bean的实例都是从Bean容器中获取, 然后再执行该实例的方法,
 * 基于此, 初始化AOP框架实际上就是用代理对象覆盖掉Bean容器中的目标对象, 这样根据目标类的Class对象从Bean容器中获取到的就是代理对象, 从而达到了对目标对象增强的目的.
 */
public class AopHelper {
    static {
        try {
            //切面类-目标类集合的映射
            Map<Class<?>, Set<Class<?>>> aspectMap = createAspectMap();
            //目标类-切面对象列表的映射
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(aspectMap);
            //把切面对象织入到目标类中, 创建代理对象
            for (Map.Entry<Class<?>, List<Proxy>> targetEntry : targetMap.entrySet()) {
                Class<?> targetClass = targetEntry.getKey();
                List<Proxy> proxyList = targetEntry.getValue();
                Object proxy = ProxyFactory.createProxy(targetClass, proxyList);
                //覆盖Bean容器里目标类对应的实例, 下次从Bean容器获取的就是代理对象了
                BeanHelper.setBean(targetClass, proxy);
            }
        } catch (Exception e) {
        }
    }

    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> aspectMap) throws Exception {
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<Class<?>, List<Proxy>>();
        for (Map.Entry<Class<?>, Set<Class<?>>> proxyEntry : aspectMap.entrySet()) {
            //切面类
            Class<?> aspectClass = proxyEntry.getKey();
            //目标类集合
            Set<Class<?>> targetClassSet = proxyEntry.getValue();
            //创建目标类-切面对象列表的映射关系
            for (Class<?> targetClass : targetClassSet) {
                //切面对象
                Proxy aspect = (Proxy) aspectClass.newInstance();
                List<Proxy> aspectList = new ArrayList<Proxy>();
                if (targetMap.containsKey(targetClass)) {
                    targetMap.get(targetClass).add(aspect);
                } else {
                    //切面对象列表
                    aspectList.add(aspect);
                    targetMap.put(targetClass, aspectList);
                }
            }
        }
        return targetMap;
    }

    private static Map<Class<?>, Set<Class<?>>> createAspectMap() throws Exception {
        Map<Class<?>, Set<Class<?>>> aspectMap = new HashMap<>();
        addAspectProxy(aspectMap);
        return aspectMap;
    }

    /**
     * 获取普通切面类-目标类集合的映射
     */
    private static void addAspectProxy(Map<Class<?>, Set<Class<?>>> aspectMap) throws Exception {
        Set<Class<?>> aspectClassSet = ClassHelper.getClassSetBySuper(Aspect.class);
        for (Class<?> aspectClass : aspectClassSet) {
            if (aspectClass.isAnnotationPresent(Aspect.class)) {
                Aspect aspect = aspectClass.getAnnotation(Aspect.class);
                //与该切面对应的目标类集合`
                Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
                aspectMap.put(aspectClass, targetClassSet);
            }
        }
    }

    /**
     * 根据@Aspect定义的包名和类名去获取对应的目标类集合
     *
     * @param aspect
     * @throws Exception
     */
    private static Set<Class<?>> createTargetClassSet(Aspect aspect) throws Exception {
        Set<Class<?>> targetClassSet = new HashSet<>();
        String pkg = aspect.pkg();
        String cls = aspect.cls();
        // 如果包名与类名均不为空，则添加指定类
        if (!pkg.equals("") && !cls.equals("")) {
            targetClassSet.add(Class.forName(pkg + "." + cls));
        } else if (!pkg.equals("")) {
            targetClassSet.addAll(ClassUtil.getClassSet(pkg));
        }
        return targetClassSet;
    }
}
