package com.blobex;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Blobexample {
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
			String query = "insert into satyam values(?,?,?)";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, 101);
			pst.setString(2, "rama");
			File f = new File("E:/2fast.jpg");
			FileInputStream fin = new FileInputStream(f);
			pst.setBlob(3, fin, f.length());
			pst.executeUpdate();
			System.out.println("image inserted into the db");
			con.close();
		}
	}

}
