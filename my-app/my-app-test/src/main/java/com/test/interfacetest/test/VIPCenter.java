package com.test.interfacetest.test;

import java.util.HashMap;
import  java.util.Map;
public class VIPCenter {
    public void getReward(User user){
        System.out.println(new OrdinaryVip().Reward());
    }

    public static void main(String[] args) {
        new VIPCenter().getReward(new User());
    }
}
