package generics;

import java.util.Collection;

interface Sink<T> { 
     void flush(T t);
}

public class WildcharTest1{

   public static <T> T writeAll(Collection<T> coll, Sink<T> snk){
     T last;
     for (T t : coll) { 
         last = t;
         snk.flush(last);
     }
     return last;
   }

   public static void main(String[] args) {
      Sink<Object> s;
      Collection<String> cs;
      String str = writeAll(cs, s); // illegal call
   }
}