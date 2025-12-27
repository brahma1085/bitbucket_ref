package edu.main;

import edu.dao.StudentDao;
import edu.pojo.Student;
import edu.pojo.StudentXtra;

public class WhyTransactionTest {
	public static void main(String[] args) {
		Student student = new Student();
		StudentXtra studentXtra = new StudentXtra();
		studentXtra.setStudentAge(24);
		studentXtra.setStudentNo(2);
		student.setStudentNo(studentXtra.getStudentNo());
		student.setStudentName("N@IT");
		student.setStudentXtra(studentXtra);
		StudentDao studentDao = new StudentDao();
		studentDao.insert(student);
		System.out.println("SUCCESS");
	}
}
