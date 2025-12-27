package generics;

import java.util.*;

public class SimpleList {
   public static void main(String[] args) {
       List<Integer> myIntList = new LinkedList<Integer>(); // 1¡¯
       myIntList.add(new Integer(10)); //2¡¯
       Integer x = myIntList.iterator().next(); // 3¡¯
       System.out.println("x is "+x);

       List<String> ls = new ArrayList<String>(); //1
       //List<Object> lo = ls; //2     compile-time error: incompatible types
       List<?> lw = ls;
       //lw.add(new Object()); // 3   compile-time error
       lw.add(null);   // no error
       Object o = lw.get(0);   // ok
       String s = (String)lw.get(0);    // no err at all
       String ss = ls.get(0);      // ok
   }
}