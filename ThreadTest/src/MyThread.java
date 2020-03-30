class MyThread extends Thread{
  MyThread(String name){
    super.setName(name);
  }

  private int num = 10;
  @Override
  public void run() {
    for (int i = 0; i < 20; i++) {
      if(num > 0){
        System.out.println(Thread.currentThread().getName() + ":" + (num--));
      }
    }
  }
}
