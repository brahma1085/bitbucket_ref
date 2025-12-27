package classes;

class A { }
class B extends A {}

class C {
     static void m(A a) {}
}

class D  {
     void m(B b) {}
}

interface I {
     void m(B b);
}

public class Method3 extends D implements I {
   public void m(B b) {}

   public static void main(String[] args) {

   }
}
