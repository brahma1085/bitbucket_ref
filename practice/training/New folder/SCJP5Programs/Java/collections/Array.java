package collections;

import java.util.*;

class Array{
       public static void main(String[] args) {
           List<String> list = Arrays.asList(new String[] {"one", "two", "three", "four" });
           for (String s : list)
                System.out.print(s+" ");
           System.out.println();

           List<Integer> iL = new ArrayList<Integer>();
           for(int x=0; x<3; x++)
	iL.add(x);

           Object[] oa = iL.toArray(); // create an Object array
           for (Object i : oa)
                 System.out.print(i+" ");   //output 0 1 2
           System.out.println();    

           Integer[] ia = iL.toArray(new Integer[0]);
           for (Integer i : ia)
                 System.out.print(i+" ");   //output 0 1 2
           System.out.println();
       }
}