package com.test.proxy.logger.container;

import com.test.proxy.logger.handler.MyLoggerHandler;
import com.test.proxy.logger.handler.MyLoggerHandler2;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class Container {
    /** 利用集合存放实例对象 **/
    private static Map<Class,Object> container = new HashMap<Class, Object>();
    public static Object addInstance(Class classz){
        /** 利用反射创建新实例 **/
        Object obj = null;
        try {
            obj = classz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if(obj != null){
            //模拟aop 实现通用日志
            MyLoggerHandler handler = new MyLoggerHandler(obj);
            Object proxy = Proxy.newProxyInstance(classz.getClassLoader(),classz.getInterfaces(),handler);
            System.out.println(classz.getName()+"初始化成功");
            container.put(classz,proxy);
        }
        return obj;
    }

    public static Object getInstance(Class classz){
        return container.get(classz);
    }
}
