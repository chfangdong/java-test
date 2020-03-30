class MyRunable implements Runnable {
  private int num = 10;
  @Override
  public void run() {
    synchronized (this) {
      for (int i = 0; i < 20; i++) {
        if(num > 0){
          System.out.println(Thread.currentThread().getName() + ":" + (num--));
        }
      }
    }
  }
}
