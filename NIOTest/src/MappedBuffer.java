import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

public class MappedBuffer {
  public static void main(String args[]){
    mappedRead();
//    System.out.println("=============");
//    directRead();
  }

  public static void mappedRead(){
    RandomAccessFile aFile = null;
    FileChannel fileChannel = null;

    try {
      aFile = new RandomAccessFile(System.getProperty("user.dir") + "\\NIOTest\\1.pptx", "rw");
//      aFile = new RandomAccessFile(System.getProperty("user.dir") + "\\NIOTest\\1.txt", "rw");
      fileChannel = aFile.getChannel();
      long fileLength = aFile.length();

      long timeBegin = System.currentTimeMillis();
      MappedByteBuffer mappedByteBuffer = fileChannel.map(MapMode.READ_ONLY, 0, fileLength);

      System.out.println((char)mappedByteBuffer.get((int)fileLength/2));

//      mappedByteBuffer.put(ByteBuffer.wrap("xyz".getBytes("utf-8")));

      long timeEnd = System.currentTimeMillis();
      System.out.println("Read time: "+(timeEnd-timeBegin)+"ms");

    } catch (IOException e) {
      e.printStackTrace();
    }finally {
      try{
        if(aFile!=null){
          aFile.close();
        }
        if(fileChannel!=null){
          fileChannel.close();
        }
      }catch(IOException e){
        e.printStackTrace();
      }

    }
  }

  public static void directRead(){
    RandomAccessFile aFile = null;
    FileChannel fileChannel = null;

    try {
      aFile = new RandomAccessFile(System.getProperty("user.dir") + "\\NIOTest\\1.pptx", "rw");
      long fileLength = aFile.length();

      fileChannel = aFile.getChannel();

      long timeBegin = System.currentTimeMillis();
      ByteBuffer buffer = ByteBuffer.allocate((int)fileLength);

      buffer.clear();
      fileChannel.read(buffer);

      System.out.println((char)buffer.get((int)fileLength/2));
      long timeEnd = System.currentTimeMillis();
      System.out.println("Read time: "+(timeEnd-timeBegin)+"ms");

    } catch (IOException e) {
      e.printStackTrace();
    }finally {
      try{
        if(aFile!=null){
          aFile.close();
        }
        if(fileChannel!=null){
          fileChannel.close();
        }
      }catch(IOException e){
        e.printStackTrace();
      }

    }
  }
}
