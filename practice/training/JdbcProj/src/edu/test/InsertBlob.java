package edu.test;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class InsertBlob {

	public static void main(String[] args) {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:XE", "system", "satya");
			statement = connection
					.prepareStatement("insert into blobs values(?,?,?)");
			statement.setInt(1, 10);
			statement.setString(2, "kris");
			File file = new File("E:/003.jpg");
			FileInputStream fin = new FileInputStream(file);
			statement.setBlob(3, fin);
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println("Exception==>" + e.getClass().getName() + "==>"
					+ e.getMessage());
		}
	}

}
