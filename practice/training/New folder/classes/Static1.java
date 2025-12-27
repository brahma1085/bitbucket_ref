package classes;

public class Static1 {
      static int i = 10;
      int j;
      
      static int getI() {  return i;} 
      static 
      {
          int m = getI();   //ok
          System.out.println(m);  // 10
           m = getK1();   //ok
           System.out.println(m);   // 0
       }

      {
          j = this.k;   //ok
          j = this.getI();   //ok
       }

      public static void main(String[] args) {
           Static1 s = new Static1();
           System.out.println(s.j); 
      }

      static int getK1() { return k;}  //ok
      static int k = 20;
}