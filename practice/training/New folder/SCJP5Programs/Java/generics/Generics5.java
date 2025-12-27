package generics;

import java.util.*;

class  Generics5{ 
	public <T> List<T> meth(List<?> type) {
		System.out.println(type); // 1    //output: [Iamhere]
		return new ArrayList<T>(); // 2
	}

  	public static void main(String[] args) { 
       		List < ? > l = new ArrayList< Integer > ();
       		//l.add(new Integer(1));   //compiler err

                ArrayList<String> arrS = new ArrayList<String>(Arrays.asList("Iamhere"));
                Generics5 g = new Generics5();
                //g.<Integer>meth(arrS);   //ok
                g.meth(arrS);   //ok
  	} 
} 
