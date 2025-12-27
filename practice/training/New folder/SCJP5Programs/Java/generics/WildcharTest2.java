package generics;

import java.util.Collection;
import java.util.ArrayList;

interface Sink<T> { 
     void flush(T t);
}

class SinkClass<T> implements Sink<T> {
     public void flush(T t) {
        System.out.println(t);
     }
}

public class WildcharTest2{

   public static <T> T writeAll(Collection<T> coll, Sink<? super T> snk){
     T last = null;
     for (T t : coll) { 
         last = t;
         snk.flush(last);
     }
     return last;
   }

   public static void main(String[] args) {
      Sink<Object> s = new SinkClass<Object>();
      Collection<String> cs = new ArrayList<String> () ;
      cs.add("werw");
      cs.add("w342");
      String str = writeAll(cs, s); // ok
   }
}