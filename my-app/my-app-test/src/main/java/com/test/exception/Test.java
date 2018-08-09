package com.test.exception;

public class Test {
    public static void init(String src){
        if(src == null){
            throw new IllegalArgumentException("not null");
        }else{
            System.out.println(src);
        }
    }

    public static void main(String[] args) {
        Test.init("1");
    }
}
