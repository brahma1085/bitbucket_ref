package classes;

class MyOuter {
         public static class MyInner { public static void foo() { } }
}

public class StaticNestClass {
         public static void main(String[] args) {
                MyOuter outer = new MyOuter();
                //MyOuter.MyInner inner = outer.new MyInner();   //compile-time err
                MyOuter.MyInner inner = new MyOuter.MyInner();   //ok
         }
}