package edu.lang;

public class String4Test {

	public static void main(String[] args) {
		String studentName1 = "value";
		String studentName2 = "value";
		if (studentName1 != null && studentName1.equals(studentName2)) {
			System.out.println("equals");
		} else {
			System.out.println("not equals");
		}
	}
}
