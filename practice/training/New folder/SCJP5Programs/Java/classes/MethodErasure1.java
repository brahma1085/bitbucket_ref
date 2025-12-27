package classes;

class C<T> { 
    T id(T x) {  
        return x; 
    } 
}

class D extends C<String> {
    Object id(Object x) { 
         return x;
   }
}

public class MethodErasure1 {
   public static void main(String[] args) {

   }
}

// compile-time error: same erasure Object