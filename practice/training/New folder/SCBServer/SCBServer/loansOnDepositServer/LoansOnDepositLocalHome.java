package loansOnDepositServer;


import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface LoansOnDepositLocalHome extends EJBLocalHome
{
	public LoansOnDepositLocal create() throws  CreateException;
}