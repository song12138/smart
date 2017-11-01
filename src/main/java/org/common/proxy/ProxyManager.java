package org.common.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/** 代理管理器
 * Created by paul on 2017/11/1.
 */
public class ProxyManager {
    public static <T> T creatProxy(final Class<T> targetClass, final List<Proxy> proxyList) {
        return (T)Enhancer.create(targetClass, new MethodInterceptor() {
            @Override
            public Object intercept(Object targetObject, Method targetMethod, Object[] methodParam, MethodProxy methodProxy) throws Throwable {
                return new ProxyChain(targetClass,targetObject,targetMethod,methodProxy,methodParam,proxyList).doProxyChain();
            }
        });
    }

}
