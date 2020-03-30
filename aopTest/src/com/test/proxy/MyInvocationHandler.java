package com.test.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyInvocationHandler implements InvocationHandler {
  private Object target;

  public MyInvocationHandler(Object target) {
    this.target = target;
  }
  //第一个参数obj一般是指代理类，method是被代理的方法，args为该方法的参数数组
  @Override
  public Object invoke(Object o, Method method, Object[] args) throws Throwable {
    System.out.println("before-------切面加入逻辑");
    Object invoke = method.invoke(target, args);
    System.out.println("after-------切面加入逻辑");
    return invoke;
  }
}
