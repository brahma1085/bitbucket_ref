
package shareServer;

import javax.ejb.CreateException;


/**
 * Created By Karthi on Feb 23, 2006
 *
 */
public interface ShareLocalHome extends javax.ejb.EJBLocalHome
{
	public ShareLocal create() throws CreateException;
}
