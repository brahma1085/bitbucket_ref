package edu.services;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;

import edu.dao.StudentDao;
import edu.exceptions.DaoException;
import edu.exceptions.ResourceHelperException;
import edu.exceptions.ServiceException;
import edu.factory.DaoFactory;
import edu.model.StudentDemo1;
import edu.utils.ResourceHelper;

public class StudentServiceImpl implements StudentService {

	public boolean insertStudentDemo1(StudentDemo1 studentDemo1)
			throws ServiceException {
		boolean flag = false;
		Connection connection = null;
		try {
			connection = ResourceHelper.getConnection();
			connection.setAutoCommit(false);
			StudentDao studentDao = DaoFactory.getStudentDao();
			studentDao.setConnection(connection);
			flag = studentDao.insertStudent(studentDemo1);
			connection.commit();
		} catch (ResourceHelperException e) {
			DbUtils.rollbackAndCloseQuietly(connection);
			throw new ServiceException();
		} catch (SQLException e) {
			DbUtils.rollbackAndCloseQuietly(connection);
			throw new ServiceException();
		} catch (DaoException e) {
			throw new ServiceException();
		} finally {
			DbUtils.closeQuietly(connection);
		}
		return flag;
	}

}
