/*
 * Created on Sep 30, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package exceptions;

/**
 * @author Murugesh
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PasswordExpiredException extends LoginException {

	public String toString() {
		return "Password Expired \n Contact Administarator";
	}
}
