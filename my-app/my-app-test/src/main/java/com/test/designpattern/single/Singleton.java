package com.test.designpattern.single;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Lock;

/**
 * 单例模式
 */
public class Singleton {
    private volatile static Singleton instance;
    private Singleton() {
    }
    public static Singleton getInstance(){
        //改版保证线程安全
        if(instance == null){//避免重复进入同步块
            synchronized (Singleton.class){//同步.class ,意味着对同步类方法调用
                if(instance == null ){
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
    public void Test(){
        System.out.println("测试成功.........");
    }

    public static void main(String[] args) {
        Set<Singleton> set = new HashSet<Singleton>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0 ; i < 100000;i++){
                    Singleton singleton = Singleton.getInstance();
                    set.add(singleton);
                }

                System.out.println("执行完成");
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0 ; i < 100000;i++){
                    Singleton singleton = Singleton.getInstance();
                    set.add(singleton);
                }
                System.out.println("执行完成");
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0 ; i < 100000;i++){
                    Singleton singleton = Singleton.getInstance();
                    set.add(singleton);
                }
                System.out.println("执行完成");
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0 ; i < 100000;i++){
                    Singleton singleton = Singleton.getInstance();
                    set.add(singleton);
                }
                System.out.println("执行完成");
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0 ; i < 100000;i++){
                    Singleton singleton = Singleton.getInstance();
                    set.add(singleton);
                }
                System.out.println("执行完成");
            }
        }).start();


        try {
            Thread.sleep(1000 * 10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(set.size());


    }
}
