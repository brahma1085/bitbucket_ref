package classes;

class Nulls {       
         public String get(int i){
                 return null;
         }
         
         public static void main(String ka[]){
                Nulls n = new Nulls();
                System.out.println(n.get(1));  //output "null"
		
                Object o = n.get(10);
                //System.out.println(o.toString());  // NullPointerException

                int[] a = null ,  b [] = null;
                //b = a;  //compiler err: b is 2 dimensional
                System.out.println( b );

         }
}
