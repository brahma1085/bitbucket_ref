package edu.test;

public class WhyTypeCastTest {

	public static void main(String[] args) {
		Long studentNo = new Long(1);
		String studentName = "N@IT";
		System.out.println("student number==>normal==>" + studentNo);
		System.out.println("student name==>normal==>" + studentName);
		Object studentNoObj = new Long(1);
		Long studentNoRef = (Long) studentNoObj;
		System.out.println("hashcode of studentNoObj==>"
				+ System.identityHashCode(studentNoObj));
		System.out.println("hashcode of studentNoRef==>"
				+ System.identityHashCode(studentNoRef));
		if (studentNoObj.equals(studentNoRef)) {
			System.out.println("equals");
		} else {
			System.out.println("not equals");
		}
		System.out.println("student number==>typecasted==>" + studentNoRef);
		Object studentNameObj = "N@IT-obj";
		String studentNameRef = (String) studentNameObj;
		System.out.println("hashcode of studentNameObj==>"
				+ studentNameObj.hashCode());
		System.out.println("hashcode of studentNameRef==>"
				+ studentNameRef.hashCode());
		if (studentNameObj.equals(studentNameRef)) {
			System.out.println("equals");
		} else {
			System.out.println("not equals");
		}
		System.out.println("student name==>typecasted==>" + studentNameRef);
	}
}
