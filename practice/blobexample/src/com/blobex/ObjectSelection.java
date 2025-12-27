package com.blobex;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Struct;

public class ObjectSelection {
	public static void main(String[] args) throws ClassNotFoundException, SQLException, ArrayIndexOutOfBoundsException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url="jdbc:oracle:thin:@localhost:1521:xe";
		String user="system";
		String password="satya";
		Connection con=DriverManager.getConnection(url, user, password);
		if (con==null)
			System.out.println("connection failed");
		else
		{
			System.out.println("connection established");
			Statement st=con.createStatement();
			String sql="select * from student";
			ResultSet rs=st.executeQuery(sql);
			rs.next();
			Object obj=rs.getObject(3);
			System.out.println(rs.getInt(1)+"|"+rs.getString(2)+"|");				
			Struct s=(Struct)obj;
			Object x[]=s.getAttributes();
			for (int i1=0;i1<=x.length;i1++)
			System.out.println("college information="+x[i1]);
		}
	con.close();
	}
	}