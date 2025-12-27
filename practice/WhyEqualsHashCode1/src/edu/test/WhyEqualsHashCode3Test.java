package edu.test;

import java.util.HashMap;
import java.util.Map;

class Student {
	private String studentNo;

	public String getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

}

public class WhyEqualsHashCode3Test {

	public static void main(String[] args) {
		Student student = new Student();
		student.setStudentNo("1");
		Map map = new HashMap();
		map.put(student, "N@IT");
		String studentname = (String) map.get(student);
		System.out.println("student name==>" + studentname);
	}
}
