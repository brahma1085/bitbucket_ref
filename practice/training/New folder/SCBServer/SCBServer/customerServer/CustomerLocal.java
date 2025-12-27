/*
 * Created on 11 Mar, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package customerServer;


import javax.ejb.EJBLocalObject;

import masterObject.customer.CustomerMasterObject;
import exceptions.CustomerNotFoundException;

/**
 * @author root
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface CustomerLocal extends EJBLocalObject
{

	public CustomerMasterObject getCustomer(int cid)throws CustomerNotFoundException;
    
    public java.util.HashMap getAddress(int cid) throws CustomerNotFoundException;
	
}
