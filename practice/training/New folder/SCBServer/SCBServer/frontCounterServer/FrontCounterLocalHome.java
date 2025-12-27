/*
 * Created on Mar 8, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package frontCounterServer;
import javax.ejb.CreateException;
/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface FrontCounterLocalHome extends javax.ejb.EJBLocalHome
{
    public FrontCounterLocal create() throws CreateException;
}
