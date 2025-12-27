import java.util.*;

class Generics2{   
      //public static <E extends Number> List<? super E> process(List<E> nums) 
         { return new List<E> (); }
      public static <E extends Number>  List<E> process(List<E> nums) {}

      public static void main(String[] args){
          ArrayList<Integer> input = null;
          List<Integer> output = null;
          output = process(input);    // error
      }
}