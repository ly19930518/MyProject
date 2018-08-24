package com.test.lang.integer;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;

/**
 * 利用元素类型实现计数器
 * 保证线程安全
 */
public class CompactCounter {
    private volatile  long counter;
    private final static AtomicLongFieldUpdater<CompactCounter> updater = AtomicLongFieldUpdater.newUpdater(CompactCounter.class,"counter");
    public void Increase(){
        updater.incrementAndGet(this);
    }
}
