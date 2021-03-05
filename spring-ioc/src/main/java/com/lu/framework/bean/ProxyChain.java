package com.lu.framework.bean;

import com.lu.framework.proxy.Proxy;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lupeng
 * 2021/3/5
 */
public class ProxyChain {

    private final Class<?> targetClass;
    private final Object targetObject;
    private final Method targetMethod;
    private final MethodProxy methodProxy;
    private final Object[] params;
    private List<Proxy> proxyList = new ArrayList<>(); //代理列表
    private int proxyIndex = 0; //代理索引

    public ProxyChain(Class<?> targetClass, Object targetObject, Method targetMethod, MethodProxy methodProxy, Object[] params,  List<Proxy> proxyList) {
        this.targetClass = targetClass;
        this.targetObject = targetObject;
        this.methodProxy = methodProxy;
        this.params = params;
        this.targetMethod = targetMethod;
        this.proxyList = proxyList;
    }
    public Object[] getMethodParams() {
        return params;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    /**
     * 循环调用
     * @return
     * @throws Throwable
     */
    public Object doProxyChain () throws Throwable{
        Object methodResult;
        if (proxyIndex < proxyList.size()) {
            methodResult = proxyList.get(proxyIndex++).doProxy(this);
        } else {
            methodResult = methodProxy.invokeSuper(targetObject, params);
        }
        return methodResult;
    }
}
