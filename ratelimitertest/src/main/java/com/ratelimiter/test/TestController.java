package com.ratelimiter.test;

import com.ratelimiter.aop.RateLimitAspect;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

  @ResponseBody
  @RateLimitAspect
  @RequestMapping("/test")
  public String test() {
    return "success";
  }
}
