package com.example.reactiveWebTest;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/test")
public class StudentController {
  @GetMapping("/time")
  public Mono<String> getTime() throws InterruptedException {
    Thread.sleep(10000);
    return Mono.just("Now is " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
  }
}
