package interfaces;

interface I<T> {
     void m(T t);
}

public class Generics1 implements I<Integer> {
   public void m(Integer t) {};

   public static void main(String[] args) {
   }
}
