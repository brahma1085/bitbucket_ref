package com.blobex;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import oracle.sql.BLOB;

public class Blobphoto {
	public static void main(String[] args) throws SQLException,
			ClassNotFoundException, IOException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "system";
		String password = "satya";
		Connection con = DriverManager.getConnection(url, user, password);
		if (con == null) {
			System.out.println("connection failed");
		} else {
			System.out.println("connectio established");
			String sql = "select*from satyam";
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sql);
			rs.next();
			int id = rs.getInt(1);
			String name = rs.getString(2);
			BLOB photo = (BLOB) rs.getBlob(3);
			System.out.println("id=" + id);
			System.out.println("name=" + name);
			System.out.println("photo=" + photo);
			// storing that photo in a file
			InputStream in = photo.getBinaryStream();
			FileOutputStream fout = new FileOutputStream("E:/bike.jpg");
			final int EOF = -1;
			int ch = in.read();
			while (ch != EOF) {
				fout.write(ch);
				ch = in.read();
			}
			System.out.println("image inserted into E:/bike.jpg");
			in.close();
			fout.close();
			con.close();

		}
	}
}
