package org.common.proxy;

import java.lang.reflect.Method;



/** 切面代理
 * Created by paul on 2017/11/1.
 */
public abstract class AspectProxy implements Proxy {
    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;

        Class<?> cls = proxyChain.getTargetClass();
        Method method = proxyChain.getTargetMethod();
        Object[] params = proxyChain.getMethodParams();

        begin();

        try {
            //?
            if(intercept(cls,method,params)){
                before(cls, method, params);
                result = proxyChain.doProxyChain();
                after(cls, method, params, result);
            }else {
                result = proxyChain.doProxyChain();
            }
        } catch (Exception e) {
            //代理失败
            error(cls,method,params,e);
            throw e;
        }finally {
            end();
        }



        return null;
    }

    //一系列的“钩子方法”


    public void begin() {

    }

    public boolean intercept(Class<?> cls, Method method, Object[] params) throws Throwable {
        return true;
    }

    public void before(Class<?> cls, Method method, Object[] params) {
    }

    public void after(Class<?> cls, Method method, Object[] params, Object result) {
    }

    public void error(Class<?> cls, Method method, Object[] params, Exception e) {

    }

    public void end() {

    }
}
