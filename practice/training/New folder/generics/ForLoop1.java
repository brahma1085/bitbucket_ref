package generics;

import java.util.*;

public class ForLoop1 {
     static void insert(List l1) {
          l1.add(new Float(12.3));
    }

     public static void main(String[] args) {
           List<Integer> I = new ArrayList<Integer>();
           insert(I);
           Object o = I.get(0);
           for (Object i: I) 
                   System.out.println(i);
     }
}