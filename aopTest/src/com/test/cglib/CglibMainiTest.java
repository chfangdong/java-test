package com.test.cglib;

import org.springframework.cglib.proxy.Enhancer;

public class CglibMainiTest {
  public static void main(String[] args) {
    CglibInterceptor interceptor = new CglibInterceptor();

    //Enhancer类是CGLib中的一个字节码增强器，它可以方便的对你想要处理的类进行扩展
    Enhancer enhancer = new Enhancer();
    enhancer.setSuperclass(Base.class);
    //设置拦截器，回调方法的参数为拦截器
    enhancer.setCallback(interceptor);

    //此刻，base不是单纯的目标类，而是增强过的目标类
    Base base = (Base) enhancer.create();
    base.add("demo");

    Class<? extends Base> baseClass = base.getClass();
    //查看增强过的类的父类是不是未增强的Base类
    System.out.println("增强过的类的父类："+baseClass.getSuperclass().getName());

//    System.out.println("============打印增强过的类的所有方法==============");
//    FanSheUtils.printMethods(baseClass);


    //没有被增强过的base类
//    Base base2 = new Base();
//    System.out.println("未增强过的类的父类："+base2.getClass().getSuperclass().getName());
//    System.out.println("=============打印增未强过的目标类的方法===============");
//    FanSheUtils.printMethods(base2.getClass());//打印没有增强过的类的所有方法
  }
}
