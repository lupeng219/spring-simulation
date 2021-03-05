package com.lu.framework.proxy;

import com.lu.framework.bean.ProxyChain;

/**
 * @author lupeng
 * 2021/3/5
 */
public interface Proxy {

     Object doProxy(ProxyChain proxyChain) throws Throwable;
}
