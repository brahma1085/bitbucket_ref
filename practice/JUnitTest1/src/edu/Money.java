package edu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

public class Money {
	private int fAmount;
	private String fCurrency;

	public Money(int fAmount, String fCurrency) {
		super();
		this.fAmount = fAmount;
		this.fCurrency = fCurrency;
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		List list = new ArrayList();
		list.add(4876);
		System.out.println(list.add(null) + "==>array list allows null values");
		list = new Vector();
		list.add(34857);
		System.out.println(list.add(null) + "==>vactor allows null values");
		Map map = new HashMap();
		map.put(3287, 3489539847d);
		System.out
				.println(map.put(null, null)
						+ "==>this allows null keys and values and returns the previously containing value");
		map = new Hashtable();
		map.put(3486, 32452374d);
		// System.out.println(map.put(null, null)+"==>doesn't allows null keys
		// and null values and even if we wnt to add them it willl thow null
		// pointer exception at runtime);
		Properties properties = new Properties();
		List<String> strings = new Vector<String>();
		strings.add("skdjhgksd");
		strings.add("sjdgfsjdi");
		Iterator<String> iterator = strings.iterator();
		while (iterator.hasNext()) {
			String string = (String) iterator.next();
			if (string != null)
				properties.put("list", string);
			properties.list(System.out);
		}

	}
}
