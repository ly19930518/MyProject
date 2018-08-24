package com.test.proxy.logger.test;

import com.test.proxy.logger.container.Container;
import com.test.proxy.logger.factory.ProxyFactory;
import com.test.proxy.logger.handler.MyLoggerHandler;
import com.test.proxy.logger.service.BusinessClassService;
import com.test.proxy.logger.service.BusinessClassServiceImpl;
import com.test.proxy.logger.service.UserService;
import com.test.proxy.logger.service.UserServiceImpl;
import org.springframework.security.core.userdetails.User;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class MyLoggerTest<T> {
    public static void main(String[] args) {
        /** 实例化真实项目中业务类 **/
        BusinessClassService businessClassService = new BusinessClassServiceImpl();
//        /** 日志类handler **/
//        MyLoggerHandler handler = new MyLoggerHandler(businessClassService);
//        /** 获取代理类对象 **/
//        BusinessClassService proxy = (BusinessClassService) Proxy.newProxyInstance(businessClassService.getClass().getClassLoader(),businessClassService.getClass().getInterfaces(),handler);
//        /** 执行方法 **/
//        BusinessClassService proxy = (BusinessClassService) ProxyFactory.getProxy(BusinessClassServiceImpl.class);
//        proxy.doSomething();
       /* UserService userService = (UserService)ProxyFactory.getProxy(UserServiceImpl.class);
        userService.getUser();*/

       /** 模拟spring 容器 **/
        System.out.println("模拟Spring AOP容器初始化中.......");
        Container.addInstance(UserServiceImpl.class);
        Container.addInstance(BusinessClassServiceImpl.class);
        System.out.println("模拟Spring AOP容器初始化完成.......");

        try {
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    UserService userService = (UserService) Container.getInstance(UserServiceImpl.class);
                    userService.getUser();
                    try {
                        Thread.sleep( 3 * 1000 );
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t1.start();
        try {
            Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    BusinessClassService businessClassService = (BusinessClassService) Container.getInstance(BusinessClassServiceImpl.class);
                    businessClassService.doSomething();
                    try {
                        Thread.sleep( 3 * 1000 );
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t2.start();

    }


}
