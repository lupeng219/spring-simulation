package com.lu.framework.proxy;

import com.lu.framework.bean.ProxyChain;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author lupeng
 * 2021/3/5
 * 这是一个代理工厂类, 我们通过这个类来梳理上面的代理逻辑.
 * 当调用 ProxyFactory.createProxy(final Class<?> targetClass, final List proxyList) 方法来创建一个代理对象后,
 * 每次执行代理方法时都会调用 intercept() 方法, 从而创建一个 ProxyChain 对象, 并调用该对象的 doProxyChain() 方法.
 * 调用doProxyChain()方法时会首先递归的执行增强, 最后再执行目标方法.
 */
public class ProxyFactory {

    public static <T> T createProxy (Class<?> targetClass, List<Proxy> proxyList) {
        return (T) Enhancer.create(targetClass, new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                return new ProxyChain(targetClass,o,method,methodProxy,objects, proxyList);
            }
        });
    }
}
