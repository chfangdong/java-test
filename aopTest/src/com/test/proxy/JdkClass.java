package com.test.proxy;

public class JdkClass implements JdkInterface {

  @Override
  public void add(String input) {
    System.out.println("目标类的add方法, 参数：" + input);
  }
}
