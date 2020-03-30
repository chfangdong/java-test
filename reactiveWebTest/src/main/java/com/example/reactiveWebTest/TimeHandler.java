package com.example.reactiveWebTest;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class TimeHandler {
  public Mono<ServerResponse> getTime(ServerRequest serverRequest) {
    return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
        .body(Mono.just("Now is " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())), String.class);
  }

  /**
   * 模拟DB获取数据
   * @param serverRequest
   * @return
   */
  public Mono<ServerResponse> getStudents(ServerRequest serverRequest) {
    List<Student> stus = Arrays.asList(Student.builder().age(10).name("zhangsan").build(),
        Student.builder().age(20).name("lisi").build());
    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)//这里改为json
        .body(Flux.just(stus), List.class);
  }

}
