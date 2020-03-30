import java.net.URI;
import java.net.URISyntaxException;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

public class MyWebsocketClient extends WebSocketClient {

  public MyWebsocketClient(String url) throws URISyntaxException {
    super(new URI(url));
  }

  public static void main(String[] args) {
    try {
      MyWebsocketClient myWebsocketClient = new MyWebsocketClient("ws://localhost:8080/websocket");
      myWebsocketClient.connect();
      while (!myWebsocketClient.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
        System.out.println("it is not connect");
      }
      System.out.println("connection is established!");
      myWebsocketClient.send("abc");
      Thread.sleep(1000);
      myWebsocketClient.close();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void onOpen(ServerHandshake serverHandshake) {
    System.out.println("MyWebSocket onOpen");
  }

  public void onMessage(String s) {
    System.out.println("MyWebSocket onMessage: " + s);
  }

  public void onClose(int i, String s, boolean b) {
    System.out.println("MyWebSocket onClose");
  }

  public void onError(Exception e) {
    System.out.println("MyWebSocket onError");
  }
}
