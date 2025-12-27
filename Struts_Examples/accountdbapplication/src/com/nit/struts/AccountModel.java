package com.nit.struts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountModel {
	public Account getAccount(String accno) {
		Account acc = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:XE", "system", "satya");
			ps = con.prepareStatement("SELECT * FROM ACCOUNT WHERE ACCNO=?");
			ps.setString(1, accno);
			rs = ps.executeQuery();
			while (rs.next()) {
				acc = new Account();
				acc.setAccno(accno);
				acc.setName(rs.getString(2));
				acc.setBalance(rs.getString(3));
			}
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException==>"
					+ e.getClass().getName() + "==>" + e.getMessage());
		} catch (SQLException e) {
			System.out.println("SQLException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
		} finally {
			try {
				if (con != null)
					con.close();
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				System.out.println("SQLException==>" + e.getClass().getName()
						+ "==>" + e.getMessage());
			}
		}
		return acc;
	}
}
