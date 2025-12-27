package classes;

class Point { static int i=2;  int j = 2; static int k=2; }
class Test extends Point {  
       double i = 4.7;    static double j = 4.7; 
       void prints() {
            System.out.println("i="+i+" j="+j);
            System.out.println("super.i="+super.i); 
            System.out.println("k="+k);
        }
}

public class Fields1 {
   public static void main(String[] args) {
        Test test = new Test();
        test.prints();
        System.out.println(test.i+" "+ test.j);
        // System.out.println(super.i);  // cannot call instance context super here);
        // System.out.println(i);  //cannot find i, instance variable is not available
        System.out.println( ((Point)test).j);   
   }
}