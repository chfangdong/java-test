package com.ratelimiter.aop;

import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;

import com.google.common.util.concurrent.RateLimiter;

@Component
@Scope
@Aspect
public class RateLimitAop {
  @Autowired
  private HttpServletResponse response;

  private RateLimiter rateLimiter = RateLimiter.create(5.0); //比如说，我这里设置"并发数"为5

  @Pointcut("@annotation(com.ratelimiter.aop.RateLimitAspect)")
  public void serviceLimit() {

  }

  @Around("serviceLimit()")
  public Object around(ProceedingJoinPoint joinPoint) {
    Boolean flag = rateLimiter.tryAcquire();
    Object obj = null;
    try {
      if (flag) {
        obj = joinPoint.proceed();
      }else{
        String result = "failure";
        output(response, result);
      }
    } catch (Throwable e) {
      e.printStackTrace();
    }
    System.out.println("flag=" + flag + ",obj=" + obj);
    return obj;
  }

  public void output(HttpServletResponse response, String msg) throws IOException {
    response.setContentType("application/json;charset=UTF-8");
    ServletOutputStream outputStream = null;
    try {
      outputStream = response.getOutputStream();
      outputStream.write(msg.getBytes("UTF-8"));
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      outputStream.flush();
      outputStream.close();
    }
  }
}
