
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class ThreadTest {

  public void startMuliThread() throws IOException {
    int corePoolSize = 2;
    int maximumPoolSize = 4;
    long keepAliveTime = 10;
    TimeUnit timeUnit = TimeUnit.SECONDS;

    BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(2);

    NameThreadFactory nameThreadFactory = new NameThreadFactory();
    RejectedExecutionHandler rejectedExecutionHandler = new MyIgnorePolicy();

    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, timeUnit,
        blockingQueue, nameThreadFactory, rejectedExecutionHandler);
    threadPoolExecutor.prestartAllCoreThreads();

    for (int i = 1; i <= 10; i++) {
      MyTask myTask = new MyTask(String.valueOf(i));
      threadPoolExecutor.execute(myTask);
    }
    threadPoolExecutor.shutdown();
    System.in.read();

  }

  static class NameThreadFactory implements ThreadFactory {

    private final AtomicInteger mThreadNum = new AtomicInteger(1);

    @Override
    public Thread newThread(Runnable r) {
      Thread newThread = new Thread(r, "my-thread-" + mThreadNum.getAndIncrement());
      System.out.println(newThread.getName() + " has been created");
      return newThread;
    }
  }

  static class MyIgnorePolicy implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
      doLog(r, executor);
    }

    private void doLog(Runnable r, ThreadPoolExecutor executor) {
      System.err.println(r.toString() + " rejected");
//      System.out.println("completedTaskCount: " + executor.getCompletedTaskCount());
    }
  }

  class MyTask implements Runnable{
    private String name;

    public MyTask(String name) {
      this.name = name;
    }

    @Override
    public void run() {
      try {
        System.out.println(this.toString() + " is running!");
        Thread.sleep(3000); //让任务执行慢点
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    public String getName() {
      return name;
    }

    @Override
    public String toString() {
      return "MyTask [name=" + name + "]";
    }
  }
}
