package edu.test;

import java.util.Enumeration;

import edu.utility.Enum;

public class EnumerationTest {

	public static void main(String[] args) {
		Enumeration enumeration = new Enum();
		while (enumeration.hasMoreElements()) {
			Object object = (Integer) enumeration.nextElement();
			System.out.println(object);
		}
	}
}