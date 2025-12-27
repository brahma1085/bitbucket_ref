package edu;

public class Callby_Value_Ref {

	public static void main(String[] args) {
		int studentNo = 1;
		StringBuffer studentName = new StringBuffer("TI@N");
		studentNo = getStudentNo(studentNo);
		System.out.println("student number==" + studentNo);
		getStudentName(studentName);
		System.out.println("student name====" + studentName);
	}

	private static int getStudentNo(int studentNo) {
		studentNo = 8;
		return studentNo;
	}

	private static void getStudentName(StringBuffer studentName) {
		studentName.reverse();
	}
}