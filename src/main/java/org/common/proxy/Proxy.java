package org.common.proxy;

/** 代理接口
 * Created by paul on 2017/11/1.
 */
public interface Proxy {
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
