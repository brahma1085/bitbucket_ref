package edu.beanutils;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

public class BeanUtils2 {

	public static void main(String[] args) {
		Student student = new Student();
		student.setStudentNo(1);
		student.setStudentName("N@IT");
		CopyOfStudent copyOfStudent = new CopyOfStudent();
		try {
			BeanUtils.copyProperties(copyOfStudent, student);
			System.out.println("student number==>copy==>"
					+ copyOfStudent.getStudentNo());
			System.out.println("student name==>copy==>"
					+ copyOfStudent.getStudentName());
		} catch (IllegalAccessException e) {
			System.out.println("IllegalAccessException==>"
					+ e.getClass().getName() + "==>" + e.getMessage());
		} catch (InvocationTargetException e) {
			System.out.println("InvocationTargetException==>"
					+ e.getClass().getName() + "==>" + e.getMessage());
		}
	}
}
