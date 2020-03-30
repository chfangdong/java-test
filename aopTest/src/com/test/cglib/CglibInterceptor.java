package com.test.cglib;

import java.lang.reflect.Method;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

public class CglibInterceptor implements MethodInterceptor {
  //Object为由CGLib动态生成的代理类实例
  //Method为上文中实体类所调用的被代理的方法引用
  //Object[]为参数值列表，MethodProxy为生成的代理类对方法的代理引用
  @Override
  public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
    System.out.println("before-------切面加入逻辑");
    methodProxy.invokeSuper(o, args);
    System.out.println("after-------切面加入逻辑");
    return null;
  }
}
