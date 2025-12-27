package edu.main;

import edu.pojo.Students;
import edu.services.TransactionClass;

public class ServiceTest {
	public static void main(String[] args) {
		Students students = new Students();
		students.setSno(1);
		students.setSname("ANAND");
		TransactionClass transactionClass = new TransactionClass();
		transactionClass.insertStudent(students);
		System.out.println("SUCCESS");
	}
}
