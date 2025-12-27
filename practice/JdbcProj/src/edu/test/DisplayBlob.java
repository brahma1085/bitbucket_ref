package edu.test;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DisplayBlob {

	public static void main(String[] args) {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:XE", "system", "satya");
			statement = connection.prepareStatement("select * from blobs");
			ResultSet rs = statement.executeQuery();
			rs.next();
			int id = rs.getInt(1);
			String s = rs.getString(2);
			Blob photo = rs.getBlob(3);
			System.out.println(id);
			System.out.println(s);
			System.out.println(photo);
			InputStream in = photo.getBinaryStream();
			FileOutputStream fout = new FileOutputStream("E:/car.jpg");
			final int EOF = -1;
			int ch = in.read();
			while (ch != EOF) {
				fout.write(ch);
				ch = in.read();
			}
			System.out.println("image inserted into E:/car.jpg");
			in.close();
			fout.close();
			connection.close();

		} catch (Exception e) {
			System.out.println("Exception==>" + e.getClass().getName() + "==>"
					+ e.getMessage());
		}
	}

}
