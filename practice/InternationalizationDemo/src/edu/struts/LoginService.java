package edu.struts;

public class LoginService {
	public boolean isAuthenticate(String user, String pwd) {
		boolean flag = false;
		if (user.equalsIgnoreCase(pwd))
			flag = true;
		return flag;
	}
}
