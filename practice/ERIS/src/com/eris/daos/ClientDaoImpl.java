package com.eris.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;

import com.eris.exceptions.DaoException;
import com.eris.exceptions.ResourceHelperException;
import com.eris.model.Client;
import com.eris.utils.ResourceHelper;

public class ClientDaoImpl implements ClientDao {
	private String sql = "INSERT INTO CLIENTDETAILS VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private Connection connection;
	private PreparedStatement preparedStatement;

	public boolean insertClientDetails(Client client) throws DaoException {
		boolean flag = false;
		try {
			connection = ResourceHelper.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, client.getClientid());
			preparedStatement.setString(2, client.getCompanyname());
			preparedStatement.setString(3, client.getName());
			preparedStatement.setString(4, client.getAddress());
			preparedStatement.setString(5, client.getCity());
			preparedStatement.setString(6, client.getState());
			preparedStatement.setString(7, client.getZip());
			preparedStatement.setString(8, client.getTitle());
			preparedStatement.setString(9, client.getExtension());
			preparedStatement.setString(10, client.getTelephone());
			preparedStatement.setString(11, client.getFax());
			preparedStatement.setString(12, client.getCountry());
			preparedStatement.setString(13, client.getEmail());
			preparedStatement.setString(14, client.getDepartment());
			flag = preparedStatement.executeUpdate() > 0 ? true : false;
		} catch (SQLException e) {
			System.out.println("SQLException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
			throw new DaoException();
		} catch (ResourceHelperException e) {
			System.out.println("ResourceHelperException==>"
					+ e.getClass().getName() + "==>" + e.getMessage());
			DbUtils.rollbackAndCloseQuietly(connection);
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
}
