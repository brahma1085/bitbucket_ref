package generics;

abstract class C<T> { abstract T id(T x); }
class D extends C<String> {
         String id(String x) { return x; }
         Object id(Object x) { return id( (String) x); }
}

public class Abstract1 {
         public static void main(String[] args) {
       	C c = new D();
         	c.id(new Object());
         }
}