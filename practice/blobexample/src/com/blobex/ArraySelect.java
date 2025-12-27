 package com.blobex;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ArraySelect {
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
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
			String sql="select * from companies";
			ResultSet rs=st.executeQuery(sql);
			rs.next();
			int id=rs.getInt(1);
			String name=rs.getString(2);
			Array phones=rs.getArray(3);
			System.out.println("id="+id);
			System.out.println("name="+name);
			System.out.println("phone numbers="+phones);
			ResultSet rs1=phones.getResultSet();
			while(rs1.next())
			{
				System.out.println(rs1.getInt(2));
			}
			System.out.println("table executed");
			con.close();
		}
	}
}	