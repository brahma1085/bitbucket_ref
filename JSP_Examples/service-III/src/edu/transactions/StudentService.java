package edu.transactions;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;

import edu.daos.StudentDao;
import edu.exceptions.StudentException;
import edu.pojos.Students;
import edu.utils.ConnectionUtil;

public class StudentService {
	public void insertStudent(Students students) {
		Connection connection = null;
		connection = ConnectionUtil.getConnection();
		try {
			connection.setAutoCommit(false);
			StudentDao studentDao = new StudentDao();
			studentDao.setConnection(connection);
			studentDao.insertStudent(students);
			connection.commit();
		} catch (SQLException e) {
			DbUtils.rollbackAndCloseQuietly(connection);
		} catch (StudentException e) {
			System.out.println("StudentException--" + e.getMessage());
		} finally {
			DbUtils.closeQuietly(connection);
			System.out.println("connection closed");
		}
	}
}
