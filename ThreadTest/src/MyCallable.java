import java.util.concurrent.Callable;

public class MyCallable implements Callable<Integer> {
  @Override
  public Integer call() throws Exception {
    System.out.println("It is running");
    Thread.sleep(10000);
    int sum = 0;
    for (int i = 0; i < 100; i++) {
      sum += i;
    }
    return sum;
  }
}
