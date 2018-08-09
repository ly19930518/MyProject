package com.test.proxy.logger.logger;

import org.apache.log4j.Logger;

import java.lang.reflect.Method;

public class MyLoggerImpl implements MyLogger
{
    @Override
    public void saveIntoMethodTime(Method method) {
        System.out.println("进入 ： "+method.getName()+"方法时间："+System.currentTimeMillis());
    }

    @Override
    public void saveQutMethodTime(Method method) {
        System.out.println("退出："+method.getName()+"方法时间:"+System.currentTimeMillis());
    }
}
