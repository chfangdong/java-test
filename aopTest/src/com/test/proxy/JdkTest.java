package com.test.proxy;

import java.lang.reflect.Proxy;

public class JdkTest {

  public static void main(String[] args) {
    JdkClass jdkClass = new JdkClass();
    MyInvocationHandler handler = new MyInvocationHandler(jdkClass);

    // Proxy为动态代理类
    //这里的proxyInstance就是我们目标类的增强代理类
    JdkInterface proxyInstance = (JdkInterface) Proxy
        .newProxyInstance(jdkClass.getClass().getClassLoader(), jdkClass.getClass().getInterfaces(), handler);

    proxyInstance.add("demo");

    //打印增强过的类类型
    System.out.println("=============" + proxyInstance.getClass());
  }
}
