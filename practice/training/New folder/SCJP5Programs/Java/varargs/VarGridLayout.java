   package varargs;

   public class VarGridLayout {
   
      VarGridLayout(Integer... size) {
         Object temp[] = {1,1,0,0};
         System.arraycopy(size, 0, temp, 0, size.length);
         System.out.printf("Create a grid with rows = %d " +
         "and cols = %d, the hgap = %d and the vgap = " +
         "%d. \n", temp);
      }
   
      public static void main(String[] args) {
         System.out.println("Call no arg constructor:");
         new VarGridLayout();
         System.out.println("Call two arg constructor: 2,3");
         new VarGridLayout(2, 3);
         System.out.println(
            "Call four arg constructor: 4,5,6,7");
         new VarGridLayout(4, 5, 6, 7);
         System.out.println(
            "Call four arg constructor: 4,5,6,7,8");
         new VarGridLayout(4, 5, 6, 7, 8);   // run-time err:ArrayIndexOutOfBoundException
      }
    }

