package edu.test;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

class Student5 {
	private String studentNo;

	public String getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public final boolean equals(Object obj) {
		boolean isEquals = false;
		Student5 student5 = (Student5) obj;
		isEquals = new EqualsBuilder().append(this.studentNo,
				student5.getStudentNo()).isEquals();
		return isEquals;
	}

	public final int hashCode() {
		int hashCode = new HashCodeBuilder().append(studentNo).toHashCode();
		return hashCode;
	}
}

public class WhyEqualsHashCode5Test {

	public static void main(String[] args) {
		Student5 student5 = new Student5();
		student5.setStudentNo("1");
		Map stuMap = new HashMap();
		stuMap.put(student5, "N@IT");
		Student5 studentRef = new Student5();
		studentRef.setStudentNo("1");
		String studentName = (String) stuMap.get(studentRef);
		System.out.println("student name==>" + studentName);
	}
}
