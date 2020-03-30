import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;


public class TestMain {

  public static void main(String[] args) throws IOException {


//    ExecutorService executorService = Executors.newCachedThreadPool();
//
//    //信号量，只允许 3个线程同时访问
//    Semaphore semaphore = new Semaphore(3);
//
//    for (int i=0;i<10;i++){
//      final long num = i;
//      executorService.submit(new Runnable() {
//        @Override
//        public void run() {
//          try {
////            System.out.println("I am thread: " + num);
//            //获取许可
//            semaphore.acquire(3);
//            //执行
//            System.out.println("Accessing: " + num);
//            Thread.sleep(new Random().nextInt(5000)); // 模拟随机执行时长
//            //释放
//            semaphore.release(3);
//            System.out.println("Release..." + num);
//          } catch (InterruptedException e) {
//            e.printStackTrace();
//          }
//        }
//      });
//    }
//
//    executorService.shutdown();


    /*-------------------------------------------------------*/
//    ThreadLocalTest threadLocalTest = new ThreadLocalTest();
//    threadLocalTest.startThreadLocalTest();

    /*-------------------------------------------------------*/
//    Demo demo = new Demo();
//    int i = 0;
//    while (i<100){
//      demo.produce();
//      demo.consume();
//      i++;
//    }

    MyRunable myRunable = new MyRunable();
    new Thread(myRunable, "t1").start();
    new Thread(myRunable, "t2").start();
    new Thread(myRunable, "t3").start();

//    MyThread myThread1 = new MyThread("t1");
//    MyThread myThread2 = new MyThread("t2");
//    MyThread myThread3 = new MyThread("t3");
//
//    myThread1.start();
//    myThread2.start();
//    myThread3.start();
//    myThread1.interrupt();
//    myThread1.isInterrupted();

//    ExecutorService executorService = Executors.newCachedThreadPool();
//    Future<Integer> result = executorService.submit(new MyCallable());
//    executorService.shutdown();
//    Integer sum = null;

//    ExecutorService executorService = Executors.newCachedThreadPool();
//    FutureTask<Integer> result = new FutureTask<>(new MyCallable());
//    executorService.submit(result);
//    executorService.shutdown();
//    Integer sum = null;

//    FutureTask<Integer> result = new FutureTask<>(new MyCallable());
//    new Thread(result, "t1").start();
//    Integer sum = null;
//
//    System.out.println("主线程在执行任务");
//
//    if (!result.isDone()){
//      System.out.println("从线程未完成");
//    }
//
//    try {
//      sum = result.get();
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    } catch (ExecutionException e) {
//      e.printStackTrace();
//    }
//
//    if (sum != null){
//      System.out.println("task运行结果:"+sum);
//    }else {
//      System.out.println("未获取到结果");
//    }
//
//    System.out.println("所有任务执行完毕");

//    ThreadTest threadTest = new ThreadTest();
//    threadTest.startMuliThread();


//    ReentrantLockRunable reentrantLockRunable = new ReentrantLockRunable();
//    new Thread(reentrantLockRunable, "t1").start();
//    new Thread(reentrantLockRunable, "t2").start();


    /*--------------------------------------------------*/
//    User user = new User();
//    user.setName("tony");
//    user.setAge(31);

//    Class<User> userClass = (Class<User>) Class.forName("User");
//    Class<User> userClass = (Class<User>) user.getClass();
//    Method[] methods = userClass.getDeclaredMethods();
//    Field nameField = userClass.getDeclaredField("name");
//    System.out.println(nameField);

    //1、获取系统类的加载器
//    ClassLoader classLoader = ClassLoader.getSystemClassLoader();
//    System.out.println(classLoader);

    //2. 获取系统类加载器的父类加载器（扩展类加载器，可以获取）.
//    classLoader = classLoader.getParent();
//    System.out.println(classLoader);

    //3. 获取扩展类加载器的父类加载器（引导类加载器，不可获取）.
//    classLoader = classLoader.getParent();
//    System.out.println(classLoader);

//    User user1 = userClass.newInstance();
//    user1.setName("cfd");
//    user1.setAge(20);
//    System.out.println(user1.getName());
//    System.out.println(user.getClass().getName());
//    System.out.println(User.class.getClassLoader());
  }
}
