import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class TestMain {
  public static void main(String args[]){
    try {
      serializedUser();
      deSerializedUser();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  private static void serializedUser() throws IOException {
    User user = new User();
    user.setName("cfd");
    user.setAge(34);
    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("E://test.txt"));
    oos.writeObject(user);
    oos.close();
    System.out.println("add transisent field age:" + user.getAge());
  }

  private static void deSerializedUser() throws IOException, ClassNotFoundException {
    RandomAccessFile randomAccessFile = new RandomAccessFile("E://test.txt", "rw");
    ByteBuffer byteBuffer = ByteBuffer.allocate(64);
    FileChannel fileChannel = randomAccessFile.getChannel();
    fileChannel.read(byteBuffer);

    byteBuffer.flip();
    while (byteBuffer.hasRemaining()){
      byteBuffer.get();
    }
    byteBuffer.clear();

    InputStream inputStream = new ByteArrayInputStream(byteBuffer.array());

    ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

    User user = null;
    try {
      user = (User)objectInputStream.readObject();
    }catch (EOFException eofException){
      System.out.println("read stop");
    }

    inputStream.close();

    System.out.println("after deSerialized field age:" + user.getAge());
  }
}
