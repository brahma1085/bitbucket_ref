package com.blobex;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ExecuteBatch {
	public static void main(String[] args) throws ClassNotFoundException,
			SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(
				"jdbc:oracle:thin:@localhost:1521:xe", "system", "satya");
		if (con == null) {
			System.out.println("connection failed");
		} else {
			System.out.println("connected to db");
			Statement st = con.createStatement();
			String sql1 = "create table neo1(id int,name varchar(10),salary int)";
			String sql2 = "insert into neo1 values(10,'dasu',3432)";
			String sql3 = "insert into neo1 values(20,'basu',3453)";
			String sql4 = "insert into neo1 values(30,'vasu',4565)";
			String sql5 = "insert into neo1 values(40,'masu',5678)";
			String sql6 = "insert into neo1 values(50,'lasu',5670)";
			st.addBatch(sql1);
			st.addBatch(sql2);
			st.addBatch(sql3);
			st.addBatch(sql4);
			st.addBatch(sql5);
			st.addBatch(sql6);
			System.out.println("batch executed successfully");
			con.close();
		}
	}
}
