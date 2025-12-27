/*
 * Created on Apr 20, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pygmyServer;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
/**
 * @author Riswan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface PygmyLocalHome extends EJBLocalHome {

	public PygmyLocalRemote create()throws CreateException;
}
