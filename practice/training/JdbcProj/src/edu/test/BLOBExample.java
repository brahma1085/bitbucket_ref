package edu.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class BLOBExample {
	private static String SQL = "CREATE TABLE BLOBS(ID INT,NAME VARCHAR2(20),IMAGE BLOB)";

	public static void main(String[] args) {
		boolean flag = false;
		Connection connection = null;
		Statement statement = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:XE", "system", "satya");
			statement = connection.createStatement();
			flag = statement.execute(BLOBExample.SQL);
			System.out.println(flag);
		} catch (Exception e) {
			System.out.println("Exception==>" + e.getClass().getName() + "==>"
					+ e.getMessage());
		}
	}
}
