package classes;

abstract class AbstractClass {
   abstract void f();
}

class InnerClassAbstractMethod {
   public static void main(String[] args) {
        AbstractClass p = new AbstractClass() {
           void f() { }
        };   // should be very careful about the ;
   }
}