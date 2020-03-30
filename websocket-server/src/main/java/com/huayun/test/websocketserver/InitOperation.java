package com.huayun.test.websocketserver;

import javax.websocket.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitOperation implements CommandLineRunner {

  @Autowired
  WebSocketServer webSocketServer;

  @Override
  public void run(String... args) throws Exception {
    boolean flag = WebSocketServer.SessionSet.isEmpty();
    while (flag) {
      flag = WebSocketServer.SessionSet.isEmpty();
      System.out.println("It is waiting session!");
      Thread.sleep(1000);
    }


    Session session = WebSocketServer.SessionSet.iterator().next();

    for (int i = 0; i < 10; i++) {
      webSocketServer.handleMessage(session, "My Number is " + i);
    }
  }
}
