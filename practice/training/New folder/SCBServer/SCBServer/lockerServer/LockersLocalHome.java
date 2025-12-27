/*
 * Created on 11 Mar, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package lockerServer;

import javax.ejb.EJBLocalHome;
/**
 * @author root
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface LockersLocalHome extends EJBLocalHome 
{
	LockersLocal create() throws javax.ejb.CreateException;

}