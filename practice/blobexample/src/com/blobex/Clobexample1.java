package com.blobex;

import java.sql.*;
import java.io.*;

public class Clobexample1 {
	public static void main(String[] args) throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "system";
		String password = "satya";
		Connection con = DriverManager.getConnection(url, user, password);
		if (con == null) {
			System.out.println("connection failed");
		} else {
			System.out.println("connection established");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select*from sat");
			rs.next();
			int i = rs.getInt(1);
			String s = rs.getString(2);
			System.out.println("id=" + i);
			System.out.println("name=" + s);
			Clob n = rs.getClob(3);
			System.out.println("notes=" + n);
			InputStream in = n.getAsciiStream();
			int ch = in.read();
			while (ch != -1) {
				System.out.print((char) ch);
				ch = in.read();
			}
			con.close();
		}
	}
}
