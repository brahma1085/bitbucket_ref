package edu.test;

import java.util.HashMap;
import java.util.Map;

public class WhyEqualsHashCode1Test {

	public static void main(String[] args) {
		Map map = new HashMap();
		map.put("1", "N@IT");
		System.out.println("student name==>" + map.get("1"));
	}
}
