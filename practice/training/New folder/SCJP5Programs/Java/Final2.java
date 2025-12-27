public class Final2 {
    public static void main(String[] args) {
         final int[] i;
         int[] arr1 = {10, 5, 3};
         i = arr1;
         i[1] = 2;
         int[] arr2 = {2, 0};
         i=arr2;   // error. final var cannot be assigned to more than once.
   }
}
