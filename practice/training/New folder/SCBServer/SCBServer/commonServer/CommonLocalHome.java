package commonServer;

import javax.ejb.CreateException;
/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface CommonLocalHome extends javax.ejb.EJBLocalHome
{
    public CommonLocal create() throws CreateException;
    
}
