package com.blobex;

import java.sql.Connection;

import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import java.sql.Statement;

public class Execute {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String q = "";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		Properties p = new Properties();
		p.put("user", "system");
		p.put("password", "satya");
		try {
			Driver d = new oracle.jdbc.driver.OracleDriver();
			Connection con = null;
			try {
				con = d.connect(url, p);
				Statement st = (Statement) con.createStatement();
				if (con != null) {
					System.out.println("connection established");
					while (!q.equalsIgnoreCase("exit")) {
						System.out.println("mySQL>");
						q = sc.nextLine();
						if (!q.equalsIgnoreCase("exit"))
							if (st.execute(q))
								showResult(st.getResultSet());
							else
								System.out.println("row updated="
										+ st.getUpdateCount());
					}
				}
			} finally {
				if (con != null) {
					con.close();
					System.out.println("connection closed");
				}
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}

	private static void showResult(ResultSet rs) throws SQLException {
		System.out.println("");
		ResultSetMetaData rm = rs.getMetaData();
		int cc = rm.getColumnCount();
		for (int i = 1; i <= cc; i++)
			System.out.println("|" + rm.getCatalogName(i));
		System.out.println("");
		while (rs.next()) {
			System.out.println("");
			for (int i = 1; i <= cc; i++)
				System.out.println("|" + rs.getString(i));
		}
	}
}
