package edu.services;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;

import edu.dao.StudentDao;
import edu.exceptions.DaoException;
import edu.exceptions.StudentException;
import edu.pojo.Students;
import edu.util.ConnectionUtil;

public class TransactionClass {
	public void insertStudent(Students students) {
		Connection connection = null;
		try {
			connection = ConnectionUtil.getConnection();
			connection.setAutoCommit(false);
			StudentDao studentDao = new StudentDao();
			studentDao.insertStudent(students, connection);
			connection.commit();
		} catch (StudentException e) {
			DbUtils.rollbackAndCloseQuietly(connection);
		} catch (SQLException e) {
			DbUtils.rollbackAndCloseQuietly(connection);
		} catch (DaoException e) {
			DbUtils.rollbackAndCloseQuietly(connection);
		}
	}
}
