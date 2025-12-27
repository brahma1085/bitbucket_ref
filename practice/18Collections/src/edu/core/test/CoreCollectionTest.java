package edu.core.test;

import java.util.ArrayList;
import java.util.List;

import edu.core.pojo.Student;

public class CoreCollectionTest {
	public static void main(String[] args) {
		List list = new ArrayList();
		list.add(7365);
		list.add("ksdjf");
		list.add(32878l);
		list.add(365.37d);
		Student student = new Student();
		student.setStudentList(list);
	}
}
