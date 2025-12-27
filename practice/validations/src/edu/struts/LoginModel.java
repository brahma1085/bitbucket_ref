package edu.struts;

import org.apache.commons.lang.StringUtils;

public class LoginModel {
	public boolean isAuthenticate(String user, String pass) {
		boolean flag = false;
		if (StringUtils.equalsIgnoreCase(user, pass))
			flag = true;
		return flag;
	}
}
