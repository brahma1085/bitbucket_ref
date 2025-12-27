/*
 * Created on Mar 27, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package exceptions;

import javax.ejb.EJBException;

/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CashExceptions extends EJBException
{
    public String toString()
    {
        return "You cannot transfer money to the same terminal";
    }

}
