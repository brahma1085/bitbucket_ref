package edu.mains;

import edu.pojos.Students;
import edu.transactions.StudentService;

public class ServiceTest {
	public static void main(String[] args) {
		Students students = new Students();
		students.setSno(10);
		students.setSname("NARESH");
		StudentService studentService = new StudentService();
		studentService.insertStudent(students);
		System.out.println("details succesfully entered in DB");
	}
}
