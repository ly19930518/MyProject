package com.test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Test1 {

    public void test(){
        Test2 t2 = new Test2();
        t2.test();

    }
    public static void main(String[] args) {

    }

}
class Test2{
    public void test(){
        System.out.println("xxxx");
    }
}