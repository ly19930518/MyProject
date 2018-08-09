package com.test.proxy.logger.handler;

import com.test.proxy.logger.logger.MyLogger;
import com.test.proxy.logger.logger.MyLoggerImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyLoggerHandler2 implements InvocationHandler {
    /** 原始对象 **/
    private Object objOriginal;

    private MyLogger myLogger = new MyLoggerImpl();
    public MyLoggerHandler2(Object obj){
        this.objOriginal = obj;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        /**调用日志方法 **/
        myLogger.saveIntoMethodTime(method);
        System.out.println("myLoggerHandler2...............");
        result = method.invoke(this.objOriginal,args);
        myLogger.saveQutMethodTime(method);
        return result;
    }
}