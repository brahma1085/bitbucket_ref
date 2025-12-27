package edu.test;

import java.util.HashMap;
import java.util.Map;

class Student4 {
	private String studentNo;

	public String getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

}

public class WhyEqualsHashCode4Test {

	public static void main(String[] args) {
		Student4 student4 = new Student4();
		student4.setStudentNo("1");
		Map map = new HashMap();
		map.put(student4, "N@IT");
		Student4 studentRef = new Student4();
		studentRef.setStudentNo("1");
		String studentName = (String) map.get(studentRef);
		System.out.println("student name==>" + studentName);
	}
}
