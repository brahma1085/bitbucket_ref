package edu.lang;

import org.apache.commons.lang.StringUtils;

public class String5Test {

	public static void main(String[] args) {
		String studentName1 = "value";
		String studentName2 = "value";
		if (StringUtils.equals(studentName1, studentName2)) {
			System.out.println("equals");
		} else {
			System.out.println("not equals");
		}
	}
}
