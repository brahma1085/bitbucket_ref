package generics;

import java.util.*;
class InstanceOf{
     public static void main(String[] args) {
	Object o = new HashSet<Double>();

	// (1) Insert code here.
	System.out.println (o instanceof Set);                        //true
	System.out.println (o instanceof Set<?>);                     //true
	//System.out.println (o instanceof Set<Double>);              //compiler err
	//System.out.println (o instanceof Set<? extends Number>);    //compiler err
	//System.out.println (o instanceof Set<? super Number>);      //compiler err
     }
}

/*
Which code when inserted at (1) will result in the program to compile and execute normally? Select the one
answer.
(a) System.out.println (o instanceof Set);
(b) System.out.println (o instanceof Set<?>);
(c) System.out.println (o instanceof Set<Double>);
(d) System.out.println (o instanceof Set<? extends Number>);
(e) System.out.println (o instanceof Set<? super Number>);
*/