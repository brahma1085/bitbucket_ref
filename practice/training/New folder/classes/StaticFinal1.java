package classes;

public class StaticFinal1 {
     static int getK() {  return k;} 
     static int getL() {  return l;} 
      static 
      {
          int m = getK();   
           System.out.println(m);   // 20
          m = getL();
           System.out.println(m);   // 0
       }

      public static void main(String[] args) {
      }

      final static int k = 20;   // final static fields are never observed to have default values.
      static int l=10;
}