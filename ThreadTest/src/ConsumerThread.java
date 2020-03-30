public class ConsumerThread extends Thread {

  private Demo demo;

  public ConsumerThread(Demo demo) {
    this.demo = demo;
  }

  public void run() {
    int i = 0;
    while (i < 10){
      demo.consume();
      i++;
    }
  }
}
