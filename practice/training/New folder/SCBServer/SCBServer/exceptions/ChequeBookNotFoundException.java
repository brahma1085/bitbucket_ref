/*
 * Created on Mar 28, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package exceptions;

import javax.ejb.EJBException;


public class ChequeBookNotFoundException extends EJBException {
	public String toString()
	{
		return "Account not found";
	}

}
