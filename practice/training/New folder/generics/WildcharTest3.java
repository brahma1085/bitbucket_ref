package generics;

import java.util.*;

interface Sink<T> { 
     void flush(T t);
}

class SinkClass<T> implements Sink<T> {
     public void flush(T t) {
        System.out.println(t);
     }
}

public class WildcharTest3{

   public static <T> T writeAll(List<? extends T> coll, Sink<T> snk){
     T last = null;
     for (T t : coll) { 
         last = t;
         snk.flush(last);
     }
     return last;
   }

   public static <T> void addToExtends(List<? extends T> coll, T t) {
     //coll.add(t);   //compile-time err: cannot add
   }

   public static <T> void addToSuper(List<? super T> coll, T t) {
      coll.add(t);   //ok
      //coll.add((t.getClass()).newInstance("sss2"));   //compile-time error
   }

   public static void main(String[] args) {
      Sink<Object> s = new SinkClass<Object>();
      List<String> cs = new ArrayList<String> () ;
      cs.add("werw");
      cs.add("w342");
      String str = (String)writeAll(cs, s); // ok but not good as WildcharTest2.java

      List<? super String> cs2 = new ArrayList<String>();
      cs2.add("sss");  //ok
      boolean b = cs2.contains("sss");   
      System.out.println(b);   //"true"
      Object o = cs2.get(0);   //ok
      System.out.println(o);   //"sss"
      String s1 = (String)cs2.get(0);  //ok

      List<Sink<Object>> sl = new ArrayList<Sink<Object>>();
      sl.add(new SinkClass<Object>());      
      List<? extends Sink<Object>> slw = sl;
      Sink<Object> si= slw.get(0);   //ok
      //slw.add(new SinkClass<Object>());  //compile-time err
 
      addToExtends(cs, "sss");
      addToSuper(cs, "sss");
   }
}