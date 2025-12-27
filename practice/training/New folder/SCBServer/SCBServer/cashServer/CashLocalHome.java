/*
 * Created on Mar 21, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package cashServer;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface CashLocalHome extends EJBLocalHome
{
    public CashLocal create() throws CreateException;
}
