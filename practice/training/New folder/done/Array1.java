public class Array1 {
     public static void main(String[] args)  {
         byte [][] big = new byte [7][7];
         byte b2 [][][][] = new byte [2][3][1][2];
         b2[0][1] = big;
         System.out.println("the size of b2[0][1] is:"+b2[0][1].length);
         System.out.println("the size of b2[0][1][0] is:"+b2[0][1][0].length);
         System.out.println("the size of b2[0][1][6] is:"+b2[0][1][6].length);
    }
}

/* The output is:
the size of b2[0][1] is:7
the size of b2[0][1][0] is:7
the size of b2[0][1][6] is:7
*/