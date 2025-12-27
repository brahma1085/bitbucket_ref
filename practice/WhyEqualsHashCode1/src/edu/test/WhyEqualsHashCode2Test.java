package edu.test;

import java.util.HashMap;
import java.util.Map;

public class WhyEqualsHashCode2Test {
	private static WhyEqualsHashCode2Test code2Test;

	public void sameRef() {
		String studentNo = "1";
		Map map = new HashMap();
		map.put(studentNo, "N@IT1");
		String studentName = (String) map.get(studentNo);
		System.out.println("student name==>same==>" + studentName);
	}

	public void differentRef() {
		String studentNo = "1";
		Map map = new HashMap();
		map.put(studentNo, "N@IT2");
		String studentNoRef = "1";
		System.out.println(studentNo.getClass().getTypeParameters());
		System.out.println(studentNoRef.getClass().getTypeParameters());
		String studentName = (String) map.get(studentNoRef);
		System.out.println("student name==>diff==>" + studentName);
	}

	public static void main(String[] args) {
		code2Test = new WhyEqualsHashCode2Test();
		code2Test.sameRef();
		code2Test.differentRef();
	}
}
