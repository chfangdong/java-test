import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

public class SocketNIOClient {
  private static InetSocketAddress inetSocketAddress;
  public static void main(String args[]){
    inetSocketAddress = new InetSocketAddress("localhost", 8000);
    client();
  }

  public static void client (){
    for (int i = 0; i < 2; i++){
      new Thread(new Runnable() {
        @Override
        public void run() {
          ByteBuffer buffer = ByteBuffer.allocate(102);
          SocketChannel socketChannel = null;

          try {
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(inetSocketAddress);

            if (socketChannel.finishConnect()){
              int i = 0;
              while (true){
                TimeUnit.SECONDS.sleep(1);
                String info = Thread.currentThread().getName() + ": I'm " + (i++) +"-th information from client";
                buffer.clear();
                buffer.put(info.getBytes());
                buffer.flip();

                while (buffer.hasRemaining()){
                  System.out.println(buffer);
                  socketChannel.write(buffer);
                }
              }

            }
          } catch (IOException | InterruptedException e) {
            e.printStackTrace();
          }finally {
            if (socketChannel != null){
              try {
                socketChannel.close();
              } catch (IOException e) {
                e.printStackTrace();
              }
            }
          }
        }
      }).start();

    }

  }
}
