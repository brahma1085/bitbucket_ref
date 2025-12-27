package edu.utility;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class EntryDemo {
	public static void main(String[] args) {
		Map map = new HashMap();
		Iterator iterator = null;
		map.put("anand", new Double(3872683247.7));
		map.put("kumar", 8723648733928l);
		map.put("raghav", new Integer(832746));
		map.put("ramesh", 83276487);
		Set set = map.keySet();

		for (iterator = set.iterator(); iterator.hasNext();) {

			String str = (String) iterator.next();
			System.out.println("key==>" + str);
		}
		Set set2 = map.entrySet();
		Iterator iterator2 = set2.iterator();
		while (iterator2.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator2.next();
			System.out.println("key==>" + entry.getKey());
			System.out.println("value==>" + entry.getValue());
		}
	}
}
