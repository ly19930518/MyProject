package com.test.proxy.logger.factory;

import com.test.proxy.logger.handler.MyLoggerHandler;

import java.lang.reflect.Proxy;

public class ProxyFactory {

    public static Object getProxy(Class  classz){
        Object obj = null;
        try {
            obj = classz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        /** 日志类handler **/
        MyLoggerHandler handler = new MyLoggerHandler(obj);
        /** 获取代理类对象 **/
        Object proxy = (Object) Proxy.newProxyInstance(obj.getClass().getClassLoader(),obj.getClass().getInterfaces(),handler);
        return proxy;
    }
}
