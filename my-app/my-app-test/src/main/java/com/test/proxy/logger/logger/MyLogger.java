package com.test.proxy.logger.logger;

import java.lang.reflect.Method;

public interface MyLogger {
    /** 记录进入方法时间 **/
    public void saveIntoMethodTime(Method method);
    /** 记录退出方法时间 **/
    public void saveQutMethodTime(Method method);
}
