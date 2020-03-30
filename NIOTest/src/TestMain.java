import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestMain {

  public static void main(String[] args) {
    nio();

    try {
      ioStream();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  static void nio() {
    RandomAccessFile randomAccessFile = null;
    try {
      randomAccessFile = new RandomAccessFile(System.getProperty("user.dir") + "\\nomal_io.txt", "rw");
      FileChannel fileChannel = randomAccessFile.getChannel();

      ByteBuffer byteBuffer = ByteBuffer.allocate(16);
      Integer num = fileChannel.read(byteBuffer);
      System.out.println("byte total num: " + num);

      byteBuffer.flip();
      while (byteBuffer.hasRemaining()) {
        System.out.println((char) byteBuffer.get());
      }
      byteBuffer.clear();

//      byteBuffer.compact();
//
//      System.out.println("****************************");
//      fileChannel.write(ByteBuffer.wrap("xyz".getBytes("utf-8")));
//      fileChannel.read(byteBuffer);
//      byteBuffer.flip();
////      byteBuffer.reset();
//
//      while (byteBuffer.hasRemaining()) {
//        System.out.println((char) byteBuffer.get());
//      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (randomAccessFile != null) {
        try {
          randomAccessFile.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  static void ioStream() throws IOException {
    InputStream in = null;
    try {
      in = new BufferedInputStream(new FileInputStream(System.getProperty("user.dir") + "\\nomal_io.txt"));
      byte[] buf = new byte[3];
      int bytesRead = in.read(buf);
      while (bytesRead != -1) {
        for (int i = 0; i < bytesRead; i++) {
          System.out.println((char) buf[i]);
        }
        bytesRead = in.read(buf);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (in != null) {
          in.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  static void zeroCopy() throws IOException {
    File file = new File("test.zip");
    RandomAccessFile raf = new RandomAccessFile(file, "rw");
    FileChannel fileChannel = raf.getChannel();
    SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("", 1234));
    // 直接使用了transferTo()进行通道间的数据传输
    fileChannel.transferTo(0, fileChannel.size(), socketChannel);
  }

  static void directMem() throws IOException {
    File file = new File("test.zip");
    RandomAccessFile raf = new RandomAccessFile(file, "rw");
    FileChannel fileChannel = raf.getChannel();
    MappedByteBuffer buffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());
    buffer.load();
    buffer.force();
  }
}
