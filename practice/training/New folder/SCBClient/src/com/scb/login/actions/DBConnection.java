package com.scb.login.actions;




import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
public class DBConnection {
	//protected static String db_url="jdbc:mysql://localhost/bkcbhoaug";
	protected static String db_url="jdbc:mysql://192.168.1.2/RAMNAGAR22022010";
	private static Connection con;
	static{
		try{
			System.out.println("Inside the static method");
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection(db_url , "root", "");
			
		}
		catch(ClassNotFoundException cnf){
			cnf.printStackTrace();
		}
		catch(SQLException se){
			se.printStackTrace();
		}
	}
	
	public static Connection getConnection(){
		if(con==null){
			System.out.println("---null---");
		}
			return con;
		
	}

}


