import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockRunable implements Runnable{
  private Lock reentrantLock = new ReentrantLock();
  @Override
  public void run() {
    reentrantLock.lock();
    try {
      for (int i = 0; i < 10; i++) {
        System.out.println(Thread.currentThread().getName() + ":" + i);
      }

      reentrantLock.lock();
      System.out.println("I am reentrant: " + Thread.currentThread().getName());
      reentrantLock.unlock();
    }finally {
      reentrantLock.unlock();
    }
  }
}
