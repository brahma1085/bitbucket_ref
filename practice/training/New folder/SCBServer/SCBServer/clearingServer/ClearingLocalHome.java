/*
 * Created on Feb 23, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package clearingServer;

import javax.ejb.EJBLocalHome;
import javax.ejb.CreateException;

/**
 * @author admin
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface ClearingLocalHome extends  EJBLocalHome {
	
	public ClearingLocal create() throws CreateException ;
	
	

	
}
