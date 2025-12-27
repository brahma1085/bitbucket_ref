package classes;

import java.util.List;

class Parent {
      <T> void o(List<T> l) {}
      //void m(List li) {}   //not ok
      void n(List<Integer> l) {}   //ok
}

class Child extends Parent {
      //void o(List<Integer> li) {}  //not ok
      void o(List li) {}
      //void m(List<Integer> li) {}   //not ok
      //void n(List l) {}   //ok
      //void n(List< Long> l) {}   //not ok
}

public class Overriding1 {
      public static void main(String[] args) {
          Child p = new Child();
          
      }
}