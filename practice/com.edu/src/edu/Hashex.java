package edu;

import java.util.HashMap;

public class Hashex {
	public static void main(String[] args) {
		HashMap<String, Integer> m = new HashMap<String, Integer>();
		m.put("idly", 12);
		int a=m.hashCode();
		System.out.println("hashcode of this map is="+a);
	}
}
