package edu.main;

import java.sql.Connection;

import org.apache.commons.dbutils.DbUtils;

import edu.exceptions.ServiceException;
import edu.pojo.Students;
import edu.services.StudentService;

public class ServiceTest {
	private static Connection connection = null;

	public static void main(String[] args) {

		try {
			Students students = new Students();
			students.setStudentNo(4);
			students.setStudentName("N@IT");
			StudentService studentService = new StudentService();
			studentService.insertStudent(students);
			System.out.println("SUCCESS");
		} catch (ServiceException e) {
			DbUtils.rollbackAndCloseQuietly(connection);
		} finally {
			DbUtils.closeQuietly(connection);
		}
	}
}
