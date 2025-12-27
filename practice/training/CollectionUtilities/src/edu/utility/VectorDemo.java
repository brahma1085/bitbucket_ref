package edu.utility;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

public class VectorDemo {
	private Vector vector;

	public void getVectorDetails() {
		vector = new Vector();
		System.out.println("size of the vector==>default==>" + vector.size()
				+ "==>now capacity==>" + vector.capacity());
		vector = new Vector(3);
		System.out.println("size of the vector==>one==>" + vector.size()
				+ "==>now capacity==>" + vector.capacity());
		vector = new Vector(4, 2);
		System.out.println("size of the vector==>two==>" + vector.size()
				+ "==>now capacity==>" + vector.capacity());
		vector.add(new Integer(23));
		vector.add(new Double(32.32));
		vector.addElement(new String("its me"));
		vector.addElement(new Integer(123));
		vector.addElement(new Integer(323));
		System.out.println("size after adding elements==>" + vector.size()
				+ "==>now capacity==>" + vector.capacity());
		vector.addElement(new Integer(2387));
		vector.add(new Integer(387));
		System.out.println("size after adding elements 2nd time==>"
				+ vector.size() + "==>now capacity==>" + vector.capacity());
		System.out.println("first element==>" + vector.firstElement());
		System.out.println("last element==>" + vector.lastElement());
		System.out.println("element at index 4==>" + vector.get(4));
		System.out
				.println("elements in the vector of enumeration object address==>"
						+ vector.elements());
		System.out.println("element '23' is at==>" + vector.indexOf(23));
		System.out.println("the first occurence of the element==>'its me'==>"
				+ vector.indexOf("its me", 2));
		vector.insertElementAt(new Integer(87687), 6);
		System.out
				.println("after inserting an element at specified location==>size="
						+ vector.size() + "==>capacity==>" + vector.capacity());
		System.out.println("copy of this Vector object's values==>"
				+ vector.clone());
		List list = new ArrayList();
		list.add(new Integer(32));
		list.add(new Double(32.343));
		list.add(new Character('s'));
		list.add(new Long(28374623876l));
		vector.addAll(list);
		System.out.println("after adding a collection of elements, size is==>"
				+ vector.size() + "==>capacity==>" + vector.capacity());
		System.out.println("whther the collection is there in vector or not==>"
				+ vector.containsAll(list));
		System.out.println("to string form of vector==>" + vector.toString());
		Enumeration enumeration = vector.elements();
		while (enumeration.hasMoreElements()) {
			Object object = (Object) enumeration.nextElement();
			System.out.println("elements in the vector==>" + object);
		}
		System.out.println("is this vector empty==>" + vector.isEmpty());
		vector.clear();
		System.out.println("is this vector empty==>" + vector.isEmpty());
	}
}
