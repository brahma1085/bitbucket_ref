package edu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

public class RetriveClientIds {
	private static String sql = "SELECT CLIENTID FROM CLIENTDETAILS";

	public static void main(String[] args) {
		List list = new ArrayList();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:XE", "system", "satya");
			System.out.println("connection established");
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				for (int i = 0; i <= resultSet.getFetchSize(); i++) {
					i = resultSet.getInt(1);
					list.add(i);
				}
			}
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException==>"
					+ e.getClass().getName() + "==>" + e.getMessage());
		} catch (SQLException e) {
			System.out.println("SQLException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
		} finally {
			DbUtils.closeQuietly(resultSet);
			DbUtils.closeQuietly(statement);
			DbUtils.closeQuietly(connection);
		}
		System.out.println(list);
	}
}