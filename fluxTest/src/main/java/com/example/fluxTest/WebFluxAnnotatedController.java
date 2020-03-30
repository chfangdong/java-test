package com.example.fluxTest;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/annotated/")
public class WebFluxAnnotatedController {

  @Autowired
  private UserRepository userRepository;

  /**
   * 查询单个用户
   *
   * @return 返回Mono 非阻塞单个结果
   */
  @GetMapping("user/{id}")
  public Mono<User> getUserByUserId(@PathVariable("id") int id) {
    return Mono.just(userRepository.getUserByUserId().get(id));
  }

  /**
   * @return 返回Flux 非阻塞序列
   */
  @GetMapping("users")
  public Flux<User> getAll() {
    printlnThread("获取HTTP请求");
    //使用lambda表达式
    return Flux.fromStream(userRepository.getUsers().entrySet().stream().map(Map.Entry::getValue));
  }

  @GetMapping("mvc/users")
  public Map<Integer, User> getAllUsers() {
    printlnThread("获取HTTP请求");
    return userRepository.getUsers();
  }

  /**
   * 打印当前线程
   */
  private void printlnThread(Object object) {
    String threadName = Thread.currentThread().getName();
    System.out.println("HelloWorldAsyncController[" + threadName + "]: " + object);
  }
}