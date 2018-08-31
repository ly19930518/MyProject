package com.test.safe.lock;

import java.util.Date;
import java.util.concurrent.locks.StampedLock;

/**
 *  显式锁
 * 另外一种读写锁
 */
public class StampedSample {
    private final StampedLock sl = new StampedLock();
    void mutate(){
        long stamp = sl.writeLock();
        try {

        }finally{
            sl.unlockWrite(stamp);
        }
    }


    Date access(){
        long stamp = sl.tryOptimisticRead();
        Date date = new Date();
        if(!sl.validate(stamp)){//检查是否进入写模式  如果不进入则成功避免了开销
            stamp = sl.readLock();
            try{
                date = new Date();
            }finally {
                sl.unlockRead(stamp);
            }
        }
        return date;
    }

    public static void main(String[] args) {
        StampedSample stamped = new StampedSample();
        stamped.access();

    }
}
