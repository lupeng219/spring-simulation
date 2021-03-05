package com.lu.framework.proxy;

import com.lu.framework.bean.ProxyChain;

import java.lang.reflect.Method;

/**
 * @author lupeng
 * 2021/3/5
 * AspectProxy是一个切面抽象类, 实现了Proxy接口, 类中定义了切入点判断和各种增强.
 * 当执行 doProxy() 方法时, 会先进行切入点判断, 再执行前置增强, 代理链的下一个doProxyChain()方法, 后置增强等.
 */
public abstract class AspectProxy implements Proxy {
    @Override
    public final Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;
        Method method = proxyChain.getTargetMethod();
        Object[] params = proxyChain.getMethodParams();
        begin();

        try {
            if (intercept(method, params)) {
                before(method, params);
                result = proxyChain.doProxyChain();
                after(method, params);
            } else {
                result = proxyChain.doProxyChain();
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            error(method, params, throwable);
        } finally {
            end();
        }
        return result;
    }

    /**
     * 开始增强
     */
    public void begin() {
    }

    /**
     * 切入点判断
     */
    public boolean intercept(Method method, Object[] params) throws Throwable {
        return true;
    }

    /**
     * 前置增强
     */
    public void before(Method method, Object[] params) throws Throwable {
    }

    /**
     * 后置增强
     */
    public void after(Method method, Object[] params) throws Throwable {
    }

    /**
     * 异常增强
     */
    public void error(Method method, Object[] params, Throwable e) {
    }

    /**
     * 最终增强
     */
    public void end() {
    }
}
