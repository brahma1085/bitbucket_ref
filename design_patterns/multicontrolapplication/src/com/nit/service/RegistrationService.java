package com.nit.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.nit.model.Registration;
import com.nit.util.DBUtil;

public class RegistrationService {
	public boolean doRegistration(Registration registration) {
		boolean flag = false;
		String name = registration.getName();
		String password = registration.getPassword();
		String profession = registration.getProfession();
		String cell = registration.getCell();
		String gender = registration.getGender();
		boolean agree = registration.isAgree();
		String query = "INSERT INTO REGISTRATION  VALUES('" + name + "','"
				+ password + "','" + profession + "'," + cell + ",'" + gender
				+ "','" + agree + "')";
		Connection connection = null;
		Statement statement = null;
		try {
			connection = DBUtil.getConnection();
			statement = connection.createStatement();
			statement.executeUpdate(query);
			flag = true;
		} catch (SQLException e) {
			System.out.println("SQLException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
		}
		return flag;
	}
}
