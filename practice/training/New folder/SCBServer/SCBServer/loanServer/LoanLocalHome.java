package loanServer;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface LoanLocalHome extends EJBLocalHome
{
	LoanLocal create() throws CreateException;
	

}
