package com.eris.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang.StringUtils;

import com.eris.exceptions.DaoException;
import com.eris.exceptions.ResourceHelperException;
import com.eris.model.Login;
import com.eris.utils.ResourceHelper;

public class LoginDaoImpl implements LoginDao {
	private String sql1 = "INSERT INTO LOGIN VALUES(?,?,?)";
	private String sql2 = "SELECT * FROM  LOGIN";
	private Connection connection;
	private PreparedStatement preparedStatement;

	public boolean insertUserDetails(Login login) throws DaoException {
		boolean flag = false;
		try {
			connection = ResourceHelper.getConnection();
			preparedStatement = connection.prepareStatement(sql1);
			preparedStatement.setString(1, login.getUsername());
			preparedStatement.setString(2, login.getPassword());
			preparedStatement.setString(3, login.getStatus());
			flag = preparedStatement.executeUpdate() > 0 ? true : false;
		} catch (SQLException e) {
			System.out.println("SQLException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
			throw new DaoException();
		} catch (ResourceHelperException e) {
			System.out.println("ResourceHelperException==>"
					+ e.getClass().getName() + "==>" + e.getMessage());
			DbUtils.closeQuietly(connection);
			throw new DaoException();
		} catch (Exception e) {
			System.out.println("Exception==>" + e.getClass().getName() + "==>"
					+ e.getMessage());
			throw new DaoException();
		} finally {
			DbUtils.closeQuietly(preparedStatement);
		}
		return flag;
	}

	public boolean isAuthenticate(Login login) throws DaoException {
		boolean flag = false;
		ResultSet rs = null;
		try {
			connection = ResourceHelper.getConnection();
			preparedStatement = connection.prepareStatement(sql2);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String s1 = rs.getString(1);
				String s2 = rs.getString(2);
				String s3 = rs.getString(3);
				String s4 = login.getUsername();
				String s5 = login.getPassword();
				String s6 = login.getStatus();
				if (StringUtils.equals(s1, s4) && StringUtils.equals(s2, s5)
						&& StringUtils.equals(s3, s6)) {
					flag = true;
					return flag;
				}
			}
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(preparedStatement);
			DbUtils.closeQuietly(connection);
		} catch (ResourceHelperException e) {
			System.out.println("ResourceHelperException==>"
					+ e.getClass().getName() + "==>" + e.getMessage());
			DbUtils.closeQuietly(connection);
			throw new DaoException();
		} catch (SQLException e) {
			System.out.println("SQLException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
			throw new DaoException();
		}
		return flag;
	}
}
