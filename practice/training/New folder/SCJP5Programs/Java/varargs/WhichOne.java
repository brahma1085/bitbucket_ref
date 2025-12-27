   package varargs;

   public class WhichOne {

      //WhichOne(Integer[] size) { }  //compile err: this is the same as the vararg one.

      WhichOne(Integer... size) {
         System.out.println("Var Args version.");
      }

      WhichOne(int i, int j) {
         System.out.println("Version with int args.");

      }

      WhichOne(Integer i, Integer j) {
         System.out.println("Version with Integer args.");
      }

      public static void main(String[] args) {
         System.out.println("Call w/ two arg:2,3");
         new WhichOne(2, 3);
         System.out.println("Call w/ Integer two arg: 2,3");
         new WhichOne(new Integer(2), new Integer(3));
         System.out.println("Call w/ Integer three arg: 2,3,4");
         new WhichOne(new Integer(2), new Integer(3), 4);
      }

   }

