import java.util.*;

class RawType {
  public static void main(String[] args) {
   // nongeneric Java code using the raw type, same as always
    List list = new ArrayList( ); // assignment ok
    list.add("foo"); // unchecked warning on usage of raw type
  }
}