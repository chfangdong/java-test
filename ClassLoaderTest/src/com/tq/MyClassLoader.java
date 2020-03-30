package com.tq;

import java.io.InputStream;

public class MyClassLoader extends ClassLoader{
  public MyClassLoader() {
    super(null);
  }

  @Override
  public Class<?> loadClass(String name) throws ClassNotFoundException {
    try{
      String className = null;
      if(name.startsWith("java.lang")){
        className = "/" + name.replace('.', '/') + ".class";
      }else{
        className = name.substring(name.lastIndexOf('.') + 1) + ".class";
      }
      System.out.println(className);
      InputStream is = getClass().getResourceAsStream(className);
      System.out.println(is);
      if(is == null)
        return super.loadClass(name);

      byte[] b = new byte[is.available()];
      is.read(b);
      return defineClass(name, b, 0, b.length);
    }catch (Exception e) {
      e.printStackTrace();
      throw new ClassNotFoundException();
    }
  }

}
