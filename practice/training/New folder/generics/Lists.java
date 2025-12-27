package generics;

import java.util.*;

class Lists {
      public static void main(String[] args) {
                 ArrayList<Integer> input = null;
                 //ArrayList<Integer> output = null;   //compile-time error
                 List<Integer> output = null;
                 output = process(input);
      }

      public static <E extends Number> List<E> process(List<E> nums) {
                 List<E> list = new ArrayList<E>();
                 return list;
      }

}
      