import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketIOServer {
  public static void main(String args[]){
    server();
  }

  public static void server() {
    ServerSocket serverSocket = null;
    InputStream inputStream = null;

    try {
      serverSocket = new ServerSocket(8000);
      byte[] buffer = new byte[1024];
      int recvMsgSize = 0;
      while (true) {
        Socket socket = serverSocket.accept();
        System.out.println("Handling client at " + socket.getInetAddress());
        inputStream = socket.getInputStream();
        while ((recvMsgSize = inputStream.read(buffer)) != -1) {
          byte[] tmpBuffer = new byte[recvMsgSize];
          System.arraycopy(buffer, 0, tmpBuffer, 0, recvMsgSize);
          System.out.println(new String(tmpBuffer));
        }
      }


    } catch (IOException e) {
      e.printStackTrace();
    } finally {

      try {
        if (serverSocket != null) {
          serverSocket.close();
        }
        if (inputStream != null) {
          inputStream.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
