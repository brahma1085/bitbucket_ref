package edu.service;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;

import edu.dao.StudentDao;
import edu.model.Student;
import edu.util.ConnectionUtil;

public class StudentService {
	public void insertStudent(Student student) {
		Connection connection = ConnectionUtil.getConnection();
		try {
			connection.setAutoCommit(false);
			StudentDao studentDao = new StudentDao();
			studentDao.setConnection(connection);
			studentDao.insertStudent(student);
			connection.commit();
		} catch (SQLException e) {
			DbUtils.rollbackAndCloseQuietly(connection);
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(connection);
		}
	}
}
