class A {
   void m1(A a) {
        System.out.print("A';]p[]l");
   }
}

class B extends A {
   void m1(B b) {
      System.out.print("B");
   }
}

class C extends B {
   void m1(C c) {
       System.out.print("C");
   }
}

class D {
    public static void main(String[] args) {
        C c1 = new C(); 
        B c3=new B();
        c3.m1(c1);
  

   }
} 