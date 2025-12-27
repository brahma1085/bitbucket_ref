/*
 * Created on 11 Mar, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package customerServer;

import javax.ejb.EJBLocalHome;
/**
 * @author root
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface CustomerLocalHome extends EJBLocalHome 
{
	CustomerLocal create() throws javax.ejb.CreateException;

}
