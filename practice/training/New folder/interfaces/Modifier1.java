package interfaces;

interface I {
   abstract void m();   //ok
}

public class Modifier1 implements I {
   public void m() {};

   public static void main(String[] args) {
        Modifier1 mf = new Modifier1();
   }
}
