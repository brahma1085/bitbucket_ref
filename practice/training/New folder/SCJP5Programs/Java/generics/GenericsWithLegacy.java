package generics;

import java.util.*;

public class GenericsWithLegacy {
    public static void main(String[] args) {
        List list1 = new ArrayList<Integer>();   //ok
        List<Integer> list2 = new ArrayList();   //ok but warning


	List<Integer> myList = new ArrayList<Integer>();
	myList.add(4);
	myList.add(6);
	Inserter in = new Inserter();
	in.insert(myList);    // pass List<Integer> to legacy code

	Adder adder = new Adder();
	int total = adder.addAll(myList);
	// pass it to an untyped argument
	System.out.println(total);


                 List test = new ArrayList();  
                 test.add(43);
                 // int x = test.get(0);   //compile-time err: found Object, need int
                 int x = (Integer)test.get(0);

                 List<Integer> test2 = new ArrayList<Integer>();  
                 test2.add(343);
                 int x2 = test2.get(0);
    }
}

class Inserter {
     // method with a non-generic List argument
     void insert(List list) {
	list.add(new Integer(42)); // adds to the incoming list   //Unchecked warning
        //list.add(new String("42")); // put a String in the list     //Unchecked warning
     }
}

class Adder {   
     int addAll(List list) {
	// method with a non-generic List argument,
	// but assumes (with no guarantee) that it will be Integers
	Iterator it = list.iterator();
	int total = 0;
	while (it.hasNext()) {
	        int i = ((Integer)it.next()).intValue();   // ClassCastException will occur here for String("42")
	        total += i;
	}
	return total;
     }
}