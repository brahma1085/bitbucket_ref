/*
 * Created on Jun 15, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package exceptions;

/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DayNotMatch extends Exception {
	public DayNotMatch(String s) {
		super(s); 
	}
	public String toString(){
		return super.getMessage();
	} 

}
