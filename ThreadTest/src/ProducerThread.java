public class ProducerThread extends Thread{
  private Demo demo;
  public ProducerThread(Demo demo) {
    this.demo = demo;
  }
  public void run(){
    int i = 0;
    while (i<10){
      demo.produce();
      i++;
    }
  }
}
