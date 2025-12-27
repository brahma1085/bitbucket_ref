package generics;

import java.util.*;

class GenericsSubtyping {
    public static void main(String[] args) {

	//ArrayList<Object> aList = new ArrayList<String>();        // compiler err
	//List<Object> cList = new ArrayList<String>();             // compiler err
	//List<? super Comparable> iList = new ArrayList<String>(); // compiler err
	//List<? super Object> jList = new ArrayList<String>();     // compiler err

	List<String> bList = new ArrayList<String>();
	List<?> dList = new ArrayList<String>();
	List<? extends String> eList = new ArrayList<String>();
	List<? extends Comparable> fList = new ArrayList<String>();
	List<? extends Object> gList = new ArrayList<String>();
	List<? super String> hList = new ArrayList<String>();
   }
}