package edu.main;

import edu.dao.StudentDao;
import edu.pojo.Student;
import edu.pojo.StudentXtra;

public class WhyTransactionTest {
	public static void main(String[] args) {
		Student student = new Student();
		StudentXtra studentXtra = new StudentXtra();
		studentXtra.setStudentAge(26);
		studentXtra.setStudentNo(1);
		student.setStudentNo(studentXtra.getStudentNo());
		student.setStudentName("N@IT");
		student.setStudentXtra(studentXtra);
		StudentDao studentDao = new StudentDao();
		studentDao.insert(student);
		System.out.println("SUCCESS");
	}
}
