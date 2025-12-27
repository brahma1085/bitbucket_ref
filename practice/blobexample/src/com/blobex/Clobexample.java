package com.blobex;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.io.*;

public class Clobexample {
	public static void main(String[] args) throws ClassNotFoundException,
			SQLException, FileNotFoundException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "system";
		String password = "satya";
		Connection con = DriverManager.getConnection(url, user, password);
		if (con == null) {
			System.out.println("connection failed");
		} else {
			System.out.println("connection established");
			String sql = "insert into sat values(?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, 102);
			ps.setString(2, "prema");
			File f = new File("E:/keys1.txt");
			FileInputStream fin = new FileInputStream(f);
			ps.setAsciiStream(3, fin, f.length());
			ps.executeUpdate();
			con.close();
		}
	}

}
