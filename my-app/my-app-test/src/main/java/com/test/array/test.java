package com.test.array;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class test {
    public static void main(String[] args) {
        Vector<String> vector = new Vector<String>();
        vector.add("String");
        System.out.println(  vector.get(0));


        LinkedList linkedList = new LinkedList();
        System.out.println(linkedList.size());

        Set<String> set =new HashSet<String>();
        String a = "123456";
        String b = "123456";
        System.out.println(a.equals(b));
        System.out.println(a == b);

        Map<String,String> map = new HashMap<String,String>();
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<String>();
    }
}
