package com.blobex;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ObjectType {
	public static void main(String[] args) throws ClassNotFoundException,
			SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "system";
		String password = "satya";
		Connection con = DriverManager.getConnection(url, user, password);
		if (con == null)
			System.out.println("connection failed");
		else {
			System.out.println("connection established");
			Statement st = con.createStatement();
			String sql1 = "insert into student values(2,'gourav',collegeinfo(300,'JNTU'))";
			String sql2 = "insert into student values(3,'somu',collegeinfo(200,'NAGARJUNA'))";
			String sql3 = "insert into student values(4,'dasu',collegeinfo(200,'SSIT'))";
			String sql4 = "insert into student values(5,'vasu',collegeinfo(200,'GRIET'))";
			String sql5 = "insert into student values(6,'guru',collegeinfo(200,'VNR'))";
			st.executeUpdate(sql1);
			st.executeUpdate(sql2);
			st.executeUpdate(sql3);
			st.executeUpdate(sql4);
			st.executeUpdate(sql5);
			System.out.println("rows updated");
			con.close();
		}
	}
}
