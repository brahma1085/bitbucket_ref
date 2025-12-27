package com.nit.struts;

public class LoginService {
	public boolean isAuthenticate(String user, String pwd) {
		boolean flag = false;
		if (user.equals(pwd))
			flag = true;
		return flag;
	}
}
