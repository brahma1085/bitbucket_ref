package termDepositServer;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface TermDepositLocalHome extends EJBLocalHome {

	public TermDepositLocalRemote create ()throws CreateException;
}
