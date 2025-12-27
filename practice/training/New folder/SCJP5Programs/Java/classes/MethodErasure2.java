package classes;

class C<T> { T id(T x) { return x; } }
interface I<T> { T id(T x); }

public class MethodErasure2 extends C<String> 
     implements I<Integer> 
{
   //String id(String x) { return x;}   

   public Integer id(Integer x) { return x;}

   public static void main(String[] args) {

   }
}

// compile-time error: same erasure Object