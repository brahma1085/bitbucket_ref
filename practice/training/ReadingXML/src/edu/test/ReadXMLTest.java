package edu.test;

import java.util.Map;
import java.util.Set;
import java.util.Iterator;

public class ReadXMLTest {
	public ReadXMLTest() {
		// TODO Auto-generated constructor stub
	}
	public static void main(String[] args) {
		Map map = System.getenv();
		Set keys = map.keySet();
		Iterator iterator = keys.iterator();
		System.out.println("Variable Name \t Variable Values");
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			String value = (String) map.get(key);
			System.out.println(key + "     " + value);
		}
	}
}