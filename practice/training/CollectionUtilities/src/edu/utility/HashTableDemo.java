package edu.utility;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HashTableDemo {
	private Hashtable hashtable;

	public void getTableDetails() {
		hashtable = new Hashtable();
		System.out.println("table size==>" + hashtable.size());
		hashtable = new Hashtable(3);
		System.out.println("table size==>" + hashtable.size());
		hashtable = new Hashtable(4, 0.6f);
		System.out.println("table size==>" + hashtable.size());
		hashtable.put("anand", new Double(1223312.21));
		hashtable.put("ramesh", new Double(938498.32));
		hashtable.put("ghoshley", new Double(2379038.23));
		hashtable.put("sharvan", new Integer(23928348));
		System.out.println("table size==>" + hashtable.size());
		System.out.println("cloned object of HT==>" + hashtable.clone());
		System.out.println("value is there?==>"
				+ hashtable.contains(1221123123.23));
		System.out.println("string format of HT==>" + hashtable.toString());
		System.out.println("key is there?==>" + hashtable.containsKey("anand"));
		System.out.println("whether the value is bounded to 1 or more keys==>"
				+ hashtable.containsValue(23928348));
		System.out.println("equals method==>" + hashtable.equals("anand"));
		System.out.println("get value by get()==>" + hashtable.get("ramesh"));
		System.out.println("removing==>" + hashtable.remove("ghoshley"));
		Set set = hashtable.entrySet();
		System.out.println(set);
		Iterator it = set.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
		Enumeration enumeration = hashtable.elements();
		while (enumeration.hasMoreElements()) {
			System.out.println("Values==>" + enumeration.nextElement());
		}
		Enumeration enumeration2 = hashtable.keys();
		while (enumeration2.hasMoreElements()) {
			System.out.println("keys==>" + enumeration2.nextElement());
		}
		Set set2 = hashtable.keySet();
		System.out.println(set2);
		Iterator iterator = set2.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
		Map map = new HashMap();
		map.put("sameera", 9238479728.23);
		map.put("faruk", 9230209348209l);
		hashtable.putAll(map);
		System.out.println("after map==>table size==>" + hashtable.size());
		Set set3 = hashtable.entrySet();
		System.out.println(set3);
	}
}
