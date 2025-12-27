/*
 * Created on Feb 16, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package exceptions;

/**
 * @author admin
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GLKeyCodeNotFound extends Exception {

	private String message;
	
	public GLKeyCodeNotFound(String message) {
		this.message =  message;
	}
	
	
	public String toString()
	{
		return "GL Code not Defined";
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message =  message;
	}
}
