package com.huayun.test.websocketserver;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@ServerEndpoint(value = "/websocket")
@Component
public class WebSocketServer {

  private static Logger log = LoggerFactory.getLogger(WebSocketServer.class);
  private static final AtomicInteger OnlineCount = new AtomicInteger(0);
  // concurrent包的线程安全Set，用来存放每个客户端对应的Session对象。
  public static CopyOnWriteArraySet<Session> SessionSet = new CopyOnWriteArraySet<Session>();

  @OnOpen
  public void onOpen(Session session) throws IOException, TimeoutException {
    SessionSet.add(session);
    int cnt = OnlineCount.incrementAndGet(); // 在线数加1
    log.info("有连接加入，当前连接数为：{}", cnt);
    handleMessage(session, "连接成功");
  }

  @OnClose
  public void onClose(Session session) throws IOException {
    SessionSet.remove(session);
    int cnt = OnlineCount.decrementAndGet();
    log.info("有连接关闭，当前连接数为：{}", cnt);
  }

  @OnMessage
  public void onMessage(String message, Session session) throws IOException {
    log.info("来自客户端的消息：{}", message);
    handleMessage(session, "收到消息，消息内容：" + message);
  }

  @OnError
  public void onError(Session session, Throwable error) {
    log.error("发生错误：{}，Session ID： {}", error.getMessage(), session.getId());
    error.printStackTrace();
    SessionSet.remove(session);
  }

  public void handleMessage(Session session, String message) throws IOException {
    try {
      session.getBasicRemote().sendText(message);
      log.info(String.format("%s (From Server，Session ID=%s)", message, session.getId()));
    } catch (IOException e) {
      log.error("发送消息出错：{}", e.getMessage());
      e.printStackTrace();
      throw e;
    }
  }
}
