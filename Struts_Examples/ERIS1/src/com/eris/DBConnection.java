package com.eris;

import java.sql.*;

public class DBConnection {
	Connection con = null;
	ResultSet rs = null;
	Statement st = null;

	public DBConnection() {
		try {
			System.out.println("in con");
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:XE", "system", "satya");
			System.out.println("after con");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("in conexp");
		}
	}

	public Connection getConnection() throws Exception {
		System.out.println("get con");
		return con;
	}

	public ResultSet executeQuery(String s) {
		try {
			st = con.createStatement();
			rs = st.executeQuery(s);
			System.out.println("in st");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("in st exp");
		}
		return rs;
	}

	public void close() throws Exception {
		rs.close();
		st.close();
		con.close();
	}
}
