package edu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import edu.exceptions.StudentException;
import edu.model.Students;
import edu.utils.ResourceHelper;

public class StudentDaoImpl implements StudentDao {
	private static final long REFRESH_INTERVAL = 1000 * 1 * 30;
	private static List<Students> cacheStudentList = new ArrayList<Students>();
	private static Long timeStamp = new Long(System.currentTimeMillis());

	public List<Students> loadAllStudents() throws StudentException {
		String sql = "SELECT SNO,SNAME FROM STUDENTS";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Students> stuList = new ArrayList<Students>();
		try {
			if (isStaleCache() && cacheStudentList.size() > 0)
				return cacheStudentList;
			else {
				cacheStudentList.clear();
				connection = ResourceHelper.getConnection();
				preparedStatement = connection.prepareStatement(sql);
				resultSet=preparedStatement.executeQuery();
				while(resultSet.next()){

				}
			}
		} catch (Exception e) {
		}
		return null;
	}

	public boolean isStaleCache() {
		return (System.currentTimeMillis() - timeStamp.longValue()) < REFRESH_INTERVAL;
	}
}
