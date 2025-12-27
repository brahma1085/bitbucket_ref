package generalLedgerServer;

import javax.ejb.EJBLocalHome;

public interface GLLocalHome extends EJBLocalHome{

	
	GLLocal create()throws javax.ejb.CreateException;
	
}
