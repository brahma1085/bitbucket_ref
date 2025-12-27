package edu.beanutils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

public class BeanUtils1 {

	public static void main(String[] args) {
		Map map = new HashMap();
		map.put("studentNo", 1);
		map.put("studentName", "N@IT");
		Student student = new Student();
		student.setStudentNo((Integer) map.get("studentNo"));
		student.setStudentName((String) map.get("studentName"));
		System.out.println("student number==>" + student.getStudentNo());
		System.out.println("student name==>" + student.getStudentName());
		try {
			BeanUtils.populate(student, map);
			System.out.println("student number==>beanutils==>"
					+ student.getStudentNo());
			System.out.println("student name==>beanutils==>"
					+ student.getStudentName());
		} catch (IllegalAccessException e) {
			System.out.println("IllegalAccessException==>"
					+ e.getClass().getName() + "==>" + e.getMessage());
		} catch (InvocationTargetException e) {
			System.out.println("InvocationTargetException==>"
					+ e.getClass().getName() + "==>" + e.getMessage());
		}
	}
}
