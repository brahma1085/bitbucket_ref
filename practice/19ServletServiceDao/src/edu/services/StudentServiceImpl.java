package edu.services;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;

import edu.daos.StudentsDao;
import edu.exceptions.ResourceHelperException;
import edu.exceptions.StudentsExceptions;
import edu.factories.DaoFactory;
import edu.models.Students;
import edu.utils.ResourceHelper;

public class StudentServiceImpl implements StudentService {

	public boolean insertStudents(Students students) throws StudentsExceptions {
		boolean flag = false;
		Connection connection = null;
		try {
			connection = ResourceHelper.getConnection();
			connection.setAutoCommit(false);
			StudentsDao studentsDao = DaoFactory.getStudentsDao();
			flag = studentsDao.insertStudents(students);
			connection.commit();
		} catch (ResourceHelperException e) {
			System.out.println("ResourceHelperException==>"
					+ e.getClass().getName() + "==>" + e.getMessage());
			DbUtils.rollbackAndCloseQuietly(connection);
			throw new StudentsExceptions();
		} catch (SQLException e) {
			System.out.println("SQLException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
			DbUtils.rollbackAndCloseQuietly(connection);
			throw new StudentsExceptions();
		} catch (Exception e) {
			System.out.println("Exception==>" + e.getClass().getName() + "==>"
					+ e.getMessage());
			throw new StudentsExceptions();
		} finally {
			DbUtils.closeQuietly(connection);
		}
		return flag;
	}

}
