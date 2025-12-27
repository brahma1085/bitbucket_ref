package classes;

import java.util.List;

class Parent {
      static void n(List l) {}  
      void m() {System.out.println("Parent's method m()"); }
      void p() {}
      static void q() {System.out.println("Parent's static method q()"); }
}

class Child extends Parent {
      //void n(List l) {}   // not allowed an instance method to override parent's static method
      static void n(List l) {}  //static override static, ok
      void m() {System.out.println("Child's method m()");}
      void n() { 
              super.m();    //"Parent's method m()"
              super.q();     //"Parent's static method q()"
      }
      //static void p() {} // not allowed an static method to override parent's instance method
      static void q() {System.out.println("Child's static method q()");}   //hiding is allowed
}

public class Overriding2 {
      public static void main(String[] args) {
          Child p = new Child();
          ((Parent)p).m();   // still print out "Child's method"
          p.n(); 
          p.q();
          ((Parent)p).q();
      }
}