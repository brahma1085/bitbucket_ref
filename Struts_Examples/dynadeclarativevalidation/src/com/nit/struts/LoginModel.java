package com.nit.struts;

public class LoginModel {
	public boolean isAuthenticated(String username, String password) {
		boolean flag = false;
		if (username.equals(password))
			flag = true;
		return flag;
	}
}
