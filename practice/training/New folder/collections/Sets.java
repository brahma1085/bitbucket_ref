package collections;

import java.util.*;

class Sets {
        public static void main(String[] args) {
	boolean[] ba = new boolean[5];
                 //Set s = new HashSet();  // run ok, only compile's unchecked warning
	//Set<String> s = new HashSet<String>();   //compile-err: add Interger and Object
	//Set<String> s = new TreeSet<String>();   //compile-err: add Interger and Object
	Set s = new TreeSet();  //compile's unchecked warning and runtime exception: ClassCastException
                  ba[0] = s.add("a");
	ba[1] = s.add(new Integer(42));
	ba[2] = s.add("b");
	ba[3] = s.add("a");
	ba[4] = s.add(new Object());
	for(int x=0; x<ba.length; x++)
	      System.out.print(ba[x] + " ");
	System.out.println("\n");
	for(Object o : s)
                       System.out.print(o + " ");
        }
}