package varargs;

public class Vararg {
        private static void method(int... i){
	System.out.println("in method1(int... i)");
        }

        /* private static void method(int[] i){
	System.out.println("in method(int[] i)");
        }
        */

         public static void main(String[] args){
	int[] a = {12,53,543,24,324,456,24};
	method(2,3,4,5,1);
	method(a);
         }
}

//compile error if it has both methods.