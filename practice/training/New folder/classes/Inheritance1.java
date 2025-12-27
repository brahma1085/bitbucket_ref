package classes;

class A { int i=10;  void m() { System.out.println("A: "+i); }}
class B extends A {   void m() { System.out.println("B: "+i); }}
class C extends B {  void m() { System.out.println("C: "+i); }}

public class Method1 {
   public static void main(String[] args) {
        A a = new C();
        a.m();   //C: 10
   }
}