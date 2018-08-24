package com.test.map;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;
import java.util.Map;

public class LinkedHashMapSample {

    public static void main(String[] args) {
        LinkedHashMap<String,String> accessOrderedMap = new LinkedHashMap<String,String>(16,0.75F,true){
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
                return size() > 3;
            }
        };
        accessOrderedMap.put("p1","val1");
        accessOrderedMap.put("p2","val2");
        accessOrderedMap.put("p3","val3");
        System.out.println(accessOrderedMap.toString());

        //模拟访问
        accessOrderedMap.get("p2");
        accessOrderedMap.get("p2");
        accessOrderedMap.get("p3");
        System.out.println(accessOrderedMap.toString());
        //触发删除
        accessOrderedMap.put("p4","val4");
        System.out.println("oldest entry should be remove");
        System.out.println(accessOrderedMap.toString());
        accessOrderedMap.forEach((k,v) ->{
            System.out.println(k+"---"+v);
        });

        try {
            System.out.println(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
