public class StackOverflowErrorExample {
  public static void main(String args[]) {
    a();
  }
  public static void a() {
    a();
  }
}
