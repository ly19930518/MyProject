package com.test.thread.blockde;

import java.awt.*;

public class test extends Thread{
    String first;
    String second;

    public test(String name,String first,String second) {
        super(name);
        this.first = first;
        this.second = second;

    }

    @Override
    public void run() {
        try{
            synchronized(first){
                System.out.println(this.getName() +"obtained"+first);
                Thread.sleep(1000L);
                synchronized (second){
                    System.out.println(this.getName()+" obtained "+second);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String [] asgr){
        test t1 = new test("Thread - 1 ","one","two");
        test t2 = new test("Thread - 2","one2","two2");
        t1.start();
        t2.start();
       try {
           t1.join();
           t2.join();

       }catch (InterruptedException e){
           e.printStackTrace();
        }

    }
}
