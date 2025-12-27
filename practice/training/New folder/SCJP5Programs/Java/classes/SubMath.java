package classes;

public class SubMath extends java.lang.Math {  //compile-time err: Math is final
      public int add(int x, int y) {
           	return x + y;
      }
      public int sub(int x, int y) {
          	return x - y;
      }
      public static void main(String [] a) {
	SubMath f = new SubMath();
	System.out.println("" + f.add(1, 2));
      }
}

