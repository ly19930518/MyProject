package com.test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Test1 {
    public static void main(String[] args) {
        Map<String,String> studentManage = new HashMap<String,String>();
        studentManage.put("小白","11");
        studentManage.put("小华","15");

        Set<Map.Entry<String,String>> set =  studentManage.entrySet();
        Iterator<Map.Entry<String,String>> it = set.iterator();
        while(it.hasNext()){
            Map.Entry<String,String> entry = it.next();
            String key = entry.getKey();
            System.out.println("key:"+key+"value:"+entry.getValue());
            if("小华".equals(key)){
                it.remove();
            }
        }
        ConcurrentMap c =  new ConcurrentHashMap();
    }

}
