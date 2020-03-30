import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class SocketNIOSelectorServer {

  private static final int BUF_SIZE = 1024;
  private static final int PORT = 8000;
  private static final int TIMEOUT = 3000;

  public static void main(String args[]) {
    selector();
  }

  public static void handleAccept(SelectionKey key) throws IOException {
    ServerSocketChannel ssChannel = (ServerSocketChannel) key.channel();
    SocketChannel sc = ssChannel.accept();
    sc.configureBlocking(false);
    sc.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocateDirect(BUF_SIZE));
  }

  public static void handleRead(SelectionKey key) throws IOException {
    SocketChannel sc = (SocketChannel) key.channel();
    ByteBuffer buf = (ByteBuffer) key.attachment();
    long bytesRead = sc.read(buf);
    while (bytesRead > 0) {
      buf.flip();
      while (buf.hasRemaining()) {
        System.out.print((char) buf.get());
      }
      System.out.println();
      buf.clear();
      bytesRead = sc.read(buf);
    }
    if (bytesRead == -1) {
      sc.close();
    }
  }

  public static void handleWrite(SelectionKey key) throws IOException {
    ByteBuffer buf = (ByteBuffer) key.attachment();
    buf.flip();
    SocketChannel sc = (SocketChannel) key.channel();
    while (buf.hasRemaining()) {
      sc.write(buf);
    }
    buf.compact();
  }

  //  把一个套接字通道(SocketChannel)注册到一个选择器(Selector)中,不时调用Selector的select()和selectedKeys()
  //  就能返回满足的选择键(SelectionKey), 键中包含了SOCKET事件信息。这就是select模型。
  public static void selector() {
    Selector selector = null;
    ServerSocketChannel serverSocketChannel = null;

    try {
      selector = Selector.open();
      serverSocketChannel = ServerSocketChannel.open();
      serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
      serverSocketChannel.configureBlocking(false);

      //将channel注册到selector，其中OP_ACCEPT表示interest集合，selector监听channel时，对什么事件感兴趣
      //Connect、Accept、Read、Write四种事件
      serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

      while (true) {
        //select()返回值表示有多少通道已经就绪
        int num = selector.select(TIMEOUT);
        if (num == 0) {
          System.out.println("==");
          continue;
        }
        //调用select()后，然后调用selectedKeys()方法，访问“已选择键集（selected key set）”中的就绪通道
        Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
        while (iterator.hasNext()) {
          SelectionKey selectionKey = iterator.next();
          if (selectionKey.isAcceptable()) {
            handleAccept(selectionKey);
          }

          if (selectionKey.isReadable()) {
            handleRead(selectionKey);
          }

          if (selectionKey.isWritable() && selectionKey.isValid()) {
            handleWrite(selectionKey);
          }

          if (selectionKey.isConnectable()) {
            System.out.println("isConnectable = true");
          }
          iterator.remove();
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (selector != null) {
          selector.close();
        }
        if (serverSocketChannel != null) {
          serverSocketChannel.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }

    }
  }
}
