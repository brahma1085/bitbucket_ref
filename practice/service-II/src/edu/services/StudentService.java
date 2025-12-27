package edu.services;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;

import edu.dao.StudentDao;
import edu.exceptions.DaoException;
import edu.exceptions.JDBCException;
import edu.exceptions.ServiceException;
import edu.pojo.Students;
import edu.util.ConnectionUtil;

public class StudentService {
	public void insertStudent(Students students) throws ServiceException {
		Connection connection = null;
		try {
			connection = ConnectionUtil.getConnection();
			connection.setAutoCommit(false);
			StudentDao studentDao = new StudentDao(connection);
			studentDao.insertStudent(students);
			connection.commit();
			System.out.println("details entered into database");
		} catch (JDBCException e) {
			throw new ServiceException(e.getMessage());
		} catch (SQLException e) {
			throw new ServiceException(e.getMessage());
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		} finally {
			DbUtils.closeQuietly(connection);
		}
	}
}
