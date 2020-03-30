import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public final class JavaMainTest {
  public static void main(String[] args) {
//    ArrayList arrayList = new ArrayList();
//    arrayList.add("abc");
//    arrayList.add(123);
//    List<String> aList = arrayList.subList(0, 1);
//    aList.add("xyz");
//    System.out.println(arrayList);

//    Integer var1 = 20;
//    Integer var2 = 20;
//    Integer var3 = 259;
//    Integer var4 = 259;
//    System.out.println(var1==var2);
//    System.out.println(var3.equals(var4));

//    Instant now = Instant.now().plusMillis(TimeUnit.HOURS.toMillis(8));
//    System.out.println(now);

    System.out.println("hello world");
    Math.abs(1);

    ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();
    concurrentHashMap.put("a", "b");

    Hashtable<String, String> hashtable = new Hashtable<>();

    HashMap<String, String> hashMap = new HashMap<>();
  }
}
