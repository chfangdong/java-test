package com.example.reactiveWebTest;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class WebFluxRoutingConfiguration {

  @Autowired
  TimeHandler timeHandler;

  @Bean
  public RouterFunction<ServerResponse> timerRouter() {
    return RouterFunctions.route(GET("/time"), req -> timeHandler.getTime(req))
        .andRoute(GET("/getStudents"), timeHandler::getStudents)
        .andRoute(GET("/getTimePerSecond"), timeHandler::getStudents);
  }
}
