package com.blobex;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ArrayType {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url="jdbc:oracle:thin:@localhost:1521:xe";
		String user="system";
		String password="satya";
		Connection con=DriverManager.getConnection(url, user, password);
		if(con==null)
			System.out.println("connection failed");
		else
		{
			System.out.println("connection established");
			Statement st=con.createStatement();
			String sql="insert into companies values(433,'CISCO',landline(2342,567567,6786,0909))";
			String sql1="insert into companies values(434,'SOFTSOLs',landline(6786,87687,98978,67657))";
			st.executeUpdate(sql);
			st.executeUpdate(sql1);
			System.out.println("2rows updated");
			con.close();
		}
	}
}
