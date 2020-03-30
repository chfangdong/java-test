class ThreadLocalTest {
  private static ThreadLocal<Integer> seqNum = new ThreadLocal<Integer>(){
    public Integer initialValue(){
      return 0;
    }
  };

  public Integer getNextNum(){
    seqNum.set(seqNum.get() + 1);
    return seqNum.get();
  }

  public void startThreadLocalTest(){
    ThreadLocalTest threadLocalTest = new ThreadLocalTest();
    TestClient testClient1 = new TestClient(threadLocalTest);
    TestClient testClient2 = new TestClient(threadLocalTest);
    TestClient testClient3 = new TestClient(threadLocalTest);

    testClient1.start();
    testClient2.start();
    testClient3.start();
  }

  private static class TestClient extends Thread{
    private ThreadLocalTest sn;

    public TestClient(ThreadLocalTest sn) {
      this.sn = sn;
    }

    @Override
    public void run(){
      for (int i = 0; i < 3; i++) {
        System.out.println("thread[" + Thread.currentThread().getName() + "] --> sn["
            + sn.getNextNum() + "]");
      }
    }
  }
}
