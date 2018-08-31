package com.test.safe.lock;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RWSample {
    private final Map<String,String>  m = new TreeMap<String,String>();
    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();//创建读写锁实例
    private final Lock r = rwl.readLock();
    private final Lock w = rwl.writeLock();

    public String get(String key){
        //加上读锁
        r.lock();
        System.out.println("读锁锁定");
        try {
            return m.get(key);
        } finally{
            //释放锁
            r.unlock();
            System.out.println("读锁释放");
        }
    }

    public void put(String key ,String value){
        w.lock();
        System.out.println("写锁锁定");
        try{
            m.put(key,value);
        }finally{
            w.unlock();
            System.out.println("写锁释放");
        }
    }

    public static void main(String[] args) {
        RWSample rw = new RWSample();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    System.out.println(rw.get("user"));
                }
            }
        });
        t1.start();


        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    rw.put("user","zhansan");
                }
            }
        });
        t2.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
