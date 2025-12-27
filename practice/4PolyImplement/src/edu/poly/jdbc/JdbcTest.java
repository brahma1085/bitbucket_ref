package edu.poly.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcTest {

	public static void main(String[] args) {
		try {
			// Class.forName()==>only for loading the class
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// DriverManager==>for registering the driver
			// Driver class's connect() will load the properties,URL and gives
			// to DriverInfo and gets the connecction from connect()
			// public static synchronized void registerDriver(java.sql.Driver
			// driver){
			// DriverInfo di = new DriverInfo();
			// di.driver = driver;
			// di.driverClass = driver.getClass();
			// di.driverClassName = di.driverClass.getName();}
			// A a=new B();
			Connection connection = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:XE", "system", "satya");
			// here Connection points to
			// type-I==>sun.jdbc.odbc.JdbcOdbcConnection
			// type-IV==>oracle.jdbc.driver.OracleConnection
			System.out.println("connection established using==>" + connection
					+ "==>class");
			// connection.createStatement();
		} catch (Exception e) {
			System.out.println("Exception==>" + e.getClass().getName() + "==>"
					+ e.getMessage());
		}
	}
}
