package com.test.lang.integer;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 计数器
 */
public class Counter {
    private static final AtomicLong counter = new AtomicLong();
    public static long  Increase(){
        counter.incrementAndGet();
        return counter.get();
    }
    public static long getCounter(){
        return counter.get();
    }
}
