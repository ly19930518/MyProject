package com.test.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class test {
    public static void main(String[] args) {
        HelloImpl hello = new HelloImpl("hello my world");
        MyInvocationHandler handler = new MyInvocationHandler(hello);
        //构造代码实例
        Hello proxyHello = (Hello) Proxy.newProxyInstance(HelloImpl.class.getClassLoader(),HelloImpl.class.getInterfaces(),handler);
        //调用代理方法
        proxyHello.sayHello();
    }
}

interface Hello{
    void sayHello();
}
class HelloImpl implements Hello{
    private String text;
    public HelloImpl(String text){
        this.text = text;
    }
    @Override
    public void sayHello() {
        System.out.println(text);
    }
}


class MyInvocationHandler implements InvocationHandler{
    private Object target;
    public MyInvocationHandler(Object target){
        this.target = target;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("invoking sayhello");
        Object result = method.invoke(target,args);
        return result;
    }
}