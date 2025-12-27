package edu.test;

public class WhyObjectTest {
	public static Long getStudentNo(Long studentNo) {
		return studentNo;
	}

	public static String getStudentName(String studentName) {
		return studentName;
	}

	public static Object whyObjectMethod(Object obj) {
		return obj;
	}

	public static void main(String[] args) {
		Long studentNo = WhyObjectTest.getStudentNo(new Long(1));
		System.out.println("student number==>normal==>" + studentNo);
		String studentName = WhyObjectTest.getStudentName("N@IT");
		System.out.println("student name==>normal==>" + studentName);
		Long studentNo1 = (Long) WhyObjectTest.whyObjectMethod(new Long(2));
		System.out.println("student number==>objectRef==>"
				+ studentNo1.getClass().getName());
		String studentName1 = (String) WhyObjectTest
				.whyObjectMethod("N@IT-obj");
		System.out.println("student name==>objectRef==>"
				+ studentName1.getClass().getName());
		System.out.println("objectRef==>for string==>" + studentName1.length());
	}
}
