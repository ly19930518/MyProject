package com.test.lang.integer;

import java.util.concurrent.atomic.AtomicInteger;

public class IntegerTest {
    AtomicInteger counter = new AtomicInteger();

    public void Increase(){
        counter.incrementAndGet();
    }
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true){
                    System.out.println(Counter.Increase());
                }
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true){
                    System.out.println(Counter.Increase());
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true){
                    System.out.println(Counter.Increase());
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true){
                    System.out.println(Counter.Increase());
                }
            }
        }).start();
    }
}
