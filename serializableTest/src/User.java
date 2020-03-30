import java.io.Serializable;

public class User implements Serializable {

  public void setAge(int age) {
    this.age = age;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public String getName() {
    return name;
  }

  private static final long serialVersionUID = 123456L;
  private transient int age;
  private String name;

  @Override
  public String toString() {
    return "User{" +
        "age=" + age +
        ", name='" + name + '\'' +
        '}';
  }
}
