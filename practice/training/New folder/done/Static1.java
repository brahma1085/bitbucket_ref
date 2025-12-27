public class Static1 {
   static{
       x=10;   //ok
       x++;   //To avoid Compile err replace x by Static1.x;
   }
   static int x=15;
   public static void main(String args[]){
           System.out.println(x);
    }
}
